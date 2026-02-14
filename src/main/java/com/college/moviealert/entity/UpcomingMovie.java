package com.college.moviealert.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "upcoming_movies")
public class UpcomingMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String language;

    private String genre;

    private LocalDate releaseDate;

    // Constructors
    public UpcomingMovie() {}

    public UpcomingMovie(String title, String language, String genre, LocalDate releaseDate) {
        this.title = title;
        this.language = language;
        this.genre = genre;
        this.releaseDate = releaseDate;
    }

    // Getters & Setters

    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }
}
