package com.fernando.estoque_api.service;

import org.springframework.stereotype.Service;

import com.fernando.estoque_api.dto.venda.VendaRequestDTO;
import com.fernando.estoque_api.dto.venda.VendaResponseDTO;
import com.fernando.estoque_api.entity.Venda;
import com.fernando.estoque_api.repository.VendaRepository;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;

    public VendaService(VendaRepository vendaRepository){
        this.vendaRepository = vendaRepository;
    }

}
