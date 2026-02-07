//package com.college.moviealert.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//
//@Data
//@AllArgsConstructor
//@Entity
//@Table(name = "show_dates")
//public class ShowDate {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private LocalDate showDate;
//
//    public ShowDate() {}
//
//    // ✅ ADD THIS CONSTRUCTOR
//    public ShowDate(LocalDate showDate) {
//        this.showDate = showDate;
//    }
//    // getters & setters
//}
//
