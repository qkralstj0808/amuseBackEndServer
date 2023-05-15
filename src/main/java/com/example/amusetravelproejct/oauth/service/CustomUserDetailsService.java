package com.example.amusetravelproejct.oauth.service;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.repository.UserRepository;
import com.example.amusetravelproejct.oauth.entity.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws CustomException {
        System.out.println("CustomUserDetailsService에서 loadUserByUsername 진입");
        User user = userRepository.findByUserId(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found with username: " + username);

        }

        return UserPrincipal.create(user);
    }
}
