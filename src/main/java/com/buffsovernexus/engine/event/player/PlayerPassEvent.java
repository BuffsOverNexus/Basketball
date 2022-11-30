package com.buffsovernexus.engine.event.player;

import com.buffsovernexus.engine.enums.Possession;
import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.Player;
import com.buffsovernexus.entity.Team;
import lombok.Builder;
import lombok.Data;

/**
 * When a player successfully passes
 */
@Builder
@Data
public class PlayerPassEvent {

    private Game game;
    private Player passer;
    private Player defender;
    private Possession possession;
    private Team passingTeam;
    private Team defendingTeam;
    private Player receivingPlayer;
}
