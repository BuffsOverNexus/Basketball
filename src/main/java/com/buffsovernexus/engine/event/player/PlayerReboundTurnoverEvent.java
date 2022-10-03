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
 * When a defensive rebound occurs
 */
public class PlayerReboundTurnoverEvent {

    private Game game;
    private Player winner;
    private Player loser;
    private Possession possession;
    private Team winningTeam;
    private Team losingTeam;
}
