package com.sujal.jobridge.service.impl;

import com.sujal.jobridge.repository.UserRepository;
import com.sujal.jobridge.service.UserService;
import org.springframework.stereotype.Service;
import com.sujal.jobridge.entity.User;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
   public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
