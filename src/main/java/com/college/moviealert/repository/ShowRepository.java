//package com.college.moviealert.repository;
//
//import com.college.moviealert.entity.Show;
//import com.college.moviealert.entity.ShowDate;
//import com.college.moviealert.entity.Theatre;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface ShowRepository extends JpaRepository<Show, Long> {
//    Optional<Show> findByShowTime(String showTime);
//
//    List<Show> findByShowDateAndTheatre(ShowDate showDate, Theatre theatre);
//
//}
//
