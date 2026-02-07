//package com.college.moviealert.repository;
//
//import com.college.moviealert.entity.Movie;
//import com.college.moviealert.entity.ShowDate;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//public interface ShowDateRepository extends JpaRepository<ShowDate, Long> {
//    Optional<ShowDate> findByShowDate(LocalDate showDate);
//
//    List<ShowDate> findByMovie(Movie movie);
//
//    Optional<ShowDate> findByMovieAndShowDate(Movie movie, LocalDate showDate);
//}
//
