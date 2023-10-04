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
public class AdditionalReservationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(length = 5000)
    private String content;

    @OneToMany(mappedBy = "additionalReservationInfo")
    private List<Item> items = new ArrayList<>();

    protected AdditionalReservationInfo(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public static AdditionalReservationInfo createFromDto(ProductRegisterDto.ReservationInfoDto reservationInfo) {
        return new AdditionalReservationInfo(reservationInfo.getName(), reservationInfo.getContent());
    }
    public AdditionalInfoResponse.ReservationInfoResponse createResponseDto() {
        return new AdditionalInfoResponse.ReservationInfoResponse(this.id, this.name, this.content);
    }

    public void updateWithDto(ProductRegisterDto.ReservationInfoUpdateDto reservationInfoUpdateDto) {
        this.name = reservationInfoUpdateDto.getName();
        this.content = reservationInfoUpdateDto.getContent();
    }
}
