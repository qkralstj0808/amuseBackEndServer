package com.example.amusetravelproejct.repository.itemAdditionalInfo;

import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.domain.itemAdditionalInfo.AdditionalReservationInfo;
import com.example.amusetravelproejct.domain.itemAdditionalInfo.TermsOfServiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TermsOfServiceInoRepository extends JpaRepository<TermsOfServiceInfo, Long> {
    List<TermsOfServiceInfo> findAllByTypeOrderBySequenceNumAsc(String type);

    void deleteAllByType(String type);
}
