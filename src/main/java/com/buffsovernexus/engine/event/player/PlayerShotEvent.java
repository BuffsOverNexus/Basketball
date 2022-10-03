package com.buffsovernexus.engine.event.player;

import com.buffsovernexus.engine.enums.PlayerShotType;
import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.Player;
import com.buffsovernexus.entity.Team;
import lombok.Builder;
import lombok.Data;

/**
 * When a player attempts to shoot with the ball.
 * Results: Either miss the shot or score the shot.
 *
 * You should probably use a more specific event to handle your case.
 */
@Data
@Builder
public class PlayerShotEvent {
    private Player shooter;
    private Player defender;
    private Team shootingTeam;
    private Team defendingTeam;
    private PlayerShotType playerShotType;
    private Game game;


}
