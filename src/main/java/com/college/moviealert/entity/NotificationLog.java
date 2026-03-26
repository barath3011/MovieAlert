package com.college.moviealert.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String movieName;

    private String theatreName;

    private String status; // PENDING, SENT, FAILED

    private LocalDateTime createdAt;

    private LocalDate showDate;

    private LocalTime showTime;
}