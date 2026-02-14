package com.college.moviealert.repository;

import com.college.moviealert.entity.UpcomingMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpcomingMovieRepository extends JpaRepository<UpcomingMovie, Long> {
}
