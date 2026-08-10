package com.fernando.estoque_api.service;

import com.fernando.estoque_api.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import com.fernando.estoque_api.dto.cliente.ClienteRequestDTO;
import com.fernando.estoque_api.dto.cliente.ClienteResponseDTO;
import com.fernando.estoque_api.exception.ResourceAlreadyExistsException;
import com.fernando.estoque_api.exception.ResourceNotFoundException;
import com.fernando.estoque_api.mapper.ClienteMapper;
import com.fernando.estoque_api.entity.Cliente;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteService(ClienteMapper clienteMapper, ClienteRepository clienteRepository){
        this.clienteMapper = clienteMapper;
        this.clienteRepository = clienteRepository;
    }
    public ClienteResponseDTO criarCliente(ClienteRequestDTO dto){
        if(dto.getCpf() != null && clienteRepository.existsByCpf(dto.getCpf())){
            throw new ResourceAlreadyExistsException("CPF ja cadastrado.");
        } 
        if(dto.getEmail() != null && clienteRepository.existsByEmail(dto.getEmail())){
            throw new ResourceAlreadyExistsException("Email ja cadastrado.");
        }
        
        Cliente cliente = new Cliente();
        cliente.setName(dto.getName());
        cliente.setEmail(dto.getEmail());
        cliente.setCpf(dto.getCpf());
        cliente.setPhone(dto.getPhone());
        Cliente clienteSalvo = clienteRepository.save(cliente);

        return clienteMapper.toDTO(clienteSalvo);
    }
    public ClienteResponseDTO buscarClientePorId(Long id){

        Cliente cliente = clienteRepository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(()-> new ResourceNotFoundException("Cliente nao encontrado."));
        
        return clienteMapper.toDTO(cliente);
    }
    public ClienteResponseDTO buscarClientePorCpf(String cpf){

        Cliente cliente = clienteRepository.findByCpfAndDeletedAtIsNull(cpf)
        .orElseThrow(()-> new ResourceNotFoundException("Cliente nao encontrado."));
        
        return clienteMapper.toDTO(cliente);
    }
    public ClienteResponseDTO buscarClientePorEmail(String email){

        Cliente cliente = clienteRepository.findByEmailAndDeletedAtIsNull(email)
        .orElseThrow(()-> new ResourceNotFoundException("Cliente nao encontrado."));
        
        return clienteMapper.toDTO(cliente);
    }
    public List<ClienteResponseDTO> listarClientes(){
        List<Cliente> clientes = clienteRepository.findByDeletedAtIsNull();
        
        List<ClienteResponseDTO> response = clientes.stream().map(cliente->{
            return clienteMapper.toDTO(cliente);
        }).toList();

        return response;
    }
    public ClienteResponseDTO atualizarClientePorId(Long id, ClienteRequestDTO dto){

        Cliente cliente = clienteRepository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(()-> new ResourceNotFoundException("Cliente nao encotrado."));
        if(dto.getCpf() != null && clienteRepository.existsByCpf(dto.getCpf()) && !cliente.getCpf().equals(dto.getCpf())){
            throw new ResourceAlreadyExistsException("CPF ja cadastrado");
        }
        if(dto.getEmail() != null && clienteRepository.existsByEmail(dto.getEmail()) && !cliente.getEmail().equals(dto.getEmail())){
            throw new ResourceAlreadyExistsException("CPF ja cadastrado");
        }
        
        cliente.setName(dto.getName());
        cliente.setCpf(dto.getCpf());
        cliente.setEmail(dto.getEmail());
        cliente.setPhone(dto.getPhone());

        Cliente clienteAtualizado = clienteRepository.save(cliente);

        return clienteMapper.toDTO(clienteAtualizado);
    }
    public ClienteResponseDTO atualizarClientePorCpf(String cpf, ClienteRequestDTO dto){
        
        Cliente cliente = clienteRepository.findByCpfAndDeletedAtIsNull(cpf)
        .orElseThrow(()-> new ResourceNotFoundException("Cliente nao encotrado."));
        if(dto.getCpf() != null && clienteRepository.existsByCpf(dto.getCpf())){
            throw new ResourceAlreadyExistsException("CPF ja cadastrado");
        }
        if(dto.getEmail() != null && clienteRepository.existsByEmail(dto.getEmail())){
            throw new ResourceAlreadyExistsException("CPF ja cadastrado");
        }

        cliente.setName(dto.getName());
        cliente.setCpf(dto.getCpf());
        cliente.setEmail(dto.getEmail());
        cliente.setPhone(dto.getPhone());

        Cliente clienteAtualizado = clienteRepository.save(cliente);

        return clienteMapper.toDTO(clienteAtualizado);
    }
    public ClienteResponseDTO atualizarClientePorEmail(String email, ClienteRequestDTO dto){
        
        Cliente cliente = clienteRepository.findByEmailAndDeletedAtIsNull(email)
        .orElseThrow(()-> new ResourceNotFoundException("Cliente nao encotrado."));
        if(dto.getCpf() != null && clienteRepository.existsByCpf(dto.getCpf())){
            throw new ResourceAlreadyExistsException("CPF ja cadastrado");
        }
        if(dto.getEmail() != null && clienteRepository.existsByEmail(dto.getEmail())){
            throw new ResourceAlreadyExistsException("CPF ja cadastrado");
        }
        
        cliente.setName(dto.getName());
        cliente.setCpf(dto.getCpf());
        cliente.setEmail(dto.getEmail());
        cliente.setPhone(dto.getPhone());

        Cliente clienteAtualizado = clienteRepository.save(cliente);

        return clienteMapper.toDTO(clienteAtualizado);
    }
    public void deletarCliente(Long id){

        Cliente cliente = clienteRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()-> new ResourceNotFoundException("Cliente nao encontrado."));
        cliente.setDeletedAt(LocalDateTime.now());

        clienteRepository.save(cliente);

    }
}
