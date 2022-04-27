package com.buffsovernexus.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/***
 * Record a Player's performance in a game.
 */
@Data
@Entity
@Table(name = "performance")
public class Performance {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @OneToOne()
    private Player player;
    @OneToOne()
    private Game game;

    private Integer points;

    private Integer fourPointers;
    private Integer attemptedFourPointers;

    private Integer twoPointers;
    private Integer attemptedTwoPointers;

    private Integer blocks;
    private Integer attemptedBlocks;

    private Integer steals;
    private Integer attemptedSteals;

    private Integer rebounds;
    private Integer attemptedRebounds;

    public void addPoints(Integer points) { this.points += points; }

    public void addFourPointers(Integer fourPointers) {
        this.fourPointers += fourPointers;
    }

    public void addAttemptedFourPointers(Integer attemptedFourPointers) { this.attemptedFourPointers += attemptedFourPointers; }

    public void addTwoPointers(Integer twoPointers) {
        this.twoPointers += twoPointers;
    }

    public void addAttemptedTwoPointers(Integer attemptedTwoPointers) { this.attemptedTwoPointers += attemptedTwoPointers; }

    public void addBlocks(Integer blocks) {
        this.blocks += blocks;
    }

    public void addAttemptedBlocks(Integer attemptedBlocks) {
        this.attemptedBlocks += attemptedBlocks;
    }

    public void addSteals(Integer steals) {
        this.steals += steals;
    }

    public void addAttemptedSteals(Integer attemptedSteals) {
        this.attemptedSteals += attemptedSteals;
    }

    public void addRebounds(Integer rebounds) {
        this.rebounds += rebounds;
    }

    public void addAttemptedRebounds(Integer attemptedRebounds) {
        this.attemptedRebounds += attemptedRebounds;
    }
}
