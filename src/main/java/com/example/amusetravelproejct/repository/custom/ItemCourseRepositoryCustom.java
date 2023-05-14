package com.example.amusetravelproejct.repository.custom;

import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.domain.ItemCourse;

import java.util.List;

public interface ItemCourseRepositoryCustom {

    List<ItemCourse> findItemCourseBySequence(Long item_id);
}
