package com.fernando.estoque_api.service;

import com.fernando.estoque_api.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import com.fernando.estoque_api.dto.product.ProductRequestDTO;
import com.fernando.estoque_api.dto.product.ProductResponseDTO;
import com.fernando.estoque_api.entity.Product;
import com.fernando.estoque_api.exception.ResourceAlreadyExistsException;
import com.fernando.estoque_api.exception.ResourceNotFoundException;
import com.fernando.estoque_api.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }
    public ProductResponseDTO createProduct(ProductRequestDTO dto){
       if(productRepository.existsBySku(dto.getSku())){
        throw new ResourceAlreadyExistsException("SKU ja cadastrado");
       } 
       Product product = new Product();
       product.setSku(dto.getSku());
       product.setName(dto.getName());
       product.setDescription(dto.getDescription());
       product.setPrice(dto.getPrice());
       product.setStockAmount(dto.getStockAmount() != null ? dto.getStockAmount():0);

       Product updatedProduct = productRepository.save(product);

       return productMapper.toDTO(updatedProduct);
    }
    public ProductResponseDTO findProductById(Long id){
        Product product = productRepository.findByIdDeletedAtIsNull(id).orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado."));
          
        return productMapper.toDTO(product);
    }
    public ProductResponseDTO findProductBySku(String sku){

        Product product = productRepository.findBySkuDeletedAtIsNull(sku).orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado."));
        
        return productMapper.toDTO(product);
    }
    public List<ProductResponseDTO> findAllProducts(){
        List<Product> products = productRepository.findByDeletedAtIsNull();

        List<ProductResponseDTO> response = products.stream().map(product -> {
            return productMapper.toDTO(product);
        }).toList();
        return response;
    }
    public ProductResponseDTO updateProductById(Long id, ProductRequestDTO dto){
        Product product = productRepository.findByIdDeletedAtIsNull(id).orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado."));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockAmount(dto.getStockAmount());

        Product produtoAtualizado = productRepository.save(product);
        
        return productMapper.toDTO(produtoAtualizado);
    }
    public ProductResponseDTO updateProductBySku(String sku, ProductRequestDTO dto){
        Product product = productRepository.findBySkuDeletedAtIsNull(sku).orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado."));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockAmount(dto.getStockAmount());

        Product produtoAtualizado = productRepository.save(product);

        return productMapper.toDTO(produtoAtualizado);
    }
    public void deleteProduct(Long id){
        Product product = productRepository.findByIdDeletedAtIsNull(id).orElseThrow(
            ()-> new ResourceNotFoundException("Produto não encontrado"));
        
        product.setDeletedAt(LocalDateTime.now());
        
        productRepository.save(product);
}}
