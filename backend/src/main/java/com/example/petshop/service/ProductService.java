package com.example.petshop.service;

import com.example.petshop.entity.Product;
import com.example.petshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品服务类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 查询所有商品
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * 根据分类查询商品
     */
    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    /**
     * 根据状态查询商品
     */
    public List<Product> findByStatus(String status) {
        return productRepository.findByStatus(status);
    }

    /**
     * 根据ID查询商品
     */
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在: " + id));
    }

    /**
     * 创建商品
     */
    @Transactional
    public Product create(Product product) {
        log.info("创建商品: {}", product.getName());
        return productRepository.save(product);
    }

    /**
     * 更新商品
     */
    @Transactional
    public Product update(Product product) {
        Product existing = findById(product.getId());
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setCategory(product.getCategory());
        existing.setPrice(product.getPrice());
        existing.setStock(product.getStock());
        existing.setImageUrl(product.getImageUrl());
        existing.setStatus(product.getStatus());
        log.info("更新商品: {}", existing.getName());
        return productRepository.save(existing);
    }

    /**
     * 删除商品
     */
    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("商品不存在: " + id);
        }
        productRepository.deleteById(id);
        log.info("删除商品: {}", id);
    }
}