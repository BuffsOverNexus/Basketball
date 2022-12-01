package com.buffsovernexus.entity;

import com.buffsovernexus.entity.identifier.SeasonTeamId;
import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "season_team")
@IdClass(SeasonTeamId.class)
@Data
public class SeasonTeam {
    @Id
    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @Id
    @ManyToOne
    @JoinColumn(name = "season_id", referencedColumnName = "id")
    private Season season;

    @Id
    @ManyToOne
    @JoinColumn(name = "scenario_id", referencedColumnName = "id")
    private Scenario scenario;

    private int wins = 0;

    private int losses = 0;
}
