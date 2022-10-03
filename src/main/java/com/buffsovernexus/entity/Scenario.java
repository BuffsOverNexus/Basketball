package com.buffsovernexus.entity;

import com.buffsovernexus.enums.ScenarioStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name="scenario")
public class Scenario {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Account account;

    private ScenarioStatus scenarioStatus;
}
