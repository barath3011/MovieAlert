package com.college.moviealert.repository;

import com.college.moviealert.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {

    // Get all notifications for a user
    List<NotificationLog> findByUserId(Long userId);

    // Optional: get notifications by status (SENT / FAILED / PENDING)
    List<NotificationLog> findByStatus(String status);
}