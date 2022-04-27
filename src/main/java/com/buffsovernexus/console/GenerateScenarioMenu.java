package com.buffsovernexus.console;

import com.buffsovernexus.CurrentSession;
import com.buffsovernexus.GameSettings;
import com.buffsovernexus.database.Database;
import com.buffsovernexus.entity.Scenario;
import com.buffsovernexus.entity.Season;
import com.buffsovernexus.entity.Team;
import com.buffsovernexus.enums.ScenarioStatus;
import com.buffsovernexus.generators.TeamGenerator;
import org.hibernate.Session;

public class GenerateScenarioMenu {

    public GenerateScenarioMenu() {
        // Retrieve all entities needed for team generation
        Session session = Database.sessionFactory.openSession();
        session.beginTransaction();

        Scenario scenario = session.get(Scenario.class, CurrentSession.scenario_id);
        String scenarioName = scenario.getName();

        session.getTransaction().commit();

        // All team and player generations are handled within their own session and transaction.
        String[] teamNames = new String[] {
                "Eagles", "Tigers", "Bulldogs", "Panthers", "Cougars", "Warriors", "Wildcats"
        };

        // Generate the number of teams based on the config.
        // TODO: Add more team names to handle more teams in the future.
        for (int i = 0; i < GameSettings.TOTAL_TEAMS - 1; i++) {
            System.out.println( String.format("Generating Team: %s", teamNames[i]) );
            TeamGenerator.generateTeam(teamNames[i], false);
        }
        // Generate the user's team.
        Team userTeam = TeamGenerator.generateTeam(scenarioName, true);
        // Alert user their team has been generated
        System.out.println( String.format("Generating Team: %s", userTeam.getName()) );

        session.beginTransaction();

        // Create a new season, too.
        Season season = new Season();
        season.setYear(1); // Always will be the first year when you start a season.
        season.setScenario(scenario);
        session.save(season);

        session.getTransaction().commit();


        // Start new transaction
        session.beginTransaction();
        session.update(season);

        // Bug - Make sure to update season_id
        CurrentSession.season_id = season.getId();
        scenario.setScenarioStatus(ScenarioStatus.SEASON);
        session.save(scenario);

        session.getTransaction().commit();
        session.close();

        new SeasonMenu();
    }

}
