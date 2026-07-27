package com.fernando.estoque_api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime; 

import jakarta.persistence.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Changelog.Timestamp;

@Entity
@Table(name="produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String sku;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false,columnDefinition = "INTEGER DEFAULT 0")
    private Integer stockAmount = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

}
