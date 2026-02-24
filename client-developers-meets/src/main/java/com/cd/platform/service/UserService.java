package com.cd.platform.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.platform.model.User;
import com.cd.platform.model.UserRole;
import com.cd.platform.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(UserRole role, String email, String passwordHash) {
        userRepository.findByEmail(email).ifPresent(existing -> {
            throw new IllegalArgumentException("Email already exists");
        });
        User user = new User();
        user.setRole(role);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        return userRepository.save(user);
    }
}
