package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.domain.WithdrawalReservation;
import com.example.amusetravelproejct.repository.WithdrawalReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jdt.internal.compiler.codegen.FloatCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WithdrawalReservationService {

    private final WithdrawalReservationRepository withdrawalReservationRepository;

    private final UserService userService;

    @Scheduled(cron = "0 0 0 * * *") // 매일 0시 0분 0초에 한번 실행
    public void processWithdrawalReservations() {
        LocalDateTime currentTime = LocalDateTime.now();
        log.info("schedule 시작 현재 시간은 " + currentTime );
        List<WithdrawalReservation> reservations = withdrawalReservationRepository.findByWithdrawalTimeBefore(currentTime);

        for (WithdrawalReservation reservation : reservations) {
            // 탈퇴 처리 로직 구현 (소셜 로그인 정보 삭제 등)
            // withdrawalReservationRepository.delete(reservation); // 예약 정보 삭제
            User user = reservation.getUser();
            userService.withdrawSocialLogin(user);
        }

    }
}
