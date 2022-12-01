package com.buffsovernexus.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table( name = "team" )
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Player> roster = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Player guard;

    @OneToOne(cascade = CascadeType.ALL)
    private Player forward;

    private Boolean userTeam;

    @OneToOne(cascade = CascadeType.ALL)
    private Scenario scenario;

    @Override
    public boolean equals(Object object) {
        try {
            Team team = (Team) object;
            return team.getId().intValue() == id;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
