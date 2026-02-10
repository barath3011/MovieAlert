package com.college.moviealert.dto;

import lombok.Data;

import java.util.List;

@Data
public class MovieWrapperDTO {
    private List<MovieResponseDTO> theatre;
}

