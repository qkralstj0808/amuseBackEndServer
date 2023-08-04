package com.example.amusetravelproejct.dto.request;

import com.example.amusetravelproejct.domain.person_enum.DisplayStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.swing.*;
import java.util.List;

public class AdminPageRequest {


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class advertisementRegister {
        private String title;
        private String startDate;
        private String endDate;
        private String pcBannerFileName;
        private String pcBannerBase64;
        private String pcBannerLink;
        private String mobileBannerFileName;
        private String mobileBannerBase64;
        private String mobileBannerLink;
        private String[] adCategory;
        private String adContent;
        private String createdBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class advertisementEdit {
        private Long id;
        private String title;
        private String startDate;
        private String endDate;
        private String pcBannerFileName;
        private String pcBannerBase64;
        private String pcBannerLink;
        private String mobileBannerFileName;
        private String mobileBannerBase64;
        private String mobileBannerLink;
        private String[] adCategory;
        private String adContent;
        private String updatedBy;
        private String pcBannerUrl;
        private String mobileBannerUrl;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class noticeRegister {
        private String title;
        private String content;
        private String createdBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class noticeEdit{
        private Long id;
        private String title;
        private String content;
        private String updatedBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class categoryRegister {
        private String category;
        private String fileName;
        private String base64Data;
        private String mainDescription;
        private String subDescription;
        private String createdBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class categoryEdit {
        private Long id;
        private String category;
        private String fileName;
        private String base64Data;
        private String mainDescription;
        private String subDescription;
        private String updatedBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class categoryDetail {
        private String category;
        private Long sequence;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class findItemByCategory {
        private List<String> categoryNames;
        private Long offset;
        private Long limit;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getItemByCategory{
        private String option;
        private Long page;
        private Long limit;
        private List<String> categoryNames;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class registerComponent{
        private Long id;
        private String title;
        private String type;
//        private String createdBy;
//        private String updatedBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class registerListComponent extends registerComponent{
        private List<Long> item_db_id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class registerBannerComponent extends registerComponent{
        private String pcBannerFileName;
        private String pcBannerBase64;
        private String pcBannerImgUrl;
        private String pcBannerLink;
        private String mobileBannerFileName;
        private String mobileBannerBase64;
        private String mobileBannerImgUrl;
        private String mobileBannerLink;
        private String content;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class registerTileComponent extends registerComponent{
        private List<getMainItem> tile;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getMainItem{
        private String tileName;
        private List<String> itemCode;
        private String tileFileName;
        private String tileBase64;
        private String tileImgUrl;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createPage {
        private String name;
        private String fileName;
        private String base64Data;
        private Long sequence;
        private String mainDescription;
        private String subDescription;
        private List<PageComponentInfo> pageComponentInfos;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PageComponentInfo {
        private Long componentId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class updatePage {
        private String name;
        private String fileName;
        private String base64Data;
        private Long sequence;
        private Boolean disable;
        private String mainDescription;
        private String subDescription;
        private String updatedBy;
        private List<PageComponentInfo> pageComponentInfos;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class changeDisplayStatus {

        private Boolean display_true;
    }



}


