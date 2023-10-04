package com.example.amusetravelproejct.dto.response;

import com.example.amusetravelproejct.dto.request.ProductRegisterDto;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
public class AdditionalInfoResponse {

    private ReservationInfoResponse reservationInfoResponse;
    private CancelPolicyInfoResponse cancelPolicyInfoResponse;
//    private TermsOfServiceInfoResponse paymentMethodInfoResponse;
    private TermsOfServiceInfoResponse termsOfServiceInfoResponse;

    @Getter
    @AllArgsConstructor

    public static class ReservationInfoResponse{
        private long id;
        private String name;
        private String content;
    }
    @Getter
    @AllArgsConstructor
    public static class CancelPolicyInfoResponse{
        private long id;
        private String type;
        private String content;
    }
    @Getter
    @AllArgsConstructor
    public static class PaymentMethodInfoResponse{
        private long id;
        private String name;
        private String content;
    }

    @AllArgsConstructor
    @Getter
    public static class TermsOfServiceInfoResponse{
        private String type;
        private List<TermsOfServiceInfoContentResponse> content;
    }

    @Getter
    @AllArgsConstructor
    public static class TermsOfServiceInfoContentResponse {
        private long id;
        private String type;
        private String title;
        private int sequenceNum;
        private Boolean mandatory;
        private String content;
    }

}
