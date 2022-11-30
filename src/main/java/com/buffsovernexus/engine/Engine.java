package com.buffsovernexus.engine;

import com.buffsovernexus.GameSettings;
import com.buffsovernexus.engine.enums.PlayerShotType;
import com.buffsovernexus.engine.enums.Possession;
import com.buffsovernexus.engine.event.game.GameEndEvent;
import com.buffsovernexus.engine.event.game.GameLeadChangeEvent;
import com.buffsovernexus.engine.event.game.GameStartEvent;
import com.buffsovernexus.engine.event.game.GamePossessionChangeEvent;
import com.buffsovernexus.engine.event.player.*;
import com.buffsovernexus.engine.handlers.*;
import com.buffsovernexus.engine.helpers.Calculators;
import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.Player;
import com.buffsovernexus.entity.Team;
import lombok.Builder;
import lombok.Data;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class Engine {

    private Game game;
    private Possession possession = Possession.HOME;
    private List<EngineListener> eventHandlers;
    private boolean hasWinner = false;
    private boolean logging = true;

    private Session session;

    private Date start, end;

    public void addEventHandlers() {
        eventHandlers.add(new LogStartGameAlreadyStartedHandler());
        eventHandlers.add(new LogStartGameHandler());
        eventHandlers.add(new LogPlayerShotAttemptHandler());
        eventHandlers.add(new LogEndGameHandler());
        eventHandlers.add(new LogPlayerPassHandler());
        eventHandlers.add(new LogPlayerStealHandler());
        eventHandlers.add(new LogPlayerScoreHandler());
        eventHandlers.add(new LogGamePossessionHandler());
        eventHandlers.add(new LogReboundHandler());
        eventHandlers.add(new LogReboundTurnoverHandler());
    }

    public void addEssentialHandlers() {
        eventHandlers.add(new EndGameRecordTeamsHandler());
    }
    public void generateGame() {
        eventHandlers = new ArrayList<>();

        addEssentialHandlers();
        if (logging)
            addEventHandlers();

        possession = Possession.HOME;

        // Declare start of the game
        startGame();

        // Ensure the game does not end while there has not been declared a winner.
        while (!game.hasWinner()) {
            // Start the possession
            startPossession();

            // Change possession automatically
            if (!hasWinner()) {
                swapPossession();
            } else {
                endGame();
            }

        }


    }

    private void startGame() {
        // Declare start of the game
        GameStartEvent gameStartEvent = GameStartEvent.builder().game(game).startingPossession(possession)
                .teamWithPossession( getOffense() ).build();
        eventHandlers.stream().forEach(eventHandler -> eventHandler.onGameStartEvent(gameStartEvent));
    }

    private void endGame() {
        Transaction transaction = session.beginTransaction();
        if (game.getAwayScore() >= GameSettings.GAME_POINTS_THRESHOLD) {
            game.setWinner(game.getAway());
            game.setLoser(game.getHome());
        } else {
            game.setWinner(game.getHome());
            game.setLoser(game.getAway());
        }
        session.update(game);
        transaction.commit();

        // We are guaranteed to have a winner.
        if (game.getHomeScore() > game.getAwayScore()) {
            GameEndEvent gameEndEvent = GameEndEvent.builder().game(game).winner(game.getHome()).loser(game.getAway()).winningScore(game.getHomeScore()).losingScore(game.getAwayScore()).build();
            eventHandlers.stream().forEach(eventHandler -> eventHandler.onGameEndEvent(gameEndEvent));
        } else {
            GameEndEvent gameEndEvent = GameEndEvent.builder().game(game).winner(game.getAway()).loser(game.getAway()).winningScore(game.getAwayScore()).losingScore(game.getHomeScore()).build();
            eventHandlers.stream().forEach(eventHandler -> eventHandler.onGameEndEvent(gameEndEvent));
        }
    }

    /**
     * Start a possession
     */
    private void startPossession() {
        guardPhase();
    }

    /**
     * Declare a guard's phase
     */
    private void guardPhase() {
        if ( EnginePhase.doesGuardAttemptPass(getGuard()) ) {
            // Attempt a pass
            guardPassingPhase();
        } else {
            // Attempt a shot instead.
            guardShootingPhase();
        }
    }

    private void forwardPhase() {
        if ( EnginePhase.doesForwardAttemptPass(getForward()) ) {
            // Attempt a pass
            forwardPassingPhase();
        } else {
            // Attempt a shot instead.
            forwardShootingPhase();
        }

    }

    private void forwardReboundPhase() {
        Player forward = getForward();
        Player opposing = getOpposingForward();
        // Do a calculator check to see who wins the rebound phase.
        if ( Calculators.getForwardRebound(forward, opposing) ) {
            PlayerReboundEvent playerReboundEvent = PlayerReboundEvent.builder()
                    .rebounder(forward)
                    .defender(opposing)
                    .reboundingTeam(getOffense())
                    .defendingTeam(getDefense()).build();
            eventHandlers.stream().forEach(eventHandler -> eventHandler.onPlayerReboundEvent(playerReboundEvent));
            forwardPhase();
        } else {
            PlayerReboundTurnoverEvent playerReboundTurnoverEvent = PlayerReboundTurnoverEvent.builder()
                    .game(game)
                    .possession(possession)
                    .winner(opposing)
                    .loser(forward)
                    .losingTeam(getOffense())
                    .winningTeam(getDefense())
                    .build();
            eventHandlers.stream().forEach(eventHandler -> eventHandler.onPlayerReboundTurnoverEvent(playerReboundTurnoverEvent));
        }
    }

    private void guardReboundPhase() {
        Player guard = getGuard();
        Player opposing = getOpposingGuard();
        if ( Calculators.getGuardRebound(getGuard(), getForward())) {
            PlayerReboundEvent playerReboundEvent = PlayerReboundEvent.builder()
                    .rebounder(guard)
                    .defender(opposing)
                    .reboundingTeam(getOffense())
                    .defendingTeam(getDefense()).build();
            eventHandlers.stream().forEach(eventHandler -> eventHandler.onPlayerReboundEvent(playerReboundEvent));
            guardPhase();
        } else {
            PlayerReboundTurnoverEvent playerReboundTurnoverEvent = PlayerReboundTurnoverEvent.builder()
                    .game(game)
                    .possession(possession)
                    .winner(opposing)
                    .loser(guard)
                    .losingTeam(getOffense())
                    .winningTeam(getDefense())
                    .build();
            eventHandlers.stream().forEach(eventHandler -> eventHandler.onPlayerReboundTurnoverEvent(playerReboundTurnoverEvent));
        }
    }

    private void guardPassingPhase() {
        Player guard = getGuard();
        Player opposing = getOpposingGuard();
        if (Calculators.getGuardPass(guard, opposing)) {
            PlayerPassEvent playerPassEvent = PlayerPassEvent.builder()
                    .game(game)
                    .possession(possession)
                    .passer(guard)
                    .defender(opposing)
                    .passingTeam(getOffense())
                    .defendingTeam(getDefense())
                    .receivingPlayer(getForward())
                    .build();
            eventHandlers.stream().forEach(eventHandler -> eventHandler.onPlayerPassEvent(playerPassEvent));
            forwardPhase();
        } else {
            // This is going to assume they stole.
            PlayerStealEvent playerStealEvent = PlayerStealEvent.builder()
                    .game(game)
                    .thief(opposing)
                    .stolenFrom(guard)
                    .possession(possession)
                    .thiefTeam(getDefense())
                    .stolenFromTeam(getOffense())
                    .build();
            eventHandlers.stream().forEach(eventHandler -> eventHandler.onPlayerStealEvent(playerStealEvent));
        }
    }

    public void forwardPassingPhase() {
        Player forward = getForward();
        Player opposing = getOpposingForward();
        if (Calculators.getForwardPass(forward, opposing)) {
            PlayerPassEvent playerPassEvent = PlayerPassEvent.builder()
                    .game(game)
                    .possession(possession)
                    .passer(forward)
                    .defender(opposing)
                    .passingTeam(getOffense())
                    .defendingTeam(getDefense())
                    .receivingPlayer(getGuard())
                    .build();
            eventHandlers.stream().forEach(eventHandler -> eventHandler.onPlayerPassEvent(playerPassEvent));
            guardPhase();
        } else {
            // This is going to assume they stole.
            PlayerStealEvent playerStealEvent = PlayerStealEvent.builder()
                    .game(game)
                    .thief(opposing)
                    .stolenFrom(forward)
                    .possession(possession)
                    .thiefTeam(getDefense())
                    .stolenFromTeam(getOffense())
                    .build();
            eventHandlers.stream().forEach(eventHandler -> eventHandler.onPlayerStealEvent(playerStealEvent));
        }
    }

    public void guardShootingPhase() {
        Player guard = getGuard();
        Player opposing = getOpposingGuard();
        if ( EnginePhase.doesGuardAttemptFourPointer(guard) ) {
            // Let the listeners know a shot was attempted.
            PlayerShotEvent playerShotEvent = PlayerShotEvent.builder()
                    .playerShotType(PlayerShotType.FOUR_POINTER)
                    .game(game)
                    .shooter(guard)
                    .defender(opposing)
                    .shootingTeam(getOffense())
                    .defendingTeam(getDefense())
                    .build();
            eventHandlers.stream().forEach(eventHandler -> eventHandler.onPlayerShotEvent(playerShotEvent));

            // Determine if the player scored.
            if ( Calculators.getGuardFourPointer(guard, opposing) ) {
                // Update scores and determine lead changing!
                updateScore(PlayerShotType.FOUR_POINTER);

                // Send the scored event
                PlayerShotScoredEvent playerShotScoredEvent = PlayerShotScoredEvent.builder()
                        .playerShotType(PlayerShotType.FOUR_POINTER)
                        .game(game)
                        .scorer(guard)
                        .defender(opposing)
                        .scoringTeam(getOffense())
                        .defendingTeam(getDefense())
                        .build();
                eventHandlers.stream().forEach(eventHandler -> eventHandler.onPlayerShotScoredEvent(playerShotScoredEvent));
            } else {
                forwardReboundPhase();
            }
        } else {
            PlayerShotEvent playerShotEvent = PlayerShotEvent.builder()
                    .playerShotType(PlayerShotType.TWO_POINTER)
                    .game(game)
                    .shooter(guard)
                    .shootingTeam(getOffense())
                    .defendingTeam(getDefense())
                    .defender(opposing)
                    .build();
            eventHandlers.stream().forEach(eventHandler -> eventHandler.onPlayerShotEvent(playerShotEvent));

            if ( Calculators.getGuardTwoPointer(guard, opposing) ) {
                // Update scores depending on who has possession
                updateScore(PlayerShotType.TWO_POINTER);

                // Declare to all listeners
                PlayerShotScoredEvent playerShotScoredEvent = PlayerShotScoredEvent.builder()
                        .playerShotType(PlayerShotType.TWO_POINTER)
                        .game(game)
                        .scorer(guard)
                        .defender(opposing)
                        .scoringTeam(getOffense())
                        .defendingTeam(getDefense())
                        .build();
                eventHandlers.stream().forEach(eventHandler -> eventHandler.onPlayerShotScoredEvent(playerShotScoredEvent));

            } else {
                forwardReboundPhase();
            }
        }
    }

    public void forwardShootingPhase() {
        Player forward = getForward();
        Player opposing = getOpposingForward();
        if ( EnginePhase.doesForwardAttemptFourPointer(forward) ) {
            PlayerShotEvent playerShotEvent = PlayerShotEvent.builder()
                    .playerShotType(PlayerShotType.FOUR_POINTER)
                    .game(game)
                    .shooter(forward)
                    .defender(opposing)
                    .shootingTeam(getOffense())
                    .defendingTeam(getDefense())
                    .build();
            eventHandlers.stream().forEach(eventHandler -> eventHandler.onPlayerShotEvent(playerShotEvent));

            if ( Calculators.getForwardFourPointer(forward, opposing) ) {
                updateScore(PlayerShotType.FOUR_POINTER);

                PlayerShotScoredEvent playerShotScoredEvent = PlayerShotScoredEvent.builder()
                        .playerShotType(PlayerShotType.FOUR_POINTER)
                        .game(game)
                        .scorer(forward)
                        .defender(opposing)
                        .scoringTeam(getOffense())
                        .defendingTeam(getDefense())
                        .build();
                eventHandlers.stream().forEach(eventHandler -> eventHandler.onPlayerShotScoredEvent(playerShotScoredEvent));
            } else {
                guardReboundPhase();
            }

        } else {
            PlayerShotEvent playerShotEvent = PlayerShotEvent.builder()
                    .playerShotType(PlayerShotType.TWO_POINTER)
                    .game(game)
                    .shooter(forward)
                    .defender(opposing)
                    .shootingTeam(getOffense())
                    .defendingTeam(getDefense())
                    .build();
            eventHandlers.stream().forEach(eventHandler -> eventHandler.onPlayerShotEvent(playerShotEvent));

            if ( Calculators.getForwardTwoPointer(forward, opposing) ) {
                updateScore(PlayerShotType.TWO_POINTER);

                PlayerShotScoredEvent playerShotScoredEvent = PlayerShotScoredEvent.builder()
                        .playerShotType(PlayerShotType.TWO_POINTER)
                        .game(game)
                        .scorer(forward)
                        .scoringTeam(getOffense())
                        .defendingTeam(getDefense())
                        .defender(opposing)
                        .build();
                eventHandlers.stream().forEach(eventHandler -> eventHandler.onPlayerShotScoredEvent(playerShotScoredEvent));
            } else {
                guardReboundPhase();
            }
        }
    }


    private void swapPossession() {
        if (possession == Possession.AWAY)
            possession = Possession.HOME;
        else
            possession = Possession.AWAY;

        if (!hasWinner) {
            GamePossessionChangeEvent gamePossessionChangeEvent = GamePossessionChangeEvent.builder()
                    .possession(possession)
                    .game(game)
                    .offense(getOffense())
                    .defense(getDefense())
                    .build();
            eventHandlers.stream().forEach(eventHandler -> eventHandler.onGamePossessionChangeEvent(gamePossessionChangeEvent));
        }
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

    private boolean isHomeLeading() {
        return game.getHomeScore() > game.getAwayScore();
    }

    private boolean isTied() {
        return game.getHomeScore() == game.getAwayScore();
    }

    private boolean isAwayLeading() {
        return game.getAwayScore() > game.getHomeScore();
    }

    private void updateScore(PlayerShotType playerShotType) {
        // First, update who has the lead.
        if (isHomeLeading()) {
            // We only care if away scores
            if (!doesHomeHavePossession()) {
                // Determine if it pushes them over
                if (game.getHomeScore() < game.getAwayScore() + playerShotType.getPoints()) {
                    int awayScore = game.getAwayScore() + playerShotType.getPoints();
                    int difference = Math.abs(game.getHomeScore() - awayScore);
                    GameLeadChangeEvent gameLeadChangeEvent = GameLeadChangeEvent.builder()
                            .game(game)
                            .leading(game.getAway())
                            .trailing(game.getHome())
                            .awayScore(awayScore)
                            .homeScore(game.getHomeScore())
                            .difference(difference)
                            .build();
                    eventHandlers.stream().forEach(eventHandler -> eventHandler.onGameLeadChangeEvent(gameLeadChangeEvent));
                }
            }
        }

        if (isTied()) {
            if (doesHomeHavePossession()) {
                int homeScore = game.getHomeScore() + playerShotType.getPoints();
                int difference = playerShotType.getPoints();
                GameLeadChangeEvent gameLeadChangeEvent = GameLeadChangeEvent.builder()
                        .game(game)
                        .leading(game.getHome())
                        .trailing(game.getAway())
                        .awayScore(game.getAwayScore())
                        .homeScore(homeScore)
                        .difference(difference)
                        .build();
                eventHandlers.stream().forEach(eventHandler -> eventHandler.onGameLeadChangeEvent(gameLeadChangeEvent));
            } else {
                int awayScore = game.getAwayScore() + playerShotType.getPoints();
                int difference = playerShotType.getPoints();
                GameLeadChangeEvent gameLeadChangeEvent = GameLeadChangeEvent.builder()
                        .game(game)
                        .leading(game.getAway())
                        .trailing(game.getHome())
                        .awayScore(awayScore)
                        .homeScore(game.getHomeScore())
                        .difference(difference)
                        .build();
                eventHandlers.stream().forEach(eventHandler -> eventHandler.onGameLeadChangeEvent(gameLeadChangeEvent));
            }
        }

        if (isAwayLeading()) {
            if (doesHomeHavePossession()) {
                if (game.getAwayScore() < game.getHomeScore() + playerShotType.getPoints()) {
                    int homeScore = game.getHomeScore() + playerShotType.getPoints();
                    int difference = Math.abs(homeScore - game.getAwayScore());
                    GameLeadChangeEvent gameLeadChangeEvent = GameLeadChangeEvent.builder()
                            .game(game)
                            .leading(game.getHome())
                            .trailing(game.getAway())
                            .awayScore(game.getAwayScore())
                            .homeScore(homeScore)
                            .difference(difference)
                            .build();
                    eventHandlers.stream().forEach(eventHandler -> eventHandler.onGameLeadChangeEvent(gameLeadChangeEvent));
                }
            }
        }

        Transaction transaction = session.beginTransaction();
        if (doesHomeHavePossession()) {
            game.addHomeScore(playerShotType.getPoints());
        } else {
            game.addAwayScore(playerShotType.getPoints());
        }
        session.update(game);
        transaction.commit();
    }

    private boolean hasWinner() {
        if (game.getAwayScore() >= GameSettings.GAME_POINTS_THRESHOLD) return true;
        if (game.getHomeScore() >= GameSettings.GAME_POINTS_THRESHOLD) return true;
        return false;
    }


}
