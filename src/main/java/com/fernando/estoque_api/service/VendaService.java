package com.fernando.estoque_api.service;

import org.springframework.stereotype.Service;

import com.fernando.estoque_api.dto.venda.VendaRequestDTO;
import com.fernando.estoque_api.dto.venda.VendaResponseDTO;
import com.fernando.estoque_api.entity.Venda;
import com.fernando.estoque_api.entity.Produto;
import com.fernando.estoque_api.repository.VendaRepository;
import com.fernando.estoque_api.mapper.VendaMapper;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final VendaMapper vendaMapper;

    public VendaService(VendaRepository vendaRepository, VendaMapper vendaMapper){
        this.vendaRepository = vendaRepository;
        this.vendaMapper = vendaMapper;
    }
    public VendaResponseDTO criarVenda(Long usuarioId, List<Produto> produtos){

        


        VendaResponseDTO response = new VendaResponseDTO();
        return response;
    }
}
