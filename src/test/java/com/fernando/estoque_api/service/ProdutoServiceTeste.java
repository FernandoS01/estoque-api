package com.fernando.estoque_api.service;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fernando.estoque_api.dto.produto.ProdutoRequestDTO;
import com.fernando.estoque_api.dto.produto.ProdutoResponseDTO;

import com.fernando.estoque_api.entity.Produto;
import com.fernando.estoque_api.exception.ResourceAlreadyExistsException;
import com.fernando.estoque_api.exception.ResourceNotFoundException;
import com.fernando.estoque_api.mapper.ProdutoMapper;
import com.fernando.estoque_api.repository.ProdutoRepository;

import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTeste {
    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveCriarProdutoComSucesso() {
        ProdutoRequestDTO request = new ProdutoRequestDTO();
        request.setName("Coca cola");
        request.setSku("CO12");
        request.setStockAmount(12);
      
        Produto produto = new Produto();
        produto.setName("Coca cola");
        produto.setSku("CO12");
        produto.setStockAmount(12);
       
        ProdutoResponseDTO response = new ProdutoResponseDTO();
        response.setName("Coca cola");
        response.setSku("CO12");
        response.setStockAmount(12);

        when(produtoRepository.existsBySku("CO12")).thenReturn(false);

        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        when(produtoMapper.toDTO(produto)).thenReturn(response);


        ProdutoResponseDTO results = produtoService.criarProduto(request);

        assertEquals("Coca cola", results.getName());
        assertEquals("CO12", results.getSku());
        assertEquals(12, results.getStockAmount());

        verify(produtoRepository).save(any(Produto.class));
    }
    @Test 
    void deveLancarExcecaoSeSkuJaExiste(){
        ProdutoRequestDTO request = new ProdutoRequestDTO();
        request.setName("Coca cola");
        request.setSku("CO12");
        request.setStockAmount(12);
      
        when(produtoRepository.existsBySku("CO12")).thenReturn(true);

        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class,
             ()-> produtoService.criarProduto(request));

        assertEquals("SKU ja cadastrado", exception.getMessage());
        
        verify(produtoRepository, never()).save(any(Produto.class));
        verify(produtoMapper,never()).toDTO(any(Produto.class));
    }
    @Test
    void deveRetornarUmProdutoSeOSkuExistir(){
        ProdutoRequestDTO request = new ProdutoRequestDTO();
        request.setName("Coca cola");
        request.setSku("CO12");
        request.setStockAmount(12);

        Produto produto = new Produto();
        produto.setName("Coca cola");
        produto.setSku("CO12");
        produto.setStockAmount(12);

        ProdutoResponseDTO response = new ProdutoResponseDTO();
        response.setName("Coca cola");
        response.setSku("CO12");
        response.setStockAmount(12);
        
        when(produtoRepository.findBySkuDeletedAtIsNull("CO12")).thenReturn(Optional.of(produto));
        when(produtoMapper.toDTO(produto)).thenReturn(response);
       
        ProdutoResponseDTO results = produtoService.buscarProdutoPorSku("CO12");
        assertEquals("CO12", results.getSku());   
    }
    @Test
    void deveRetornarExcecaoSeSkuNaoExistir(){
    
        when(produtoRepository.findBySkuDeletedAtIsNull("CO13")).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
            ()-> produtoService.buscarProdutoPorSku("CO13"));

        assertEquals("Produto não encontrado.", exception.getMessage());

        verify(produtoRepository).findBySkuDeletedAtIsNull("CO13");
        verify(produtoMapper,never()).toDTO(any(Produto.class));
    }
    @Test
    void deveRetornarExcecaoSeIdNaoExistir(){
        when(produtoRepository.findByIdDeletedAtIsNull(Long.valueOf(1550))).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
            ()-> produtoService.buscarProdutoPorId(Long.valueOf(1550)));

        assertEquals("Produto não encontrado.", exception.getMessage());

        verify(produtoRepository).findByIdDeletedAtIsNull(Long.valueOf(1550));
        verify(produtoMapper,never()).toDTO(any(Produto.class));
    }
    @Test
    void deveAtualizarOProdutoPeloSkuComSucesso(){
        ProdutoRequestDTO request = new ProdutoRequestDTO();
        request.setName("Fanta Laranja");
        request.setPrice(BigDecimal.valueOf(12.00));
        request.setDescription("Garrafa descartavel - 2 litros de volume");
        request.setStockAmount(15);
        
        Produto produto = new Produto();
        produto.setName("Coca cola");
        produto.setSku("CO12");
        produto.setPrice(BigDecimal.valueOf(15.00));
        produto.setDescription("Garrafa retornavel - 1 litro de volume");
        produto.setStockAmount(12);

        ProdutoResponseDTO response = new ProdutoResponseDTO();
        response.setName("Fanta Laranja");
        response.setPrice(BigDecimal.valueOf(12.00));
        response.setDescription("Garrafa descartavel - 2 litros de volume");
        response.setStockAmount(15);

        when(produtoRepository.findBySkuDeletedAtIsNull("CO12")).thenReturn(Optional.of(produto));
        when(produtoRepository.save(produto)).thenReturn(produto);
        when(produtoMapper.toDTO(produto)).thenReturn(response);

        ProdutoResponseDTO results = produtoService.atualizarProdutoPorSKU("CO12", request);

        assertEquals("Fanta Laranja", results.getName());
        assertEquals(BigDecimal.valueOf(12.00), results.getPrice());
        assertEquals("Garrafa descartavel - 2 litros de volume", results.getDescription());
        assertEquals(15,results.getStockAmount());

        verify(produtoRepository).findBySkuDeletedAtIsNull("CO12");
        verify(produtoRepository).save(produto);
        verify(produtoMapper).toDTO(produto);
    }
    @Test
    void deveLancarUmaExcecaoSeNaoForAtualizadoPorSku(){
        ProdutoRequestDTO request = new ProdutoRequestDTO();
        Produto produto = new Produto();

        when(produtoRepository.findBySkuDeletedAtIsNull("CAR12")).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            produtoService.atualizarProdutoPorSKU("CAR12", request);
        });

        assertEquals("Produto não encontrado", exception.getMessage());
        verify(produtoRepository, never()).save(any(Produto.class));
        verify(produtoRepository).findBySkuDeletedAtIsNull("CAR12");
        verify(produtoMapper, never()).toDTO(produto);
    }
    @Test
    void deveDeletarComSucesso(){

    }
}
