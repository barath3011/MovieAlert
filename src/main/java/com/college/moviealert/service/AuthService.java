package com.college.moviealert.service;

import com.college.moviealert.dto.LoginRequest;
import com.college.moviealert.dto.LoginResponse;
import com.college.moviealert.dto.RegisterRequest;
import com.college.moviealert.entity.User;
import com.college.moviealert.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            return "Username already exists";
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already exists";
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            return "Phone number already exists";
        }

        User user = new User(
                request.getName(),
                request.getUsername(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getPassword()
        );

        userRepository.save(user);
        return "User registered successfully";
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                "Login successful"
        );
    }
}
