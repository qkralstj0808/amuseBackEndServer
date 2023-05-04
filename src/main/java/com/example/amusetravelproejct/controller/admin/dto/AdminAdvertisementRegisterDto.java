package com.example.amusetravelproejct.controller.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdminAdvertisementRegisterDto {
    private String title;
    private Date startDate;
    private Date endDate;
    private String adType;
    private String adCategory;
    private String adContent;
    private String createdAd;
}
