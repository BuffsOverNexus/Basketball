package com.buffsovernexus.generators;

import com.buffsovernexus.CurrentSession;
import com.buffsovernexus.database.Database;
import com.buffsovernexus.entity.Scenario;
import com.buffsovernexus.entity.Season;
import com.buffsovernexus.entity.SeasonTeam;
import com.buffsovernexus.entity.Team;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SeasonTeamGenerator {

    public static void generateSeasonTeams() {
        Session session = Database.sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            List<Team> teams = session.createQuery(String.format("FROM Team WHERE scenario = %s", CurrentSession.scenarioId), Team.class).list();
            Scenario scenario = session.createQuery(String.format("FROM Scenario WHERE id='%s'", CurrentSession.scenarioId), Scenario.class).uniqueResult();
            Season season = session.createQuery(String.format("FROM Season WHERE id='%s'", CurrentSession.seasonId), Season.class).uniqueResult();

            teams.forEach(team -> {
               SeasonTeam seasonTeam = new SeasonTeam();
               seasonTeam.setTeam(team);
               seasonTeam.setScenario(scenario);
               seasonTeam.setSeason(season);
               seasonTeam.setWins(0);
               seasonTeam.setLosses(0);
               session.persist(seasonTeam);
            });

            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        session.close();
    }
}
