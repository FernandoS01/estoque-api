package com.fernando.estoque_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fernando.estoque_api.dto.produto.ProdutoRequestDTO;
import com.fernando.estoque_api.dto.produto.ProdutoResponseDTO;
import com.fernando.estoque_api.service.ProdutoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService){
        this.produtoService = produtoService;
    }
    @PostMapping("/")
    public ProdutoResponseDTO criarProduto(@RequestBody ProdutoRequestDTO produto) {
        return produtoService.criarProduto(produto);
    }
    @GetMapping("/id/{id}")
    public ProdutoResponseDTO buscarProdutoPorId(@PathVariable Long id) {
        return produtoService.buscarProdutoPorId(id);
    }
    @GetMapping("/sku/{sku}")
    public ProdutoResponseDTO buscarProdutoPorSku(@PathVariable String sku) {
        return produtoService.buscarProdutoPorSku(sku);
    }
    @PutMapping("/{id}")
    public ProdutoResponseDTO atualizarProduto(@PathVariable Long id, @RequestBody ProdutoRequestDTO data) {
        return produtoService.atualizarProdutoPorId(id, data);
    }
    @GetMapping("/")
    public List<ProdutoResponseDTO> listarProdutos() {
        return produtoService.listarProdutos();
    }
    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable Long id){
        produtoService.deletarProduto(id);
    }
    
}
