package com.fernando.estoque_api.service;

import com.fernando.estoque_api.mapper.ProdutoMapper;
import org.springframework.stereotype.Service;

import com.fernando.estoque_api.dto.produto.ProdutoRequestDTO;
import com.fernando.estoque_api.dto.produto.ProdutoResponseDTO;
import com.fernando.estoque_api.entity.Produto;
import com.fernando.estoque_api.exception.ResourceAlreadyExistsException;
import com.fernando.estoque_api.exception.ResourceNotFoundException;
import com.fernando.estoque_api.repository.ProdutoRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProdutoService {

    private final ProdutoMapper produtoMapper;
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper){
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    public ProdutoResponseDTO createProduto(ProdutoRequestDTO dto){
       if(produtoRepository.existsBySku(dto.getSku())){
        throw new ResourceAlreadyExistsException("SKU ja cadastrado");
       } 
       Produto produto = new Produto();
       produto.setSku(dto.getSku());
       produto.setName(dto.getName());
       produto.setDescription(dto.getDescription());
       produto.setPrice(dto.getPrice());
       produto.setStockAmount(dto.getStockAmount() != null ? dto.getStockAmount():0);

       Produto produtoSalvo = produtoRepository.save(produto);

       return produtoMapper.toDTO(produtoSalvo);
    }

    public ProdutoResponseDTO buscarProdutoPorId(Long id){
        Produto produto = produtoRepository.findByIdDeletedAtIsNull(id).orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado."));
          
        return produtoMapper.toDTO(produto);
    }

    public ProdutoResponseDTO buscarProdutoPorSku(String sku){

        Produto produto = produtoRepository.findBySkuDeletedAtIsNull(sku).orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado."));
        
        return produtoMapper.toDTO(produto);
    }

    public List<ProdutoResponseDTO> listarProdutos(){
        List<Produto> produtos = produtoRepository.findByDeletedAtIsNull();

        List<ProdutoResponseDTO> response = produtos.stream().map(produto -> {
            return produtoMapper.toDTO(produto);
        }).toList();
        return response;
    }

    public ProdutoResponseDTO atualizarProdutoPorId(Long id, ProdutoRequestDTO dto){
        Produto produto = produtoRepository.findByIdDeletedAtIsNull(id).orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado"));

        produto.setName(dto.getName());
        produto.setDescription(dto.getDescription());
        produto.setPrice(dto.getPrice());
        produto.setStockAmount(dto.getStockAmount());

        Produto produtoAtualizado = produtoRepository.save(produto);
        
        return produtoMapper.toDTO(produtoAtualizado);
    }

    public ProdutoResponseDTO atualizarProdutoPorSKU(String sku, ProdutoRequestDTO dto){
        Produto produto = produtoRepository.findBySkuDeletedAtIsNull(sku).orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado"));

        produto.setName(dto.getName());
        produto.setDescription(dto.getDescription());
        produto.setPrice(dto.getPrice());
        produto.setStockAmount(dto.getStockAmount());

        Produto produtoAtualizado = produtoRepository.save(produto);

        return produtoMapper.toDTO(produtoAtualizado);
    }
    
    public void deletarProduto(Long id){
        Produto produto = produtoRepository.findByIdDeletedAtIsNull(id).orElseThrow(
            ()-> new ResourceNotFoundException("Produto não encontrado"));
        
        produto.setDeletedAt(LocalDateTime.now());
        
        produtoRepository.save(produto);
}}
