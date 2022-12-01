package com.buffsovernexus.console;

import com.buffsovernexus.CurrentSession;
import com.buffsovernexus.database.Database;
import com.buffsovernexus.entity.PostSeason;
import com.buffsovernexus.entity.Scenario;
import com.buffsovernexus.entity.Season;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ContinueScenario {

    public ContinueScenario() {

        System.out.println();
        System.out.println(">> CONTINUE SCENARIO");
        System.out.println();

        System.out.println("Please wait as the system continues with your scenario.");
        Session session = Database.sessionFactory.openSession();
        session.beginTransaction();
        Scenario scenario = session.get(Scenario.class, CurrentSession.scenarioId);
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
                selectMostRecentPostSeason();
                new PostSeasonMenu();
                break;
        }
    }

    private void selectMostRecentPostSeason() {
        Session session = Database.sessionFactory.openSession();
        PostSeason mostRecentPostSeason = session.createQuery(
                String.format("FROM PostSeason WHERE scenario = '%s' ORDER BY id DESC", CurrentSession.scenarioId),
                PostSeason.class).uniqueResult();
        CurrentSession.seasonId = mostRecentPostSeason.getSeason().getId();
        CurrentSession.postSeasonId = mostRecentPostSeason.getId();
        session.close();
    }

    private void selectMostRecentSeason() {
        Session session = Database.sessionFactory.openSession();
        Season mostRecentSeason = session.createQuery(
                String.format("FROM Season WHERE scenario = '%s' ORDER BY id DESC", CurrentSession.scenarioId) ,
                Season.class).uniqueResult();
        CurrentSession.seasonId = mostRecentSeason.getId();
        session.close();
    }
}
