package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;

import com.example.amusetravelproejct.domain.Admin;
import com.example.amusetravelproejct.config.util.UtilMethod;
import com.example.amusetravelproejct.domain.Guide;

import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.dto.request.AdminRequest;
import com.example.amusetravelproejct.dto.response.AdminResponse;
import com.example.amusetravelproejct.oauth.entity.UserPrincipal;

import com.example.amusetravelproejct.repository.AdminRepository;

import com.example.amusetravelproejct.repository.GuideRepository;

import com.example.amusetravelproejct.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    private final AdminRepository adminRepository;

    private final GuideRepository guideRepository;

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

    @Transactional(readOnly = false)
    public AdminResponse.GuideInfo createGuide(AdminRequest.guide request,UtilMethod utilMethod){
        Guide guide = new Guide();
        guide.setCode(request.getGuideCode());
        guide.setName(request.getName());
        guide.setImgUrl(utilMethod.getImgUrl(request.getBase64Data(),request.getFileName()));
        guide.setEmile(request.getEmail());
        guide.setIntroduce(request.getIntroduce());
        guideRepository.save(guide);

        return new AdminResponse.GuideInfo(guide.getId(), guide.getCode(), guide.getName(),guide.getEmile(), guide.getImgUrl(),guide.getIntroduce());
    }
    @Transactional(readOnly = false)
    public AdminResponse.GuideInfo updateGuide(AdminRequest.guide request,String guideCode,UtilMethod utilMethod){
        Guide guide = guideRepository.findByCode(guideCode).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_GUIDE)
        );
        guide.setName(request.getName());
        if (request.getFileName() != null){
            guide.setImgUrl(utilMethod.getImgUrl(request.getBase64Data(),request.getFileName()));
        }
        guide.setEmile(request.getEmail());
        guide.setIntroduce(request.getIntroduce());
        guideRepository.save(guide);

        return new AdminResponse.GuideInfo(guide.getId(), guide.getCode(), guide.getName(),guide.getEmile(), guide.getImgUrl(),guide.getIntroduce());
    }

    public AdminResponse.GuideInfo readGuide(String guideCode) {
        Guide guide = guideRepository.findByCode(guideCode).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_GUIDE)
        );

        return new AdminResponse.GuideInfo(guide.getId(),guide.getCode(),guide.getName(),guide.getEmile(),guide.getImgUrl(),guide.getIntroduce());
    }
    @Transactional(readOnly = false)

    public void deleteGuide(String guideCode){
        Guide guide = guideRepository.findByCode(guideCode).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_GUIDE)
        );
        guideRepository.delete(guide);
    }

    public AdminResponse.ListGuide listGuide(Long page, Long limit){
        Pageable pageable = PageRequest.of(Math.toIntExact(page - 1), Math.toIntExact(limit));
        List<AdminResponse.GuideInfo> guideInfos = new ArrayList<>();
        Page<Guide> guides = guideRepository.findAll(pageable);
        Long pageCount = (long) guides.getTotalPages();
        guides.forEach(data ->{
            guideInfos.add(new AdminResponse.GuideInfo(data.getId(),data.getCode(), data.getName(),data.getEmile(),data.getImgUrl(), data.getIntroduce()));

            log.info(data.getCode());
        });
        AdminResponse.ListGuide listGuide = new AdminResponse.ListGuide(pageCount,guideInfos);
        return listGuide;
    }
}
