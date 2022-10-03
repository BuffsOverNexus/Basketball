package com.buffsovernexus.engine.event.game;

import com.buffsovernexus.engine.enums.Lead;
import com.buffsovernexus.engine.enums.Possession;
import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.Team;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameLeadChangeEvent {

    private Game game;
    private int awayScore, homeScore, difference;
    private Team leading;
    private Team trailing;
}
