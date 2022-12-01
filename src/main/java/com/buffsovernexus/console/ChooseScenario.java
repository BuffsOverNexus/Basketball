package com.buffsovernexus.console;

import com.buffsovernexus.CurrentSession;
import com.buffsovernexus.Runner;
import com.buffsovernexus.database.Database;
import com.buffsovernexus.entity.Scenario;
import org.hibernate.Session;

import java.util.List;

public class ChooseScenario {

    public ChooseScenario() {

        boolean validScenarioEntered = false;

        while (!validScenarioEntered) {
            System.out.println();
            System.out.println(">>> CHOOSE OR CREATE SCENARIO");

            System.out.println("To continue, please review all of your scenarios...");
            System.out.println("Enter in the ID of the scenario to select it. (e.g. [1 - My Scenario] has an ID of 1)");
            System.out.println("Otherwise, enter in zero (0) to create a new scenario.");

            System.out.println();
            System.out.println("--------------------------");

            // Retrieve all scenarios
            Session session = Database.sessionFactory.openSession();
            session.beginTransaction();
            String query = String.format("FROM Scenario WHERE account = '%s'", CurrentSession.accountId);
            List<Scenario> scenarios = session.createQuery(query, Scenario.class).list();

            if (scenarios.size() == 0) {
                System.out.println("!NO SCENARIOS FOUND!");
            } else {
                for (Scenario scenario : scenarios) {
                    System.out.printf("[%s - %s]%n", scenario.getId(), scenario.getName());
                }
            }

            session.getTransaction().commit();
            session.close();

            // Prompt user to enter in scenario ID

            System.out.println();
            System.out.print("Enter ID or (0) to create new Scenario: ");
            int id = Runner.in.nextInt();

            if (id > 0 && scenarios.size() > 0) {
                Session selectScenarioSession = Database.sessionFactory.openSession();
                selectScenarioSession.beginTransaction();
                // Select the existing scenario
                Scenario scenario = selectScenarioSession.get(Scenario.class, (long) id);
                CurrentSession.scenarioId = scenario.getId();
                System.out.printf(" ** SELECTED SCENARIO -> %s ** %n", scenario.getName());

                selectScenarioSession.getTransaction().commit();
                selectScenarioSession.close();

                validScenarioEntered = true;
                // Continue with the scenario based on the status of the scenario.
                new ContinueScenario();
            } else if (id == 0) {
                validScenarioEntered = true;
                new CreateScenario();
            } else {
                System.out.println();
                System.out.println("Invalid Scenario! Try again...");
                System.out.println();
            }

        }


    }
}
