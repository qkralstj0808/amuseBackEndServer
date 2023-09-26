package com.example.amusetravelproejct.controller;

import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.dto.request.ProductRegisterDto;
import com.example.amusetravelproejct.dto.response.AdditionalInfoResponse;
import com.example.amusetravelproejct.repository.itemAdditionalInfo.AdditionalReservationInfoRepository;
import com.example.amusetravelproejct.repository.itemAdditionalInfo.PaymentCancelPolicyInfoRepository;
import com.example.amusetravelproejct.repository.itemAdditionalInfo.PaymentMethodInfoRepository;
import com.example.amusetravelproejct.repository.itemAdditionalInfo.TermsOfServiceInoRepository;
import com.example.amusetravelproejct.service.PaymentPageInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/test/api")
@Slf4j
public class PaymentPageInfoController {

    private final PaymentPageInfoService additionalInfoService;

    // 결제 창에 들어갈 추가 정보 관련 조회 api
    @GetMapping("/all-additional-info")
    public ResponseTemplate<AdditionalInfoResponse> getAllAdditionalInfo(){
        return additionalInfoService.getAllAdditionalInfo();
    }
    @GetMapping("/reservation-info")
    public ResponseTemplate<List<AdditionalInfoResponse.ReservationInfoResponse>> getReservationInfoList(){
        return additionalInfoService.getReservationInfoList();
    }

    @GetMapping("/cancel-policy-info")
    public ResponseTemplate<List<AdditionalInfoResponse.CancelPolicyInfoResponse>> getCancelPolicyInfoList(){
        return additionalInfoService.getCancelPolicyInfoList();
    }

    @GetMapping("/payment-method-info")
    public ResponseTemplate<List<AdditionalInfoResponse.PaymentMethodInfoResponse>> getPaymentMethodInfoList(){
        return additionalInfoService.getPaymentMethodInfoList();
    }

    @GetMapping("/terms-of-service-info")
    public ResponseTemplate<List<AdditionalInfoResponse.TermsOfServiceInfoResponse>> getTermsOfServiceInfoList(){
        return additionalInfoService.getTermsOfServiceInfoList();
    }

    // 결제 창에 들어갈 추가 정보 관련 단건 조회 api
    @GetMapping("/reservation-info/{reservation-info-id}")
    public ResponseTemplate<AdditionalInfoResponse.ReservationInfoResponse> getReservationInfo(
            @PathVariable("reservation-info-id") Long reservationInfoId
    ){
        return additionalInfoService.getReservationInfo(reservationInfoId);
    }

    @GetMapping("/cancel-policy-info/{cancel-policy-info-id}")
    public ResponseTemplate<AdditionalInfoResponse.CancelPolicyInfoResponse> getCancelPolicyInfo(
            @PathVariable("cancel-policy-info-id") Long cancelPolicyInfoId
    ){
        return additionalInfoService.getCancelPolicyInfo(cancelPolicyInfoId);
    }

    @GetMapping("/payment-method-info/{payment-method-info-id}")
    public ResponseTemplate<AdditionalInfoResponse.PaymentMethodInfoResponse> getPaymentMethodInfo(
            @PathVariable("payment-method-info-id") Long paymentMethodInfoId
    ){
        return additionalInfoService.getPaymentMethodInfo(paymentMethodInfoId);
    }

    @GetMapping("/terms-of-service-info/{terms-of-service-info-id}")
    public ResponseTemplate<AdditionalInfoResponse.TermsOfServiceInfoResponse> getTermsOfServiceInfo(
            @PathVariable("terms-of-service-info-id") Long termsOfServiceInfoId
    ){
        return additionalInfoService.getTermsOfServiceInfo(termsOfServiceInfoId);
    }

    @PutMapping("/reservation-info/{reservation-info-id}")
    public ResponseTemplate<String> updateReservationInfo(
            @PathVariable("reservation-info-id") Long reservationInfoId,
            @RequestBody ProductRegisterDto.ReservationInfoUpdateDto reservationInfoUpdateDto
    ){
        return additionalInfoService.updateReservationInfo(reservationInfoId, reservationInfoUpdateDto);
    }

    @PutMapping("/cancel-policy-info/{cancel-policy-info-id}")
    public ResponseTemplate<String> updateCancelPolicyInfo(
            @PathVariable("cancel-policy-info-id") Long cancelPolicyInfoId,
            @RequestBody ProductRegisterDto.CancelPolicyInfoUpdateDto cancelPolicyInfoUpdateDto
    ){
        return additionalInfoService.updateCancelPolicyInfo(cancelPolicyInfoId, cancelPolicyInfoUpdateDto);
    }

    @PutMapping("/payment-method-info/{payment-method-info-id}")
    public ResponseTemplate<String> updatePaymentMethodInfo(
            @PathVariable("payment-method-info-id") Long paymentMethodInfoId,
            @RequestBody ProductRegisterDto.PaymentMethodInfoUpdateDto paymentMethodInfoUpdateDto
    ){
        return additionalInfoService.updatePaymentMethodInfo(paymentMethodInfoId, paymentMethodInfoUpdateDto);
    }

    @PutMapping("/terms-of-service-info/{terms-of-service-info-id}")
    public ResponseTemplate<String> updateTermsOfServiceInfo(
            @PathVariable("terms-of-service-info-id") Long termsOfServiceInfoId,
            @RequestBody ProductRegisterDto.TermsOfServiceInfoUpdateDto termsOfServiceInfoUpdateDto
    ){
        return additionalInfoService.updateTermsOfServiceInfo(termsOfServiceInfoId, termsOfServiceInfoUpdateDto);
    }




    @DeleteMapping("/reservation-info/{reservation-info-id}")
    public ResponseTemplate<String> deleteReservationInfo(
            @PathVariable("reservation-info-id") Long reservationInfoId
    ){
        additionalInfoService.deleteReservationInfo(reservationInfoId);
        return new ResponseTemplate<>("추가 예약 정보 삭제 성공");
    }

    @DeleteMapping("/cancel-policy-info/{cancel-policy-info-id}")
    public ResponseTemplate<String> deleteCancelPolicyInfo(
            @PathVariable("cancel-policy-info-id") Long cancelPolicyInfoId
    ){
        additionalInfoService.deleteCancelPolicyInfo(cancelPolicyInfoId);
        return new ResponseTemplate<>("취소 정책 정보 삭제 성공");
    }

    @DeleteMapping("/payment-method-info/{payment-method-info-id}")
    public ResponseTemplate<String> deletePaymentMethodInfo(
            @PathVariable("payment-method-info-id") Long paymentMethodInfoId
    ){
        additionalInfoService.deletePaymentMethodInfo(paymentMethodInfoId);
        return new ResponseTemplate<>("결제 방법 정보 삭제 성공");
    }

    @DeleteMapping("/terms-of-service-info/{terms-of-service-info-id}")
    public ResponseTemplate<String> deleteTermsOfServiceInfo(
            @PathVariable("terms-of-service-info-id") Long termsOfServiceInfoId
    ){
        additionalInfoService.deleteTermsOfServiceInfo(termsOfServiceInfoId);
        return new ResponseTemplate<>("약관동의 정보 삭제 성공");
    }
}
