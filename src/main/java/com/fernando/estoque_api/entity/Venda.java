package com.fernando.estoque_api.entity;


import jakarta.persistence.*;
import java.util.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fernando.estoque_api.enums.VendaStatus;


@Entity
@Table(name="vendas")

public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Usuario usuario;

    private Cliente cliente;

    private List<ItemVenda> itens;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime soldAt;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VendaStatus status;
}
