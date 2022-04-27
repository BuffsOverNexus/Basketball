package com.buffsovernexus.engine.event.player;

import com.buffsovernexus.engine.enums.PlayerShotType;
import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.Player;
import com.buffsovernexus.entity.Team;

/**
 * When a player attempts to shoot with the ball.
 * Results: Either miss the shot or score the shot.
 *
 * You should probably use a more specific event to handle your case.
 */
public class PlayerShotEvent {
    private Player shooter;
    private PlayerShotType playerShotType;
    private Game game;
    private Team team;

    public PlayerShotEvent(Game game, Team team, Player shooter, PlayerShotType playerShotType) {
        this.setGame(game);
        this.setTeam(team);
        this.setShooter(shooter);
        this.setPlayerShotType(playerShotType);
    }

    public Player getShooter() {
        return shooter;
    }

    public void setShooter(Player shooter) {
        this.shooter = shooter;
    }

    public PlayerShotType getPlayerShotType() {
        return playerShotType;
    }

    public void setPlayerShotType(PlayerShotType playerShotType) {
        this.playerShotType = playerShotType;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
