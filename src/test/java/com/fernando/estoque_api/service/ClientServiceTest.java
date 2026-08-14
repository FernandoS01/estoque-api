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

import com.fernando.estoque_api.dto.client.ClientRequestDTO;
import com.fernando.estoque_api.dto.client.ClientResponseDTO;
import com.fernando.estoque_api.entity.Client;
import com.fernando.estoque_api.exception.ResourceNotFoundException;
import com.fernando.estoque_api.mapper.ClientMapper;
import com.fernando.estoque_api.repository.ClientRepository;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    private Long id = 140L;
    private String name = "cliente1";
    private String cpf = "12345678901";
    private String email = "client@email.com";
    private String phone = "121212121212";

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientMapper clientMapper;
    @InjectMocks
    private  ClientService clientService;

    @Test void shouldCreateClientWithSucessfully(){
        ClientRequestDTO request = new ClientRequestDTO();
        request.setName(name);
        request.setCpf(cpf);
        request.setEmail(email);
        request.setPhone(phone);

        Client client = new Client();
        client.setName(name);
        client.setCpf(cpf);
        client.setEmail(email);
        client.setPhone(phone);

        ClientResponseDTO response = new ClientResponseDTO();
        response.setName(name);
        response.setCpf(cpf);
        response.setEmail(email);
        response.setPhone(phone);

        when(clientRepository.existsByCpf(cpf)).thenReturn(false);
        when(clientRepository.existsByEmail(email)).thenReturn(false);
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(clientMapper.toDTO(client)).thenReturn(response);

        ClientResponseDTO results = clientService.createClient(request);

        assertEquals(name, results.getName());
        assertEquals(cpf,results.getCpf());
        assertEquals(email, results.getEmail());
        assertEquals(phone, results.getPhone());
        
        verify(clientRepository).save(any(Client.class));
        verify(clientMapper).toDTO(client);

    };  
    @Test void shouldReturnClientById(){
        Client client = new Client();
        client.setName(name);
        client.setCpf(cpf);
        client.setEmail(email);
        client.setPhone(phone);

        ClientResponseDTO response = new ClientResponseDTO();
        response.setName(name);
        response.setCpf(cpf);
        response.setEmail(email);
        response.setPhone(phone);

        when(clientRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(client));
        when(clientMapper.toDTO(client)).thenReturn(response);

        ClientResponseDTO results = clientService.findClientById(id);

        assertEquals(name, results.getName());
        assertEquals(cpf, results.getCpf());
        assertEquals(email, results.getEmail());
        assertEquals(phone, results.getPhone());

        verify(clientRepository).findByIdAndDeletedAtIsNull(id);
        verify(clientMapper).toDTO(client);
    };
    @Test void shouldReturnClientByCpf(){
        Client client = new Client();
        client.setName(name);
        client.setCpf(cpf);
        client.setEmail(email);
        client.setPhone(phone);

        ClientResponseDTO response = new ClientResponseDTO();
        response.setName(name);
        response.setCpf(cpf);
        response.setEmail(email);
        response.setPhone(phone);

        when(clientRepository.findByCpfAndDeletedAtIsNull(cpf)).thenReturn(Optional.of(client));
        when(clientMapper.toDTO(client)).thenReturn(response);

        ClientResponseDTO results = clientService.findClientByCpf(cpf);

        assertEquals(name, results.getName());
        assertEquals(cpf, results.getCpf());
        assertEquals(email, results.getEmail());
        assertEquals(phone, results.getPhone());

        verify(clientRepository).findByCpfAndDeletedAtIsNull(cpf);
        verify(clientMapper).toDTO(client);
    };
    @Test void shouldReturnClientByEmail(){
        Client client = new Client();
        client.setName(name);
        client.setCpf(cpf);
        client.setEmail(email);
        client.setPhone(phone);

        ClientResponseDTO response = new ClientResponseDTO();
        response.setName(name);
        response.setCpf(cpf);
        response.setEmail(email);
        response.setPhone(phone);

        when(clientRepository.findByEmailAndDeletedAtIsNull(email)).thenReturn(Optional.of(client));
        when(clientMapper.toDTO(client)).thenReturn(response);

        ClientResponseDTO results = clientService.findClientByEmail(email);

        assertEquals(name, results.getName());
        assertEquals(cpf, results.getCpf());
        assertEquals(email, results.getEmail());
        assertEquals(phone, results.getPhone());

        verify(clientRepository).findByEmailAndDeletedAtIsNull(email);
        verify(clientMapper).toDTO(client);

     };
    @Test void shouldReturnClients(){
        Client cliente1 = new Client();
        cliente1.setName(name);
        Client cliente2 = new Client();
        cliente2.setName("cliente2");

        ClientResponseDTO response1 = new ClientResponseDTO();
        response1.setName(name);
        ClientResponseDTO response2 = new ClientResponseDTO();
        response2.setName("cliente2");

        when(clientRepository.findByDeletedAtIsNull()).thenReturn(List.of(cliente1,cliente2));
        when(clientMapper.toDTO(cliente1)).thenReturn(response1);
        when(clientMapper.toDTO(cliente2)).thenReturn(response2);

        List<ClientResponseDTO> results = clientService.findAllClients();
        
        assertEquals(2, results.size());
        assertEquals(name, results.get(0).getName());
        assertEquals("cliente2", results.get(1).getName());

        verify(clientRepository).findByDeletedAtIsNull();
        verify(clientMapper).toDTO(cliente1);
        verify(clientMapper).toDTO(cliente2);
    }
    @Test void shouldUpdateClientById(){
        ClientRequestDTO request = new ClientRequestDTO();
        request.setName("clienteAtualizado");
        request.setCpf("9876543210");
        request.setEmail("emailatualizado@email.com");
        request.setPhone("19734679521");

        Client client = new Client();
        client.setId(id);
        client.setName(name);
        client.setCpf(cpf);
        client.setEmail(email);
        client.setPhone(phone);

        ClientResponseDTO response = new ClientResponseDTO();
        response.setName("clienteAtualizado");
        response.setCpf("9876543210");
        response.setEmail("emailatualizado@email.com");
        response.setPhone("19734679521");

        when(clientRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(clientMapper.toDTO(client)).thenReturn(response);

        ClientResponseDTO results = clientService.updateClientById(id, request);

        assertEquals("clienteAtualizado", results.getName());
        assertEquals("9876543210", results.getCpf());
        assertEquals("emailatualizado@email.com", results.getEmail());
        assertEquals("19734679521", results.getPhone());
        
        verify(clientRepository).save(any(Client.class));
        verify(clientMapper).toDTO(client);
        verify(clientRepository).findByIdAndDeletedAtIsNull(id);

    }
    @Test void shouldUpadteClientByCpf(){
         ClientRequestDTO request = new ClientRequestDTO();
        request.setName("clienteAtualizado");
        request.setEmail("emailatualizado@email.com");
        request.setPhone("19734679521");

        Client client = new Client();
        client.setId(id);
        client.setEmail(email);
        client.setPhone(phone);

        ClientResponseDTO response = new ClientResponseDTO();
        response.setName("clienteAtualizado");
        response.setEmail("emailatualizado@email.com");
        response.setPhone("19734679521");

        when(clientRepository.findByCpfAndDeletedAtIsNull(cpf)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(clientMapper.toDTO(client)).thenReturn(response);

        ClientResponseDTO results = clientService.updateClientByCpf(cpf, request);

        assertEquals("clienteAtualizado", results.getName());
        assertEquals("emailatualizado@email.com", results.getEmail());
        assertEquals("19734679521", results.getPhone());
        
        verify(clientRepository).save(any(Client.class));
        verify(clientMapper).toDTO(client);
        verify(clientRepository).findByCpfAndDeletedAtIsNull(cpf);
    }
    @Test void shouldUpdateClientByEmail(){
        ClientRequestDTO request = new ClientRequestDTO();
        request.setName("clienteAtualizado");
        request.setCpf("9876543210");
        request.setPhone("19734679521");

        Client client = new Client();
        client.setId(id);
        client.setName(name);
        client.setCpf(cpf);
        client.setPhone(phone);

        ClientResponseDTO response = new ClientResponseDTO();
        response.setName("clienteAtualizado");
        response.setCpf("9876543210");
        response.setPhone("19734679521");

        when(clientRepository.findByEmailAndDeletedAtIsNull(email)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(clientMapper.toDTO(client)).thenReturn(response);

        ClientResponseDTO results = clientService.updateClientByEmail(email, request);

        assertEquals("clienteAtualizado", results.getName());
        assertEquals("9876543210", results.getCpf());
        assertEquals("19734679521", results.getPhone());
        
        verify(clientRepository).save(any(Client.class));
        verify(clientMapper).toDTO(client);
        verify(clientRepository).findByEmailAndDeletedAtIsNull(email);

    }
    @Test void shouldDeleteClient(){
        Client client = new Client();
        when(clientRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(client));
        clientService.deleteClient(id);

        assertNotNull(client.getDeletedAt());
        verify(clientRepository).findByIdAndDeletedAtIsNull(id);
        verify(clientRepository).save(any(Client.class));
    }
    @Test void shouldThrowExceptionWhenIdNotExists(){
        when(clientRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            clientService.findClientById(id);
        });
        assertEquals("Cliente não encontrado.", exception.getMessage());
        verify(clientMapper,never()).toDTO(any(Client.class));

    };
    @Test void shouldThrowExceptionWhenEmailNotExists(){
        when(clientRepository.findByEmailAndDeletedAtIsNull(email)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            clientService.findClientByEmail(email);
        });
        assertEquals("Cliente não encontrado.", exception.getMessage());
        verify(clientMapper,never()).toDTO(any(Client.class));
    };
    @Test void shouldThrowExceptionWhenCpfNotExists(){
        when(clientRepository.findByCpfAndDeletedAtIsNull(cpf)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            clientService.findClientByCpf(cpf);
        });
        assertEquals("Cliente não encontrado.", exception.getMessage());
        verify(clientMapper,never()).toDTO(any(Client.class));
    };
}
