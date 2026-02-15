package com.college.moviealert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MovieAlertSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieAlertSystemApplication.class, args);
	}

}
