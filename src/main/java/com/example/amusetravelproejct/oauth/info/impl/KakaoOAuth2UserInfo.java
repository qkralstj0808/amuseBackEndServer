package com.example.amusetravelproejct.oauth.info.impl;

import com.example.amusetravelproejct.oauth.info.OAuth2UserInfo;

import java.util.Map;

// 처음에 로그인할 때 카카오로 하면 카카오 로그인 페이지가 나오고 확인 누르면 이 페이지를 거쳐서 db에 정보들이 저장이 된다.
public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        if (properties == null) {
            return null;
        }

        return (String) properties.get("nickname");
    }

    // kakao에 등록되어 있는 이메일을 받아온다. (만약 이메일 허용을 했을 경우)
    @Override
    public String getEmail() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("kakao_account");

        if (properties == null) {
            return null;
        }

        return (String) properties.get("email");
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        if (properties == null) {
            return null;
        }

        return (String) properties.get("thumbnail_image");
    }
}
