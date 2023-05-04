package com.example.amusetravelproejct.controller.admin.service;

import com.example.amusetravelproejct.domain.ItemCourse;
import com.example.amusetravelproejct.repository.ItemCourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ItemCourseService {
    private final ItemCourseRepository itemCourseRepository;


    public Optional<ItemCourse> saveItemCourse(ItemCourse itemCourse) {
        return Optional.of(itemCourseRepository.save(itemCourse));
    }
}
