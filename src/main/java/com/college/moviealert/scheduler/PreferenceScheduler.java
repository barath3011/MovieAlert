package com.college.moviealert.scheduler;

import com.college.moviealert.repository.UserPreferenceRepository;
import com.college.moviealert.enums.PreferenceStatus;
import com.college.moviealert.entity.UserPreference;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class PreferenceScheduler {

    private final UserPreferenceRepository preferenceRepository;

    public PreferenceScheduler(UserPreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    // Runs every day at midnight
    @Scheduled(fixedRate = 60000)
    public void moveExpiredPreferencesToCompleted() {

        LocalDate today = LocalDate.now();

        List<UserPreference> expiredPreferences =
                preferenceRepository.findByStatusAndShowDateBefore(
                        PreferenceStatus.ACTIVE, today);

        for (UserPreference pref : expiredPreferences) {
            pref.setStatus(PreferenceStatus.COMPLETED);
        }

        preferenceRepository.saveAll(expiredPreferences);
    }
}

