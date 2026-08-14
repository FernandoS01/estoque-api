package com.fernando.estoque_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fernando.estoque_api.dto.sale.SaleRequestDTO;
import com.fernando.estoque_api.dto.sale.SaleResponseDTO;
import com.fernando.estoque_api.service.SaleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/sales")
public class SaleController {
    private final SaleService saleService;
    public SaleController(SaleService saleService){
        this.saleService = saleService;
    }
    @PostMapping("/")
    public SaleResponseDTO createSale(@RequestBody SaleRequestDTO venda) {
        return saleService.createSale(venda);
    }
    
}
