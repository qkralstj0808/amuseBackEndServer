package com.example.amusetravelproejct.dto.response;

import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.dto.request.AdminPageRequest;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public class AdminPageResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class advertisementRegister {
        private Long id;
        private String title;
        private String startDate;
        private String endDate;
        private String pcBannerUrl;
        private String pcBannerLink;
        private String mobileBannerUrl;
        private String mobileBannerLink;
        private String[] adCategory;
        private String adContent;
        private String createdBy;
    }

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class advertisementEdit {
        private Long id;
        private String title;
        private String startDate;
        private String endDate;
        private String pcBannerUrl;
        private String pcBannerLink;
        private String mobileBannerUrl;
        private String mobileBannerLink;
        private String[] adCategory;
        private String adContent;
        private String createdBy;
        private String createdAt;
        private String updatedAt;
        private String updatedBy;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class advertisementResult{
        private int pageCount;
        private int page;
        private List<advertisementList> data;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class advertisementList{
        private Long id;
        private String title;
        private Date startDate;
        private Date endDate;
        private String[] adCategory;
        private LocalDateTime createdAt;
        private String createdBy;
        private LocalDateTime updatedAt;
        private String updatedBy;
    }



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class noticeRegister {
        private Long id;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private String createdBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class noticeEdit {
        private Long id;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private String createdBy;
        private LocalDateTime updatedAt;
        private String updatedBy;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class noticeResult{
        private int pageCount;
        private int page;
        private List<noticeList> data;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class noticeList{
        private Long id;
        private String title;
        private LocalDateTime createdAt;
        private String createdBy;
        private LocalDateTime updatedAt;
        private String updatedBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class categoryRegister {
        private Long   id;
        private String hashTag;
        private String imgUrl;
        private Long sequence;
        private String mainDescription;
        private String subDescription;
        private LocalDateTime createdAt;
        private String createdBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class categoryEdit {
        private Long   id;
        private String hashTag;
        private String imgUrl;
        private Long sequence;
        private String mainDescription;
        private String subDescription;
        private LocalDateTime createdAt;
        private String createdBy;
        private LocalDateTime updatedAt;
        private String updatedBy;
    }



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class categorySequence{
        private Long id;
        private String displayHashTag;
        private Long sequence;
        private LocalDateTime createdAt;
        private String createdBy;
        private LocalDateTime updatedAt;
        private String updatedBy;
    }

//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class findItemByCategoryResult{
//        private List<findItemByCategory> Items;
//    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class findItemByCategory{
        private String id;
        private String title;
//        private String content;
        private List<String> categoryNames;
        private String createdBy;
        private LocalDateTime createdAt;
        private String updatedBy;
        private LocalDateTime updatedAt;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getItemByDisplayStatus{
        private Long pageCount;
        private List<getItemsByDisplayStat> data;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getItemsByDisplayStat{
        private Long item_db_id;
        private String itemCode;
        private String title;
        private String imgUrl;
    }



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getItemByCategory{
        private Long pageCount;
        private List<findItemByCategory> data;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getMainComponent{
        private Long id;
        private String title;
        private String type;
        private LocalDateTime createAt;
        private String createBy;
        private LocalDateTime updateAt;
        private String updateBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getMainPageItem{
        private Long item_db_id;
        private String product_code;
        private Long startPrice;
        private String title;
        private List<String> category;
        private LocalDateTime createAt;
        private String createBy;
        private LocalDateTime updateAt;
        private String updateBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class respRegisterComponent{
        private Long id;
        private String title;
        private String type;
        private LocalDateTime createdAt;
        private String createdBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class registerListComponent extends respRegisterComponent {
        private List<AdminPageRequest.ItemIdAndSequence> item_db_id;
        private LocalDateTime updatedAt;
        private String updatedBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemIdAndSequence{
        private Long itemDbId;
        private Integer sequence;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getListComponent extends respRegisterComponent {
        private LocalDateTime updatedAt;
        private String updatedBy;
        private List<getMainPageItem> productList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class registerBannerComponent extends respRegisterComponent {
        private LocalDateTime updatedAt;
        private String updatedBy;
        private String pcBannerImgUrl;
        private String pcBannerLink;
        private String mobileBannerImgUrl;
        private String mobileBannerLink;
        private String content;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getBannerComponent extends respRegisterComponent {
        private LocalDateTime updatedAt;
        private String updatedBy;
        private String pcBannerImgUrl;
        private String pcBannerLink;
        private String mobileBannerImgUrl;
        private String mobileBannerLink;
        private String content;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class registerTileComponent extends respRegisterComponent {
        private List<AdminPageResponse.getMainItem> tile;
        private LocalDateTime updatedAt;
        private String updatedBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getTileComponent extends respRegisterComponent{
        private LocalDateTime updatedAt;
        private String updatedBy;
        private List<AdminPageResponse.getDetailItem> tile;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getDetailItem {
        private String title;
        List<getMainPageItem> productList;
        private String tileImgUrl;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getMainItem{
        private String tileName;
        private List<String> itemCode;
        private String tileImgUrl;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createPage {
        private Long   id;
        private String hashTag;
        private String imgUrl;
        private Long sequence;
        private String mainDescription;
        private String subDescription;
        private LocalDateTime createdAt;
        private String createdBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class updatePage {
        private Long id;
        private Boolean disable;
        private String name;
        private String imgUrl;
        private Long sequence;
        private String mainDescription;
        private String subDescription;
        private LocalDateTime createdAt;
        private String createdBy;
        private LocalDateTime updatedAt;
        private String updatedBy;
        private List<PageComponentInfo> pageComponentInfos;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PageComponentInfo {
        private Long id;
        private String type;
        private String title;
        private String PcBannerUrl;
        private String PcBannerLink;
        private String MobileBannerUrl;
        private String MobileBannerLink;
        private String content;
        private String createBy;
        private String updateBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getPage {
        private Long   id;
        private Boolean disable;
        private String name;
        private String imgUrl;
        private Long sequence;
        private String mainDescription;
        private String subDescription;
        private LocalDateTime createdAt;
        private String createdBy;
        private LocalDateTime updatedAt;
        private String updatedBy;
        private List<PageComponentInfo> pageComponentInfos;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getAllPage {
        private Long   id;
        private Boolean disable;
        private String name;
        private String imgUrl;
        private Long sequence;
        private String mainDescription;
        private String subDescription;
        private LocalDateTime createdAt;
        private String createdBy;
        private LocalDateTime updatedAt;
        private String updatedBy;
        private List<PageComponentId> pageComponentInfos;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PageComponentId {
        private Long id;
        private Long sequence;
    }
}
