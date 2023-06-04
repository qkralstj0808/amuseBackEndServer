package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.domain.Admin;
import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.dto.request.AdminRequest;
import com.example.amusetravelproejct.dto.response.AdminResponse;
import com.example.amusetravelproejct.oauth.entity.UserPrincipal;
import com.example.amusetravelproejct.repository.AdminRepository;
import com.example.amusetravelproejct.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    public User getUser(String userId) {
        return userRepository.findByUserId(userId);
    }

    public User getUserByPrincipal(UserPrincipal userPrincipal){
        if(userPrincipal == null){
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        }

        String user_id = userPrincipal.getUserId();

        return getUser(user_id);
    }

    public ResponseTemplate<AdminResponse.getUser> getUserByEmail(String email) {
        List<User> userListByEmail = userRepository.findUserListByEmail(email);
        if(userListByEmail.size() == 0){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return new ResponseTemplate(userListByEmail.stream().map(
                findUser -> new AdminResponse.UserInfo(
                        findUser.getId(),
                        findUser.getUserId(),
                        findUser.getUsername(),
                        findUser.getEmail(),
                        findUser.getProfileImageUrl(),
                        findUser.getProviderType(),
                        findUser.getRoleType()
                )
        ).collect(Collectors.toList()));
    }

    @Transactional
    public ResponseTemplate<AdminResponse.updateUserRoleType> updateUserRoleType(Long user_db_id, AdminRequest.updateUserRoleType request) {
        User findUser = userRepository.findById(user_db_id).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        Optional<Admin> findAdmin = adminRepository.findByUserId(findUser.getUserId());

        if(findAdmin.isEmpty()){
            Admin admin = new Admin();
            admin.setUser(findUser);
            admin.setEmail(findUser.getEmail());
            admin.setPassword(findUser.getPassword());
            admin.setName(findUser.getUsername());
            admin.setProfileImgLink(findUser.getProfileImageUrl());
            admin.setUserId(findUser.getUserId());
            admin.setProviderType(findUser.getProviderType());
            adminRepository.save(admin);
        }else{
            Admin admin = findAdmin.get();
            admin.setUser(findUser);
            admin.setEmail(findUser.getEmail());
            admin.setPassword(findUser.getPassword());
            admin.setName(findUser.getUsername());
            admin.setProfileImgLink(findUser.getProfileImageUrl());
            admin.setUserId(findUser.getUserId());
            admin.setProviderType(findUser.getProviderType());
        }


        findUser.setRoleType(request.getRoleType());


        return new ResponseTemplate(new AdminResponse.updateUserRoleType(
                new AdminResponse.UserInfo(
                        findUser.getId(),
                        findUser.getUserId(),
                        findUser.getUsername(),
                        findUser.getEmail(),
                        findUser.getProfileImageUrl(),
                        findUser.getProviderType(),
                        findUser.getRoleType()
                )
        ));
    }
}
