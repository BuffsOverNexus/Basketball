package com.buffsovernexus.entity;

import com.buffsovernexus.entity.identifier.PostSeasonTeamId;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "postseason_team")
@IdClass(PostSeasonTeamId.class)
@Data
public class PostSeasonTeam {
    @Id
    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @Id
    @ManyToOne
    @JoinColumn(name = "scenario_id", referencedColumnName = "id")
    private Scenario scenario;

    @Id
    @ManyToOne
    @JoinColumn(name = "postseason_id", referencedColumnName = "id")
    private PostSeason postSeason;

    private int wins = 0, losses = 0, seed = 0;
}
