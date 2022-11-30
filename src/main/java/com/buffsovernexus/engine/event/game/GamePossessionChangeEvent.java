package com.buffsovernexus.engine.event.game;

import com.buffsovernexus.engine.enums.Possession;
import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.Team;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
/**
 * Handles when the possession changes.
 * NOTE: This is when the possession is successful changed. Not before.
 */
public class GamePossessionChangeEvent {

    private Possession possession;
    private Game game;
    private Team offense, defense;
}
