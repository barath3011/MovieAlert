package com.college.moviealert.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserPreferenceRequest {

    private String email;

    // Movie (either one)
    private Long movieId;
    private String movieName;

    // Theatre (either one or many)
    private List<Long> theatreIds;
    private List<String> theatreNames;

    private List<LocalDate> showDates;
    private List<LocalTime> showTimes;
}


