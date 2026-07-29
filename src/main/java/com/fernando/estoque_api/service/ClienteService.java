package com.fernando.estoque_api.service;

import org.springframework.stereotype.Service;

import com.fernando.estoque_api.dto.cliente.ClienteRequestDTO;
import com.fernando.estoque_api.dto.cliente.ClienteResponseDTO;
import com.fernando.estoque_api.entity.Produto;
import com.fernando.estoque_api.repository.ProdutoRepository;

@Service
public class ClienteService {

    private final ProdutoRepository produtoRepository;

    public ClienteService(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }
}
