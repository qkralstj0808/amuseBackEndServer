package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Tile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TileRepository extends JpaRepository<Tile, Long> {

}
