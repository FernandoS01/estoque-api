package com.fernando.estoque_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fernando.estoque_api.dto.product.ProductRequestDTO;
import com.fernando.estoque_api.dto.product.ProductResponseDTO;
import com.fernando.estoque_api.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @PostMapping("/")
    public ProductResponseDTO createProduct(@RequestBody ProductRequestDTO product) {
        return productService.createProduct(product);
    }
    @GetMapping("/id/{id}")
    public ProductResponseDTO findProductById(@PathVariable Long id) {
        return productService.findProductById(id);
    }
    @GetMapping("/sku/{sku}")
    public ProductResponseDTO findProductBySku(@PathVariable String sku) {
        return productService.findProductBySku(sku);
    }
    @PutMapping("/{id}")
    public ProductResponseDTO updateProductById(@PathVariable Long id, @RequestBody ProductRequestDTO data) {
        return productService.updateProductById(id, data);
    }
    @GetMapping("/")
    public List<ProductResponseDTO> findAllProducts() {
        return productService.findAllProducts();
    }
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }
    
}
