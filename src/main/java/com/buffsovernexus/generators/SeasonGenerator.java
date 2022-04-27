package com.buffsovernexus.generators;

import com.buffsovernexus.CurrentSession;
import com.buffsovernexus.GameSettings;
import com.buffsovernexus.database.Database;
import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.Performance;
import com.buffsovernexus.entity.Season;
import com.buffsovernexus.entity.Team;
import org.hibernate.Session;

import java.util.List;

public class SeasonGenerator {


    public static void generateGames() {
        // Goal: Generate games based on TOTAL_GAMES_AGAINST
        // Past Issues: Teams were playing games unevenly.
        Session session = Database.sessionFactory.openSession();
        session.beginTransaction();

        // Grab the Season
        Season season = session.get(Season.class, CurrentSession.season_id);

        // Grab all teams based on the scenario.
        List<Team> teams =
                session.createQuery(
                        String.format("FROM Team WHERE scenario_id = '%s'", CurrentSession.scenario_id),
                        Team.class).list();

        // O(N3) is horrible, but the easiest way to code it right now...
        for (int i = 0; i < GameSettings.TOTAL_GAMES_AGAINST; i++) {
            for (Team team : teams) {
                for (Team against : teams) {
                    // Teams cannot play each other...
                    if (team.getId() != against.getId()) {
                        // Create the Game
                        Game game = new Game();
                        game.setHome(team);
                        game.setAway(against);
                        // Create the Performances
                        Performance homeGuardPerformance = new Performance();
                        Performance awayGuardPerformance = new Performance();
                        Performance homeForwardPerformance = new Performance();
                        Performance awayForwardPerformance = new Performance();
                        homeGuardPerformance.setGame(game);
                        homeGuardPerformance.setPlayer(team.getGuard());
                        awayGuardPerformance.setGame(game);
                        awayGuardPerformance.setPlayer(against.getGuard());
                        homeForwardPerformance.setGame(game);
                        homeForwardPerformance.setPlayer(team.getForward());
                        awayForwardPerformance.setGame(game);
                        awayForwardPerformance.setPlayer(against.getForward());
                        // Add performances to the game.
                        game.setHomeGuardPerformance(homeGuardPerformance);
                        game.setAwayGuardPerformance(awayGuardPerformance);
                        game.setHomeForwardPerformance(homeForwardPerformance);
                        game.setAwayForwardPerformance(awayForwardPerformance);
                        game.setSeason(season);

                        // Add game to the list
                        session.save(game);

                    }
                }
            }
        }
        session.getTransaction().commit();
        session.close();
    }
}
