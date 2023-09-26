package com.example.amusetravelproejct.domain.itemAdditionalInfo;

import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.dto.request.ProductRegisterDto;
import com.example.amusetravelproejct.dto.response.AdditionalInfoResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class PaymentCancelPolicyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 5000)
    String content;

    @OneToMany(mappedBy = "paymentCancelPolicyInfo")
    private List<Item> items = new ArrayList<>();

    protected PaymentCancelPolicyInfo(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public static PaymentCancelPolicyInfo createFromDto(ProductRegisterDto.CancelPolicyInfoDto cancelPolicyInfo) {
        return new PaymentCancelPolicyInfo(cancelPolicyInfo.getName(), cancelPolicyInfo.getContent());
    }

    public AdditionalInfoResponse.CancelPolicyInfoResponse createResponseDto() {
        return new AdditionalInfoResponse.CancelPolicyInfoResponse(this.id, this.name, this.content);
    }

    public void updateWithDto(ProductRegisterDto.CancelPolicyInfoUpdateDto cancelPolicyInfoUpdateDto) {
        this.name = cancelPolicyInfoUpdateDto.getName();
        this.content = cancelPolicyInfoUpdateDto.getContent();
    }
}
