package com.college.moviealert.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "notification_log",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "movie_show_id"}
        )
)
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_show_id", nullable = false)
    private MovieShow movieShow;

    private LocalDateTime sentTime = LocalDateTime.now();

    public NotificationLog() {}

    // getters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public MovieShow getMovieShow() {
        return movieShow;
    }
}
