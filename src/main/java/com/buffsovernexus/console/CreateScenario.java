package com.buffsovernexus.console;

import com.buffsovernexus.CurrentSession;
import com.buffsovernexus.Runner;
import com.buffsovernexus.database.Database;
import com.buffsovernexus.entity.Account;
import com.buffsovernexus.entity.Scenario;
import com.buffsovernexus.enums.ScenarioStatus;
import org.hibernate.Session;

public class CreateScenario {

    public CreateScenario() {

        System.out.println();
        System.out.println(">> CREATE NEW SCENARIO");
        System.out.println();
        System.out.print("Scenario Name: ");
        String name = Runner.in.next();
        System.out.println();

        // Create a session based on the name
        Session session = Database.sessionFactory.openSession();
        session.beginTransaction();

        // Acquire Account
        Account account = session
                .createQuery(
                        String.format("FROM Account WHERE id='%s'", CurrentSession.accountId),
                        Account.class).uniqueResult();
        Scenario scenario = new Scenario();
        scenario.setScenarioStatus(ScenarioStatus.SETUP);
        scenario.setAccount(account);
        scenario.setName(name);

        session.persist(scenario);
        CurrentSession.scenarioId = scenario.getId();

        session.getTransaction().commit();
        session.close();



        System.out.printf("** SUCCESS ** Created New Scenario: %s%n", name.toUpperCase());
        new ContinueScenario();
    }
}
