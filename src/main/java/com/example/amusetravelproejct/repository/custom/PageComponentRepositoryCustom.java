package com.example.amusetravelproejct.repository.custom;

import com.example.amusetravelproejct.domain.PageComponent;

import java.util.List;

public interface PageComponentRepositoryCustom {

    List<PageComponent> findListByPageComponentIdList(List<Long> component_id_list);
}
