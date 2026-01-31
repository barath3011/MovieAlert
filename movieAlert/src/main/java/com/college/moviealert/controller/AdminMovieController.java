package com.college.moviealert.controller;

import com.college.moviealert.dto.CreateMovieRequest;
import com.college.moviealert.dto.MovieShowBulkRequest;
import com.college.moviealert.dto.MovieShowRequest;
import com.college.moviealert.entity.Movie;
import com.college.moviealert.entity.MovieShow;
import com.college.moviealert.entity.Theatre;
import com.college.moviealert.service.AdminMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
}
