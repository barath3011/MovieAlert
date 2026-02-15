package com.college.moviealert.controller;

import com.college.moviealert.dto.*;
import com.college.moviealert.entity.*;
import com.college.moviealert.service.AdminMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminMovieController {

    @Autowired
    private AdminMovieService adminService;

    // ---------------- Create Movie ----------------
    @PostMapping(value = "/movie")
    public Movie createMovie(@RequestBody CreateMovieRequest request) {
        return adminService.createMovie(request);
    }


    // ---------------- Create Theatre ----------------
    @PostMapping("/theatre")
    public Theatre createTheatre(@RequestBody Theatre theatre) {
        return adminService.createTheatre(theatre);
    }

    // ---------------- Release Show ----------------
//    @PostMapping("/show")
//    public String releaseShow(@RequestBody MovieShowRequest request) {
//
//        return adminService.releaseShow(
//                request.getMovieName(),
//                request.getTheatreName(),
//                request.getShowDate(),
//                request.getShowTime(),
//                request.getBookingUrl()
//        );
//    }

    //------------------ Create shows with moviename and theatre name -----------//
    @PostMapping("/show")
    public String releaseMultipleShows(
            @RequestBody MovieShowBulkRequest request
    ) {
        return adminService.releaseMultipleShows(request);
    }


    // ---------------- Get all Movies ----------------
    @GetMapping("/movies")
    public List<Movie> getAllMovies() {
        return adminService.getAllMovies();
    }

    // ---------------- Get all Theatres ----------------
    @GetMapping("/theatres")
    public List<Theatre> getAllTheatres() {
        return adminService.getAllTheatres();
    }

    // ---------------- Get all Shows ----------------
    @GetMapping("/shows")
    public List<MovieShow> getAllShows() {
        return adminService.getAllShows();
    }

    // ---------------- GET SHOWS BY MOVIE ----------------
    @GetMapping("/shows/movie/{movieName}")
    public List<MovieShow> getShowsByMovie(@PathVariable String movieName) {
        return adminService.getShowsByMovie(movieName);
    }

    // ---------------- GET SHOWS BY MOVIE + THEATRE ----------------
    @GetMapping("/shows/movie/{movieName}/theatre/{theatreName}")
    public List<MovieShow> getShowsByMovieAndTheatre(
            @PathVariable String movieName,
            @PathVariable String theatreName) {
        return adminService.getShowsByMovieAndTheatre(movieName, theatreName);
    }

    //  Available dates for movie
    @GetMapping("movies/{movieName}/dates")
    public List<LocalDate> getAvailableDates(@PathVariable String movieName) {
        return adminService.getAvailableDates(movieName);
    }

    //  Theatres for movie + date
    @GetMapping("movies/{movieName}/dates/{date}/theatres")
    public List<Theatre> getTheatres(
            @PathVariable String movieName,
            @PathVariable LocalDate date) {

        return adminService.getTheatresByMovieAndDate(movieName, date);
    }


    //  Shows for movie + date + theatre
    @GetMapping("movies/{movieName}/dates/{date}/theatres/{theatreName}/shows")
    public List<LocalTime> getShows(
            @PathVariable String movieName,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable String theatreName) {

        return adminService.getShowTimes(movieName, date, theatreName);
    }


    @PostMapping("/preferences")
    public String savePreference(@RequestBody UserPreferenceRequest request) {
        return adminService.savePreference(request);
    }

//    @GetMapping("/preferences/active")
//    public List<UserPreference> getMyActivePreferences() {
//
//        Authentication auth =
//                SecurityContextHolder.getContext().getAuthentication();
//
//        String email = auth.getName(); // comes from JWT (sub)
//
//        return adminService.getActivePreferences(email);
//    }

    @GetMapping("/movies/theatres-shows")
    public MovieWrapperDTO getAllMoviesTheatresAndShows() {
        return adminService.getAllMoviesTheatresAndShows();
    }

    // Add movie (JSON input)
    @PostMapping("/upcoming-movie")
    public UpcomingMovie addMovie(@RequestBody UpcomingMovie movie) {
        return adminService.addMovie(movie);
    }

    // Get all movies
    @GetMapping("/upcoming-movie")
    public List<UpcomingMovie> getAllUpcomingMovies() {
        return adminService.getAllUpcomingMovies();
    }


    @GetMapping("/preferences/active")
    public List<UserPreferenceResponse> getActive(Authentication authentication) {

        String email = authentication.getName();   // extracted from JWT

        return adminService.getActivePreferencesByEmail(email);
    }

    @GetMapping("/preferences/completed")
    public List<UserPreferenceResponse> getCompleted(Authentication authentication) {

        String email = authentication.getName();

        return adminService.getCompletedPreferencesByEmail(email);
    }


    @PutMapping("/complete")
    public String complete(@RequestBody CompleteRequest request) {
        adminService.completePreferences(request.getIds());
        return "Marked as completed";
    }








}
