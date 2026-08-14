package com.fernando.estoque_api.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="sale_items")
@Getter
@Setter
public class SaleItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sale_id",nullable = false)
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision=10, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, precision=10, scale = 2)
    private BigDecimal subtotal;
}
