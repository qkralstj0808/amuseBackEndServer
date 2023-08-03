package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.PageComponent;
import com.example.amusetravelproejct.repository.custom.PageComponentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageComponentRepository extends JpaRepository<PageComponent,Long>, PageComponentRepositoryCustom {


}
