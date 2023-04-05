package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.UserAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

public interface UserAlarmRepository extends JpaRepository<UserAlarm, Long> {
}
