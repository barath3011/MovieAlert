package com.college.moviealert.service;

import com.college.moviealert.entity.MovieShow;
import com.college.moviealert.entity.NotificationLog;
import com.college.moviealert.entity.User;
import com.college.moviealert.entity.UserPreference;
import com.college.moviealert.repository.NotificationLogRepository;
import com.college.moviealert.repository.UserPreferenceRepository;
import com.college.moviealert.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class NotificationService {

    @Autowired
    private UserPreferenceRepository userPreferenceRepository;

    @Autowired
    private EmailService emailService; // now uses SendGrid

    @Autowired
    private NotificationLogRepository notificationLogRepository;

    @Autowired
    private UserRepository userRepository;

    public void notifyUsersForShows(List<MovieShow> newShows) {

        List<UserPreference> preferences = userPreferenceRepository.findAll();

        // Group shows per user
        Map<Long, List<MovieShow>> userShows = new HashMap<>();
        Map<Long, List<NotificationLog>> userLogs = new HashMap<>();

        for (MovieShow newShow : newShows) {
            for (UserPreference pref : preferences) {

                boolean movieMatch = pref.getMovieName().equalsIgnoreCase(newShow.getMovie().getName());

                List<String> preferredTheatres =
                        Arrays.asList(pref.getTheatreName().split("\\s*,\\s*"));

                boolean theatreMatch = preferredTheatres.stream()
                        .anyMatch(t -> t.equalsIgnoreCase(newShow.getTheatre().getName()));

                boolean dateMatch = pref.getShowDate().equals(newShow.getShowDate());

                boolean timeMatch = pref.getShowTime().equals(newShow.getShowTime());

                if (movieMatch && theatreMatch && dateMatch && timeMatch) {

                    Long userId = pref.getUser().getId();

                    // Group shows for the user
                    userShows.computeIfAbsent(userId, k -> new ArrayList<>()).add(newShow);

                    // Prepare DB log
                    NotificationLog log = new NotificationLog();
                    log.setUserId(userId);
                    log.setMovieName(pref.getMovieName());
                    log.setTheatreName(newShow.getTheatre().getName());
                    log.setShowDate(newShow.getShowDate());
                    log.setShowTime(newShow.getShowTime());
                    log.setStatus("PENDING");
                    log.setCreatedAt(LocalDateTime.now());

                    userLogs.computeIfAbsent(userId, k -> new ArrayList<>()).add(log);
                }
            }
        }

        // Send one HTML email per user
        for (Long userId : userShows.keySet()) {

            List<MovieShow> shows = userShows.get(userId);

            StringBuilder emailBody = new StringBuilder();
            emailBody.append("<html><body>");
            emailBody.append("<h2>🌟 Hey, Your wait is over! 🌟</h2>");

            for (MovieShow show : shows) {
                emailBody.append("<h3>🎬 Movie Name: ").append(show.getMovie().getName()).append("</h3>")
                        .append("<p>📍 Theatre: ").append(show.getTheatre().getName()).append("<br>")
                        .append("📅 Date: ").append(show.getShowDate()).append("<br>")
                        .append("🕘 Showtimes: ").append(show.getShowTime()).append("</p>")
                        .append("<a href='").append(show.getBookingUrl())
                        .append("' style='display:inline-block;padding:10px 20px;margin:10px 0;background-color:#FF5733;color:white;text-decoration:none;border-radius:5px;'>")
                        .append("🎟️ Book Your Seats Now")
                        .append("</a><br><br>");
            }

            emailBody.append("<p>✨ \"You’ve got first preference for booking since you saved this movie in your favorites. Grab your seats before anyone else!\" ✨</p>")
                    .append("<p>Thank you for being a valued movie lover! 🍿💖</p>")
                    .append("</body></html>");

            User user = userRepository.findById(userId).orElse(null);
            String email = (user != null) ? user.getEmail() : null;

            boolean emailSent = false;

            if (email != null) {
                String subject = "New Movie Show Alert!";
                // ✅ Use SendGrid EmailService
                emailSent = emailService.sendEmail(email, subject, emailBody.toString());
            }

            // Save all logs
            List<NotificationLog> logs = userLogs.get(userId);
            for (NotificationLog log : logs) {
                log.setStatus(emailSent ? "SENT" : "FAILED");
                notificationLogRepository.save(log);
            }

            // Console
            System.out.println("Email to User " + userId + ":\n" + emailBody);
        }
    }
}