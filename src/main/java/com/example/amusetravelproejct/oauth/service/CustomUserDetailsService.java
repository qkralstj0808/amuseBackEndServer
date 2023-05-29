package com.example.amusetravelproejct.oauth.service;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.oauth.entity.RoleType;
import com.example.amusetravelproejct.repository.UserRepository;
import com.example.amusetravelproejct.oauth.entity.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(RoleType.USER.getCode()));

        if (RoleType.ADMIN.equals(user.getRoleType())) {
            authorities.add(new SimpleGrantedAuthority(RoleType.ADMIN.getCode()));
        }


        return UserPrincipal.create(user,authorities);
    }
}
