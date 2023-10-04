package com.example.amusetravelproejct.domain.itemAdditionalInfo;

import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.dto.request.ProductRegisterDto;
import com.example.amusetravelproejct.dto.response.AdditionalInfoResponse;
import lombok.Builder;
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

    private String title;
    @Column(unique = true)
    private String type;
    private int sequenceNum;
    private Boolean mandatory;

    @Column(length = 5000)
    private String content;

    @Builder
    public TermsOfServiceInfo(String title, String type, int sequenceNum, Boolean mandatory, String content) {
        this.title = title;
        this.type = type;
        this.sequenceNum = sequenceNum;
        this.mandatory = mandatory;
        this.content = content;
    }


    public ProductRegisterDto.TermsOfServiceInfoDto createDto() {
        return new ProductRegisterDto.TermsOfServiceInfoDto(this.getId(), this.getTitle(),  this.getType(), this.getSequenceNum(), this.getMandatory(), this.getContent());
    }

    public AdditionalInfoResponse.TermsOfServiceInfoContentResponse createResponseDto() {
        return new AdditionalInfoResponse.TermsOfServiceInfoContentResponse(this.getId(), this.getType(), this.getTitle(), this.getSequenceNum(), this.getMandatory(), this.getContent());
    }

    public void updateWithDto(ProductRegisterDto.TermsOfServiceInfoContentDto termsOfServiceInfoContentDto) {
        this.title = termsOfServiceInfoContentDto.getTitle();
        this.sequenceNum = termsOfServiceInfoContentDto.getSequenceNum();
        this.mandatory = termsOfServiceInfoContentDto.getMandatory();
        this.content = termsOfServiceInfoContentDto.getContent();
    }
}
