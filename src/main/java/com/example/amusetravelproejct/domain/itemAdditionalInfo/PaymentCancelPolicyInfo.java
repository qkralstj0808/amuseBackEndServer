package com.example.amusetravelproejct.domain.itemAdditionalInfo;

import com.example.amusetravelproejct.dto.request.ProductRegisterDto;
import com.example.amusetravelproejct.dto.response.AdditionalInfoResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PaymentCancelPolicyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String type;

    @Column(length = 5000)
    String content;


    protected PaymentCancelPolicyInfo(String name, String content) {
        this.type = name;
        this.content = content;
    }

    public static PaymentCancelPolicyInfo createFromDto(ProductRegisterDto.CancelPolicyInfoCreateOrUpdateDto cancelPolicyInfoCreateOrUpdateDto) {
        return new PaymentCancelPolicyInfo(cancelPolicyInfoCreateOrUpdateDto.getType(), cancelPolicyInfoCreateOrUpdateDto.getContent());
    }

    public AdditionalInfoResponse.CancelPolicyInfoResponse createResponseDto() {
        return new AdditionalInfoResponse.CancelPolicyInfoResponse(this.id, this.type, this.content);
    }

    public void updateWithDto(ProductRegisterDto.CancelPolicyInfoCreateOrUpdateDto cancelPolicyInfoUpdateDto) {
        this.type = cancelPolicyInfoUpdateDto.getType();
        this.content = cancelPolicyInfoUpdateDto.getContent();
    }
}
