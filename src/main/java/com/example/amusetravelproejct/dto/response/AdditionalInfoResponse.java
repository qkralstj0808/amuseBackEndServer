package com.example.amusetravelproejct.dto.response;

import com.example.amusetravelproejct.dto.request.ProductRegisterDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class AdditionalInfoResponse {

    private List<ReservationInfoResponse> reservationInfoResponse;
    private List<CancelPolicyInfoResponse> cancelPolicyInfoResponse;
    private List<PaymentMethodInfoResponse> paymentMethodInfoResponse;
    private List<TermsOfServiceInfoResponse> termsOfServiceInfoResponse;

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
        private String name;
        private String content;
    }
    @Getter
    @AllArgsConstructor
    public static class PaymentMethodInfoResponse{
        private long id;
        private String name;
        private String content;
    }
    @Getter
    @AllArgsConstructor
    public static class TermsOfServiceInfoResponse{
        private long id;
        private String name;
        private List<String> content;
    }

    protected AdditionalInfoResponse(List<ReservationInfoResponse> reservationInfoResponse,
                                     List<CancelPolicyInfoResponse> cancelPolicyInfoResponse,
                                     List<PaymentMethodInfoResponse> paymentMethodInfoResponse,
                                     List<TermsOfServiceInfoResponse> termsOfServiceInfoResponse) {
        this.reservationInfoResponse = reservationInfoResponse;
        this.cancelPolicyInfoResponse = cancelPolicyInfoResponse;
        this.paymentMethodInfoResponse = paymentMethodInfoResponse;
        this.termsOfServiceInfoResponse = termsOfServiceInfoResponse;
    }

    public static AdditionalInfoResponse create(List<ReservationInfoResponse> reservationInfoResponse,
                                         List<CancelPolicyInfoResponse> cancelPolicyInfoResponse,
                                         List<PaymentMethodInfoResponse> paymentMethodInfoResponse,
                                         List<TermsOfServiceInfoResponse> termsOfServiceInfoResponse) {

        return new AdditionalInfoResponse(reservationInfoResponse, cancelPolicyInfoResponse, paymentMethodInfoResponse, termsOfServiceInfoResponse);

    }
}
