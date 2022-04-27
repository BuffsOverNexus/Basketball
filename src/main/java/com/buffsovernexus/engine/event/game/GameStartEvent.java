package com.buffsovernexus.engine.event.game;

import com.buffsovernexus.engine.enums.Possession;
import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.Team;
import lombok.Builder;
import lombok.Data;

/***
 * When the Game starts.
 *
 */
@Data
@Builder
public class GameStartEvent extends GameEvent {
    private Game game;
    private Possession startingPossession;
    private Team teamWithPossession;
}
