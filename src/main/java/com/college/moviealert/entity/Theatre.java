package com.college.moviealert.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "theatres")
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Theatre() {}

    public Theatre(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void Theatre(String name) {
        this.name = name;
    }
}
