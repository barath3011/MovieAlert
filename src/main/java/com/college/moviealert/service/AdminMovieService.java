package com.college.moviealert.service;

import com.college.moviealert.dto.CreateMovieRequest;
import com.college.moviealert.dto.MovieShowBulkRequest;
import com.college.moviealert.entity.*;
import com.college.moviealert.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminMovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private MovieShowRepository movieShowRepository;


    // ---------------- Create Movie ----------------
    public Movie createMovie(CreateMovieRequest request) {

        if (request.getMovieName() == null || request.getMovieName().trim().isEmpty()) {
            throw new RuntimeException("Movie name cannot be empty");
        }

        return movieRepository
                .findByNameIgnoreCase(request.getMovieName().trim())
                .orElseGet(() -> {
                    Movie movie = new Movie();
                    movie.setName(request.getMovieName().trim());
                    return movieRepository.save(movie);
                });
    }


    // ---------------- Create Theatre ----------------
    public Theatre createTheatre(Theatre theatre) {

        return theatreRepository
                .findByNameIgnoreCase(theatre.getName())
                .orElseGet(() -> theatreRepository.save(
                        new Theatre(theatre.getName())
                ));
    }


//    public String releaseShow(
//            String movieName,
//            String theatreName,
//            LocalDate showDate,
//            LocalTime showTime,
//            String bookingUrl
//    ) {
//
//        Movie movie = movieRepository
//                .findByNameIgnoreCase(movieName)
//                .orElseGet(() -> movieRepository.save(new Movie(movieName)));
//
//        Theatre theatre = theatreRepository
//                .findByNameIgnoreCase(theatreName)
//                .orElseGet(() -> theatreRepository.save(new Theatre(theatreName)));
//
//        boolean exists = movieShowRepository
//                .existsByMovieAndTheatreAndShowDateAndShowTime(
//                        movie, theatre, showDate, showTime
//                );
//
//        if (exists) {
//            return "This show is already released!";
//        }
//
//        MovieShow show = new MovieShow();
//        show.setMovie(movie);
//        show.setTheatre(theatre);
//        show.setShowDate(showDate);
//        show.setShowTime(showTime);
//        show.setBookingUrl(bookingUrl);
//
//        movieShowRepository.save(show);
//
//        return "Show released successfully!";
//    }


    // ---------------- Get All Movies ----------------
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // ---------------- Get All Theatres ----------------
    public List<Theatre> getAllTheatres() {
        return theatreRepository.findAll();
    }

    // ---------------- Get All Shows ----------------
    public List<MovieShow> getAllShows() {
        return movieShowRepository.findAll();
    }

    public String releaseMultipleShows(MovieShowBulkRequest request) {

        Movie movie = movieRepository
                .findByNameIgnoreCase(request.getMovieName())
                .orElseGet(() -> movieRepository.save(
                        new Movie(request.getMovieName())
                ));

        Theatre theatre = theatreRepository
                .findByNameIgnoreCase(request.getTheatreName())
                .orElseGet(() -> theatreRepository.save(
                        new Theatre(request.getTheatreName())
                ));


// Date
//        ShowDate showDate = showDateRepository
//                .findByShowDate(request.getShowDate())
//                .orElseGet(() -> showDateRepository.save(
//                        new ShowDate(request.getShowDate())
//                ));
//
//        List<Show> shows = new ArrayList<>();
//
//        for (LocalTime time : request.getShowTimes()) {
//
//            String timeStr = time.toString(); // "09:00"
//
//            Show show = showRepository
//                    .findByShowTime(timeStr)
//                    .orElseGet(() -> showRepository.save(
//                            new Show(timeStr)
//                    ));
//
//            shows.add(show);
//        }




        int insertedCount = 0;

        for (LocalTime time : request.getShowTimes()) {

            boolean exists = movieShowRepository
                    .existsByMovieAndTheatreAndShowDateAndShowTime(
                            movie,
                            theatre,
                            request.getShowDate(),
                            time
                    );

            if (!exists) {
                MovieShow show = new MovieShow();
                show.setMovie(movie);
                show.setTheatre(theatre);
                show.setShowDate(request.getShowDate());
                show.setShowTime(time);
                show.setBookingUrl(request.getBookingUrl());

                movieShowRepository.save(show);
                insertedCount++;
            }
        }

        if (insertedCount == 0) {
            return "All shows already released!";
        }

        return insertedCount + " new show(s) released successfully!";
    }



    // ---------------- GET SHOWS BY MOVIE ----------------
    public List<MovieShow> getShowsByMovie(String movieName) {
        return movieShowRepository.findByMovie_NameIgnoreCase(movieName);
    }

    // ---------------- GET SHOWS BY MOVIE + THEATRE ----------------
    public List<MovieShow> getShowsByMovieAndTheatre(String movieName, String theatreName) {
        return movieShowRepository.findByMovie_NameIgnoreCaseAndTheatre_NameIgnoreCase(movieName, theatreName);
    }

    //  Get available dates for a movie
    public List<LocalDate> getAvailableDates(String movieName) {

        Movie movie = movieRepository.findByNameIgnoreCase(movieName)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        return movieShowRepository.findAvailableDatesByMovie(movie.getId());
    }


    //  Get theatres for movie + date
    public List<Theatre> getTheatresByMovieAndDate(String movieName, LocalDate date) {

        Movie movie = movieRepository.findByNameIgnoreCase(movieName)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        return movieShowRepository.findTheatresByMovieAndDate(
                movie.getId(),
                date
        );
    }


    //  Get shows for movie + date + theatre
    public List<LocalTime> getShowTimes(
            String movieName,
            LocalDate date,
            String theatreName
    ) {

        Movie movie = movieRepository.findByNameIgnoreCase(movieName)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Theatre theatre = theatreRepository.findByNameIgnoreCase(theatreName)
                .orElseThrow(() -> new RuntimeException("Theatre not found"));

        return movieShowRepository.findShowTimesByMovieDateAndTheatre(
                movie.getId(),
                theatre.getId(),
                date
        );
    }




}
