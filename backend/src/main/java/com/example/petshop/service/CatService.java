package com.example.petshop.service;

import com.example.petshop.entity.Cat;
import com.example.petshop.repository.CatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 猫咪服务类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CatService {

    private final CatRepository catRepository;

    /**
     * 查询所有猫咪
     */
    public List<Cat> findAll() {
        return catRepository.findAll();
    }

    /**
     * 根据状态查询猫咪
     */
    public List<Cat> findByStatus(String status) {
        return catRepository.findByStatus(status);
    }

    /**
     * 根据品种查询猫咪
     */
    public List<Cat> findByBreed(String breed) {
        return catRepository.findByBreed(breed);
    }

    /**
     * 根据ID查询猫咪
     */
    public Cat findById(Long id) {
        return catRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("猫咪不存在: " + id));
    }

    /**
     * 创建猫咪
     */
    @Transactional
    public Cat create(Cat cat) {
        log.info("创建猫咪: {}", cat.getName());
        return catRepository.save(cat);
    }

    /**
     * 更新猫咪
     */
    @Transactional
    public Cat update(Cat cat) {
        Cat existing = findById(cat.getId());
        existing.setName(cat.getName());
        existing.setBreed(cat.getBreed());
        existing.setGender(cat.getGender());
        existing.setAge(cat.getAge());
        existing.setPrice(cat.getPrice());
        existing.setDescription(cat.getDescription());
        existing.setImageUrl(cat.getImageUrl());
        existing.setStatus(cat.getStatus());
        existing.setBadge(cat.getBadge());
        existing.setHealthInfo(cat.getHealthInfo());
        log.info("更新猫咪: {}", existing.getName());
        return catRepository.save(existing);
    }

    /**
     * 删除猫咪
     */
    @Transactional
    public void delete(Long id) {
        if (!catRepository.existsById(id)) {
            throw new RuntimeException("猫咪不存在: " + id);
        }
        catRepository.deleteById(id);
        log.info("删除猫咪: {}", id);
    }
}