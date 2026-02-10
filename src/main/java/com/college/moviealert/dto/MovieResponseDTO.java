package com.college.moviealert.dto;

import lombok.Data;

import java.util.List;

@Data
public class MovieResponseDTO {
    private Long id;
    private String name;
    private List<DateDTO> dates;
}

