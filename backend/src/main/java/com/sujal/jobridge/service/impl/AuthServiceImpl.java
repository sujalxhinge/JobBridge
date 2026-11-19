
package com.sujal.jobridge.service.impl;

import com.sujal.jobridge.dto.RegisterRequestDTO;
import com.sujal.jobridge.dto.RegisterResponseDTO;
import com.sujal.jobridge.entity.Role;
import com.sujal.jobridge.entity.User;
import com.sujal.jobridge.repository.UserRepository;
import com.sujal.jobridge.service.AuthService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO request) {

        // 1. Check whether email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // 2. Encrypt password
        String encodedPassword =
                passwordEncoder.encode(request.getPassword());

        // 3. Create candidate user
        User user = new User(
                request.getName(),
                request.getEmail(),
                encodedPassword,
                Role.CANDIDATE
        );

        // 4. Save user
        User savedUser = userRepository.save(user);

        // 5. Prepare response DTO
        RegisterResponseDTO response = new RegisterResponseDTO();

        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole().name());

        return response;
    }
}
