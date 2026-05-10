package com.example.petshop.repository;

import com.example.petshop.entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 猫咪数据访问层
 */
@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {

    List<Cat> findByStatus(String status);

    List<Cat> findByBreed(String breed);

    List<Cat> findByStatusAndBreed(String status, String breed);
}