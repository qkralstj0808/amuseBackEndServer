package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.domain.itemAdditionalInfo.AdditionalReservationInfo;
import com.example.amusetravelproejct.domain.itemAdditionalInfo.PaymentCancelPolicyInfo;
import com.example.amusetravelproejct.domain.itemAdditionalInfo.PaymentMethodInfo;
import com.example.amusetravelproejct.domain.itemAdditionalInfo.TermsOfServiceInfo;
import com.example.amusetravelproejct.domain.person_enum.DisplayStatus;
import com.example.amusetravelproejct.repository.custom.ItemRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom, QuerydslPredicateExecutor<Item> {

    Optional<Item> findById(Long id);
    Optional<Item> findByItemCode(String itemCode);
    Page<Item> findAllByDisplay(Boolean display, Pageable pageable);

    Optional<Item> findByIdAndDisplayTrue(Long id);

    List<Item> findAllByDisplay(Boolean display);

    List<Item> findAllByAdditionalReservationInfo(AdditionalReservationInfo additionalReservationInfo);

}
