package com.buffsovernexus.engine.event.player;

import com.buffsovernexus.engine.enums.PlayerShotType;
import com.buffsovernexus.entity.Game;
import com.buffsovernexus.entity.Player;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerShotMissedEvent {

    private Player scorer;
    private Player defender;
    private Game game;
    private PlayerShotType playerShotType;
}
