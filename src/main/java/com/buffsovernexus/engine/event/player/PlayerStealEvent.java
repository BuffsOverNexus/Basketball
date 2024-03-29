package com.buffsovernexus.engine.event.player;

import com.buffsovernexus.engine.enums.Possession;
import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.Player;
import com.buffsovernexus.entity.Team;
import lombok.Builder;
import lombok.Data;

/***
 * When a Player successfully steals the ball from another Player.
 */
@Data
@Builder
public class PlayerStealEvent {

    private Game game;
    private Player thief;
    private Player stolenFrom;
    private Possession possession;
    private Team thiefTeam;
    private Team stolenFromTeam;
}
