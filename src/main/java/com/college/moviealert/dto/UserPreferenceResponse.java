package com.college.moviealert.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Getter
@Setter
public class UserPreferenceResponse {

    private List<Long> preferenceIds;   // 👈 ADD THIS
    private String movieName;
    private List<String> theatreNames;
    private List<LocalTime> showTimes;
    private LocalDate showDate;

    public UserPreferenceResponse(List<Long> preferenceIds,
                                  String movieName,
                                  List<String> theatreNames,
                                  List<LocalTime> showTimes,
                                  LocalDate showDate) {
        this.preferenceIds = preferenceIds;
        this.movieName = movieName;
        this.theatreNames = theatreNames;
        this.showTimes = showTimes;
        this.showDate = showDate;
    }

    // getters
}
