package com.buffsovernexus.entity;

import com.buffsovernexus.GameSettings;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


@Data
@Entity
@Table(name="game")
public class Game {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Team home;
    @OneToOne(cascade = CascadeType.ALL)
    private Team away;
    @OneToOne(cascade = CascadeType.ALL)
    private Team winner = null;
    @OneToOne(cascade = CascadeType.ALL)
    private Team loser = null;

    private Integer homeScore = 0;
    private Integer awayScore = 0;

    @OneToOne(cascade = CascadeType.ALL)
    private Season season;

    @OneToOne(cascade = CascadeType.ALL)
    private PostSeason postSeason;

    public boolean hasWinner() {
        return awayScore < GameSettings.GAME_POINTS_THRESHOLD && homeScore < GameSettings.GAME_POINTS_THRESHOLD;
    }

    public boolean isSeason() {
        return season != null;
    }

    public boolean isPostSeason() {
        return postSeason != null;
    }

    public int getScore(Team team) {
        return team.equals(home) ? homeScore : awayScore;
    }

    public void addHomeScore(int points) { this.homeScore += points; }
    public void addAwayScore(int points) { this.awayScore += points; }

}
