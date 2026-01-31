package com.college.moviealert.repository;

import com.college.moviealert.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {
    List<UserPreference> findByStatus(String status);
}
