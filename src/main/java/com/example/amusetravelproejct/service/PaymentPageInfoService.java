package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.domain.itemAdditionalInfo.AdditionalReservationInfo;
import com.example.amusetravelproejct.domain.itemAdditionalInfo.PaymentCancelPolicyInfo;
import com.example.amusetravelproejct.domain.itemAdditionalInfo.PaymentMethodInfo;
import com.example.amusetravelproejct.domain.itemAdditionalInfo.TermsOfServiceInfo;
import com.example.amusetravelproejct.dto.request.ProductRegisterDto;
import com.example.amusetravelproejct.dto.response.AdditionalInfoResponse;
import com.example.amusetravelproejct.repository.ItemRepository;
import com.example.amusetravelproejct.repository.itemAdditionalInfo.AdditionalReservationInfoRepository;
import com.example.amusetravelproejct.repository.itemAdditionalInfo.PaymentCancelPolicyInfoRepository;
import com.example.amusetravelproejct.repository.itemAdditionalInfo.PaymentMethodInfoRepository;
import com.example.amusetravelproejct.repository.itemAdditionalInfo.TermsOfServiceInoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class PaymentPageInfoService {

    private final AdditionalReservationInfoRepository additionalReservationInfoRepository;
    private final PaymentMethodInfoRepository paymentMethodInfoRepository;
    private final PaymentCancelPolicyInfoRepository paymentCancelPolicyInfoRepository;
    private final TermsOfServiceInoRepository termsOfServiceInoRepository;

    private final ItemRepository itemRepository;

    public ResponseTemplate<AdditionalInfoResponse> getAllAdditionalInfo() {

        List<AdditionalInfoResponse.ReservationInfoResponse> reservationResponseList = new ArrayList();

        List<AdditionalReservationInfo> additionalReservationInfos = additionalReservationInfoRepository.findAll();

        for (AdditionalReservationInfo additionalReservationInfo : additionalReservationInfos) {
            reservationResponseList.add(additionalReservationInfo.createResponseDto());
        }

        List<AdditionalInfoResponse.CancelPolicyInfoResponse> cancelPolicyResponseList = new ArrayList();

        List<PaymentCancelPolicyInfo> paymentCancelPolicyInfos = paymentCancelPolicyInfoRepository.findAll();

        for (PaymentCancelPolicyInfo paymentCancelPolicyInfo : paymentCancelPolicyInfos) {
            cancelPolicyResponseList.add(paymentCancelPolicyInfo.createResponseDto());
        }

        List<AdditionalInfoResponse.PaymentMethodInfoResponse> paymentMethodInfoResponseList = new ArrayList();

        List<PaymentMethodInfo> paymentMethodInfos = paymentMethodInfoRepository.findAll();

        for (PaymentMethodInfo paymentMethodInfo : paymentMethodInfos) {
            paymentMethodInfoResponseList.add(paymentMethodInfo.createResponseDto());
        }

        List<AdditionalInfoResponse.TermsOfServiceInfoResponse> termsOrServiceResponseList = new ArrayList();

        List<TermsOfServiceInfo> termsOfServiceInfos = termsOfServiceInoRepository.findAll();

        for (TermsOfServiceInfo termsOfServiceInfo : termsOfServiceInfos) {
            termsOrServiceResponseList.add(termsOfServiceInfo.createResponseDto());
        }

        return new ResponseTemplate<>(AdditionalInfoResponse.create(reservationResponseList, cancelPolicyResponseList, paymentMethodInfoResponseList, termsOrServiceResponseList));
    }


    public ResponseTemplate<List<AdditionalInfoResponse.ReservationInfoResponse>> getReservationInfoList() {
        List<AdditionalInfoResponse.ReservationInfoResponse> responseList = new ArrayList();

        List<AdditionalReservationInfo> additionalReservationInfos = additionalReservationInfoRepository.findAll();

        for (AdditionalReservationInfo additionalReservationInfo : additionalReservationInfos) {
            responseList.add(additionalReservationInfo.createResponseDto());
        }

        return new ResponseTemplate<>(responseList);
    }

    public ResponseTemplate<List<AdditionalInfoResponse.CancelPolicyInfoResponse>> getCancelPolicyInfoList() {
        List<AdditionalInfoResponse.CancelPolicyInfoResponse> responseList = new ArrayList();

        List<PaymentCancelPolicyInfo> paymentCancelPolicyInfos = paymentCancelPolicyInfoRepository.findAll();

        for (PaymentCancelPolicyInfo paymentCancelPolicyInfo : paymentCancelPolicyInfos) {
            responseList.add(paymentCancelPolicyInfo.createResponseDto());
        }

        return new ResponseTemplate<>(responseList);
    }

    public ResponseTemplate<List<AdditionalInfoResponse.PaymentMethodInfoResponse>> getPaymentMethodInfoList() {
        List<AdditionalInfoResponse.PaymentMethodInfoResponse> responseList = new ArrayList();

        List<PaymentMethodInfo> paymentMethodInfos = paymentMethodInfoRepository.findAll();

        for (PaymentMethodInfo paymentMethodInfo : paymentMethodInfos) {
            responseList.add(paymentMethodInfo.createResponseDto());
        }

        return new ResponseTemplate<>(responseList);
    }

    public ResponseTemplate<List<AdditionalInfoResponse.TermsOfServiceInfoResponse>> getTermsOfServiceInfoList() {
        List<AdditionalInfoResponse.TermsOfServiceInfoResponse> responseList = new ArrayList();

        List<TermsOfServiceInfo> termsOfServiceInfos = termsOfServiceInoRepository.findAll();

        for (TermsOfServiceInfo termsOfServiceInfo : termsOfServiceInfos) {
            responseList.add(termsOfServiceInfo.createResponseDto());
        }

        return new ResponseTemplate<>(responseList);
    }


    public ResponseTemplate<AdditionalInfoResponse.ReservationInfoResponse> getReservationInfo(Long reservationInfoId) {
        AdditionalReservationInfo additionalReservationInfo = additionalReservationInfoRepository.findById(reservationInfoId).orElseThrow(
                () -> new CustomException(ErrorCode.RESERVATION_INFO_NOT_FOUND)
        );

        return new ResponseTemplate<>(additionalReservationInfo.createResponseDto());
    }

    public ResponseTemplate<AdditionalInfoResponse.CancelPolicyInfoResponse> getCancelPolicyInfo(Long cancelPolicyInfoId) {
        PaymentCancelPolicyInfo paymentCancelPolicyInfo = paymentCancelPolicyInfoRepository.findById(cancelPolicyInfoId).orElseThrow(
                () -> new CustomException(ErrorCode.CANCEL_POLICY_INFO_NOT_FOUND)
        );

        return new ResponseTemplate<>(paymentCancelPolicyInfo.createResponseDto());
    }


    public ResponseTemplate<AdditionalInfoResponse.PaymentMethodInfoResponse> getPaymentMethodInfo(Long paymentMethodInfoId) {
        PaymentMethodInfo paymentMethodInfo = paymentMethodInfoRepository.findById(paymentMethodInfoId).orElseThrow(
                () -> new CustomException(ErrorCode.PAYMENT_METHOD_INFO_NOT_FOUND)
        );

        return new ResponseTemplate<>(paymentMethodInfo.createResponseDto());
    }


    public ResponseTemplate<AdditionalInfoResponse.TermsOfServiceInfoResponse> getTermsOfServiceInfo(Long termsOfServiceInfoId) {
        TermsOfServiceInfo termsOfServiceInfo = termsOfServiceInoRepository.findById(termsOfServiceInfoId).orElseThrow(
                () -> new CustomException(ErrorCode.TERMS_OF_SERVICE_INFO_NOT_FOUND)
        );

        return new ResponseTemplate<>(termsOfServiceInfo.createResponseDto());
    }

    @Transactional
    public ResponseTemplate<String> updateReservationInfo(Long reservationInfoId, ProductRegisterDto.ReservationInfoUpdateDto reservationInfoUpdateDto) {
        AdditionalReservationInfo additionalReservationInfo = additionalReservationInfoRepository.findById(reservationInfoId).orElseThrow(
                () -> new CustomException(ErrorCode.RESERVATION_INFO_NOT_FOUND)
        );

        additionalReservationInfo.updateWithDto(reservationInfoUpdateDto);
        return new ResponseTemplate<>("추가 예약정보 업데이트 성공");
    }

    @Transactional
    public ResponseTemplate<String> updateCancelPolicyInfo(Long cancelPolicyInfoId, ProductRegisterDto.CancelPolicyInfoUpdateDto cancelPolicyInfoUpdateDto) {
        PaymentCancelPolicyInfo paymentCancelPolicyInfo = paymentCancelPolicyInfoRepository.findById(cancelPolicyInfoId).orElseThrow(
                () -> new CustomException(ErrorCode.CANCEL_POLICY_INFO_NOT_FOUND)
        );

        paymentCancelPolicyInfo.updateWithDto(cancelPolicyInfoUpdateDto);
        return new ResponseTemplate<>("취소 정책 정보 업데이트 성공");
    }

    @Transactional
    public ResponseTemplate<String> updatePaymentMethodInfo(Long paymentMethodInfoId, ProductRegisterDto.PaymentMethodInfoUpdateDto paymentMethodInfoUpdateDto) {
        PaymentMethodInfo paymentMethodInfo = paymentMethodInfoRepository.findById(paymentMethodInfoId).orElseThrow(
                () -> new CustomException(ErrorCode.PAYMENT_METHOD_INFO_NOT_FOUND)
        );

        paymentMethodInfo.updateWithDto(paymentMethodInfoUpdateDto);
        return new ResponseTemplate<>("결제 방법 정보 업데이트 성공");
    }

    @Transactional
    public ResponseTemplate<String> updateTermsOfServiceInfo(Long termsOfServiceInfoId, ProductRegisterDto.TermsOfServiceInfoUpdateDto termsOfServiceInfoUpdateDto) {
        TermsOfServiceInfo termsOfServiceInfo = termsOfServiceInoRepository.findById(termsOfServiceInfoId).orElseThrow(
                () -> new CustomException(ErrorCode.TERMS_OF_SERVICE_INFO_NOT_FOUND)
        );

        termsOfServiceInfo.updateWithDto(termsOfServiceInfoUpdateDto);
        return new ResponseTemplate<>("약관동의 내용 업데이트 성공");
    }

    @Transactional
    public void deleteReservationInfo(Long reservationInfoId) {

        AdditionalReservationInfo additionalReservationInfo = additionalReservationInfoRepository.findById(reservationInfoId).orElseThrow(
                () -> new CustomException(ErrorCode.RESERVATION_INFO_NOT_FOUND)
        );

        List<Item> itemList = itemRepository.findAllByAdditionalReservationInfo(additionalReservationInfo);

        for (Item item : itemList) {
            item.changeAdditionalReservationInfo(null);
        }

        additionalReservationInfoRepository.delete(additionalReservationInfo);
    }

    @Transactional
    public void deleteCancelPolicyInfo(Long cancelPolicyInfoId) {

        PaymentCancelPolicyInfo paymentCancelPolicyInfo = paymentCancelPolicyInfoRepository.findById(cancelPolicyInfoId).orElseThrow(
                () -> new CustomException(ErrorCode.CANCEL_POLICY_INFO_NOT_FOUND)
        );

        List<Item> itemList = itemRepository.findAllByPaymentCancelPolicyInfo(paymentCancelPolicyInfo);

        for (Item item : itemList) {
            item.changePaymentCancelPolicyInfo(null);
        }

        paymentCancelPolicyInfoRepository.delete(paymentCancelPolicyInfo);
    }

    @Transactional
    public void deletePaymentMethodInfo(Long paymentMethodInfoId) {
        PaymentMethodInfo paymentMethodInfo = paymentMethodInfoRepository.findById(paymentMethodInfoId).orElseThrow(
                () -> new CustomException(ErrorCode.PAYMENT_METHOD_INFO_NOT_FOUND)
        );

        List<Item> itemList = itemRepository.findAllByPaymentMethodInfo(paymentMethodInfo);

        for (Item item : itemList) {
            item.changePaymentMethodInfo(null);
        }

        paymentMethodInfoRepository.delete(paymentMethodInfo);
    }

    @Transactional
    public void deleteTermsOfServiceInfo(Long termsOfServiceInfoId) {
        TermsOfServiceInfo termsOfServiceInfo = termsOfServiceInoRepository.findById(termsOfServiceInfoId).orElseThrow(
                () -> new CustomException(ErrorCode.TERMS_OF_SERVICE_INFO_NOT_FOUND)
        );

        List<Item> itemList = itemRepository.findAllByTermsOfServiceInfo(termsOfServiceInfo);

        for (Item item : itemList) {
            item.changeTermsOfServiceInfo(null);
        }

        termsOfServiceInoRepository.delete(termsOfServiceInfo);
    }
}
