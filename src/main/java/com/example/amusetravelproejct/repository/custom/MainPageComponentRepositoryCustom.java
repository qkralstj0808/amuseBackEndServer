package com.example.amusetravelproejct.repository.custom;

import com.example.amusetravelproejct.domain.PageComponent;

import java.util.List;

public interface MainPageComponentRepositoryCustom {

    List<PageComponent> findByTypeSortSequence(String type);
}
