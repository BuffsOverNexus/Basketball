package com.buffsovernexus.engine.event.game;

import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.Team;
import lombok.Builder;
import lombok.Data;

/***
 * Called when the game concludes.
 */
@Data
@Builder
public class GameEndEvent {

    private Game game;
    private Team winner, loser;
    private int winningScore, losingScore;

}
