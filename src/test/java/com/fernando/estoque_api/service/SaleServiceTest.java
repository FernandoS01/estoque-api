package com.fernando.estoque_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fernando.estoque_api.dto.client.ClientResponseDTO;
import com.fernando.estoque_api.dto.sale.SaleItemRequestDTO;
import com.fernando.estoque_api.dto.sale.SaleRequestDTO;
import com.fernando.estoque_api.dto.sale.SaleResponseDTO;
import com.fernando.estoque_api.dto.user.UserResponseDTO;
import com.fernando.estoque_api.entity.Client;
import com.fernando.estoque_api.entity.Product;
import com.fernando.estoque_api.entity.User;
import com.fernando.estoque_api.entity.Sale;
import com.fernando.estoque_api.exception.BusinessException;
import com.fernando.estoque_api.exception.ResourceNotFoundException;
import com.fernando.estoque_api.mapper.SaleMapper;
import com.fernando.estoque_api.repository.ClientRepository;
import com.fernando.estoque_api.repository.ProductRepository;
import com.fernando.estoque_api.repository.UserRepository;
import com.fernando.estoque_api.repository.SaleRepository;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {
    @Mock private SaleRepository saleRepository;
    @Mock private UserRepository userRepository;
    @Mock private ClientRepository clientRepository;
    @Mock private ProductRepository productRepository;
    @Mock private SaleMapper saleMapper;
    @InjectMocks SaleService saleService;

    @Test void shouldCreateSaleSucessfully(){
        Long user_id = 120L;
        Long client_id = 130L;
        Long product1_id = 140L;
        Long product2_id = 150L;

        SaleItemRequestDTO item1 = new SaleItemRequestDTO();
        item1.setProductId(product1_id);
        item1.setQuantity(2);

        SaleItemRequestDTO item2 = new SaleItemRequestDTO();
        item2.setProductId(product2_id);
        item2.setQuantity(3);

        List<SaleItemRequestDTO> itens = List.of(item1,item2); 

        SaleRequestDTO request = new SaleRequestDTO();
        request.setUserId(user_id);
        request.setClientId(client_id);
        request.setItens(itens);

        Client client = new Client();

        User user = new User();

        Product product1 = new Product();
        product1.setId(product1_id);
        product1.setName("Coca Cola");
        product1.setPrice(BigDecimal.valueOf(12.00));
        product1.setStockAmount(20);
        
        Product product2 = new Product();
        product2.setId(product2_id);
        product2.setName("Fanta Laranja");
        product2.setPrice(BigDecimal.valueOf(15.00));
        product2.setStockAmount(25);


        Sale sale = new Sale();
        sale.setClient(client);
        sale.setUser(user);
        sale.setTotalAmount(BigDecimal.ZERO);

        ClientResponseDTO clientResponse = new ClientResponseDTO();
        clientResponse.setId(client_id);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user_id);

        SaleResponseDTO response = new SaleResponseDTO();
        response.setClient(clientResponse);
        response.setUser(userResponseDTO);
        response.setTotalAmount(BigDecimal.valueOf(69.00));

        when(clientRepository.findByIdAndDeletedAtIsNull(client_id)).thenReturn(Optional.of(client));
        when(userRepository.findByIdAndDeletedAtIsNull(user_id)).thenReturn(Optional.of(user));
        when(productRepository.findByIdDeletedAtIsNull(product1_id)).thenReturn(Optional.of(product1));
        when(productRepository.findByIdDeletedAtIsNull(product2_id)).thenReturn(Optional.of(product2));
        when(saleRepository.save(any(Sale.class))).thenReturn(sale);
        when(saleMapper.toDTO(any(Sale.class))).thenReturn(response);

        SaleResponseDTO results = saleService.createSale(request);

        
        assertEquals(client_id, results.getClient().getId());
        assertEquals(user_id, results.getUser().getId());
        assertEquals(18, product1.getStockAmount());
        assertEquals(22, product2.getStockAmount());
        assertEquals(BigDecimal.valueOf(69.00), results.getTotalAmount());
        verify(productRepository).save(product1);
        verify(productRepository).save(product2);
        verify(saleRepository).save(any(Sale.class));
    }
    @Test void shouldThrowExceptionWhenStockIsInsuficient(){
        Long user_id = 120L;
        Long client_id = 130L;
        Long id_produto = 140L;

        SaleItemRequestDTO item1 = new SaleItemRequestDTO();
        item1.setProductId(id_produto);
        item1.setQuantity(20);

        List<SaleItemRequestDTO> itens = List.of(item1); 

        SaleRequestDTO request = new SaleRequestDTO();
        request.setUserId(user_id);
        request.setClientId(client_id);
        request.setItens(itens);

        Client client = new Client();

        User user = new User();

        Product produto = new Product();
        produto.setId(id_produto);
        produto.setName("Coca Cola");
        produto.setPrice(BigDecimal.valueOf(12.00));
        produto.setStockAmount(10);
      
        Sale sale = new Sale();
        sale.setClient(client);
        sale.setUser(user);
        sale.setTotalAmount(BigDecimal.ZERO);

        when(clientRepository.findByIdAndDeletedAtIsNull(client_id)).thenReturn(Optional.of(client));
        when(userRepository.findByIdAndDeletedAtIsNull(user_id)).thenReturn(Optional.of(user));
        when(productRepository.findByIdDeletedAtIsNull(id_produto)).thenReturn(Optional.of(produto));

        BusinessException exception = assertThrows(BusinessException.class, ()->{
            saleService.createSale(request);
        });

        assertEquals("Estoque insuficiente", exception.getMessage());

        verify(productRepository,never()).save(any(Product.class));
        verify(saleRepository,never()).save(any(Sale.class));

    }
    @Test void shouldThrowExceptionWhenClientNotExists(){
        Long client_id = 10L;
        Long user_id = 11L;
    
        SaleRequestDTO request = new SaleRequestDTO();
        request.setClientId(client_id);
        request.setUserId(user_id);


        when(clientRepository.findByIdAndDeletedAtIsNull(client_id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            saleService.createSale(request);
        });

        assertEquals("Cliente não encontrado.", exception.getMessage());
        verify(saleRepository,never()).save(any(Sale.class));
    }
    @Test void shouldThrowExceptionWhenUserNotExists(){
        Long client_id = 10L;
        Long user_id = 11L;
    
        SaleRequestDTO request = new SaleRequestDTO();
        request.setClientId(client_id);
        request.setUserId(user_id);

        Client client = new Client();

        when(clientRepository.findByIdAndDeletedAtIsNull(client_id)).thenReturn(Optional.of(client));
        when(userRepository.findByIdAndDeletedAtIsNull(user_id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            saleService.createSale(request);
        });

        assertEquals("Usuário não encontrado.", exception.getMessage());
        verify(saleRepository,never()).save(any(Sale.class));
    }
    @Test void shouldThrowExceptionWhenProductNotExists(){
        Long client_id = 10L;
        Long user_id = 11L;
        Long product_id = 110L;

        SaleItemRequestDTO item = new SaleItemRequestDTO();
        item.setProductId(product_id);
        item.setQuantity(10);

        List<SaleItemRequestDTO> itens = List.of(item);
    
        SaleRequestDTO request = new SaleRequestDTO();
        request.setClientId(client_id);
        request.setUserId(user_id);
        request.setItens(itens);

        Client client = new Client();
        User user = new User();

        when(clientRepository.findByIdAndDeletedAtIsNull(client_id)).thenReturn(Optional.of(client));
        when(userRepository.findByIdAndDeletedAtIsNull(user_id)).thenReturn(Optional.of(user));
        when(productRepository.findByIdDeletedAtIsNull(product_id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            saleService.createSale(request);
        });

        assertEquals("Produto não encontrado.", exception.getMessage());
        verify(saleRepository,never()).save(any(Sale.class));
        verify(productRepository,never()).save(any(Product.class));
    }
    @Test void shouldThrowExceptionWhenQuantityIsEqualOrLessThanZero(){
        Long client_id = 10L;
        Long user_id = 11L;
        Long product_id = 110L;

        SaleItemRequestDTO item = new SaleItemRequestDTO();
        item.setProductId(product_id);
        item.setQuantity(0);

        List<SaleItemRequestDTO> itens = List.of(item);
    
        SaleRequestDTO request = new SaleRequestDTO();
        request.setClientId(client_id);
        request.setUserId(user_id);
        request.setItens(itens);

        Client client = new Client();
        User user = new User();
        Product produto = new Product();
        produto.setStockAmount(20);

        when(clientRepository.findByIdAndDeletedAtIsNull(client_id)).thenReturn(Optional.of(client));
        when(userRepository.findByIdAndDeletedAtIsNull(user_id)).thenReturn(Optional.of(user));
        when(productRepository.findByIdDeletedAtIsNull(product_id)).thenReturn(Optional.of(produto));

        BusinessException exception = assertThrows(BusinessException.class, ()->{
            saleService.createSale(request);
        });

        assertEquals("Quantidade não pode ser menor ou igual a 0.", exception.getMessage());
        verify(saleRepository,never()).save(any(Sale.class));
        verify(productRepository,never()).save(any(Product.class));
    }
    @Test void shouldThrowExceptionWhenItemsListIsNull(){
        Long client_id = 10L;
        Long user_id = 11L;
    
        SaleRequestDTO request = new SaleRequestDTO();
        request.setClientId(client_id);
        request.setUserId(user_id);
        request.setItens(null);

        Client client = new Client();
        User user = new User();
       

        when(clientRepository.findByIdAndDeletedAtIsNull(client_id)).thenReturn(Optional.of(client));
        when(userRepository.findByIdAndDeletedAtIsNull(user_id)).thenReturn(Optional.of(user));
      
        BusinessException exception = assertThrows(BusinessException.class, ()->{
            saleService.createSale(request);
        });

        assertEquals("Não foi possivel completar a venda, adicione itens.", exception.getMessage());

        verify(saleRepository,never()).save(any(Sale.class));
    }
    @Test void shouldThrowExceptionWhenItemsListIsEmpty(){
        Long client_id = 10L;
        Long user_id = 11L;
    
        SaleRequestDTO request = new SaleRequestDTO();
        request.setClientId(client_id);
        request.setUserId(user_id);
        request.setItens(List.of());

        Client client = new Client();
        User user = new User();
       

        when(clientRepository.findByIdAndDeletedAtIsNull(client_id)).thenReturn(Optional.of(client));
        when(userRepository.findByIdAndDeletedAtIsNull(user_id)).thenReturn(Optional.of(user));
      
        BusinessException exception = assertThrows(BusinessException.class, ()->{
            saleService.createSale(request);
        });

        assertEquals("Não foi possivel completar a venda, adicione itens.", exception.getMessage());

        verify(saleRepository,never()).save(any(Sale.class));
    }
}
