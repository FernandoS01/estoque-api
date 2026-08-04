package com.fernando.estoque_api.controller;

import com.fernando.estoque_api.dto.cliente.ClienteRequestDTO;
import com.fernando.estoque_api.dto.cliente.ClienteResponseDTO;
import com.fernando.estoque_api.service.ClienteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }
    @PostMapping("/")
    public ClienteResponseDTO criarCliente(@RequestBody ClienteRequestDTO cliente) {
        return clienteService.criarCliente(cliente);
    }
    @GetMapping("/{id}")
    public ClienteResponseDTO buscarClientePorId(@PathVariable Long id) {
        return clienteService.buscarClientePorId(id);
    }
    @GetMapping("/cpf/{cpf}")
    public ClienteResponseDTO buscarClientePorCpf(@PathVariable String cpf) {
        return clienteService.buscarClientePorCpf(cpf);
    }
    @GetMapping("/email/{email}")
    public ClienteResponseDTO buscarClientePorEmail(@PathVariable String email) {
        return clienteService.buscarClientePorEmail(email);
    }
    @GetMapping("/")
    public List<ClienteResponseDTO> listarClientes() {
        return clienteService.listarClientes();
    }
    @PutMapping("/id/{id}")
    public ClienteResponseDTO atualizarClientePorId(@PathVariable Long id, @RequestBody ClienteRequestDTO data) {
        return clienteService.atualizarClientePorId(id, data);
    }
    @PutMapping("/cpf/{cpf}")
    public ClienteResponseDTO atualizarClientePorCpf(@PathVariable String cpf, @RequestBody ClienteRequestDTO data) {
        return clienteService.atualizarClientePorCpf(cpf, data);
    }
    @PutMapping("/email/{email}")
    public ClienteResponseDTO atualizarClientePorEmail(@PathVariable String email, @RequestBody ClienteRequestDTO data) {
        return clienteService.atualizarClientePorEmail(email, data);
    }

    @DeleteMapping("/{id}")
    public void deletarCliente(@PathVariable Long id){
        clienteService.deletarCliente(id);
    }

}
