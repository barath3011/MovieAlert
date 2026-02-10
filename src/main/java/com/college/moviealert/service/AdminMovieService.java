package com.college.moviealert.service;

import com.college.moviealert.dto.*;
import com.college.moviealert.entity.*;
import com.college.moviealert.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminMovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private MovieShowRepository movieShowRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPreferenceRepository userPreferenceRepository;


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


    private Movie resolveMovie(UserPreferenceRequest request) {

        if (request.getMovieId() != null) {
            return movieRepository.findById(request.getMovieId())
                    .orElseThrow(() -> new RuntimeException("Movie not found by ID"));
        }

        if (request.getMovieName() != null && !request.getMovieName().isBlank()) {
            return movieRepository.findByNameIgnoreCase(request.getMovieName())
                    .orElseThrow(() -> new RuntimeException("Movie not found by name"));
        }

        throw new RuntimeException("Movie ID or Movie Name must be provided");
    }


    private List<Theatre> resolveTheatres(UserPreferenceRequest request) {

        List<Theatre> theatres = new ArrayList<>();

        if (request.getTheatreIds() != null) {
            for (Long id : request.getTheatreIds()) {
                Theatre theatre = theatreRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Theatre not found by ID: " + id));
                theatres.add(theatre);
            }
        }

        if (request.getTheatreNames() != null) {
            for (String name : request.getTheatreNames()) {
                Theatre theatre = theatreRepository.findByNameIgnoreCase(name)
                        .orElseThrow(() -> new RuntimeException("Theatre not found by name: " + name));
                theatres.add(theatre);
            }
        }

        if (theatres.isEmpty()) {
            throw new RuntimeException("At least one theatre ID or name must be provided");
        }

        return theatres;
    }


    public String savePreference(UserPreferenceRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Movie movie = resolveMovie(request);

        List<Theatre> theatres = resolveTheatres(request);

        boolean anyNewSaved = false;

        for (Theatre theatre : theatres) {
            for (LocalDate date : request.getShowDates()) {
                for (LocalTime time : request.getShowTimes()) {

                    boolean exists =
                            userPreferenceRepository
                                    .existsByUserAndMovieAndTheatreAndShowDateAndShowTime(
                                            user,
                                            movie,
                                            theatre,
                                            date,
                                            time
                                    );

                    if (exists) {
                        continue;
                    }

                    UserPreference pref = new UserPreference();
                    pref.setUser(user);
                    pref.setMovie(movie);
                    pref.setTheatre(theatre);
                    pref.setShowDate(date);
                    pref.setShowTime(time);

                    userPreferenceRepository.save(pref);
                    anyNewSaved = true;
                }
            }
        }

        if (!anyNewSaved) {
            return "Preference already exists";
        }

        return "New preference saved for existing movie";
    }


    public List<UserPreference> getActivePreferences(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userPreferenceRepository.findByUser(user);
    }


    public MovieWrapperDTO getAllMoviesTheatresAndShows() {

        List<MovieShow> shows =
                movieShowRepository.findAllByOrderByMovie_NameAscShowDateAscShowTimeAsc();

        if (shows.isEmpty()) {
            throw new RuntimeException("No shows available");
        }

        // Movie → Date → Theatre → Times
        Map<Movie, Map<LocalDate, Map<Theatre, List<String>>>> groupedData = new LinkedHashMap<>();

        for (MovieShow show : shows) {

            groupedData
                    .computeIfAbsent(show.getMovie(), m -> new LinkedHashMap<>())
                    .computeIfAbsent(show.getShowDate(), d -> new LinkedHashMap<>())
                    .computeIfAbsent(show.getTheatre(), t -> new ArrayList<>())
                    .add(show.getShowTime().toString());
        }

        List<MovieResponseDTO> movieDTOs = new ArrayList<>();

        for (var movieEntry : groupedData.entrySet()) {

            Movie movie = movieEntry.getKey();
            Map<LocalDate, Map<Theatre, List<String>>> dateMap = movieEntry.getValue();

            List<DateDTO> dateDTOs = new ArrayList<>();

            for (var dateEntry : dateMap.entrySet()) {

                LocalDate showDate = dateEntry.getKey();

                DateDTO dateDTO = new DateDTO();
                dateDTO.setDay(showDate.getMonth().toString().substring(0, 3));
                dateDTO.setDate(String.format("%02d", showDate.getDayOfMonth()));
                dateDTO.setLabel(showDate.getDayOfWeek().toString().substring(0, 3));

                List<TheatreShowDTO> theatreDTOs = new ArrayList<>();

                for (var theatreEntry : dateEntry.getValue().entrySet()) {

                    Theatre theatre = theatreEntry.getKey();

                    TheatreShowDTO theatreDTO = new TheatreShowDTO();
                    theatreDTO.setId(theatre.getId());
                    theatreDTO.setName(theatre.getName());
                    theatreDTO.setTimes(theatreEntry.getValue());

                    theatreDTOs.add(theatreDTO);
                }

                dateDTO.setTheatres(theatreDTOs);
                dateDTOs.add(dateDTO);
            }

            MovieResponseDTO movieDTO = new MovieResponseDTO();
            movieDTO.setId(movie.getId());
            movieDTO.setName(movie.getName());
            movieDTO.setDates(dateDTOs);

            movieDTOs.add(movieDTO);
        }

        MovieWrapperDTO wrapper = new MovieWrapperDTO();
        wrapper.setTheatre(movieDTOs);

        return wrapper;
    }



}
