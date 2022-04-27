package com.buffsovernexus.engine.handlers;

import com.buffsovernexus.database.Database;
import com.buffsovernexus.engine.EngineEventHandler;
import com.buffsovernexus.engine.enums.PlayerScoreType;
import com.buffsovernexus.engine.enums.Possession;
import com.buffsovernexus.engine.event.player.PlayerShotScoredEvent;
import com.buffsovernexus.entity.Performance;
import com.buffsovernexus.enums.PlayerPosition;
import org.hibernate.Session;

public class SavePointsToRecordHandler extends EngineEventHandler {

    @Override
    public void onPlayerShotScoredEvent(PlayerShotScoredEvent event) {
        Session session = Database.sessionFactory.openSession();
        session.beginTransaction();

        Performance gamePerformance;

        if (event.getScorer().getPosition() == PlayerPosition.GUARD)
            gamePerformance = (event.getPossession() == Possession.HOME) ?
                    event.getGame().getHomeGuardPerformance() : event.getGame().getAwayGuardPerformance();
        else
            gamePerformance = (event.getPossession() == Possession.HOME) ?
                    event.getGame().getHomeForwardPerformance() : event.getGame().getAwayForwardPerformance();

        // Add points to the record
        gamePerformance.addPoints( event.getPlayerScoreType().getAmount() );

        if (event.getPlayerScoreType() == PlayerScoreType.FOUR_POINTER) {
            gamePerformance.addAttemptedFourPointers(1);
            gamePerformance.addFourPointers(1);
        } else {
            gamePerformance.addAttemptedTwoPointers(1);
            gamePerformance.addTwoPointers(1);
        }

        session.getTransaction().commit();
        session.close();
        System.out.println("Points Scored: " + event.getPlayerScoreType().getAmount());
    }
}
