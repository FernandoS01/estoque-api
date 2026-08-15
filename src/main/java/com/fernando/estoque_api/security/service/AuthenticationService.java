package com.fernando.estoque_api.security.service;

import com.fernando.estoque_api.entity.User;
import com.fernando.estoque_api.exception.BusinessException;
import com.fernando.estoque_api.exception.ResourceNotFoundException;
import com.fernando.estoque_api.repository.UserRepository;
import com.fernando.estoque_api.security.dto.authentication.AuthenticationRequestDTO;
import com.fernando.estoque_api.security.dto.authentication.AuthenticationResponseDTO;

public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final JWTService jwtService;

    public AuthenticationService(UserRepository userRepository,
                                PasswordService passwordService,
                                JWTService jwtService){
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.jwtService = jwtService;
    }
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO dto){
        User user = userRepository.findByEmailAndDeletedAtIsNull(
            dto.getEmail())
            .orElseThrow(
                ()-> new ResourceNotFoundException("Usuário não encontrado."));
        AuthenticationResponseDTO response = new AuthenticationResponseDTO();
        if(passwordService.verifyPassword(dto.getPassword(), user.getPassword())){
            response.setToken(jwtService.generateToken(user.getEmail()));
        }else {
            throw new BusinessException("Senha invalida.");
        };
        response.setName(user.getName());
        response.setRole(user.getRole());
        
        return response;
    }
}
