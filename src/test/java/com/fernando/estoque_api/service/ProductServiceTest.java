package com.fernando.estoque_api.service;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fernando.estoque_api.dto.product.ProductRequestDTO;
import com.fernando.estoque_api.dto.product.ProductResponseDTO;
import com.fernando.estoque_api.entity.Product;
import com.fernando.estoque_api.exception.ResourceAlreadyExistsException;
import com.fernando.estoque_api.exception.ResourceNotFoundException;
import com.fernando.estoque_api.mapper.ProductMapper;
import com.fernando.estoque_api.repository.ProductRepository;

import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test void shouldCreateProductSucessfully() {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setName("Coca cola");
        request.setSku("CO12");
        request.setStockAmount(12);
      
        Product product = new Product();
        product.setName("Coca cola");
        product.setSku("CO12");
        product.setStockAmount(12);
       
        ProductResponseDTO response = new ProductResponseDTO();
        response.setName("Coca cola");
        response.setSku("CO12");
        response.setStockAmount(12);

        when(productRepository.existsBySku("CO12")).thenReturn(false);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        when(productMapper.toDTO(product)).thenReturn(response);


        ProductResponseDTO results = productService.createProduct(request);

        assertEquals("Coca cola", results.getName());
        assertEquals("CO12", results.getSku());
        assertEquals(12, results.getStockAmount());

        verify(productRepository).save(any(Product.class));
    }
    @Test void shouldThrowExceptionWhenSkuAlreadyExists(){
        ProductRequestDTO request = new ProductRequestDTO();
        request.setName("Coca cola");
        request.setSku("CO12");
        request.setStockAmount(12);
      
        when(productRepository.existsBySku("CO12")).thenReturn(true);

        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class,
             ()-> productService.createProduct(request));

        assertEquals("SKU ja cadastrado", exception.getMessage());
        
        verify(productRepository, never()).save(any(Product.class));
        verify(productMapper,never()).toDTO(any(Product.class));
    }
    @Test void shouldReturnProductBySku(){
        ProductRequestDTO request = new ProductRequestDTO();
        request.setName("Coca cola");
        request.setSku("CO12");
        request.setStockAmount(12);

        Product product = new Product();
        product.setName("Coca cola");
        product.setSku("CO12");
        product.setStockAmount(12);

        ProductResponseDTO response = new ProductResponseDTO();
        response.setName("Coca cola");
        response.setSku("CO12");
        response.setStockAmount(12);
        
        when(productRepository.findBySkuDeletedAtIsNull("CO12")).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(response);
       
        ProductResponseDTO results = productService.findProductBySku("CO12");
        assertEquals("CO12", results.getSku());   
    }
    @Test void shouldThrowExceptionWhenSkuNotExistis(){
    
        when(productRepository.findBySkuDeletedAtIsNull("CO13")).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
            ()-> productService.findProductBySku("CO13"));

        assertEquals("Produto não encontrado.", exception.getMessage());

        verify(productRepository).findBySkuDeletedAtIsNull("CO13");
        verify(productMapper,never()).toDTO(any(Product.class));
    }
    @Test void shoulThrowExceptionWhenIdNotExists(){
        when(productRepository.findByIdDeletedAtIsNull(Long.valueOf(1550))).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
            ()-> productService.findProductById(Long.valueOf(1550)));

        assertEquals("Produto não encontrado.", exception.getMessage());

        verify(productRepository).findByIdDeletedAtIsNull(Long.valueOf(1550));
        verify(productMapper,never()).toDTO(any(Product.class));
    }
    @Test void shouldUpdateProductBySku(){
        ProductRequestDTO request = new ProductRequestDTO();
        request.setName("Fanta Laranja");
        request.setPrice(BigDecimal.valueOf(12.00));
        request.setDescription("Garrafa descartavel - 2 litros de volume");
        request.setStockAmount(15);
        
        Product product = new Product();
        product.setName("Coca cola");
        product.setSku("CO12");
        product.setPrice(BigDecimal.valueOf(15.00));
        product.setDescription("Garrafa retornavel - 1 litro de volume");
        product.setStockAmount(12);

        ProductResponseDTO response = new ProductResponseDTO();
        response.setName("Fanta Laranja");
        response.setPrice(BigDecimal.valueOf(12.00));
        response.setDescription("Garrafa descartavel - 2 litros de volume");
        response.setStockAmount(15);

        when(productRepository.findBySkuDeletedAtIsNull("CO12")).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(response);

        ProductResponseDTO results = productService.updateProductBySku("CO12", request);

        assertEquals("Fanta Laranja", results.getName());
        assertEquals(BigDecimal.valueOf(12.00), results.getPrice());
        assertEquals("Garrafa descartavel - 2 litros de volume", results.getDescription());
        assertEquals(15,results.getStockAmount());

        verify(productRepository).findBySkuDeletedAtIsNull("CO12");
        verify(productRepository).save(product);
        verify(productMapper).toDTO(product);
    }
    @Test void shouldReturnProducts(){
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Coca Cola");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Fanta");

        ProductResponseDTO response1 = new ProductResponseDTO();
        response1.setId(1L);
        response1.setName("Coca Cola");

        ProductResponseDTO response2 = new ProductResponseDTO();
        response2.setId(2L);
        response2.setName("Fanta");

        when(productRepository.findByDeletedAtIsNull())
                .thenReturn(List.of(product1, product2));

        when(productMapper.toDTO(product1))
                .thenReturn(response1);

        when(productMapper.toDTO(product2))
                .thenReturn(response2);

        List<ProductResponseDTO> results =
                productService.findAllProducts();

        assertEquals(2, results.size());
        assertEquals(1L, results.get(0).getId());
        assertEquals("Coca Cola", results.get(0).getName());
        assertEquals(2L, results.get(1).getId());
        assertEquals("Fanta", results.get(1).getName());

        verify(productRepository).findByDeletedAtIsNull();
        verify(productMapper).toDTO(product1);
        verify(productMapper).toDTO(product2);    
    }
    @Test void shouldReturnProductById() {
        Product product = new Product();
        product.setId(1550L);
        product.setName("Coca cola");
        product.setSku("CO12");
        product.setStockAmount(12);

        ProductResponseDTO response = new ProductResponseDTO();
        response.setId(1550L);
        response.setName("Coca cola");
        response.setSku("CO12");
        response.setStockAmount(12);

        when(productRepository.findByIdDeletedAtIsNull(1550L))
                .thenReturn(Optional.of(product));

        when(productMapper.toDTO(product))
                .thenReturn(response);

        ProductResponseDTO result =
                productService.findProductById(1550L);

        assertEquals(1550L, result.getId());
        assertEquals("Coca cola", result.getName());
        assertEquals("CO12", result.getSku());
        assertEquals(12, result.getStockAmount());

        verify(productRepository).findByIdDeletedAtIsNull(1550L);
        verify(productMapper).toDTO(product);
    }
    @Test void shouldUpdateProductById() {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setName("Fanta Laranja");
        request.setPrice(new BigDecimal("12.00"));
        request.setDescription("Garrafa descartavel - 2 litros de volume");
        request.setStockAmount(15);

        Product product = new Product();
        product.setId(1550L);
        product.setName("Coca cola");
        product.setSku("CO12");
        product.setPrice(new BigDecimal("15.00"));
        product.setDescription("Garrafa retornavel - 1 litro de volume");
        product.setStockAmount(12);

        ProductResponseDTO response = new ProductResponseDTO();
        response.setId(1550L);
        response.setName("Fanta Laranja");
        response.setSku("CO12");
        response.setPrice(new BigDecimal("12.00"));
        response.setDescription("Garrafa descartavel - 2 litros de volume");
        response.setStockAmount(15);

        when(productRepository.findByIdDeletedAtIsNull(1550L))
                .thenReturn(Optional.of(product));

        when(productRepository.save(product))
                .thenReturn(product);

        when(productMapper.toDTO(product))
                .thenReturn(response);

        ProductResponseDTO result =
                productService.updateProductById(1550L, request);

        assertEquals(1550L, result.getId());
        assertEquals("Fanta Laranja", result.getName());
        assertEquals("CO12", result.getSku());
        assertEquals(new BigDecimal("12.00"), result.getPrice());
        assertEquals("Garrafa descartavel - 2 litros de volume",
                result.getDescription());
        assertEquals(15, result.getStockAmount());

        assertEquals("Fanta Laranja", product.getName());
        assertEquals(new BigDecimal("12.00"), product.getPrice());
        assertEquals(15, product.getStockAmount());

        verify(productRepository).findByIdDeletedAtIsNull(1550L);
        verify(productRepository).save(product);
        verify(productMapper).toDTO(product);
    }    
    @Test void shouldDeleteProduct(){
            Product product = new Product();
            product.setId(1550L);

            when(productRepository.findByIdDeletedAtIsNull(1550L)).thenReturn(Optional.of(product));
            productService.deleteProduct(1550L);
            
            assertNotNull(product.getDeletedAt());
            verify(productRepository).findByIdDeletedAtIsNull(1550L);
            verify(productRepository).save(product);
        }
    }
