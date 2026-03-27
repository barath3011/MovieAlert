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
    private EmailService emailService; // ✅ Now uses Brevo internally

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
                    log.setMovieShowId(newShow.getId());
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
            emailBody.append("<html><body style='font-family:Arial;'>");

            emailBody.append("<h2>🌟 Hey Movie Lover! Your wait is over! 🎉</h2>");
            emailBody.append("<p>Good news! Your preferred movie show is now available:</p>");

            for (MovieShow show : shows) {
                emailBody.append("<div style='border:1px solid #ddd;padding:10px;margin:10px 0;border-radius:8px;'>")
                        .append("<h3>🎬 ").append(show.getMovie().getName()).append("</h3>")
                        .append("<p>")
                        .append("📍 Theatre: ").append(show.getTheatre().getName()).append("<br>")
                        .append("📅 Date: ").append(show.getShowDate()).append("<br>")
                        .append("🕘 Time: ").append(show.getShowTime())
                        .append("</p>")
                        .append("<a href='").append(show.getBookingUrl())
                        .append("' style='display:inline-block;padding:10px 20px;background-color:#FF5733;color:white;text-decoration:none;border-radius:5px;'>")
                        .append("🎟️ Book Now")
                        .append("</a>")
                        .append("</div>");
            }

            emailBody.append("<p style='color:green;'><b>")
                    .append("You got FIRST preference because you saved this movie! Book before others 🎯")
                    .append("</b></p>");

            emailBody.append("<p>🍿 Thank you for using MovieAlert!</p>");
            emailBody.append("</body></html>");

            User user = userRepository.findById(userId).orElse(null);
            String email = (user != null) ? user.getEmail() : null;

            boolean emailSent = false;

            if (email != null) {
                String subject = "🎬 Your Preferred Movie is Now Available!";

                // ✅ Uses Brevo EmailService internally
                emailSent = emailService.sendEmail(email, subject, emailBody.toString());
            }

            // Save all logs
            List<NotificationLog> logs = userLogs.get(userId);
            for (NotificationLog log : logs) {
                log.setStatus(emailSent ? "SENT" : "FAILED");
                notificationLogRepository.save(log);
            }

            // Console log
            System.out.println("Email to User " + userId + " sent via Brevo");
        }
    }
}