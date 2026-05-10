package com.example.petshop.repository;

import com.example.petshop.entity.FosterOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 寄养订单数据访问层
 */
@Repository
public interface FosterOrderRepository extends JpaRepository<FosterOrder, Long> {

    Optional<FosterOrder> findByOrderNo(String orderNo);

    List<FosterOrder> findByUserId(Long userId);

    List<FosterOrder> findByStatus(String status);
}