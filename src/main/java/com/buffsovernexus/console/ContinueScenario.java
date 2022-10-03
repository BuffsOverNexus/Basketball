package com.buffsovernexus.console;

import com.buffsovernexus.CurrentSession;
import com.buffsovernexus.database.Database;
import com.buffsovernexus.entity.Scenario;
import com.buffsovernexus.entity.Season;
import org.hibernate.Session;

public class ContinueScenario {

    public ContinueScenario() {

        System.out.println();
        System.out.println(">> CONTINUE SCENARIO");
        System.out.println();

        System.out.println("Please wait as the system continues with your scenario.");
        Session session = Database.sessionFactory.openSession();
        session.beginTransaction();
        Scenario scenario = session.get(Scenario.class, CurrentSession.scenario_id);
        switch (scenario.getScenarioStatus()) {
            // Brand new scenario, generate the scenario.
            case SETUP:
                session.getTransaction().commit();
                session.close();
                new GenerateScenarioMenu();
                break;
            // Continue or create a season.
            case SEASON:
                session.getTransaction().commit();
                session.close();
                selectMostRecentSeason();
                new SeasonMenu();
                break;
            case POST_SEASON:
                session.getTransaction().commit();
                session.close();
                new PostSeasonMenu();
                break;
        }
    }

    private void selectMostRecentSeason() {
        Session session = Database.sessionFactory.openSession();
        session.beginTransaction();
        Season mostRecentSeason = session.createQuery(
                String.format("FROM Season WHERE scenario_id = '%s' ORDER BY id DESC", CurrentSession.scenario_id) ,
                Season.class).uniqueResult();
        CurrentSession.season_id = mostRecentSeason.getId();
        session.getTransaction().commit();
        session.close();
    }
}
