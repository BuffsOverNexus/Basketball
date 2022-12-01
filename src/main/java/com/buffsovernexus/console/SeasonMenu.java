package com.buffsovernexus.console;

import com.buffsovernexus.CurrentSession;
import com.buffsovernexus.Runner;
import com.buffsovernexus.database.Database;
import com.buffsovernexus.engine.Engine;
import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.Scenario;
import com.buffsovernexus.entity.SeasonTeam;
import com.buffsovernexus.entity.Team;
import com.buffsovernexus.enums.ScenarioStatus;
import com.buffsovernexus.generators.PostSeasonGenerator;
import com.buffsovernexus.generators.SeasonGenerator;
import com.buffsovernexus.generators.SeasonTeamGenerator;
import com.buffsovernexus.utility.Percentage;
import com.buffsovernexus.utility.Sorting;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Console;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class SeasonMenu {

    public SeasonMenu() {
        boolean closeMenu = false;
        System.out.println();
        System.out.println();
        System.out.println(">>> SEASON MENU");

        // Verify the integrity of the games of the season.
        verifyGamesGenerated();

        // Verify if all teams have a generated assocation for standings purposes.
        verifyIfStandingsGenerated();

        while (!closeMenu) {
            System.out.println("Please choose an option: ");
            System.out.println("(g) - Simulate next game.");
            System.out.println("(a) - Generate all games.");
            System.out.println("(v) - Watch next game.");
            System.out.println("(r) - Review next game.");
            System.out.println("(s) - View standings.");
            System.out.println("(c) - Continue to Post Season.");
            System.out.println("(q) - Quit the game.");
            System.out.println();
            System.out.print("Selection: ");
            char option = Runner.in.next().trim().toLowerCase().toCharArray()[0];
            Session session = Database.sessionFactory.openSession();
            Game game = nextGame(session);
            Engine engine = Engine.builder().session(session).game(game).logging(true).build();
            switch (option) {
                default:
                case 'q':
                    closeMenu = true;
                    break;
                case 'g':
                    engine.setLogging(false);
                    engine.generateGame();
                    break;
                case 'v':
                    engine.generateGame();
                    break;
                case 'r':
                    Game nextGame = nextGame(session);
                    if (nextGame != null) {
                        getReview(session, nextGame);
                    } else {
                        System.out.println("You have no pending games left to play. Press (c) to continue...");
                    }
                    System.out.println();
                    break;
                case 's':
                    getStandings(session);
                    break;
                case 'c':
                    if (!verifyAllGamesPlayed()) {
                        System.out.println("ERROR: You must play all games before continuing.");
                    } else {
                        solveTieBreakers(session);
                        updateScenarioToPostSeason(session);
                        closeMenu = true;
                        new PostSeasonMenu();
                    }
                    break;
                case 'a':
                    List<Game> unFinishedGames = getUnfinishedGames(session);
                    int totalGames = unFinishedGames.size();
                    if (totalGames == 0)
                        System.out.println("No games left to run.");
                    for (int i = 0; i < totalGames; i++) {
                        Engine.builder().session(session).game(unFinishedGames.get(i)).logging(false).build().generateGame();
                        System.out.printf("Finished %s of %s%n", i + 1, totalGames + 1);
                    }
                    break;
            }
            session.close();
        }
    }

    private void updateScenarioToPostSeason(Session session) {
        // Update scenario to proper status
        Transaction transaction = session.beginTransaction();
        Scenario scenario = session.createQuery(String.format("FROM Scenario WHERE id = '%s'", CurrentSession.scenarioId), Scenario.class).uniqueResult();
        scenario.setScenarioStatus(ScenarioStatus.POST_SEASON);
        session.merge(scenario);
        transaction.commit();
        session.close();
    }

    // Helper Method - Verify that all games have been generated.
    private void verifyGamesGenerated() {
        Session session = Database.sessionFactory.openSession();

        List<Game> seasonGames = session.createQuery( String.format("FROM Game WHERE season ='%s'", CurrentSession.seasonId), Game.class ).list();

        if (seasonGames.size() == 0) {
            System.out.println();
            System.out.println("GENERATING GAMES FOR SEASON");
            System.out.println("This may take a bit...");
            SeasonGenerator.generateGames(session);
        }

        session.close();
    }

    /***
     * Ensure that all games have been played and a winner has been awarded.
     * @return true/false depending on if all games have been played in a season
     */
    private boolean verifyAllGamesPlayed() {
        Session session = Database.sessionFactory.openSession();
        session.beginTransaction();

        // Grab all season games
        List<Game> seasonGames = session.createQuery( String.format("FROM Game WHERE season ='%s'", CurrentSession.seasonId), Game.class ).list();

        for (Game game : seasonGames) {
            if (game.hasWinner()) {
                session.getTransaction().commit();
                session.close();
                return false;
            }
        }
        session.getTransaction().commit();
        session.close();
        return true;
    }

    private void verifyIfStandingsGenerated() {
        Session session = Database.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<SeasonTeam> seasonTeams = session.createQuery(String.format("FROM SeasonTeam WHERE season ='%s' AND scenario ='%s'", CurrentSession.seasonId, CurrentSession.scenarioId), SeasonTeam.class).list();
        if (seasonTeams.isEmpty()) {
            SeasonTeamGenerator.generateSeasonTeams();
        }
        transaction.commit();
        session.close();
    }

    private Game nextGame(Session session) {
        List<Game> seasonGames = session.createQuery( String.format("FROM Game WHERE season ='%s' ORDER BY id ASC", CurrentSession.seasonId), Game.class ).list();

        for (Game game : seasonGames) {
            if (game.hasWinner()) {
                return game;
            }
        }
        return null;
    }

    private void getStandings(Session session) {
        List<SeasonTeam> seasonTeams = session.createQuery( String.format("FROM SeasonTeam WHERE season = '%s'", CurrentSession.seasonId), SeasonTeam.class).list();

        // Attempt to sort based on wins. Otherwise, it should not care.
        Sorting.sortSeason(seasonTeams);
        System.out.println("-- STANDINGS --");
        for (int i = 0; i < seasonTeams.size(); i++) {
            SeasonTeam seasonTeam = seasonTeams.get(i);
            System.out.printf("[%s] %s | %s-%s (%s)%n", (i + 1), seasonTeam.getTeam().getName(), seasonTeam.getWins(), seasonTeam.getLosses(), seasonTeam.getPrettyPercentage() );
        }
        System.out.println("---------------");
    }

    private List<Game> getUnfinishedGames(Session session) {
        List<Game> seasonGames = session.createQuery( String.format("FROM Game WHERE season ='%s' ORDER BY id ASC", CurrentSession.seasonId), Game.class ).list();
        List<Game> unplayedGames = new ArrayList<>();

        for (Game game : seasonGames) {
            if (game.hasWinner()) {
                unplayedGames.add(game);
            }
        }
        return unplayedGames;
    }

    private void getReview(Session session, Game game) {
        SeasonTeam homeSeasonTeam = session.createQuery(
                    String.format("FROM SeasonTeam WHERE scenario = '%s' and season = '%s' and team = '%s'",
                            CurrentSession.scenarioId,
                            CurrentSession.seasonId,
                            game.getHome().getId()
                ), SeasonTeam.class).uniqueResult();
        SeasonTeam awaySeasonTeam = session.createQuery(
                String.format("FROM SeasonTeam WHERE scenario = '%s' and season = '%s' and team = '%s'",
                        CurrentSession.scenarioId,
                        CurrentSession.seasonId,
                        game.getAway().getId()
                ), SeasonTeam.class).uniqueResult();
        System.out.println();
        System.out.printf("Home >> %s (%s-%s)%n", game.getHome().getName(), homeSeasonTeam.getWins(), homeSeasonTeam.getLosses());
        System.out.printf("Away >> %s (%s-%s)%n", game.getAway().getName(), awaySeasonTeam.getWins(), awaySeasonTeam.getLosses());
    }

    private void solveTieBreakers(Session session) {
        try {
            // First, gather all teams based on wins
            List<SeasonTeam> seasonTeams = session.createQuery( String.format("FROM SeasonTeam WHERE season = '%s'", CurrentSession.seasonId), SeasonTeam.class).list();
            playAllTieBreakers(session, seasonTeams);

            // Update to reflect newly played games.
            seasonTeams = session.createQuery( String.format("FROM SeasonTeam WHERE season = '%s'", CurrentSession.seasonId), SeasonTeam.class).list();

            // Sort the teams based on wins.
            Sorting.sortSeason(seasonTeams);

            for (int i = 0; i < seasonTeams.size(); i++) {
                SeasonTeam seasonTeam = seasonTeams.get(i);
                System.out.printf("[%s] %s - (%s - %s) %s%n",
                        (i + 1),
                        seasonTeam.getTeam().getName(),
                        seasonTeam.getWins(),
                        seasonTeam.getLosses(),
                        seasonTeam.getPrettyPercentage());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void playAllTieBreakers(Session session, List<SeasonTeam> seasonTeams) {
        Map<Long, Set<Long>> tieBreakers = new HashMap<>();
        for (SeasonTeam homeSeasonTeam : seasonTeams) {
            for (SeasonTeam awaySeasonTeam: seasonTeams) {

                Team home = homeSeasonTeam.getTeam();
                Team away = awaySeasonTeam.getTeam();

                // Do not let the same team play itself.
                if (home.getId().equals(away.getId()))
                    continue;

                // Determine if losses are equal
                if (homeSeasonTeam.getLosses() != awaySeasonTeam.getLosses())
                    continue;

                // Determine if they have already played. If they have, skip it.
                if (tieBreakers.containsKey(away.getId()))
                    if (tieBreakers.get(away.getId()).contains(home.getId()))
                        continue;

                // Determine if there needs to be a tiebreaker between the team.
                if (homeSeasonTeam.getWins() == awaySeasonTeam.getWins()) {
                    System.out.printf("Tiebreaker >> %s (%s-%s) vs %s (%s-%s)",
                            home.getName(),
                            homeSeasonTeam.getWins(),
                            homeSeasonTeam.getLosses(),
                            away.getName(),
                            awaySeasonTeam.getWins(),
                            awaySeasonTeam.getLosses());
                    Game game = SeasonGenerator.generateTieBreakerGame(session, home, away);
                    Engine engine = Engine.builder().session(session).game(game).logging(false).build();
                    engine.generateGame();
                    System.out.printf(" --> Winner: %s <%s to %s>%n",
                            game.getWinner().getName(),
                            game.getScore(game.getWinner()),
                            game.getScore(game.getLoser()));

                    // Record that [away] played against [home]
                    if (tieBreakers.containsKey(home.getId())) {
                        tieBreakers.get(home.getId()).add(away.getId());
                    } else {
                        tieBreakers.put(home.getId(), new HashSet<>(List.of(away.getId())));
                    }
                }
            }
        }
    }


}
