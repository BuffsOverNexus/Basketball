package com.buffsovernexus.engine;

import com.buffsovernexus.GameSettings;
import com.buffsovernexus.database.Database;
import com.buffsovernexus.engine.entity.PlayerMove;
import com.buffsovernexus.engine.enums.Possession;
import com.buffsovernexus.engine.event.game.GameEndEvent;
import com.buffsovernexus.engine.event.game.GameStartEvent;
import com.buffsovernexus.engine.event.game.PossessionChangeEvent;
import com.buffsovernexus.engine.helpers.Calculator;
import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.Player;
import com.buffsovernexus.entity.Team;
import lombok.Builder;
import lombok.Data;
import org.hibernate.Session;

import java.security.Guard;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Data
@Builder
public class Engine {

    private Game game;
    private Possession possession = Possession.HOME;
    private List<EngineListener> eventHandlers;
    private boolean hasWinner = false;
    private boolean logging = false;

    public void generateGame() {
        Session session = Database.sessionFactory.openSession();

        // Declare start of the game
        GameStartEvent gameStartEvent = GameStartEvent.builder().game(game).startingPossession(possession)
                .teamWithPossession( getOffense() ).build();
        eventHandlers.stream().forEach(eventHandler -> eventHandler.onGameStartEvent(gameStartEvent));

        // Ensure the game does not end while there has not been declared a winner.
        while (!game.hasWinner()) {

            // Step 1: Guard next action
            startPossession();

            // Change possession automatically
            if (!hasWinner) {
                swapPossession();
            } else {
                GameEndEvent gameEndEvent = GameEndEvent.builder().game(game).winner(game.getWinner()).loser(game.getLoser()).winningScore(game.getWinnerScore()).losingScore(game.getLoserScore()).build();
                eventHandlers.stream().forEach(eventHandler -> eventHandler.onGameEndEvent(gameEndEvent));
            }

        }

        session.close();

    }

    /**
     * Start a possession
     */
    private void startPossession() {
        PossessionChangeEvent possessionChangeEvent = PossessionChangeEvent.builder()
                .possession(possession)
                .game(game)
                .offense(getOffense())
                .defense(getDefense())
                .build();
        eventHandlers.stream().forEach(eventHandler -> eventHandler.onPossessionChangeEvent(possessionChangeEvent));

        guardPhase();
    }

    /**
     * Declare a guard's phase
     */
    private void guardPhase() {
        // Logic - Attempt a four pointer if 4 > 2.
        Player guard = getGuard();
        if (guard.getFourPointer() > guard.getTwoPointer()) {

        } else {

        }
    }

    private void forwardPhase() {

    }

    private void reboundPhase() {

    }

    private void passingPhase() {

    }

    private void shootingPhase() {

    }

    private void swapPossession() {
        if (possession == Possession.AWAY)
            possession = Possession.HOME;
        else
            possession = Possession.AWAY;
    }

    /**
     * Return the Guard who has possession of the ball
     * @return
     */
    private Player getGuard() {
        return possession == Possession.HOME ? game.getHome().getGuard() : game.getAway().getGuard();
    }

    /**
     * Return the Forward who has possession of the ball
     * @return
     */
    private Player getForward() {
        return possession == Possession.HOME ? game.getHome().getForward() : game.getAway().getForward();
    }

    /**
     * Get the Guard who does not have possession of the ball
     * @return
     */
    private Player getOpposingGuard() {
        return possession == Possession.AWAY ? game.getHome().getGuard() : game.getAway().getGuard();
    }
    /**
     * Get the Forward who does not have possession of the ball
     * @return
     */
    private Player getOpposingForward() {
        return possession == Possession.AWAY ? game.getHome().getForward() : game.getAway().getForward();
    }

    /**
     * Determine if the home has the ball
     * @return
     */
    private boolean doesHomeHavePossession() {
        return possession == Possession.HOME;
    }

    /**
     * Gather the team that is attempting to score.
     * @return - The team with the ball
     */
    private Team getOffense() {
        return doesHomeHavePossession() ? game.getHome() : game.getAway();
    }

    /**
     * The team that is attempting to prevent the offense from scoring.
     * @return - The team defending
     */
    private Team getDefense() {
        return doesHomeHavePossession() ? game.getAway() : game.getHome();
    }

}
