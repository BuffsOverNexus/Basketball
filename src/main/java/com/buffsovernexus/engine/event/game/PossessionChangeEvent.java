package com.buffsovernexus.engine.event.game;

import com.buffsovernexus.engine.enums.Possession;
import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.Team;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PossessionChangeEvent extends GameEvent {

    private Possession possession;
    private Game game;
    private Team offense, defense;
}
