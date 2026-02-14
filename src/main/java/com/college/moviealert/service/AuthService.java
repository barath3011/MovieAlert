package com.college.moviealert.service;

import com.college.moviealert.dto.LoginRequest;
import com.college.moviealert.dto.LoginResponse;
import com.college.moviealert.dto.RegisterRequest;
import com.college.moviealert.entity.User;
import com.college.moviealert.repository.UserRepository;
import com.college.moviealert.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Auth object: " + authentication);

        if(authentication != null) {
            System.out.println("Email from token: " + authentication.getName());
        }


        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));


        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        // 🔹 Generate JWT using email (or username)
        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                token
        );
    }

}
