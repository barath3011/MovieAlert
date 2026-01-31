package com.college.moviealert.repository;

import com.college.moviealert.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {

    Optional<Theatre> findByNameIgnoreCase(String name);
}
