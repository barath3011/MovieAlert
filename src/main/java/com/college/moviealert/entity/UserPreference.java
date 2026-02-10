package com.college.moviealert.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Entity
@Table(
        name = "user_preference",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "user_id",
                                "movie_id",
                                "theatre_id",
                                "show_date",
                                "show_time"
                        }
                )
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 User
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    // 🔹 Movie
    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    // 🔹 Theatre (ONE per row)
    @ManyToOne(optional = false)
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;

    // 🔹 Date (ONE per row)
    @Column(name = "show_date", nullable = false)
    private LocalDate showDate;

    // 🔹 Time (ONE per row)
    @Column(name = "show_time", nullable = false)
    private LocalTime showTime;
}
