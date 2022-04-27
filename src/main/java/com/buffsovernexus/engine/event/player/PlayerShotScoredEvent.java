package com.buffsovernexus.engine.event.player;

import com.buffsovernexus.engine.enums.PlayerScoreType;
import com.buffsovernexus.engine.enums.Possession;
import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.Player;

/**
 * This event uses the "PlayerShootEvent" and only fires when the player scores.
 */
public class PlayerShotScoredEvent {
    private Player scorer;
    private Player defender;
    private PlayerScoreType playerScoreType;
    private Game game;
    private Possession possession;

    /***
     * PlayerScoreEvent
     * Event is called when a player scores points.
     * Note: Use the "PlayerShotEvent" to handle all results, including failures.
     * @param scorer
     * @param defender
     * @param playerScoreType
     * @param game
     * @param possession
     */
    public PlayerShotScoredEvent(Player scorer, Player defender, Possession possession, PlayerScoreType playerScoreType, Game game) {
        setScorer(scorer);
        setDefender(defender);
        setPlayerScoreType(playerScoreType);
        setGame(game);
        setPossession(possession);
    }

    public Player getDefender() {
        return defender;
    }

    public void setDefender(Player defender) {
        this.defender = defender;
    }

    public Player getScorer() {
        return scorer;
    }

    public void setScorer(Player scorer) {
        this.scorer = scorer;
    }

    public PlayerScoreType getPlayerScoreType() {
        return playerScoreType;
    }

    public void setPlayerScoreType(PlayerScoreType playerScoreType) {
        this.playerScoreType = playerScoreType;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Possession getPossession() {
        return possession;
    }

    public void setPossession(Possession possession) {
        this.possession = possession;
    }
}
