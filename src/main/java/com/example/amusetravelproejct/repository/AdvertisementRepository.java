package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Advertisement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    @Query("SELECT ad FROM advertisement ad WHERE ad.id > :offsetCount ORDER BY ad.id ASC")
    List<Advertisement> findAdsByOffsetAndLimitCount(@Param("offsetCount") Long offsetCount,
                                                     @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable );
}
