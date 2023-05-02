package com.example.amusetravelproejct.controller.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemInfoDto {
    private String itemCode = "0";
    private Long category;
    private String itemTitle;
    private String location;
    private Long startingPrice;
    private String thumbnailImgUrl;
}
