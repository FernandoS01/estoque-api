package com.fernando.estoque_api.service;

import org.springframework.stereotype.Service;

import com.fernando.estoque_api.dto.venda.ItemVendaDTO;
import com.fernando.estoque_api.dto.venda.VendaRequestDTO;
import com.fernando.estoque_api.dto.venda.VendaResponseDTO;
import com.fernando.estoque_api.entity.Venda;
import com.fernando.estoque_api.enums.VendaStatus;
import com.fernando.estoque_api.entity.Produto;
import com.fernando.estoque_api.entity.Cliente;
import com.fernando.estoque_api.entity.Usuario;
import com.fernando.estoque_api.entity.ItemVenda;
import com.fernando.estoque_api.repository.ClienteRepository;
import com.fernando.estoque_api.repository.ProdutoRepository;
import com.fernando.estoque_api.repository.UsuarioRepository;
import com.fernando.estoque_api.repository.VendaRepository;

import jakarta.transaction.Transactional;

import com.fernando.estoque_api.mapper.VendaMapper;
import com.fernando.estoque_api.exception.BusinessException;
import com.fernando.estoque_api.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VendaService {

    
    private final VendaRepository vendaRepository;
    private final VendaMapper vendaMapper;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;

    public VendaService(
        VendaRepository vendaRepository, 
        VendaMapper vendaMapper,
        ClienteRepository clienteRepository,
        UsuarioRepository usuarioRepository,
        ProdutoRepository produtoRepository
    ){
        this.vendaRepository = vendaRepository;
        this.vendaMapper = vendaMapper;
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
    }
    
    public VendaResponseDTO criarVenda(VendaRequestDTO dto){

        Cliente cliente = clienteRepository.findByIdAndDeletedAtIsNull(dto.getClienteId()).orElseThrow(()-> new ResourceNotFoundException("Cliente nao encontrado."));
        Usuario usuario = usuarioRepository.findByIdAndDeletedAtIsNull(dto.getUsuarioId()).orElseThrow(()-> new ResourceNotFoundException("Usuario nao encontrado"));
        
        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setUsuario(usuario);
        venda.setTotalAmount(BigDecimal.ZERO);  

        List<ItemVendaDTO> itens = dto.getItens();
        List<ItemVenda> itensVenda = new ArrayList<>();
        if(dto.getItens() == null || dto.getItens().size() == 0) {
            throw new BusinessException("Nao e possivel completar a venda, adicione itens.");
        }   

        for(ItemVendaDTO item:itens) {
            Produto produto = produtoRepository.findByIdDeletedAtIsNull(item.getProdutoId())
            .orElseThrow(()-> new ResourceNotFoundException("Produto nao encontrado."));
            if(item.getQuantity() <= 0 ){
                throw new BusinessException("Quantidade nao pode ser menor que 0.");
            }
            if(produto.getStockAmount() < item.getQuantity()) {
                throw new BusinessException("Quantidade maior que estoque atual");
            }
            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setVenda(venda);
            itemVenda.setProduto(produto);
            itemVenda.setUnitPrice(produto.getPrice());
            itemVenda.setQuantity(item.getQuantity());
            itemVenda.setSubtotal(produto.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            venda.setTotalAmount(venda.getTotalAmount().add(itemVenda.getSubtotal()));
            produto.setStockAmount(produto.getStockAmount() - item.getQuantity());
            produtoRepository.save(produto);
            itensVenda.add(itemVenda);
        }
        venda.setItens(itensVenda);
        venda.setStatus(VendaStatus.COMPLETED);
        venda.setSoldAt(LocalDateTime.now());
        
        Venda vendaSalva = vendaRepository.save(venda);

        return vendaMapper.toDTO(vendaSalva);
    }
}
