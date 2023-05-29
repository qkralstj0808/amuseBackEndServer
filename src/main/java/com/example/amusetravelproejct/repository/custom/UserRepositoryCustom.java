package com.example.amusetravelproejct.repository.custom;

import com.example.amusetravelproejct.domain.User;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> findUserListByEmail(String email);
}
