package com.example.amusetravelproejct.social.api.service;

import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String userId) {
        return userRepository.findByUserId(userId);
    }
}
