package com.buffsovernexus.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Data
@Entity
@Table(name="season")
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Scenario scenario;

    private Integer year;
}
