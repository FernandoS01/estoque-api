package com.fernando.estoque_api.service;

import com.fernando.estoque_api.repository.ClientRepository;
import org.springframework.stereotype.Service;

import com.fernando.estoque_api.exception.ResourceAlreadyExistsException;
import com.fernando.estoque_api.exception.ResourceNotFoundException;
import com.fernando.estoque_api.mapper.ClientMapper;
import com.fernando.estoque_api.dto.client.ClientRequestDTO;
import com.fernando.estoque_api.dto.client.ClientResponseDTO;
import com.fernando.estoque_api.entity.Client;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientMapper clientMapper, ClientRepository clientRepository){
        this.clientMapper = clientMapper;
        this.clientRepository = clientRepository;
    }
    public ClientResponseDTO createClient(ClientRequestDTO dto){
        if(dto.getCpf() != null && clientRepository.existsByCpf(dto.getCpf())){
            throw new ResourceAlreadyExistsException("CPF já cadastrado.");
        } 
        if(dto.getEmail() != null && clientRepository.existsByEmail(dto.getEmail())){
            throw new ResourceAlreadyExistsException("Email já cadastrado.");
        }
        
        Client client = new Client();
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setCpf(dto.getCpf());
        client.setPhone(dto.getPhone());
        Client createdClient = clientRepository.save(client);

        return clientMapper.toDTO(createdClient);
    }
    public ClientResponseDTO findClientById(Long id){

        Client client = clientRepository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(()-> new ResourceNotFoundException("Cliente não encontrado."));
        
        return clientMapper.toDTO(client);
    }
    public ClientResponseDTO findClientByCpf(String cpf){

        Client client = clientRepository.findByCpfAndDeletedAtIsNull(cpf)
        .orElseThrow(()-> new ResourceNotFoundException("Cliente não encontrado."));
        
        return clientMapper.toDTO(client);
    }
    public ClientResponseDTO findClientByEmail(String email){

        Client client = clientRepository.findByEmailAndDeletedAtIsNull(email)
        .orElseThrow(()-> new ResourceNotFoundException("Cliente não encontrado."));
        
        return clientMapper.toDTO(client);
    }
    public List<ClientResponseDTO> findAllClients(){
        List<Client> clientes = clientRepository.findByDeletedAtIsNull();
        
        List<ClientResponseDTO> response = clientes.stream().map(client->{
            return clientMapper.toDTO(client);
        }).toList();

        return response;
    }
    public ClientResponseDTO updateClientById(Long id, ClientRequestDTO dto){

        Client client = clientRepository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(()-> new ResourceNotFoundException("Cliente não encotrado."));
        if(dto.getCpf() != null && clientRepository.existsByCpf(dto.getCpf()) && !client.getCpf().equals(dto.getCpf())){
            throw new ResourceAlreadyExistsException("CPF já cadastrado.");
        }
        if(dto.getEmail() != null && clientRepository.existsByEmail(dto.getEmail()) && !client.getEmail().equals(dto.getEmail())){
            throw new ResourceAlreadyExistsException("CPF já cadastrado.");
        }
        
        client.setName(dto.getName());
        client.setCpf(dto.getCpf());
        client.setEmail(dto.getEmail());
        client.setPhone(dto.getPhone());

        Client clientUpdated = clientRepository.save(client);

        return clientMapper.toDTO(clientUpdated);
    }
    public ClientResponseDTO updateClientByCpf(String cpf, ClientRequestDTO dto){
        
        Client client = clientRepository.findByCpfAndDeletedAtIsNull(cpf)
        .orElseThrow(()-> new ResourceNotFoundException("Cliente não encotrado."));
        if(dto.getCpf() != null && clientRepository.existsByCpf(dto.getCpf())){
            throw new ResourceAlreadyExistsException("CPF ja cadastrado");
        }
        if(dto.getEmail() != null && clientRepository.existsByEmail(dto.getEmail())){
            throw new ResourceAlreadyExistsException("CPF já cadastrado");
        }

        client.setName(dto.getName());
        client.setCpf(dto.getCpf());
        client.setEmail(dto.getEmail());
        client.setPhone(dto.getPhone());

        Client clientUpdated = clientRepository.save(client);

        return clientMapper.toDTO(clientUpdated);
    }
    public ClientResponseDTO updateClientByEmail(String email, ClientRequestDTO dto){
        
        Client client = clientRepository.findByEmailAndDeletedAtIsNull(email)
        .orElseThrow(()-> new ResourceNotFoundException("Cliente não encotrado."));
        if(dto.getCpf() != null && clientRepository.existsByCpf(dto.getCpf())){
            throw new ResourceAlreadyExistsException("CPF ja cadastrado");
        }
        if(dto.getEmail() != null && clientRepository.existsByEmail(dto.getEmail())){
            throw new ResourceAlreadyExistsException("CPF já cadastrado");
        }
        
        client.setName(dto.getName());
        client.setCpf(dto.getCpf());
        client.setEmail(dto.getEmail());
        client.setPhone(dto.getPhone());

        Client clientUpdated = clientRepository.save(client);

        return clientMapper.toDTO(clientUpdated);
    }
    public void deleteClient(Long id){

        Client client = clientRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()-> new ResourceNotFoundException("Cliente não encontrado."));
        client.setDeletedAt(LocalDateTime.now());

        clientRepository.save(client);

    }
}
