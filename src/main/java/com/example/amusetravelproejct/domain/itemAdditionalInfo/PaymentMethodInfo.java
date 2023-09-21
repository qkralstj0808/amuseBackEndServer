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

    private String name;

    @Column(length = 5000)
    String content;

    @OneToMany(mappedBy = "paymentMethodInfo")
    private List<Item> items = new ArrayList<>();

    public PaymentMethodInfo(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public static PaymentMethodInfo createFromDto(ProductRegisterDto.PaymentMethodInfoDto paymentMethodInfo) {
        return new PaymentMethodInfo(paymentMethodInfo.getName(), paymentMethodInfo.getContent());
    }

    public AdditionalInfoResponse.PaymentMethodInfoResponse createResponseDto() {
        return new AdditionalInfoResponse.PaymentMethodInfoResponse(this.getId(), this.getName(), this.getContent());
    }

    public void updateWithDto(ProductRegisterDto.PaymentMethodInfoUpdateDto paymentMethodInfoUpdateDto) {
        this.name = paymentMethodInfoUpdateDto.getName();
        this.content = paymentMethodInfoUpdateDto.getContent();
    }
}
