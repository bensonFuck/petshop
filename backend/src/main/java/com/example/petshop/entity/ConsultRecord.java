package com.example.petshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 问诊记录实体类
 */
@Entity
@Table(name = "consult_records")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String consultNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 50)
    private String catName;

    @Column(length = 50)
    private String catBreed;

    @Column(length = 20)
    private String catAge;

    @Column(nullable = false, length = 500)
    private String question;

    @Column(length = 1000)
    private String answer;

    @Column(length = 20)
    @Builder.Default
    private String status = "PENDING";

    @Column(length = 50)
    private String vetName;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
}