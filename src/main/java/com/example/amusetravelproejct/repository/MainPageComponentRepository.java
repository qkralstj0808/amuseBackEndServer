package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.MainPageComponent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainPageComponentRepository extends JpaRepository<MainPageComponent,Long> {
    public List<MainPageComponent> findNotSequenceOrderBySequence(Long sequence);
    public List<MainPageComponent> findBySequenceNot(Long sequence);

    List<MainPageComponent> findBySequenceNotOrderBySequence(long l);
}
