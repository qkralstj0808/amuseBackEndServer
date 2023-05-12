package com.example.amusetravelproejct.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AdvertisementRegisterDbDto {
    private Long id;
    private String title;
    private Date startDate;
    private Date endDate;
    private String adType;
    private String adCategory;
    private String adContent;
    private LocalDateTime adDate;
    private String updatedAd;
}
