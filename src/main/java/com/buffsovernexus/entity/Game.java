package com.buffsovernexus.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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

    private Integer homeScore;
    private Integer awayScore;

    @OneToOne(cascade = CascadeType.ALL)
    private Performance homeGuardPerformance;
    @OneToOne(cascade = CascadeType.ALL)
    private Performance homeForwardPerformance;
    @OneToOne(cascade = CascadeType.ALL)
    private Performance awayGuardPerformance;
    @OneToOne(cascade = CascadeType.ALL)
    private Performance awayForwardPerformance;

    @OneToOne(cascade = CascadeType.ALL)
    private Season season;

    @OneToOne(cascade = CascadeType.ALL)
    private PostSeason postSeason;

    public boolean hasWinner() {
        return winner != null;
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

    public int getWinnerScore() {
        if (hasWinner()) {
            return getScore(winner);
        } else {
            return 0;
        }
    }

    public int getLoserScore() {
        if (hasWinner()) {
            return getScore(loser);
        } else {
            return 0;
        }
    }
}
