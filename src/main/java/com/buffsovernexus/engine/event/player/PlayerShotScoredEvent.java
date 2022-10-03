package com.buffsovernexus.engine.event.player;

import com.buffsovernexus.engine.enums.PlayerScoreType;
import com.buffsovernexus.engine.enums.PlayerShotType;
import com.buffsovernexus.engine.enums.Possession;
import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.Player;
import com.buffsovernexus.entity.Team;
import lombok.Builder;
import lombok.Data;

/**
 * This event only fires when the player scores.
 */
@Data
@Builder
public class PlayerShotScoredEvent {
    private Player scorer;
    private Player defender;
    private PlayerShotType playerShotType;
    private Game game;
    private Team scoringTeam;
    private Team defendingTeam;
    private Possession possession;

}
