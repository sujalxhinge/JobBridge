package com.sujal.jobridge.config;

import com.sujal.jobridge.entity.Role;
import com.sujal.jobridge.entity.User;
import com.sujal.jobridge.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CandidateDataLoader {

    @Bean
    public CommandLineRunner createCandidate(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            String email = "candidate@test.com";

            if (userRepository.findByEmail(email).isEmpty()) {

                User candidate = new User(
                        "Test Candidate",
                        email,
                        passwordEncoder.encode("Candidate@123"),
                        Role.CANDIDATE
                );

                userRepository.save(candidate);

                System.out.println("Test candidate created successfully!");
            }
        };
    }
}