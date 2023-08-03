package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Alarm;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    @Query("SELECT al FROM alarm al WHERE al.id > :offsetCount ORDER BY al.id ASC")
    List<Alarm> findAdsByOffsetAndLimitCount(@Param("offsetCount") Long offsetCount,
                                                     @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable );
}

