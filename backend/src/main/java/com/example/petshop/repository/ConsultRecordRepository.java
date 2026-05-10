package com.example.petshop.repository;

import com.example.petshop.entity.ConsultRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 问诊记录数据访问层
 */
@Repository
public interface ConsultRecordRepository extends JpaRepository<ConsultRecord, Long> {

    Optional<ConsultRecord> findByConsultNo(String consultNo);

    List<ConsultRecord> findByUserId(Long userId);

    List<ConsultRecord> findByStatus(String status);
}