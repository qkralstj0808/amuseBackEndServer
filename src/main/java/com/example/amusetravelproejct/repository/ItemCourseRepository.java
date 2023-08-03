package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.ItemCourse;
import com.example.amusetravelproejct.repository.custom.ItemCourseRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCourseRepository extends JpaRepository<ItemCourse, Long>, ItemCourseRepositoryCustom {
}
