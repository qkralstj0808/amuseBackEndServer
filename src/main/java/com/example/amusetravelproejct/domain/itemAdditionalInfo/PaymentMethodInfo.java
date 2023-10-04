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
public class PaymentMethodInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String type;

    @Column(length = 5000)
    String content;


    public PaymentMethodInfo(String name, String content) {
        this.type = name;
        this.content = content;
    }

    public static PaymentMethodInfo createFromDto(ProductRegisterDto.PaymentMethodInfoDto paymentMethodInfo) {
        return new PaymentMethodInfo(paymentMethodInfo.getName(), paymentMethodInfo.getContent());
    }

    public AdditionalInfoResponse.PaymentMethodInfoResponse createResponseDto() {
        return new AdditionalInfoResponse.PaymentMethodInfoResponse(this.getId(), this.getType(), this.getContent());
    }

    public void updateWithDto(ProductRegisterDto.PaymentMethodInfoUpdateDto paymentMethodInfoUpdateDto) {
        this.type = paymentMethodInfoUpdateDto.getName();
        this.content = paymentMethodInfoUpdateDto.getContent();
    }
}
