package com.buffsovernexus.engine.handlers.essential;

import com.buffsovernexus.CurrentSession;
import com.buffsovernexus.database.Database;
import com.buffsovernexus.engine.EngineEventHandler;
import com.buffsovernexus.engine.event.game.GameEndEvent;
import com.buffsovernexus.entity.SeasonTeam;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EndGameRecordTeamsHandler extends EngineEventHandler {

    @Override
    public void onGameEndEvent(GameEndEvent event) {
        try {
            Session session = Database.sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            SeasonTeam winningTeam = session.createQuery(
                    String.format("FROM SeasonTeam WHERE scenario = '%s' and season ='%s' and team = '%s'", CurrentSession.scenarioId, CurrentSession.seasonId, event.getWinner().getId()),
                    SeasonTeam.class).uniqueResult();
            SeasonTeam losingTeam = session.createQuery(
                    String.format("FROM SeasonTeam WHERE scenario = '%s' and season ='%s' and team = '%s'", CurrentSession.scenarioId, CurrentSession.seasonId, event.getLoser().getId()),
                    SeasonTeam.class).uniqueResult();

            winningTeam.setWins(winningTeam.getWins() + 1);
            losingTeam.setLosses(losingTeam.getLosses() + 1);

            session.persist(winningTeam);
            session.persist(losingTeam);
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
