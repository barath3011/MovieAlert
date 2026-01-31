package com.college.moviealert.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MovieShowBulkRequest {

    private String movieName;
    private String theatreName;
    private LocalDate showDate;
    private List<LocalTime> showTimes;
    private String bookingUrl;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public LocalDate getShowDate() {
        return showDate;
    }

    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
    }

    public List<LocalTime> getShowTimes() {
        return showTimes;
    }

    public void setShowTimes(List<LocalTime> showTimes) {
        this.showTimes = showTimes;
    }

    public String getBookingUrl() {
        return bookingUrl;
    }

    public void setBookingUrl(String bookingUrl) {
        this.bookingUrl = bookingUrl;
    }
}
