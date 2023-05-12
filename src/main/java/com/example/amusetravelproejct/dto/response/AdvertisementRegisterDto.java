package com.example.amusetravelproejct.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdvertisementRegisterDto {
    private String title;
    private Date startDate;
    private Date endDate;
    private String adType;
    private String adCategory;
    private String adContent;
    private String createdAd;
}
