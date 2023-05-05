package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.ItemCourse;
import com.example.amusetravelproejct.repository.custom.ItemCourseRepositoryCustom;
import com.example.amusetravelproejct.repository.customImpl.ItemCourseRepositoryImpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCourseRepository extends JpaRepository<ItemCourse, Long>, ItemCourseRepositoryCustom {
}
