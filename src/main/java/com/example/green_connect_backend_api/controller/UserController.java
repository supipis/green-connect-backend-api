package com.example.green_connect_backend_api.controller;

import com.example.green_connect_backend_api.entity.User;
import com.example.green_connect_backend_api.repository.UserRepository;
import com.example.green_connect_backend_api.security.SecurityConfig;
import com.example.green_connect_backend_api.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SecurityConfig securityConfig;


    @PostMapping("/register-user-1")
    void registerUser1(){
        User user = new User();
        user.setUsername("supipi");
        user.setPassword("password");
        userRepository.save(user);
    }

    @PostMapping("/register-1")
    public ResponseEntity<String> registerUser1(@RequestBody CreateUserDTO user) {
        return ResponseEntity.ok("Works");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody CreateUserDTO user) {
        // Check if user already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        // Encode password
        user.setPassword(securityConfig.passwordEncoder().encode(user.getPassword()));

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());

        // Save user
        userRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/hello")
    String sayHello() {
        return "Hello";
    }

}
