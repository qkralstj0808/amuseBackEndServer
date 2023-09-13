package com.example.amusetravelproejct.service;
import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.config.util.UtilMethod;
import com.example.amusetravelproejct.domain.*;
import com.example.amusetravelproejct.domain.person_enum.Grade;
import com.example.amusetravelproejct.dto.request.AdminRequest;
import com.example.amusetravelproejct.dto.request.AuthRequest;
import com.example.amusetravelproejct.dto.request.UserRequest;
import com.example.amusetravelproejct.dto.response.AdminResponse;
import com.example.amusetravelproejct.dto.response.UserResponse;
import com.example.amusetravelproejct.oauth.entity.ProviderType;
import com.example.amusetravelproejct.oauth.entity.RoleType;
import com.example.amusetravelproejct.oauth.entity.UserPrincipal;
import com.example.amusetravelproejct.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    private final AdminRepository adminRepository;

    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final UserRefreshTokenService userRefreshTokenService;

    private final GuideRepository guideRepository;

    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    private final WithdrawalReservationRepository withdrawalReservationRepository;

    @Value("${spring.security.oauth2.client.registration.naver.clientId}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.naver.clientSecret}")
    private String naverclientSecret;

    private final String KAKAO_ADMIN_KEY = "6c0306342ad8c3e69157deac322bd553";

    public User getUser(String userId) {
        return userRepository.findByUserId(userId);
    }

    public User getUserById(Long user_db_id){
        return userRepository.findById(user_db_id).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public User getUserByPrincipal(UserPrincipal userPrincipal){
        if(userPrincipal == null){
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        }

        String user_id = userPrincipal.getUserId();

        return getUser(user_id);
    }

    public ResponseTemplate<UserResponse.getAllUserInfo> getAllUserInfo() {
        List<User> allUser = userRepository.findAll();
        return new ResponseTemplate(new UserResponse.getAllUserInfo(allUser.stream().map(
                user -> new UserResponse.getUserInfo(
                        user.getId(),
                        user.getUserId(),
                        user.getUsername(),
                        user.getProfileImageUrl(),
                        user.getEmail(),
                        user.getGrade(),
                        user.getPhoneNumber(),
                        user.getAdvertisementTrue(),
                        user.getOver14AgeTrue()
                )
        ).collect(Collectors.toList())
        ));
    }

    @Transactional
    public ResponseTemplate<UserResponse.getUserInfo> changeUserGrade(Long user_db_id,UserRequest.changeUserGrade request) {
        User findUser = getUserById(user_db_id);
        findUser.setGrade(request.getGrade());
        userRepository.save(findUser);

        return new ResponseTemplate(new UserResponse.getUserInfo(
                findUser.getId(),
                findUser.getUserId(),
                findUser.getUsername(),
                findUser.getProfileImageUrl(),
                findUser.getEmail(),
                findUser.getGrade(),
                findUser.getPhoneNumber(),
                findUser.getAdvertisementTrue(),
                findUser.getOver14AgeTrue()));
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

//    @Transactional
//    public ResponseTemplate<AdminResponse.updateUserRoleType> updateUserRoleType(Long user_db_id, AdminRequest.updateUserRoleType request) {
//        User findUser = userRepository.findById(user_db_id).orElseThrow(
//                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
//        );
//
//        Optional<Admin> findAdmin = adminRepository.findByUserId(findUser.getUserId());
//
//        if(findAdmin.isEmpty()){
//            Admin admin = new Admin();
////            admin.setUser(findUser);
//            admin.setEmail(findUser.getEmail());
//            admin.setEmail(findUser.getAdminId());
//            admin.setPassword(findUser.getPassword());
//            admin.setName(findUser.getUsername());
//            admin.setProfileImgLink(findUser.getProfileImageUrl());
////            admin.setUserId(findUser.getUserId());
////            admin.setProviderType(findUser.getProviderType());
//            adminRepository.save(admin);
//        }else{
//            Admin admin = findAdmin.get();
////            admin.setUser(findUser);
//            admin.setEmail(findUser.getEmail());
//            admin.setAdminId(findUser.getAdmin());
//            admin.setPassword(findUser.getPassword());
//            admin.setName(findUser.getUsername());
//            admin.setProfileImgLink(findUser.getProfileImageUrl());
////            admin.setUserId(findUser.getUserId());
////            admin.setProviderType(findUser.getProviderType());
//        }
//
//
//        findUser.setRoleType(request.getRoleType());
//
//
//        return new ResponseTemplate(new AdminResponse.updateUserRoleType(
//                new AdminResponse.UserInfo(
//                        findUser.getId(),
//                        findUser.getUserId(),
//                        findUser.getUsername(),
//                        findUser.getEmail(),
//                        findUser.getProfileImageUrl(),
//                        findUser.getProviderType(),
//                        findUser.getRoleType()
//                )
//        ));
//    }

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
    public AdminResponse.GuideInfo updateGuide(AdminRequest.guide request,Long guide_db_id,UtilMethod utilMethod){
        Guide guide = guideRepository.findById(guide_db_id).orElseThrow(
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

    public AdminResponse.GuideInfo readGuide(Long guide_db_id) {
        Guide guide = guideRepository.findById(guide_db_id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_GUIDE)
        );

        return new AdminResponse.GuideInfo(guide.getId(),guide.getCode(),guide.getName(),guide.getEmile(),guide.getImgUrl(),guide.getIntroduce());
    }
    @Transactional(readOnly = false)

    public void deleteGuide(Long guide_db_id){
        Guide guide = guideRepository.findById(guide_db_id).orElseThrow(
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

    @Transactional
    public ResponseTemplate<UserResponse.getUserInfo> createUserInfo(User findUser, UserRequest.createUserInfo request) {
        findUser.setPhoneNumber(request.getPhone_number());
        userRepository.save(findUser);
        return new ResponseTemplate(new UserResponse.getUserInfo(
                findUser.getId(),
                findUser.getUserId(),
                findUser.getUsername(),
                findUser.getProfileImageUrl(),
                findUser.getEmail(),
                findUser.getGrade(),
                findUser.getPhoneNumber(),
                findUser.getAdvertisementTrue(),
                findUser.getOver14AgeTrue()));
    }
    

    @Transactional
    public ResponseTemplate<UserResponse.getUserInfoBeforeLogin> createUserInfoBeforeLogin(User findUser, UserRequest.createUserInfoBeforeLogin request) {

        findUser.setPhoneNumber(request.getPhone_number());
        findUser.setAdvertisementTrue(request.getAdvertisement_true());
        findUser.setOver14AgeTrue(request.getOver_14_age_true());
        userRepository.save(findUser);
        return new ResponseTemplate(new UserResponse.getUserInfoBeforeLogin(
                findUser.getUserId(),
                findUser.getUsername(),
                findUser.getProfileImageUrl(),
                findUser.getEmail(),
                findUser.getGrade(),
                findUser.getPhoneNumber(),
                findUser.getAdvertisementTrue(),
                findUser.getOver14AgeTrue()));
    }

    public ResponseTemplate<UserResponse.getUserInfoBeforeLogin> getUserInfoBeforeLogin(User findUser) {
        return new ResponseTemplate(new UserResponse.getUserInfoBeforeLogin(
                findUser.getUserId(),
                findUser.getUsername(),
                findUser.getProfileImageUrl(),
                findUser.getEmail(),
                findUser.getGrade(),
                findUser.getPhoneNumber(),
                findUser.getAdvertisementTrue(),
                findUser.getOver14AgeTrue()));
    }

    @Transactional
    public void withdrawLocalLogin(User user) {
        userRefreshTokenService.deleteRefreshToken(user.getUserId());
        userRepository.delete(user);
    }

    @Transactional
    public void withdrawSocialLogin(User user) {
        if(user.getProviderType().equals(ProviderType.KAKAO)){
            unlinkKaKao(user);
        }else if(user.getProviderType().equals(ProviderType.GOOGLE)){
            unlinkGoogle(user);
        }else if(user.getProviderType().equals(ProviderType.NAVER)){
            unlinkNaver(user);
        }

        userRefreshTokenService.deleteRefreshToken(user.getUserId());
        userRepository.delete(user);
    }

    private void unlinkKaKao(User user){
        String url = "https://kapi.kakao.com/v1/user/unlink";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + KAKAO_ADMIN_KEY);

        // 요청 본문 설정
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("target_id_type", "user_id");
        body.add("target_id", user.getUserId());

        // HTTP 요청을 보내기 위한 객체 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        // POST 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        log.info(response.getBody());

        if(response.getBody() == null) {
            throw new CustomException(ErrorCode.UNLINKFAIL);
        }

    }

    private void unlinkGoogle(User user){
//        UserRefreshToken googleAccessToken = userRefreshTokenRepository.findByUserId(user.getUserId() + "SocialAccessToken");

        OAuth2AuthorizedClient oAuth2AuthorizedClientGoogle = oAuth2AuthorizedClientService.loadAuthorizedClient("google", user.getUserId());
        String accessToken = null;
        if(oAuth2AuthorizedClientGoogle == null){
            log.info("oAuth2AuthorizedClient google이 null이다.");
            new CustomException(ErrorCode.OAUTHORIZEDCLIENT_NULL);
        }else {
            accessToken = oAuth2AuthorizedClientGoogle.getAccessToken().getTokenValue();
        }

        log.info("google access token : " + accessToken);

        String revokeEndpoint = "https://oauth2.googleapis.com/revoke?token=" + accessToken;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(revokeEndpoint, HttpMethod.POST, entity, String.class);
        log.info("google 연동 해제의 결과는 : " + response.getBody());
    }

    private void unlinkNaver(User user){
//        UserRefreshToken naverAccessToken = userRefreshTokenRepository.findByUserId(user.getUserId() + "SocialAccessToken");

        String url = "https://nid.naver.com/oauth2.0/token";
        String clientId = naverClientId;
        String clientSecret = naverclientSecret;
        OAuth2AuthorizedClient oAuth2AuthorizedClientNaver = oAuth2AuthorizedClientService.loadAuthorizedClient("naver", user.getUserId());
        String accessToken = null;
        if(oAuth2AuthorizedClientNaver == null){
            log.info("oAuth2AuthorizedClientNaver가 null이다.");
            new CustomException(ErrorCode.OAUTHORIZEDCLIENT_NULL);
        }else {
            accessToken = oAuth2AuthorizedClientNaver.getAccessToken().getTokenValue();
        }

        String requestBody = String.format("grant_type=delete&client_id=%s&client_secret=%s&access_token=%s&service_provider=NAVER",
                clientId, clientSecret, accessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        log.info("naver 연동 해제의 결과는 : " + response.getBody());
    }

    @Transactional
    public void withdrawReservation(User user) {
        WithdrawalReservation findWithdrawalReservation = withdrawalReservationRepository.findByUserId(user.getId());

        if(findWithdrawalReservation == null){
            WithdrawalReservation withdrawalReservation = WithdrawalReservation.builder()
                    .user(user)
                    .withdrawalTime(LocalDateTime.now().plus(7, ChronoUnit.DAYS))
                    .build();
            withdrawalReservationRepository.save(withdrawalReservation);
        }

    }

    @Transactional
    public User createUser(String userId, String db_password) {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(db_password);
        user.setProviderType(ProviderType.LOCAL);
        user.setGrade(Grade.Bronze);
        user.setRoleType(RoleType.USER);

        userRepository.save(user);
        return user;
    }

    @Transactional
    public ResponseTemplate<String> changeUserPassword(User findUser, AuthRequest.changePassword request) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String db_password = encoder.encode(request.getPassword_for_change());

        findUser.setPassword(db_password);
        userRepository.save(findUser);
        return new ResponseTemplate("비밀 번호 변경 완료");
    }

}
