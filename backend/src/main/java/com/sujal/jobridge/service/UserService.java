package com.sujal.jobridge.service;

import com.sujal.jobridge.entity.User;
import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
}
