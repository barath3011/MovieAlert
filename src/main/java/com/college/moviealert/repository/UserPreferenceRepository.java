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

    boolean existsByUserAndMovieName(
            User user,
            String movieName
    );


    List<UserPreference> findByUser(User user);



    boolean existsByUserAndMovieNameAndTheatreNameAndShowDateAndShowTime(
            User user,
            String movieName,
            String theatreName,
            LocalDate showDate,
            LocalTime showTime
    );

}

