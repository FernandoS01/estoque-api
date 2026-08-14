package com.fernando.estoque_api.controller;

import com.fernando.estoque_api.dto.client.ClientRequestDTO;
import com.fernando.estoque_api.dto.client.ClientResponseDTO;
import com.fernando.estoque_api.service.ClientService;
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
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }
    @PostMapping("/")
    public ClientResponseDTO createClient(@RequestBody ClientRequestDTO client) {
        return clientService.createClient(client);
    }
    @GetMapping("/{id}")
    public ClientResponseDTO findClientById(@PathVariable Long id) {
        return clientService.findClientById(id);
    }
    @GetMapping("/cpf/{cpf}")
    public ClientResponseDTO findClientByCpf(@PathVariable String cpf) {
        return clientService.findClientByCpf(cpf);
    }
    @GetMapping("/email/{email}")
    public ClientResponseDTO findClientByEmail(@PathVariable String email) {
        return clientService.findClientByEmail(email);
    }
    @GetMapping("/")
    public List<ClientResponseDTO> findAllClients() {
        return clientService.findAllClients();
    }
    @PutMapping("/id/{id}")
    public ClientResponseDTO updateClientById(@PathVariable Long id, @RequestBody ClientRequestDTO data) {
        return clientService.updateClientById(id, data);
    }
    @PutMapping("/cpf/{cpf}")
    public ClientResponseDTO updateClientByCpf(@PathVariable String cpf, @RequestBody ClientRequestDTO data) {
        return clientService.updateClientByCpf(cpf, data);
    }
    @PutMapping("/email/{email}")
    public ClientResponseDTO updateClientByEmail(@PathVariable String email, @RequestBody ClientRequestDTO data) {
        return clientService.updateClientByEmail(email, data);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id){
        clientService.deleteClient(id);
    }

}
