package com.example.amusetravelproejct.controller;

import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.dto.request.ProductRegisterDto;
import com.example.amusetravelproejct.dto.response.AdditionalInfoResponse;
import com.example.amusetravelproejct.service.PaymentPageInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/test/api")
@Slf4j
public class PaymentPageInfoController {

    private final PaymentPageInfoService paymentPageInfoService;

    /*결제 창에 들어갈 추가 정보 관련 생성 api*/
    @PostMapping("/cancel-policy-info")
    public ResponseTemplate<String> createCancelPolicyInfo(
            @RequestBody ProductRegisterDto.CancelPolicyInfoCreateOrUpdateDto cancelPolicyInfoCreateOrUpdateDto
    ) {
        return paymentPageInfoService.createCancelPolicyInfo(cancelPolicyInfoCreateOrUpdateDto);
    }

    @PostMapping("/terms-of-service-info")
    public ResponseTemplate<String> createTermsOfServiceInfo(
            @RequestBody ProductRegisterDto.TermsOfServiceInfoCreateOrUpdateDto termsOfServiceInfoCreateDto
    ) {
        return paymentPageInfoService.createTermsOfServiceInfo(termsOfServiceInfoCreateDto);
    }


    /*결제 창에 들어갈 추가 정보 관련 조회 api*/

    @GetMapping("/payment-page-all-info")
    public ResponseTemplate<AdditionalInfoResponse> getAllPaymentPageInfo(
            @RequestParam Long reservation_info_id,
            @RequestParam String type
    ){
        return paymentPageInfoService.getAllPaymentPageInfo(reservation_info_id, type);
    }
    @GetMapping("/reservation-info")
    public ResponseTemplate<List<AdditionalInfoResponse.ReservationInfoResponse>> getReservationInfoList() {
        return paymentPageInfoService.getReservationInfoList();
    }

    @GetMapping("/cancel-policy-info")
    public ResponseTemplate<List<AdditionalInfoResponse.CancelPolicyInfoResponse>> getCancelPolicyInfoList() {
        return paymentPageInfoService.getCancelPolicyInfoList();
    }

/*
    @GetMapping("/payment-method-info")
    public ResponseTemplate<List<AdditionalInfoResponse.PaymentMethodInfoResponse>> getPaymentMethodInfoList(){
        return additionalInfoService.getPaymentMethodInfoList();
    }
*/

    @GetMapping("/terms-of-service-info")
    public ResponseTemplate<List<AdditionalInfoResponse.TermsOfServiceInfoContentResponse>> getTermsOfServiceInfoList() {
        return paymentPageInfoService.getTermsOfServiceInfoList();
    }


    // 결제 창에 들어갈 추가 정보 관련 단건 조회 api
    @GetMapping("/reservation-info/{reservation-info-id}")
    public ResponseTemplate<AdditionalInfoResponse.ReservationInfoResponse> getReservationInfo(
            @PathVariable("reservation-info-id") Long reservationInfoId
    ) {
        return paymentPageInfoService.getReservationInfo(reservationInfoId);
    }

    @GetMapping("/cancel-policy-info/{cancel-policy-info-id}")
    public ResponseTemplate<AdditionalInfoResponse.CancelPolicyInfoResponse> getCancelPolicyInfo(
            @PathVariable("cancel-policy-info-id") Long cancelPolicyInfoId
    ) {
        return paymentPageInfoService.getCancelPolicyInfo(cancelPolicyInfoId);
    }

/*
    @GetMapping("/payment-method-info/{payment-method-info-id}")
    public ResponseTemplate<AdditionalInfoResponse.PaymentMethodInfoResponse> getPaymentMethodInfo(
            @PathVariable("payment-method-info-id") Long paymentMethodInfoId
    ){
        return additionalInfoService.getPaymentMethodInfo(paymentMethodInfoId);
    }
*/

    @GetMapping("/terms-of-service-info/{terms-of-service-info-id}")
    public ResponseTemplate<AdditionalInfoResponse.TermsOfServiceInfoContentResponse> getTermsOfServiceInfo(
            @PathVariable("terms-of-service-info-id") Long termsOfServiceInfoId
    ) {
        return paymentPageInfoService.getTermsOfServiceInfo(termsOfServiceInfoId);
    }

    /*결제 창에 들어갈 추가 정보 관련 type 별 조회 api*/
    @GetMapping("/cancel-policy-info-type")
    public ResponseTemplate<AdditionalInfoResponse.CancelPolicyInfoResponse> getCancelPolicyInfoByType(
            @RequestParam String type
    ) {
        return paymentPageInfoService.getCancelPolicyInfoByType(type);
    }

    @GetMapping("/terms-of-service-info-type")
    public ResponseTemplate<AdditionalInfoResponse.TermsOfServiceInfoResponse> getTermsOfServiceInfoByType(
            @RequestParam String type
    ) {
        return paymentPageInfoService.getTermsOfServiceInfoByType(type);
    }

    /*결제 창에 들어갈 추가 정보 관련 수정 api*/
    @PutMapping("/reservation-info/{reservation-info-id}")
    public ResponseTemplate<String> updateReservationInfo(
            @PathVariable("reservation-info-id") Long reservationInfoId,
            @RequestBody ProductRegisterDto.ReservationInfoUpdateDto reservationInfoUpdateDto
    ) {
        return paymentPageInfoService.updateReservationInfo(reservationInfoId, reservationInfoUpdateDto);
    }

    @PutMapping("/cancel-policy-info/{cancel-policy-info-id}")
    public ResponseTemplate<String> updateCancelPolicyInfo(
            @PathVariable("cancel-policy-info-id") Long cancelPolicyInfoId,
            @RequestBody ProductRegisterDto.CancelPolicyInfoCreateOrUpdateDto cancelPolicyInfoUpdateDto
    ) {
        return paymentPageInfoService.updateCancelPolicyInfo(cancelPolicyInfoId, cancelPolicyInfoUpdateDto);
    }

    /*@PutMapping("/payment-method-info/{payment-method-info-id}")
    public ResponseTemplate<String> updatePaymentMethodInfo(
            @PathVariable("payment-method-info-id") Long paymentMethodInfoId,
            @RequestBody ProductRegisterDto.PaymentMethodInfoUpdateDto paymentMethodInfoUpdateDto
    ){
        return additionalInfoService.updatePaymentMethodInfo(paymentMethodInfoId, paymentMethodInfoUpdateDto);
    }*/

    @PutMapping("/terms-of-service-info")
    public ResponseTemplate<String> updateTermsOfServiceInfo(
            @RequestBody ProductRegisterDto.TermsOfServiceInfoCreateOrUpdateDto termsOfServiceInfoUpdateDto
    ) {
        return paymentPageInfoService.updateTermsOfServiceInfo(termsOfServiceInfoUpdateDto);
    }



    /*결제 창에 들어갈 추가 정보 관련 삭제 api*/

    @DeleteMapping("/reservation-info/{reservation-info-id}")
    public ResponseTemplate<String> deleteReservationInfo(
            @PathVariable("reservation-info-id") Long reservationInfoId
    ) {
        paymentPageInfoService.deleteReservationInfo(reservationInfoId);
        return new ResponseTemplate<>("추가 예약 정보 삭제 성공");
    }

    @DeleteMapping("/cancel-policy-info/{cancel-policy-info-id}")
    public ResponseTemplate<String> deleteCancelPolicyInfo(
            @PathVariable("cancel-policy-info-id") Long cancelPolicyInfoId
    ) {
        paymentPageInfoService.deleteCancelPolicyInfo(cancelPolicyInfoId);
        return new ResponseTemplate<>("취소 정책 정보 삭제 성공");
    }

//    @DeleteMapping("/payment-method-info/{payment-method-info-id}")
//    public ResponseTemplate<String> deletePaymentMethodInfo(
//            @PathVariable("payment-method-info-id") Long paymentMethodInfoId
//    ){
//        paymentPageInfoService.deletePaymentMethodInfo(paymentMethodInfoId);
//        return new ResponseTemplate<>("결제 방법 정보 삭제 성공");
//    }

    @DeleteMapping("/terms-of-service-info/{terms-of-service-info-id}")
    public ResponseTemplate<String> deleteTermsOfServiceInfo(
            @PathVariable("terms-of-service-info-id") Long termsOfServiceInfoId
    ) {
        paymentPageInfoService.deleteTermsOfServiceInfo(termsOfServiceInfoId);
        return new ResponseTemplate<>("약관동의 정보 삭제 성공");
    }

    /*결제 창에 들어갈 추가 정보 관련 type 별 삭제 api*/
    @DeleteMapping("/cancel-policy-info-type")
    public ResponseTemplate<String> deleteCancelPolicyInfoByType(
            @RequestParam String type
    ) {
        paymentPageInfoService.deleteCancelPolicyInfoByType(type);
        return new ResponseTemplate<>("취소 정책 정보 삭제 성공");
    }

    @DeleteMapping("/terms-of-service-info-type")
    public ResponseTemplate<String> deleteTermsOfServiceInfoByType(
            @RequestParam String type
    ) {
        paymentPageInfoService.deleteTermsOfServiceInfoByType(type);
        return new ResponseTemplate<>("약관동의 정보 삭제 성공");
    }
}
