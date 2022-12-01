package com.buffsovernexus.generators;

import com.buffsovernexus.CurrentSession;
import com.buffsovernexus.database.Database;
import com.buffsovernexus.entity.Player;
import com.buffsovernexus.entity.Scenario;
import com.buffsovernexus.entity.Team;
import com.buffsovernexus.enums.PlayerPosition;
import org.hibernate.Session;

public class TeamGenerator {

    private final String[] teamNames = new String[] {
            "Eagles", "Tigers", "Bulldogs", "Panthers", "Cougars", "Warriors", "Wildcats"
    };

    public static Team generateTeam(String name, boolean isUserTeam) {
        Session session = Database.sessionFactory.openSession();
        Player guard = PlayerGenerator.generatePlayer(session, PlayerPosition.GUARD);
        Player forward = PlayerGenerator.generatePlayer(session, PlayerPosition.FORWARD);

        session.beginTransaction();

        Scenario scenario = session.get(Scenario.class, CurrentSession.scenarioId);

        Team team = new Team();
        team.setGuard(guard);
        team.setForward(forward);
        team.setUserTeam(isUserTeam);
        team.setScenario(scenario);
        team.setName(name);

        session.persist(team);

        session.getTransaction().commit();
        session.close();


        return team;
    }
}
