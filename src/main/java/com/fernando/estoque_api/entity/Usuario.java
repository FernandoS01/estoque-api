package com.fernando.estoque_api.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.*;
import jakarta.persistence.*;

@Entity
@Table(name="usuarios")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100,nullable = false)
    private String name;

    @Column(length = 255,nullable = false, unique = true)
    private String email;

    @Column(length = 255,nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

}