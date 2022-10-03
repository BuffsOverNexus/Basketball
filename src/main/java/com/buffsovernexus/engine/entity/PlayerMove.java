package com.buffsovernexus.engine.entity;

import com.buffsovernexus.engine.enums.PlayerMoveType;
import lombok.Data;

@Data
public class PlayerMove {
    private boolean success;
    private PlayerMoveType moveType;
}
