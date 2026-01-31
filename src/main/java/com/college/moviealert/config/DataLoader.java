//package com.college.moviealert.config;
//
//import com.college.moviealert.entity.Theatre;
//import com.college.moviealert.repository.TheatreRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class DataLoader {
//
//    @Bean
//    CommandLineRunner loadTheatres(TheatreRepository theatreRepo) {
//        return args -> {
//
//            if (theatreRepo.count() == 0) {
//                theatreRepo.save(new Theatre("PVR Cinemas", "Chennai"));
//                theatreRepo.save(new Theatre("Rohini Theatre", "Chennai"));
//                theatreRepo.save(new Theatre("Kamala Theatre", "Chennai"));
//                theatreRepo.save(new Theatre("Sathyam Theatre", "Chennai"));
//                theatreRepo.save(new Theatre("EGA Theatre", "Chennai"));
//            }
//        };
//    }
//}
//
