package com.buffsovernexus.entity;

import com.buffsovernexus.entity.identifier.SeasonTeamId;
import com.buffsovernexus.utility.Percentage;
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

    /**
     * Retrieve the string value of the win percentage of a team
     * @return the win percentage of a team up to 3 decimals
     */
    public double getPercentage() {
        return Percentage.calculatePercentage(wins, losses);
    }

    public String getPrettyPercentage() {
        return Percentage.toPretty(getPercentage());
    }

}
