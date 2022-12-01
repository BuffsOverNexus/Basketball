package com.buffsovernexus.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Data
@Entity
@Table(name="postseason")
public class PostSeason {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Scenario scenario;
    @OneToOne(cascade = CascadeType.ALL)
    private Season season;

}
