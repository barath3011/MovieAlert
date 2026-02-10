package com.college.moviealert.repository;

import com.college.moviealert.entity.Movie;
import com.college.moviealert.entity.MovieShow;
import com.college.moviealert.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    List<MovieShow> findByMovie_NameIgnoreCase(String movieName);

    List<MovieShow> findByMovie_NameIgnoreCaseAndTheatre_NameIgnoreCase(
            String movieName,
            String theatreName
    );

    @Query("""
        SELECT DISTINCT ms.showDate
        FROM MovieShow ms
        WHERE ms.movie.id = :movieId
        ORDER BY ms.showDate
    """)
    List<LocalDate> findAvailableDatesByMovie(@Param("movieId") Long movieId);

    @Query("""
        SELECT DISTINCT ms.theatre
        FROM MovieShow ms
        WHERE ms.movie.id = :movieId
          AND ms.showDate = :date
    """)
    List<Theatre> findTheatresByMovieAndDate(
            @Param("movieId") Long movieId,
            @Param("date") LocalDate date
    );

    @Query("""
        SELECT ms.showTime
        FROM MovieShow ms
        WHERE ms.movie.id = :movieId
          AND ms.theatre.id = :theatreId
          AND ms.showDate = :date
        ORDER BY ms.showTime
    """)
    List<LocalTime> findShowTimesByMovieDateAndTheatre(
            @Param("movieId") Long movieId,
            @Param("theatreId") Long theatreId,
            @Param("date") LocalDate date
    );

    List<MovieShow> findByMovie_NameIgnoreCaseOrderByShowDateAscShowTimeAsc(String movieName);

}
