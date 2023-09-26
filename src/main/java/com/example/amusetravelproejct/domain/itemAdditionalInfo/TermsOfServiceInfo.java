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
public class TermsOfServiceInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 5000)
    String content;

    @OneToMany(mappedBy = "termsOfServiceInfo")
    private List<Item> items = new ArrayList<>();

    private TermsOfServiceInfo(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public static TermsOfServiceInfo createFromDto(ProductRegisterDto.TermsOfServiceInfoDto termsOfServiceInfo) {
        return new TermsOfServiceInfo(termsOfServiceInfo.getName(), termsOfServiceInfo.getContent());
    }

    public ProductRegisterDto.TermsOfServiceInfoDto createDto() {
        return new ProductRegisterDto.TermsOfServiceInfoDto(this.getId(), this.getName(), this.getContent());
    }

    public AdditionalInfoResponse.TermsOfServiceInfoResponse createResponseDto() {
        return new AdditionalInfoResponse.TermsOfServiceInfoResponse(this.getId(), this.getName(), List.of(this.getContent().split("\n")));
    }

    public void updateWithDto(ProductRegisterDto.TermsOfServiceInfoUpdateDto termsOfServiceInfoUpdateDto) {
        this.name = termsOfServiceInfoUpdateDto.getName();
        this.content = termsOfServiceInfoUpdateDto.getContent();
    }
}
