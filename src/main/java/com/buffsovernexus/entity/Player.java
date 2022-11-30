package com.buffsovernexus.entity;

import com.buffsovernexus.enums.PlayerPosition;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table( name = "player" )
public class Player {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    private String firstName;
    private String lastName;
    private PlayerPosition position;
    private Integer fourPointer;
    private Integer twoPointer;
    private Integer steal;
    private Integer block;
    private Integer rebound;

    public String getName() {
        return firstName + " " + lastName;
    }


}
