package com.fernando.estoque_api.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name="itens_venda")
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Venda venda;

    private Produto produto;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision=10, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, precision=10, scale = 2)
    private BigDecimal subtotal;
}
