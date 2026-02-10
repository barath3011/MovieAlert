package com.college.moviealert.dto;

import lombok.Data;

import java.util.List;

@Data
public class TheatreShowDTO {
    private Long id;
    private String name;
    private List<String> times;
}

