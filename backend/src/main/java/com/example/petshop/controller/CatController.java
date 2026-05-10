package com.example.petshop.controller;

import com.example.petshop.entity.Cat;
import com.example.petshop.service.CatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 猫咪管理控制器
 */
@RestController
@RequestMapping("/api/cats")
@RequiredArgsConstructor
public class CatController {

    private final CatService catService;

    /**
     * 查询所有猫咪
     */
    @GetMapping
    public ResponseEntity<List<Cat>> getAllCats(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String breed) {
        List<Cat> cats;
        if (status != null && breed != null) {
            cats = catService.findByStatus(status);
            cats = cats.stream()
                    .filter(cat -> breed.equals(cat.getBreed()))
                    .toList();
        } else if (status != null) {
            cats = catService.findByStatus(status);
        } else if (breed != null) {
            cats = catService.findByBreed(breed);
        } else {
            cats = catService.findAll();
        }
        return ResponseEntity.ok(cats);
    }

    /**
     * 根据ID查询猫咪
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cat> getCatById(@PathVariable Long id) {
        Cat cat = catService.findById(id);
        return ResponseEntity.ok(cat);
    }

    /**
     * 创建猫咪
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Cat> createCat(@RequestBody Cat cat) {
        Cat createdCat = catService.create(cat);
        return ResponseEntity.ok(createdCat);
    }

    /**
     * 更新猫咪
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Cat> updateCat(@PathVariable Long id, @RequestBody Cat cat) {
        cat.setId(id);
        Cat updatedCat = catService.update(cat);
        return ResponseEntity.ok(updatedCat);
    }

    /**
     * 删除猫咪
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCat(@PathVariable Long id) {
        catService.delete(id);
        return ResponseEntity.noContent().build();
    }
}