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

import com.fernando.estoque_api.dto.cliente.ClienteResponseDTO;
import com.fernando.estoque_api.dto.produto.ProdutoResponseDTO;
import com.fernando.estoque_api.dto.usuario.UsuarioResponseDTO;
import com.fernando.estoque_api.dto.venda.ItemVendaRequestDTO;

import com.fernando.estoque_api.dto.venda.VendaRequestDTO;
import com.fernando.estoque_api.dto.venda.VendaResponseDTO;
import com.fernando.estoque_api.entity.Cliente;
import com.fernando.estoque_api.entity.ItemVenda;
import com.fernando.estoque_api.entity.Produto;
import com.fernando.estoque_api.entity.Usuario;
import com.fernando.estoque_api.entity.Venda;
import com.fernando.estoque_api.exception.BusinessException;
import com.fernando.estoque_api.exception.ResourceNotFoundException;
import com.fernando.estoque_api.mapper.VendaMapper;
import com.fernando.estoque_api.repository.ClienteRepository;
import com.fernando.estoque_api.repository.ProdutoRepository;
import com.fernando.estoque_api.repository.UsuarioRepository;
import com.fernando.estoque_api.repository.VendaRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class VendaServiceTest {
    @Mock private VendaRepository vendaRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private ClienteRepository clienteRepository;
    @Mock private ProdutoRepository produtoRepository;
    @Mock private VendaMapper vendaMapper;
    @InjectMocks VendaService vendaService;

    @Test void deveCriarVendaComSucesso(){
        Long id_usuario = 120L;
        Long id_cliente = 130L;
        Long id_produto1 = 140L;
        Long id_produto2 = 150L;

        ItemVendaRequestDTO item1 = new ItemVendaRequestDTO();
        item1.setProdutoId(id_produto1);
        item1.setQuantity(2);

        ItemVendaRequestDTO item2 = new ItemVendaRequestDTO();
        item2.setProdutoId(id_produto2);
        item2.setQuantity(3);

        List<ItemVendaRequestDTO> itens = List.of(item1,item2); 

        VendaRequestDTO request = new VendaRequestDTO();
        request.setUsuarioId(id_usuario);
        request.setClienteId(id_cliente);
        request.setItens(itens);

        Cliente cliente = new Cliente();

        Usuario usuario = new Usuario();

        Produto produto1 = new Produto();
        produto1.setId(id_produto1);
        produto1.setName("Coca Cola");
        produto1.setPrice(BigDecimal.valueOf(12.00));
        produto1.setStockAmount(20);
        
        Produto produto2 = new Produto();
        produto2.setId(id_produto2);
        produto2.setName("Fanta Laranja");
        produto2.setPrice(BigDecimal.valueOf(15.00));
        produto2.setStockAmount(25);


        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setUsuario(usuario);
        venda.setTotalAmount(BigDecimal.ZERO);

        ClienteResponseDTO clienteResponse = new ClienteResponseDTO();
        clienteResponse.setId(id_cliente);

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(id_usuario);

        VendaResponseDTO response = new VendaResponseDTO();
        response.setCliente(clienteResponse);
        response.setUsuario(usuarioResponseDTO);
        response.setTotalAmount(BigDecimal.valueOf(69.00));

        when(clienteRepository.findByIdAndDeletedAtIsNull(id_cliente)).thenReturn(Optional.of(cliente));
        when(usuarioRepository.findByIdAndDeletedAtIsNull(id_usuario)).thenReturn(Optional.of(usuario));
        when(produtoRepository.findByIdDeletedAtIsNull(id_produto1)).thenReturn(Optional.of(produto1));
        when(produtoRepository.findByIdDeletedAtIsNull(id_produto2)).thenReturn(Optional.of(produto2));
        when(vendaRepository.save(any(Venda.class))).thenReturn(venda);
        when(vendaMapper.toDTO(any(Venda.class))).thenReturn(response);

        VendaResponseDTO results = vendaService.criarVenda(request);

        
        assertEquals(id_cliente, results.getCliente().getId());
        assertEquals(id_usuario, results.getUsuario().getId());
        assertEquals(18, produto1.getStockAmount());
        assertEquals(22, produto2.getStockAmount());
        assertEquals(BigDecimal.valueOf(69.00), results.getTotalAmount());
        verify(produtoRepository).save(produto1);
        verify(produtoRepository).save(produto2);
        verify(vendaRepository).save(any(Venda.class));
    }
    @Test void deveLancarExcessaoSeEstoqueForInsuficiente(){
        Long id_usuario = 120L;
        Long id_cliente = 130L;
        Long id_produto = 140L;

        ItemVendaRequestDTO item1 = new ItemVendaRequestDTO();
        item1.setProdutoId(id_produto);
        item1.setQuantity(20);

        List<ItemVendaRequestDTO> itens = List.of(item1); 

        VendaRequestDTO request = new VendaRequestDTO();
        request.setUsuarioId(id_usuario);
        request.setClienteId(id_cliente);
        request.setItens(itens);

        Cliente cliente = new Cliente();

        Usuario usuario = new Usuario();

        Produto produto = new Produto();
        produto.setId(id_produto);
        produto.setName("Coca Cola");
        produto.setPrice(BigDecimal.valueOf(12.00));
        produto.setStockAmount(10);
      
        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setUsuario(usuario);
        venda.setTotalAmount(BigDecimal.ZERO);

        when(clienteRepository.findByIdAndDeletedAtIsNull(id_cliente)).thenReturn(Optional.of(cliente));
        when(usuarioRepository.findByIdAndDeletedAtIsNull(id_usuario)).thenReturn(Optional.of(usuario));
        when(produtoRepository.findByIdDeletedAtIsNull(id_produto)).thenReturn(Optional.of(produto));

        BusinessException exception = assertThrows(BusinessException.class, ()->{
            vendaService.criarVenda(request);
        });

        assertEquals("Estoque insuficiente", exception.getMessage());

        verify(produtoRepository,never()).save(any(Produto.class));
        verify(vendaRepository,never()).save(any(Venda.class));

    }
    @Test void deveRetornarExcessaoSeClienteNaoExistir(){
        Long cliente_id = 10L;
        Long usuario_id = 11L;
    
        VendaRequestDTO request = new VendaRequestDTO();
        request.setClienteId(cliente_id);
        request.setUsuarioId(usuario_id);


        when(clienteRepository.findByIdAndDeletedAtIsNull(cliente_id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            vendaService.criarVenda(request);
        });

        assertEquals("Cliente nao encontrado.", exception.getMessage());
        verify(vendaRepository,never()).save(any(Venda.class));
    }
    @Test void deveRetornarExcessaoSeUsuarioNaoExistir(){
        Long cliente_id = 10L;
        Long usuario_id = 11L;
    
        VendaRequestDTO request = new VendaRequestDTO();
        request.setClienteId(cliente_id);
        request.setUsuarioId(usuario_id);

        Cliente cliente = new Cliente();

        when(clienteRepository.findByIdAndDeletedAtIsNull(cliente_id)).thenReturn(Optional.of(cliente));
        when(usuarioRepository.findByIdAndDeletedAtIsNull(usuario_id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            vendaService.criarVenda(request);
        });

        assertEquals("Usuario nao encontrado", exception.getMessage());
        verify(vendaRepository,never()).save(any(Venda.class));
    }
    @Test void deveRetornarExcessaoSeProdutoNaoExistir(){
        Long cliente_id = 10L;
        Long usuario_id = 11L;
        Long produto_id = 110L;

        ItemVendaRequestDTO item = new ItemVendaRequestDTO();
        item.setProdutoId(produto_id);
        item.setQuantity(10);

        List<ItemVendaRequestDTO> itens = List.of(item);
    
        VendaRequestDTO request = new VendaRequestDTO();
        request.setClienteId(cliente_id);
        request.setUsuarioId(usuario_id);
        request.setItens(itens);

        Cliente cliente = new Cliente();
        Usuario usuario = new Usuario();

        when(clienteRepository.findByIdAndDeletedAtIsNull(cliente_id)).thenReturn(Optional.of(cliente));
        when(usuarioRepository.findByIdAndDeletedAtIsNull(usuario_id)).thenReturn(Optional.of(usuario));
        when(produtoRepository.findByIdDeletedAtIsNull(produto_id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            vendaService.criarVenda(request);
        });

        assertEquals("Produto nao encontrado.", exception.getMessage());
        verify(vendaRepository,never()).save(any(Venda.class));
        verify(produtoRepository,never()).save(any(Produto.class));
    }
    @Test void deveRetornarExcessaoSeQuantidadeForMenorOuIgualAZero(){
        Long cliente_id = 10L;
        Long usuario_id = 11L;
        Long produto_id = 110L;

        ItemVendaRequestDTO item = new ItemVendaRequestDTO();
        item.setProdutoId(produto_id);
        item.setQuantity(0);

        List<ItemVendaRequestDTO> itens = List.of(item);
    
        VendaRequestDTO request = new VendaRequestDTO();
        request.setClienteId(cliente_id);
        request.setUsuarioId(usuario_id);
        request.setItens(itens);

        Cliente cliente = new Cliente();
        Usuario usuario = new Usuario();
        Produto produto = new Produto();
        produto.setStockAmount(20);

        when(clienteRepository.findByIdAndDeletedAtIsNull(cliente_id)).thenReturn(Optional.of(cliente));
        when(usuarioRepository.findByIdAndDeletedAtIsNull(usuario_id)).thenReturn(Optional.of(usuario));
        when(produtoRepository.findByIdDeletedAtIsNull(produto_id)).thenReturn(Optional.of(produto));

        BusinessException exception = assertThrows(BusinessException.class, ()->{
            vendaService.criarVenda(request);
        });

        assertEquals("Quantidade nao pode ser menor ou igual a 0.", exception.getMessage());
        verify(vendaRepository,never()).save(any(Venda.class));
        verify(produtoRepository,never()).save(any(Produto.class));
    }
    @Test void deveRetornarExcessaoSeListaDeItensForNula(){
        Long cliente_id = 10L;
        Long usuario_id = 11L;
    
        VendaRequestDTO request = new VendaRequestDTO();
        request.setClienteId(cliente_id);
        request.setUsuarioId(usuario_id);
        request.setItens(null);

        Cliente cliente = new Cliente();
        Usuario usuario = new Usuario();
       

        when(clienteRepository.findByIdAndDeletedAtIsNull(cliente_id)).thenReturn(Optional.of(cliente));
        when(usuarioRepository.findByIdAndDeletedAtIsNull(usuario_id)).thenReturn(Optional.of(usuario));
      
        BusinessException exception = assertThrows(BusinessException.class, ()->{
            vendaService.criarVenda(request);
        });

        assertEquals("Nao e possivel completar a venda, adicione itens.", exception.getMessage());

        verify(vendaRepository,never()).save(any(Venda.class));
    }
    @Test void deveRetornarExcessaoSeListaDeItensNaoConterItens(){
        Long cliente_id = 10L;
        Long usuario_id = 11L;
    
        VendaRequestDTO request = new VendaRequestDTO();
        request.setClienteId(cliente_id);
        request.setUsuarioId(usuario_id);
        request.setItens(List.of());

        Cliente cliente = new Cliente();
        Usuario usuario = new Usuario();
       

        when(clienteRepository.findByIdAndDeletedAtIsNull(cliente_id)).thenReturn(Optional.of(cliente));
        when(usuarioRepository.findByIdAndDeletedAtIsNull(usuario_id)).thenReturn(Optional.of(usuario));
      
        BusinessException exception = assertThrows(BusinessException.class, ()->{
            vendaService.criarVenda(request);
        });

        assertEquals("Nao e possivel completar a venda, adicione itens.", exception.getMessage());

        verify(vendaRepository,never()).save(any(Venda.class));
    }
}
