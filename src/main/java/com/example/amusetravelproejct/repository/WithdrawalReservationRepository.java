package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.WithdrawalReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WithdrawalReservationRepository extends JpaRepository<WithdrawalReservation, Long> {

    List<WithdrawalReservation> findByWithdrawalTimeBefore(LocalDateTime withdrawalTime);
}
