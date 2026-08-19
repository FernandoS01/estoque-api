package com.fernando.estoque_api.service;

import org.springframework.stereotype.Service;

import com.fernando.estoque_api.entity.Sale;
import com.fernando.estoque_api.enums.SaleStatus;
import com.fernando.estoque_api.entity.Product;
import com.fernando.estoque_api.dto.sale.SaleItemRequestDTO;
import com.fernando.estoque_api.dto.sale.SaleRequestDTO;
import com.fernando.estoque_api.dto.sale.SaleResponseDTO;
import com.fernando.estoque_api.entity.Client;
import com.fernando.estoque_api.entity.User;
import com.fernando.estoque_api.entity.SaleItems;
import com.fernando.estoque_api.repository.ClientRepository;
import com.fernando.estoque_api.repository.ProductRepository;
import com.fernando.estoque_api.repository.UserRepository;
import com.fernando.estoque_api.repository.SaleRepository;

import jakarta.transaction.Transactional;

import com.fernando.estoque_api.mapper.SaleMapper;
import com.fernando.estoque_api.exception.BusinessException;
import com.fernando.estoque_api.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SaleService {

    
    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public SaleService(
        SaleRepository saleRepository, 
        SaleMapper saleMapper,
        ClientRepository clientRepository,
        UserRepository userRepository,
        ProductRepository productRepository
    ){
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }
    
    public SaleResponseDTO createSale(SaleRequestDTO dto){

        Client client = clientRepository.findByIdAndDeletedAtIsNull(dto.getClientId()).orElseThrow(()-> new ResourceNotFoundException("Cliente não encontrado."));
        User user = userRepository.findByIdAndDeletedAtIsNull(dto.getUserId()).orElseThrow(()-> new ResourceNotFoundException("Usuário não encontrado."));
        
        Sale sale = new Sale();
        sale.setClient(client);
        sale.setUser(user);
        sale.setTotalAmount(BigDecimal.ZERO);  

        List<SaleItemRequestDTO> itens = dto.getItens();
        List<SaleItems> saleItems = new ArrayList<>();
        if(dto.getItens() == null || dto.getItens().size() == 0) {
            throw new BusinessException("Não foi possivel completar a venda, adicione itens.");
        }   

        for(SaleItemRequestDTO item:itens) {
            Product product = productRepository.findByIdDeletedAtIsNull(item.getProductId())
            .orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado."));
            if(item.getQuantity() <= 0 ){
                throw new BusinessException("Quantidade não pode ser menor ou igual a 0.");
            }
            if(product.getStockAmount() < item.getQuantity()) {
                throw new BusinessException("Estoque insuficiente");
            }
            SaleItems saleItem = new SaleItems();
            saleItem.setSale(sale);
            saleItem.setProduct(product);
            saleItem.setUnitPrice(product.getPrice());
            saleItem.setQuantity(item.getQuantity());
            saleItem.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            sale.setTotalAmount(sale.getTotalAmount().add(saleItem.getSubtotal()));
            product.setStockAmount(product.getStockAmount() - item.getQuantity());
            productRepository.save(product);
            saleItems.add(saleItem);
        }
        sale.setItens(saleItems);
        sale.setStatus(SaleStatus.COMPLETED);
        sale.setSoldAt(LocalDateTime.now());
        
        Sale createdSale = saleRepository.save(sale);

        return saleMapper.toDTO(createdSale);
    }
}
