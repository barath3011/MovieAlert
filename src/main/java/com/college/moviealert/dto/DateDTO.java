package com.college.moviealert.dto;

import lombok.Data;

import java.util.List;

@Data
public class DateDTO {
    private String day;    // FEB
    private String date;   // 09
    private String label;  // Mon
    private List<TheatreShowDTO> theatres;
}

