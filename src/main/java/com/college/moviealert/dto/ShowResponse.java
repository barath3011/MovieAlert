package com.college.moviealert.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ShowResponse {

    private Long showId;
    private String movieName;
    private String theatreName;
    private LocalDate showDate;
    private LocalTime showTime;
    private String bookingUrl;

    public ShowResponse(Long showId, String movieName, String theatreName,
                        LocalDate showDate, LocalTime showTime, String bookingUrl) {
        this.showId = showId;
        this.movieName = movieName;
        this.theatreName = theatreName;
        this.showDate = showDate;
        this.showTime = showTime;
        this.bookingUrl = bookingUrl;
    }

    public Long getShowId() {
        return showId;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public LocalDate getShowDate() {
        return showDate;
    }

    public LocalTime getShowTime() {
        return showTime;
    }

    public String getBookingUrl() {
        return bookingUrl;
    }
}
