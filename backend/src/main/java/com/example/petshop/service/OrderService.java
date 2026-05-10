package com.example.petshop.service;

import com.example.petshop.entity.Order;
import com.example.petshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 订单服务类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * 查询所有订单
     */
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    /**
     * 根据用户ID查询订单
     */
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    /**
     * 根据状态查询订单
     */
    public List<Order> findByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    /**
     * 根据订单号查询订单
     */
    public Order findByOrderNo(String orderNo) {
        return orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new RuntimeException("订单不存在: " + orderNo));
    }

    /**
     * 根据ID查询订单
     */
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("订单不存在: " + id));
    }

    /**
     * 创建订单
     */
    @Transactional
    public Order create(Order order) {
        order.setOrderNo(generateOrderNo());
        log.info("创建订单: {}", order.getOrderNo());
        return orderRepository.save(order);
    }

    /**
     * 更新订单状态
     */
    @Transactional
    public Order updateStatus(Long id, String status) {
        Order order = findById(id);
        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        log.info("更新订单状态: {} -> {}", order.getOrderNo(), status);
        return orderRepository.save(order);
    }

    /**
     * 删除订单
     */
    @Transactional
    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("订单不存在: " + id);
        }
        orderRepository.deleteById(id);
        log.info("删除订单: {}", id);
    }

    /**
     * 生成订单号
     */
    private String generateOrderNo() {
        return "ORD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}