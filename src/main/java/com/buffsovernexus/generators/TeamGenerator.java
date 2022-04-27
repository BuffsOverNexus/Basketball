package com.buffsovernexus.generators;

import com.buffsovernexus.CurrentSession;
import com.buffsovernexus.database.Database;
import com.buffsovernexus.entity.Player;
import com.buffsovernexus.entity.Scenario;
import com.buffsovernexus.entity.Team;
import com.buffsovernexus.enums.PlayerPosition;
import org.hibernate.Session;

public class TeamGenerator {

    private String[] teamNames = new String[] {
            "Eagles", "Tigers", "Bulldogs", "Panthers", "Cougars", "Warriors", "Wildcats"
    };

    public static Team generateTeam(String name, boolean isUserTeam) {
        Player guard = PlayerGenerator.generatePlayer(PlayerPosition.GUARD);
        Player forward = PlayerGenerator.generatePlayer(PlayerPosition.FORWARD);

        Session session = Database.sessionFactory.openSession();
        session.beginTransaction();

        Scenario scenario = session.get(Scenario.class, CurrentSession.scenario_id);

        Team team = new Team();
        team.setGuard(guard);
        team.setForward(forward);
        team.setUserTeam(isUserTeam);
        team.setScenario(scenario);
        team.setName(name);

        session.save(team);

        session.getTransaction().commit();
        session.close();


        return team;
    }
}
