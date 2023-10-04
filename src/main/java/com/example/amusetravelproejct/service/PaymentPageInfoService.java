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


import java.util.*;


@Service
@AllArgsConstructor
public class PaymentPageInfoService {

    private final AdditionalReservationInfoRepository additionalReservationInfoRepository;
    private final PaymentMethodInfoRepository paymentMethodInfoRepository;
    private final PaymentCancelPolicyInfoRepository paymentCancelPolicyInfoRepository;
    private final TermsOfServiceInoRepository termsOfServiceInoRepository;

    private final ItemRepository itemRepository;


    public ResponseTemplate<String> createCancelPolicyInfo(
            ProductRegisterDto.CancelPolicyInfoCreateOrUpdateDto cancelPolicyInfoCreateOrUpdateDto
    ) {
        PaymentCancelPolicyInfo byType = paymentCancelPolicyInfoRepository.findByType(cancelPolicyInfoCreateOrUpdateDto.getType());
        if (byType != null) {
            throw new RuntimeException("이미 해당 타입의 취소 정책 내용이 있습니다.");
        }
        PaymentCancelPolicyInfo paymentCancelPolicyInfo = PaymentCancelPolicyInfo.createFromDto(cancelPolicyInfoCreateOrUpdateDto);
        paymentCancelPolicyInfoRepository.save(paymentCancelPolicyInfo);
        return new ResponseTemplate<>("취소 정책이 생성되었습니다.");
    }

    public ResponseTemplate<String> createTermsOfServiceInfo(
            ProductRegisterDto.TermsOfServiceInfoCreateOrUpdateDto termsOfServiceInfoCreateDto
    ) {
        String type = termsOfServiceInfoCreateDto.getType();
        List<ProductRegisterDto.TermsOfServiceInfoContentDto> contentList = termsOfServiceInfoCreateDto.getContent();

        List<TermsOfServiceInfo> termsOfServiceInfos = termsOfServiceInoRepository.findAllByTypeOrderBySequenceNumAsc(type);
        if (termsOfServiceInfos.size() != 0) {
            throw new RuntimeException("이미 해당 타입의 약관 동의 내용이 있습니다.");
        }

        confirmSequenceNumDuple(termsOfServiceInfoCreateDto);

        for (ProductRegisterDto.TermsOfServiceInfoContentDto content : contentList) {
            TermsOfServiceInfo termsOfServiceInfo = TermsOfServiceInfo.builder()
                    .title(content.getTitle())
                    .type(type)
                    .sequenceNum(content.getSequenceNum())
                    .mandatory(content.getMandatory())
                    .content(content.getContent())
                    .build();

            termsOfServiceInoRepository.save(termsOfServiceInfo);
        }

        return new ResponseTemplate<>("약관 정보 내용이 생성되었습니다.");
    }

    private void confirmSequenceNumDuple(ProductRegisterDto.TermsOfServiceInfoCreateOrUpdateDto termsOfServiceInfoCreateDto) {
        List<Integer> numList = new ArrayList<>();

        for (ProductRegisterDto.TermsOfServiceInfoContentDto content : termsOfServiceInfoCreateDto.getContent()) {
            numList.add(content.getSequenceNum());
        }

        Set<Integer> numSet = new HashSet<>(numList);
        if (numSet.size() != numList.size()) {
            throw new RuntimeException("순서 번호가 중복되었습니다.");
        }
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

    public ResponseTemplate<List<AdditionalInfoResponse.TermsOfServiceInfoContentResponse>> getTermsOfServiceInfoList() {
        List<AdditionalInfoResponse.TermsOfServiceInfoContentResponse> responseList = new ArrayList();

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


    public ResponseTemplate<AdditionalInfoResponse.TermsOfServiceInfoContentResponse> getTermsOfServiceInfo(Long termsOfServiceInfoId) {
        TermsOfServiceInfo termsOfServiceInfo = termsOfServiceInoRepository.findById(termsOfServiceInfoId).orElseThrow(
                () -> new CustomException(ErrorCode.TERMS_OF_SERVICE_INFO_NOT_FOUND)
        );

        return new ResponseTemplate<>(termsOfServiceInfo.createResponseDto());
    }

    public ResponseTemplate<AdditionalInfoResponse.CancelPolicyInfoResponse> getCancelPolicyInfoByType(String type) {
        PaymentCancelPolicyInfo paymentCancelPolicyInfo = paymentCancelPolicyInfoRepository.findByType(type);
        if (paymentCancelPolicyInfo==null) {
            throw new CustomException(ErrorCode.CANCEL_POLICY_INFO_NOT_FOUND);
        }

        return new ResponseTemplate<>(paymentCancelPolicyInfo.createResponseDto());
    }

    public ResponseTemplate<AdditionalInfoResponse.TermsOfServiceInfoResponse> getTermsOfServiceInfoByType(String type) {
        List<TermsOfServiceInfo> termsOfServiceInfoList = termsOfServiceInoRepository.findAllByTypeOrderBySequenceNumAsc(type);
        List<AdditionalInfoResponse.TermsOfServiceInfoContentResponse> contentList = new ArrayList<>();
        for (TermsOfServiceInfo termsOfServiceInfo : termsOfServiceInfoList) {
            contentList.add(termsOfServiceInfo.createResponseDto());
        }

        return new ResponseTemplate<>(new AdditionalInfoResponse.TermsOfServiceInfoResponse(type, contentList));
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
    public ResponseTemplate<String> updateCancelPolicyInfo(Long cancelPolicyInfoId, ProductRegisterDto.CancelPolicyInfoCreateOrUpdateDto cancelPolicyInfoUpdateDto) {
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
    public ResponseTemplate<String> updateTermsOfServiceInfo(ProductRegisterDto.TermsOfServiceInfoCreateOrUpdateDto termsOfServiceInfoCreateOrUpdateDto) {
        String type = termsOfServiceInfoCreateOrUpdateDto.getType();
        List<TermsOfServiceInfo> termsOfServiceInfoList = termsOfServiceInoRepository.findAllByTypeOrderBySequenceNumAsc(type);

        if (termsOfServiceInfoList.size() != termsOfServiceInfoCreateOrUpdateDto.getContent().size()) {
            throw new RuntimeException("약관 동의 내용 업데이트 오류 - 개수가 맞지 않습니다.");
        }

        for (int i = 0; i < termsOfServiceInfoList.size(); i++) {
            TermsOfServiceInfo termsOfServiceInfo = termsOfServiceInfoList.get(i);
            termsOfServiceInfo.updateWithDto(termsOfServiceInfoCreateOrUpdateDto.getContent().get(i));
        }
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
//
//        List<Item> itemList = itemRepository.findAllByPaymentCancelPolicyInfo(paymentCancelPolicyInfo);
//
//        paymentCancelPolicyInfoRepository.delete(paymentCancelPolicyInfo);
    }

    @Transactional
    public void deletePaymentMethodInfo(Long paymentMethodInfoId) {
        PaymentMethodInfo paymentMethodInfo = paymentMethodInfoRepository.findById(paymentMethodInfoId).orElseThrow(
                () -> new CustomException(ErrorCode.PAYMENT_METHOD_INFO_NOT_FOUND)
        );

//        List<Item> itemList = itemRepository.findAllByPaymentMethodInfo(paymentMethodInfo);
//
//        paymentMethodInfoRepository.delete(paymentMethodInfo);
    }

    @Transactional
    public void deleteTermsOfServiceInfo(Long termsOfServiceInfoId) {
        TermsOfServiceInfo termsOfServiceInfo = termsOfServiceInoRepository.findById(termsOfServiceInfoId).orElseThrow(
                () -> new CustomException(ErrorCode.TERMS_OF_SERVICE_INFO_NOT_FOUND)
        );

//        List<Item> itemList = itemRepository.findAllByTermsOfServiceInfo(termsOfServiceInfo);

//        termsOfServiceInoRepository.delete(termsOfServiceInfo);
    }


    @Transactional
    public void deleteCancelPolicyInfoByType(String type) {
        paymentCancelPolicyInfoRepository.deleteAllByType(type);
    }

    @Transactional
    public void deleteTermsOfServiceInfoByType(String type) {
        termsOfServiceInoRepository.deleteAllByType(type);
    }

    public ResponseTemplate<AdditionalInfoResponse> getAllPaymentPageInfo(Long reservation_info_id, String type) {
        AdditionalReservationInfo reservationInfo = additionalReservationInfoRepository.findById(reservation_info_id).orElseThrow(
                () -> new CustomException(ErrorCode.RESERVATION_INFO_NOT_FOUND)
        );
        PaymentCancelPolicyInfo paymentCancelPolicyInfo = paymentCancelPolicyInfoRepository.findByType(type);
        List<TermsOfServiceInfo> termsOfServiceInfoList = termsOfServiceInoRepository.findAllByTypeOrderBySequenceNumAsc(type);

        List<AdditionalInfoResponse.TermsOfServiceInfoContentResponse> contentList = new ArrayList<>();
        for (TermsOfServiceInfo termsOfServiceInfo : termsOfServiceInfoList) {
            contentList.add(termsOfServiceInfo.createResponseDto());
        }

        AdditionalInfoResponse.ReservationInfoResponse reservationInfoResponse = new AdditionalInfoResponse.ReservationInfoResponse(reservationInfo.getId(), reservationInfo.getName(), reservationInfo.getContent());
        AdditionalInfoResponse.CancelPolicyInfoResponse cancelPolicyInfoResponse = new AdditionalInfoResponse.CancelPolicyInfoResponse(paymentCancelPolicyInfo.getId(), paymentCancelPolicyInfo.getType(), paymentCancelPolicyInfo.getContent());
        AdditionalInfoResponse.TermsOfServiceInfoResponse termsOfServiceInfoResponse = new AdditionalInfoResponse.TermsOfServiceInfoResponse(type, contentList);


        return new ResponseTemplate<>(new AdditionalInfoResponse(reservationInfoResponse, cancelPolicyInfoResponse, termsOfServiceInfoResponse));
    }
}
