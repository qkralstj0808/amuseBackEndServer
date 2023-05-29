package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.dto.request.AdminRequest;
import com.example.amusetravelproejct.dto.response.AdminResponse;
import com.example.amusetravelproejct.oauth.entity.UserPrincipal;
import com.example.amusetravelproejct.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

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


    public ResponseTemplate<AdminResponse.updateUserRoleType> updateUserRoleType(Long user_db_id, AdminRequest.updateUserRoleType request) {
        User findUser = userRepository.findById(user_db_id).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

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
