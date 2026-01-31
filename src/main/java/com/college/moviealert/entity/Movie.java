package com.college.moviealert.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public Movie(String name) {
        this.name = name;
    }
}
