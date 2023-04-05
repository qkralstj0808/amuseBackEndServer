package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Alarm findByContent(String s);
}
