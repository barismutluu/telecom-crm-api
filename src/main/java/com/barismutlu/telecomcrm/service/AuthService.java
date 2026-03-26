package com.barismutlu.telecomcrm.service;

import com.barismutlu.telecomcrm.model.User;
import com.barismutlu.telecomcrm.repository.UserRepository;
import com.barismutlu.telecomcrm.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public String register(String username, String password) {


        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("USER");

        userRepository.save(user);

        return jwtService.generateToken(username);
    }

    public String login(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Wrong password");
        }

        return jwtService.generateToken(username);
    }
}