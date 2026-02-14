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
                                "movie_name",
                                "theatre_name",
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    // 🔥 Store plain values
    @Column(name = "movie_name", nullable = false)
    private String movieName;

    @Column(name = "theatre_name", nullable = false)
    private String theatreName;

    @Column(name = "show_date", nullable = false)
    private LocalDate showDate;

    @Column(name = "show_time", nullable = false)
    private LocalTime showTime;
}
