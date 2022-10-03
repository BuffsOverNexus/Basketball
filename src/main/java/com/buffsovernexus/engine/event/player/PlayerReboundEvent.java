package com.buffsovernexus.engine.event.player;

import com.buffsovernexus.engine.enums.Possession;
import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.Player;
import com.buffsovernexus.entity.Team;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
/**
 * When an offensive rebound occurs.
 */
public class PlayerReboundEvent {

    private Game game;
    private Player rebounder;
    private Player defender;
    private Possession possession;
    private Team reboundingTeam;
    private Team defendingTeam;
}
