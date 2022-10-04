package com.buffsovernexus.entity.identifier;

import javax.persistence.Id;
import java.io.Serializable;

public class SeasonTeamId implements Serializable {

    private Long team;
    private Long season;
    private Long scenario;
}
