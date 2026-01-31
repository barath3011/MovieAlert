package com.college.moviealert.repository;

import com.college.moviealert.entity.Movie;
import com.college.moviealert.entity.MovieShow;
import com.college.moviealert.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MovieShowRepository extends JpaRepository<MovieShow, Long> {

    boolean existsByMovieAndTheatreAndShowDateAndShowTime(
            Movie movie,
            Theatre theatre,
            LocalDate showDate,
            LocalTime showTime
    );

    List<MovieShow> findByMovieNameIgnoreCase(String movieName);

    List<MovieShow> findByMovieNameIgnoreCaseAndTheatreNameIgnoreCase(
            String movieName,
            String theatreName
    );
}
