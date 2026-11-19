
package com.sujal.jobridge.controller;

import com.sujal.jobridge.dto.RegisterRequestDTO;
import com.sujal.jobridge.dto.RegisterResponseDTO;
import com.sujal.jobridge.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponseDTO register(
            @RequestBody RegisterRequestDTO request) {

        return authService.register(request);
    }
}
