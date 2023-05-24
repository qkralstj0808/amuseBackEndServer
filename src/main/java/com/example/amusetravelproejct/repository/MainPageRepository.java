package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.MainPage;
import com.example.amusetravelproejct.domain.MainPageComponent;
import com.example.amusetravelproejct.domain.Tile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainPageRepository extends JpaRepository<MainPage, Long> {
    List<MainPage> findByMainPageComponent(MainPageComponent mainPageComponent);
    List<MainPage> findByTile(Tile tile);

}
