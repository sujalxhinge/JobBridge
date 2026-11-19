package com.sujal.jobridge.service;

import com.sujal.jobridge.dto.RegisterRequestDTO;
import com.sujal.jobridge.dto.RegisterResponseDTO;

public interface AuthService {

    RegisterResponseDTO register(RegisterRequestDTO request);
}