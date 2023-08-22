package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.domain.WithdrawalReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WithdrawalReservationRepository extends JpaRepository<WithdrawalReservation, Long> {

    List<WithdrawalReservation> findByWithdrawalTimeBefore(LocalDateTime withdrawalTime);

    WithdrawalReservation findByUserId(Long user_id);

    @Transactional
    @Modifying
    @Query("DELETE FROM WithdrawalReservation wr WHERE wr.user.id = :user_id")
    void deleteByUserId(Long user_id);
}
