package com.example.petshop.service;

import com.example.petshop.entity.FosterOrder;
import com.example.petshop.repository.FosterOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 寄养订单服务类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FosterOrderService {

    private final FosterOrderRepository fosterOrderRepository;

    /**
     * 查询所有寄养订单
     */
    public List<FosterOrder> findAll() {
        return fosterOrderRepository.findAll();
    }

    /**
     * 根据用户ID查询寄养订单
     */
    public List<FosterOrder> findByUserId(Long userId) {
        return fosterOrderRepository.findByUserId(userId);
    }

    /**
     * 根据状态查询寄养订单
     */
    public List<FosterOrder> findByStatus(String status) {
        return fosterOrderRepository.findByStatus(status);
    }

    /**
     * 根据订单号查询寄养订单
     */
    public FosterOrder findByOrderNo(String orderNo) {
        return fosterOrderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new RuntimeException("寄养订单不存在: " + orderNo));
    }

    /**
     * 根据ID查询寄养订单
     */
    public FosterOrder findById(Long id) {
        return fosterOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("寄养订单不存在: " + id));
    }

    /**
     * 创建寄养订单
     */
    @Transactional
    public FosterOrder create(FosterOrder order) {
        order.setOrderNo(generateOrderNo());
        order.setDays((int) java.time.temporal.ChronoUnit.DAYS.between(order.getStartDate(), order.getEndDate()));
        log.info("创建寄养订单: {}", order.getOrderNo());
        return fosterOrderRepository.save(order);
    }

    /**
     * 更新寄养订单状态
     */
    @Transactional
    public FosterOrder updateStatus(Long id, String status) {
        FosterOrder order = findById(id);
        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        log.info("更新寄养订单状态: {} -> {}", order.getOrderNo(), status);
        return fosterOrderRepository.save(order);
    }

    /**
     * 删除寄养订单
     */
    @Transactional
    public void delete(Long id) {
        if (!fosterOrderRepository.existsById(id)) {
            throw new RuntimeException("寄养订单不存在: " + id);
        }
        fosterOrderRepository.deleteById(id);
        log.info("删除寄养订单: {}", id);
    }

    /**
     * 生成订单号
     */
    private String generateOrderNo() {
        return "FOST" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}