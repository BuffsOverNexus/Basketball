package com.buffsovernexus.console;

import com.buffsovernexus.CurrentSession;
import com.buffsovernexus.Runner;
import com.buffsovernexus.database.Database;
import com.buffsovernexus.engine.Engine;
import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.SeasonTeam;
import com.buffsovernexus.entity.Team;
import com.buffsovernexus.generators.SeasonGenerator;
import com.buffsovernexus.generators.SeasonTeamGenerator;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SeasonMenu {

    private boolean closeMenu = false;

    public SeasonMenu() {
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
            switch (option) {
                default:
                case 'q':
                    closeMenu = true;
                    break;
                case 'g':
                    Game game = nextGame(session);
                    Engine engine = Engine.builder().session(session).game(game).build();
                    engine.generateGame();
                    break;
                case 'v':
                    break;
                case 'r':
                    Game nextGame = nextGame(session);
                    if (nextGame != null) {
                        String home = nextGame.getHome().getName();
                        String away = nextGame.getAway().getName();
                        System.out.println();
                        System.out.println( String.format("-- %s vs %s --", home, away) );
                    } else {
                        System.out.println("You have no pending games left to play. Try continuing to post season or viewing standings");
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
                        closeMenu = true;
                        new PostSeasonMenu();
                    }
                    break;
            }
            session.close();
        }
    }

    // Helper Method - Verify that all games have been generated.
    private void verifyGamesGenerated() {
        Session session = Database.sessionFactory.openSession();
        session.beginTransaction();

        List<Game> seasonGames = session.createQuery( String.format("FROM Game WHERE season_id='%s'", CurrentSession.season_id), Game.class ).list();

        if (seasonGames.size() == 0) {
            System.out.println();
            System.out.println("GENERATING GAMES FOR SEASON");
            System.out.println("This may take a bit...");
            SeasonGenerator.generateGames();
        }

        session.getTransaction().commit();
        session.close();
    }

    /***
     * Ensure that all games have been played and a winner has been awarded.
     * @return
     */
    private boolean verifyAllGamesPlayed() {
        Session session = Database.sessionFactory.openSession();
        session.beginTransaction();

        // Grab all season games
        List<Game> seasonGames = session.createQuery( String.format("FROM Game WHERE season_id='%s'", CurrentSession.season_id), Game.class ).list();

        for (Game game : seasonGames) {
            if (!game.hasWinner()) {
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
        List<SeasonTeam> seasonTeams = session.createQuery(String.format("FROM SeasonTeam WHERE season_id='%s' AND scenario_id='%s'", CurrentSession.season_id, CurrentSession.scenario_id), SeasonTeam.class).list();
        if (seasonTeams.isEmpty()) {
            SeasonTeamGenerator.generateSeasonTeams();
        }
        transaction.commit();
        session.close();
    }

    private Game nextGame(Session session) {
        List<Game> seasonGames = session.createQuery( String.format("FROM Game WHERE season_id='%s' ORDER BY id ASC", CurrentSession.season_id), Game.class ).list();

        for (Game game : seasonGames) {
            if (!game.hasWinner()) {
                return game;
            }
        }
        return null;
    }

    private void getStandings(Session session) {
        List<SeasonTeam> seasonTeams = session.createQuery( String.format("FROM SeasonTeam WHERE season_id = '%s'", CurrentSession.season_id), SeasonTeam.class).list();
        System.out.println("-- STANDINGS --");
        seasonTeams.forEach(seasonTeam -> {
             System.out.println(String.format("%s | %s - %s", seasonTeam.getTeam().getName(), seasonTeam.getWins(), seasonTeam.getLosses() ));
        });
        System.out.println("---------------");
    }


}
