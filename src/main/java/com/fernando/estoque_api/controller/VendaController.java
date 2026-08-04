package com.fernando.estoque_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fernando.estoque_api.dto.venda.VendaRequestDTO;
import com.fernando.estoque_api.dto.venda.VendaResponseDTO;
import com.fernando.estoque_api.service.VendaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/vendas")
public class VendaController {
    private final VendaService vendaService;
    public VendaController(VendaService vendaService){
        this.vendaService = vendaService;
    }
    @PostMapping("/")
    public VendaResponseDTO criarVenda(@RequestBody VendaRequestDTO venda) {
        return vendaService.criarVenda(venda);
    }
    
}
