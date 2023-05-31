package com.example.amusetravelproejct.repository.custom;

import com.example.amusetravelproejct.domain.MainPageComponent;

import java.util.List;

public interface MainPageComponentRepositoryCustom {

    List<MainPageComponent> findByTypeSortSequence(String type);
}
