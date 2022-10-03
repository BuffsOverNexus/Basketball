package com.buffsovernexus.engine;

import com.buffsovernexus.GameSettings;
import com.buffsovernexus.database.Database;
import com.buffsovernexus.engine.entity.PlayerMove;
import com.buffsovernexus.engine.enums.Possession;
import com.buffsovernexus.engine.event.game.GameEndEvent;
import com.buffsovernexus.engine.event.game.GameStartEvent;
import com.buffsovernexus.engine.helpers.Calculator;
import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.Player;
import com.buffsovernexus.entity.Team;
import lombok.Builder;
import lombok.Data;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Data
@Builder
public class Engine {

    private Game game;
    private Possession possession = Possession.HOME;
    private List<EngineListener> eventHandlers = new ArrayList<>();
    private boolean hasWinner = false;
    private boolean logging = false;

    // These are recent moves by the previous player.
    private Stack<PlayerMove> homeGuardMoves = new Stack<PlayerMove>();
    private Stack<PlayerMove> awayGuardMoves = new Stack<PlayerMove>();
    private Stack<PlayerMove> homeForwardMoves = new Stack<PlayerMove>();
    private Stack<PlayerMove> awayForwardMoves = new Stack<PlayerMove>();

    public void generateGame() {
        Session session = Database.sessionFactory.openSession();

        // Declare start of the game
        GameStartEvent gameStartEvent = GameStartEvent.builder().game(game).startingPossession(possession)
                .teamWithPossession( getTeamWithPossession() ).build();
        eventHandlers.stream().forEach(eventHandler -> eventHandler.onGameStartEvent(gameStartEvent));

        // Ensure the game does not end while there has not been declared a winner.
        while (!game.hasWinner()) {

            // Step 1: Guard next action


            // Change possession automatically
            if (!hasWinner) {
                possession = doesHomeHavePossession() ? Possession.AWAY : Possession.HOME;
            } else {
                GameEndEvent gameEndEvent = GameEndEvent.builder().game(game).winner(game.getWinner()).loser(game.getLoser()).winningScore(game.getWinnerScore()).losingScore(game.getLoserScore()).build();
                eventHandlers.stream().forEach(eventHandler -> eventHandler.onGameEndEvent(gameEndEvent));
            }

        }

        session.close();

    }

    private boolean guardShot(Session session) {
        try {
            Player guard = getGuard();
            Calculator calc = new Calculator();
            if (guard.getFourPointer() > guard.getTwoPointer()) {
                // Attempt a four pointer
                calc.add( guard.getFourPointer() , GameSettings.FOUR_POINTER_WEIGHT, true);
            } else {
                // Attempt a two pointer
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * The start of a guard's turn.
     * @param session
     * @return
     */
    private boolean guardTurn(Session session) {
        try {
            Player guard = getGuard();
            boolean guardHasBetterFourPointer = guard.getFourPointer() >= guard.getTwoPointer();

            // For usability purposes, we want the guard to potentially consider the forward for a 4 pointer

            // Check if they have made any moves recently.
            if (doesHomeHavePossession()) {
                if (homeGuardMoves.size() > 0) {

                } else {
                    // A person is needed
                }
            }
            PlayerMove recentMove = doesHomeHavePossession() ? homeGuardMoves.peek() : awayGuardMoves.peek();


        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }

    private void rebound() {

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

    private Team getTeamWithPossession() {
        return doesHomeHavePossession() ? game.getHome() : game.getAway();
    }

}
