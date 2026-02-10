package com.college.moviealert.repository;

import com.college.moviealert.entity.Movie;
import com.college.moviealert.entity.Theatre;
import com.college.moviealert.entity.User;
import com.college.moviealert.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface UserPreferenceRepository
        extends JpaRepository<UserPreference, Long> {

    boolean existsByUserAndMovie(User user, Movie movie);


    List<UserPreference> findByUser(User user);


    boolean existsByUserAndMovieAndTheatreAndShowDateAndShowTime(
            User user,
            Movie movie,
            Theatre theatre,
            LocalDate showDate,
            LocalTime showTime
    );


}

