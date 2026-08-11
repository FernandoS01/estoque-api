package com.fernando.estoque_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fernando.estoque_api.dto.cliente.ClienteRequestDTO;
import com.fernando.estoque_api.dto.cliente.ClienteResponseDTO;
import com.fernando.estoque_api.entity.Cliente;
import com.fernando.estoque_api.exception.ResourceNotFoundException;
import com.fernando.estoque_api.mapper.ClienteMapper;
import com.fernando.estoque_api.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {
    private Long id = 140L;
    private String name = "cliente1";
    private String cpf = "12345678901";
    private String email = "cliente@email.com";
    private String phone = "121212121212";

    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ClienteMapper clienteMapper;
    @InjectMocks
    private  ClienteService clienteService;

    @Test void deveCriarClienteComSucesso(){
        ClienteRequestDTO request = new ClienteRequestDTO();
        request.setName(name);
        request.setCpf(cpf);
        request.setEmail(email);
        request.setPhone(phone);

        Cliente cliente = new Cliente();
        cliente.setName(name);
        cliente.setCpf(cpf);
        cliente.setEmail(email);
        cliente.setPhone(phone);

        ClienteResponseDTO response = new ClienteResponseDTO();
        response.setName(name);
        response.setCpf(cpf);
        response.setEmail(email);
        response.setPhone(phone);

        when(clienteRepository.existsByCpf(cpf)).thenReturn(false);
        when(clienteRepository.existsByEmail(email)).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(clienteMapper.toDTO(cliente)).thenReturn(response);

        ClienteResponseDTO results = clienteService.criarCliente(request);

        assertEquals(name, results.getName());
        assertEquals(cpf,results.getCpf());
        assertEquals(email, results.getEmail());
        assertEquals(phone, results.getPhone());
        
        verify(clienteRepository).save(any(Cliente.class));
        verify(clienteMapper).toDTO(cliente);

    };  
    @Test void deveRetornarUmClientePorId(){
        Cliente cliente = new Cliente();
        cliente.setName(name);
        cliente.setCpf(cpf);
        cliente.setEmail(email);
        cliente.setPhone(phone);

        ClienteResponseDTO response = new ClienteResponseDTO();
        response.setName(name);
        response.setCpf(cpf);
        response.setEmail(email);
        response.setPhone(phone);

        when(clienteRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toDTO(cliente)).thenReturn(response);

        ClienteResponseDTO results = clienteService.buscarClientePorId(id);

        assertEquals(name, results.getName());
        assertEquals(cpf, results.getCpf());
        assertEquals(email, results.getEmail());
        assertEquals(phone, results.getPhone());

        verify(clienteRepository).findByIdAndDeletedAtIsNull(id);
        verify(clienteMapper).toDTO(cliente);
    };
    @Test void deveRetornarUmClientePorCpf(){
        Cliente cliente = new Cliente();
        cliente.setName(name);
        cliente.setCpf(cpf);
        cliente.setEmail(email);
        cliente.setPhone(phone);

        ClienteResponseDTO response = new ClienteResponseDTO();
        response.setName(name);
        response.setCpf(cpf);
        response.setEmail(email);
        response.setPhone(phone);

        when(clienteRepository.findByCpfAndDeletedAtIsNull(cpf)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toDTO(cliente)).thenReturn(response);

        ClienteResponseDTO results = clienteService.buscarClientePorCpf(cpf);

        assertEquals(name, results.getName());
        assertEquals(cpf, results.getCpf());
        assertEquals(email, results.getEmail());
        assertEquals(phone, results.getPhone());

        verify(clienteRepository).findByCpfAndDeletedAtIsNull(cpf);
        verify(clienteMapper).toDTO(cliente);
    };
    @Test void deveRetornarUmClientePorEmail(){
        Cliente cliente = new Cliente();
        cliente.setName(name);
        cliente.setCpf(cpf);
        cliente.setEmail(email);
        cliente.setPhone(phone);

        ClienteResponseDTO response = new ClienteResponseDTO();
        response.setName(name);
        response.setCpf(cpf);
        response.setEmail(email);
        response.setPhone(phone);

        when(clienteRepository.findByEmailAndDeletedAtIsNull(email)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toDTO(cliente)).thenReturn(response);

        ClienteResponseDTO results = clienteService.buscarClientePorEmail(email);

        assertEquals(name, results.getName());
        assertEquals(cpf, results.getCpf());
        assertEquals(email, results.getEmail());
        assertEquals(phone, results.getPhone());

        verify(clienteRepository).findByEmailAndDeletedAtIsNull(email);
        verify(clienteMapper).toDTO(cliente);

     };
    @Test void deveRetornarClientes(){
        Cliente cliente1 = new Cliente();
        cliente1.setName(name);
        Cliente cliente2 = new Cliente();
        cliente2.setName("cliente2");

        ClienteResponseDTO response1 = new ClienteResponseDTO();
        response1.setName(name);
        ClienteResponseDTO response2 = new ClienteResponseDTO();
        response2.setName("cliente2");

        when(clienteRepository.findByDeletedAtIsNull()).thenReturn(List.of(cliente1,cliente2));
        when(clienteMapper.toDTO(cliente1)).thenReturn(response1);
        when(clienteMapper.toDTO(cliente2)).thenReturn(response2);

        List<ClienteResponseDTO> results = clienteService.listarClientes();
        
        assertEquals(2, results.size());
        assertEquals(name, results.get(0).getName());
        assertEquals("cliente2", results.get(1).getName());

        verify(clienteRepository).findByDeletedAtIsNull();
        verify(clienteMapper).toDTO(cliente1);
        verify(clienteMapper).toDTO(cliente2);
    }
    @Test void deveAtualizarClientePorId(){
        ClienteRequestDTO request = new ClienteRequestDTO();
        request.setName("clienteAtualizado");
        request.setCpf("9876543210");
        request.setEmail("emailatualizado@email.com");
        request.setPhone("19734679521");

        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setName(name);
        cliente.setCpf(cpf);
        cliente.setEmail(email);
        cliente.setPhone(phone);

        ClienteResponseDTO response = new ClienteResponseDTO();
        response.setName("clienteAtualizado");
        response.setCpf("9876543210");
        response.setEmail("emailatualizado@email.com");
        response.setPhone("19734679521");

        when(clienteRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(clienteMapper.toDTO(cliente)).thenReturn(response);

        ClienteResponseDTO results = clienteService.atualizarClientePorId(id, request);

        assertEquals("clienteAtualizado", results.getName());
        assertEquals("9876543210", results.getCpf());
        assertEquals("emailatualizado@email.com", results.getEmail());
        assertEquals("19734679521", results.getPhone());
        
        verify(clienteRepository).save(any(Cliente.class));
        verify(clienteMapper).toDTO(cliente);
        verify(clienteRepository).findByIdAndDeletedAtIsNull(id);

    }
    @Test void deveAtualizarClientePorCpf(){
         ClienteRequestDTO request = new ClienteRequestDTO();
        request.setName("clienteAtualizado");
        request.setEmail("emailatualizado@email.com");
        request.setPhone("19734679521");

        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setEmail(email);
        cliente.setPhone(phone);

        ClienteResponseDTO response = new ClienteResponseDTO();
        response.setName("clienteAtualizado");
        response.setEmail("emailatualizado@email.com");
        response.setPhone("19734679521");

        when(clienteRepository.findByCpfAndDeletedAtIsNull(cpf)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(clienteMapper.toDTO(cliente)).thenReturn(response);

        ClienteResponseDTO results = clienteService.atualizarClientePorCpf(cpf, request);

        assertEquals("clienteAtualizado", results.getName());
        assertEquals("emailatualizado@email.com", results.getEmail());
        assertEquals("19734679521", results.getPhone());
        
        verify(clienteRepository).save(any(Cliente.class));
        verify(clienteMapper).toDTO(cliente);
        verify(clienteRepository).findByCpfAndDeletedAtIsNull(cpf);
    }
    @Test void deveAtualizarClientePorEmail(){
        ClienteRequestDTO request = new ClienteRequestDTO();
        request.setName("clienteAtualizado");
        request.setCpf("9876543210");
        request.setPhone("19734679521");

        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setName(name);
        cliente.setCpf(cpf);
        cliente.setPhone(phone);

        ClienteResponseDTO response = new ClienteResponseDTO();
        response.setName("clienteAtualizado");
        response.setCpf("9876543210");
        response.setPhone("19734679521");

        when(clienteRepository.findByEmailAndDeletedAtIsNull(email)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(clienteMapper.toDTO(cliente)).thenReturn(response);

        ClienteResponseDTO results = clienteService.atualizarClientePorEmail(email, request);

        assertEquals("clienteAtualizado", results.getName());
        assertEquals("9876543210", results.getCpf());
        assertEquals("19734679521", results.getPhone());
        
        verify(clienteRepository).save(any(Cliente.class));
        verify(clienteMapper).toDTO(cliente);
        verify(clienteRepository).findByEmailAndDeletedAtIsNull(email);

    }
    @Test void deveDeletarClientes(){
        Cliente cliente = new Cliente();
        when(clienteRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(cliente));
        clienteService.deletarCliente(id);

        assertNotNull(cliente.getDeletedAt());
        verify(clienteRepository).findByIdAndDeletedAtIsNull(id);
        verify(clienteRepository).save(any(Cliente.class));
    }
    @Test void deveLancarExcessaoSeIdNaoExistir(){
        when(clienteRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            clienteService.buscarClientePorId(id);
        });
        assertEquals("Cliente nao encontrado.", exception.getMessage());
        verify(clienteMapper,never()).toDTO(any(Cliente.class));

    };
    @Test void deveLancarExcessaoSeEmailNaoExistir(){
        when(clienteRepository.findByEmailAndDeletedAtIsNull(email)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            clienteService.buscarClientePorEmail(email);
        });
        assertEquals("Cliente nao encontrado.", exception.getMessage());
        verify(clienteMapper,never()).toDTO(any(Cliente.class));
    };
    @Test void deveLancarExcessaoSeCpfExistir(){
        when(clienteRepository.findByCpfAndDeletedAtIsNull(cpf)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            clienteService.buscarClientePorCpf(cpf);
        });
        assertEquals("Cliente nao encontrado.", exception.getMessage());
        verify(clienteMapper,never()).toDTO(any(Cliente.class));
    };
}
