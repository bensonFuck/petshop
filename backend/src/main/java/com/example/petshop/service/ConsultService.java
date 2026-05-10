package com.example.petshop.service;

import com.example.petshop.entity.ConsultRecord;
import com.example.petshop.repository.ConsultRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 问诊服务类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultService {

    private final ConsultRecordRepository consultRecordRepository;

    /**
     * 查询所有问诊记录
     */
    public List<ConsultRecord> findAll() {
        return consultRecordRepository.findAll();
    }

    /**
     * 根据用户ID查询问诊记录
     */
    public List<ConsultRecord> findByUserId(Long userId) {
        return consultRecordRepository.findByUserId(userId);
    }

    /**
     * 根据状态查询问诊记录
     */
    public List<ConsultRecord> findByStatus(String status) {
        return consultRecordRepository.findByStatus(status);
    }

    /**
     * 根据ID查询问诊记录
     */
    public ConsultRecord findById(Long id) {
        return consultRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("问诊记录不存在: " + id));
    }

    /**
     * 创建问诊记录
     */
    @Transactional
    public ConsultRecord create(ConsultRecord record) {
        record.setConsultNo(generateConsultNo());
        log.info("创建问诊记录: {}", record.getConsultNo());
        return consultRecordRepository.save(record);
    }

    /**
     * 回复问诊
     */
    @Transactional
    public ConsultRecord reply(Long id, String answer, String vetName) {
        ConsultRecord record = findById(id);
        record.setAnswer(answer);
        record.setVetName(vetName);
        record.setStatus("REPLIED");
        record.setUpdatedAt(LocalDateTime.now());
        log.info("回复问诊记录: {}", record.getConsultNo());
        return consultRecordRepository.save(record);
    }

    /**
     * 关闭问诊
     */
    @Transactional
    public ConsultRecord close(Long id) {
        ConsultRecord record = findById(id);
        record.setStatus("CLOSED");
        record.setUpdatedAt(LocalDateTime.now());
        log.info("关闭问诊记录: {}", record.getConsultNo());
        return consultRecordRepository.save(record);
    }

    /**
     * 删除问诊记录
     */
    @Transactional
    public void delete(Long id) {
        if (!consultRecordRepository.existsById(id)) {
            throw new RuntimeException("问诊记录不存在: " + id);
        }
        consultRecordRepository.deleteById(id);
        log.info("删除问诊记录: {}", id);
    }

    /**
     * 生成问诊编号
     */
    private String generateConsultNo() {
        return "CONS" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}