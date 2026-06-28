package com.barismutlu.telecomcrm.service;

import com.barismutlu.telecomcrm.model.User;
import com.barismutlu.telecomcrm.repository.UserRepository;
import com.barismutlu.telecomcrm.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public String register(String username, String password) {


        if (userRepository.findByUsername(username).isPresent()) {
            log.warn("Registration failed. Username already exists. username={}", username);
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("USER");

        userRepository.save(user);
        log.info("User registered. username={}", username);

        return jwtService.generateToken(username);
    }

    public String login(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Login failed. User not found. username={}", username);
                    return new RuntimeException("User not found");
                });

        if (!user.getPassword().equals(password)) {
            log.warn("Login failed. Wrong password. username={}", username);
            throw new RuntimeException("Wrong password");
        }

        log.info("User logged in. username={}", username);
        return jwtService.generateToken(username);
    }
}
