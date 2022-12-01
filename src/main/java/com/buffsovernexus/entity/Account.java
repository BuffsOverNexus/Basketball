package com.buffsovernexus.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.*;

@Data
@Entity
@Table(name="account")
public class Account {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    private String username;
    private String password;
}
