package com.buffsovernexus.generators;

import com.buffsovernexus.CurrentSession;
import com.buffsovernexus.GameSettings;
import com.buffsovernexus.database.Database;
import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.Season;
import com.buffsovernexus.entity.SeasonTeam;
import com.buffsovernexus.entity.Team;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SeasonGenerator {


    public static void generateGames(Session session) {
        // Goal: Generate games based on TOTAL_GAMES_AGAINST
        // Past Issues: Teams were playing games unevenly.
        Transaction transaction = session.beginTransaction();

        // Grab the Season
        Season season = session.get(Season.class, CurrentSession.seasonId);

        // Grab all teams based on the scenario.
        List<Team> teams =
                session.createQuery(
                        String.format("FROM Team WHERE scenario = '%s'", CurrentSession.scenarioId),
                        Team.class).list();

        // O(N3) is horrible, but the easiest way to code it right now...
        for (int i = 0; i < GameSettings.TOTAL_GAMES_AGAINST; i++) {
            for (Team team : teams) {
                for (Team against : teams) {
                    // Teams cannot play each other...
                    if (team.getId().equals(against.getId())) {
                        // Create the Game
                        Game game = new Game();
                        game.setHome(team);
                        game.setAway(against);
//                        // Create the Performances
//                        Performance homeGuardPerformance = new Performance();
//                        Performance awayGuardPerformance = new Performance();
//                        Performance homeForwardPerformance = new Performance();
//                        Performance awayForwardPerformance = new Performance();
//                        // Add performances to the game.
//                        game.setHomeGuardPerformance(homeGuardPerformance);
//                        game.setAwayGuardPerformance(awayGuardPerformance);
//                        game.setHomeForwardPerformance(homeForwardPerformance);
//                        game.setAwayForwardPerformance(awayForwardPerformance);
                        game.setSeason(season);

                        // Add game to the list
                        session.persist(game);

                    }
                }
            }
        }
        session.getTransaction().commit();
    }

    public static Game generateTieBreakerGame(Session session, Team home, Team away) {
        Game game = new Game();
        Transaction transaction = session.beginTransaction();
        game.setHome(home);
        game.setAway(away);
        Season season = session.get(Season.class, CurrentSession.seasonId);
        game.setSeason(season);
        game.setTieBreaker(true); // This is a tiebreaker game
        session.persist(game);
        transaction.commit();
        return game;
    }
}
