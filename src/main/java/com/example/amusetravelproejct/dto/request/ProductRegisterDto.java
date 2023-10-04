package com.example.amusetravelproejct.dto.request;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductRegisterDto {
    private Long id;
    private String productId;
    private List<String> category;
//    private List<ItemIcon> itemIcon;  프론트 미구현
    private String title;
    private Location location;
    private List<MainImageDto> mainImg;
    private List<TicketDto> ticket;
    private String mainInfo;
    private List<CourseDto> course;
    private String extraInfo;
    private String admin;
    private String updateAdmin;
    private Long startPrice;
    private String duration;
    private String startDate;
    private String endDate;
    private String option;
    private String guide_code;
//    private Long guide_db_id;
    private String guide_comment;
    private accessData accessAuthority;
    private String startPoint;
    private String runningTime;
    private String activityIntensity;
    private String language;
    private ReservationInfoDto reservationInfo;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemIcon{
        private String text;
        private Long iconId;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class accessData {
        private List<String> accessibleUserList;
        private String accessibleTier;
    }

    // Getter, Setter, Constructor 생략
    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class Location {
        private String country;
        private String city;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class MainImageDto {
        private Long id;
        private String fileName;
        private String base64Data;
        private String imgUrl;
        private Integer sequence;
        // Getter, Setter, Constructor 생략
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class TicketDto {
        private Long id;
        private String title;
        private String content;
        private Long sequenceNum;
        private List<PriceListDto> priceList;

        // Getter, Setter, Constructor 생략
        @Getter
        @Setter
        @NoArgsConstructor
        @ToString
        public static class PriceListDto {
            private Long id;
            private String startDate;
            private String endDate;
            private Long quantity;
            private WeekdayPrices weekdayPrices;
            @Getter
            @Setter
            @NoArgsConstructor
            @ToString
            public static class WeekdayPrices{
                private String mon;
                private String tue;
                private String wed;
                private String thu;
                private String fri;
                private String sat;
                private String sun;
            }
            // Getter, Setter, Constructor 생략
        }
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class CourseDto {
        private Long id;
        private Long sequenceId;
        private Long day;
        private String title;
        private String timeCost;
        private String content;
        private LocationDto location;
        private CourseImageDto image;

        // Getter, Setter, Constructor 생략
        @Getter
        @Setter
        @NoArgsConstructor
        @ToString
        public static class CourseImageDto {
            private String fileName;
            private String base64Data;
            private String imgUrl;
            // Getter, Setter, Constructor 생략
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @ToString
        public static class LocationDto {
            private String latitude;
            private String longitude;
            // Getter, Setter, Constructor 생략
        }

    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ReservationInfoDto{
        private Long id;
        private String name;
        private String content;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class CancelPolicyInfoDto{
        private Long id;
        private String name;
        private String content;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class PaymentMethodInfoDto{
        private Long id;
        private String name;
        private String content;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    public static class TermsOfServiceInfoDto{
        private Long id;
        private String title;
        private String type;
        private int sequenceNum;
        private Boolean mandatory;
        private String content;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    public static class ReservationInfoUpdateDto{
        private String name;
        private String content;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    public static class CancelPolicyInfoCreateOrUpdateDto {
        private String type;
        private String content;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    public static class PaymentMethodInfoUpdateDto{
        private String name;
        private String content;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    public static class TermsOfServiceInfoCreateOrUpdateDto {
        private String type;
        private List<TermsOfServiceInfoContentDto> content;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    public static class TermsOfServiceInfoContentDto{
        private String title;
        private int sequenceNum;
        private Boolean mandatory;
        private String content;
    }
}
