package com.example.amusetravelproejct.controller.admin.service;

import com.example.amusetravelproejct.domain.Alarm;
import com.example.amusetravelproejct.repository.AlarmRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;


    public Optional<Alarm> saveAlarm(Alarm alarm) {
        return Optional.of(alarmRepository.save(alarm));
    }

    public Optional<Alarm> findAlarmById(Long id) {

        return Optional.of(alarmRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 알람이 없습니다.")
        ));
    }

}
