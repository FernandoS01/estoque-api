package com.fernando.estoque_api.service;

import org.springframework.stereotype.Service;

import com.fernando.estoque_api.dto.user.UserRequestDTO;
import com.fernando.estoque_api.dto.user.UserResponseDTO;
import com.fernando.estoque_api.entity.User;
import com.fernando.estoque_api.exception.ResourceAlreadyExistsException;
import com.fernando.estoque_api.exception.ResourceNotFoundException;
import com.fernando.estoque_api.mapper.UserMapper;
import com.fernando.estoque_api.repository.UserRepository;
import com.fernando.estoque_api.security.service.PasswordService;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class UserService {
    
    private final UserRepository UserRepository;
    private final UserMapper UserMapper;
    private final PasswordService passwordService;
    
    public UserService(UserRepository UserRepository,UserMapper UserMapper,PasswordService passwordService) {
        this.UserRepository = UserRepository;
        this.UserMapper = UserMapper;
        this.passwordService = passwordService;
    }
    public UserResponseDTO createUser(UserRequestDTO dto){
        if(UserRepository.existsByEmail(dto.getEmail())){
            throw new ResourceAlreadyExistsException("Email já cadastrado.");
        }
        User User = new User();
        User.setName(dto.getName());
        User.setEmail(dto.getEmail());
        User.setRole(dto.getRole());
        User.setPassword(passwordService.encode(dto.getPassword()));
        User UserSalvo = UserRepository.save(User);

        return UserMapper.toDTO(UserSalvo);
    }
    public UserResponseDTO findUserById(Long id){
       
        User User = UserRepository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(()-> new ResourceNotFoundException("Usuário não encontrado."));
       
        return UserMapper.toDTO(User);
    }
    public UserResponseDTO findUserByEmail(String email){
       
        User User = UserRepository.findByEmailAndDeletedAtIsNull(email)
        .orElseThrow(()-> new ResourceNotFoundException("Email não encontrado."));
       
        return UserMapper.toDTO(User);
    }
    public List<UserResponseDTO> findAllUsers(){
        List<User> Users = UserRepository.findByDeletedAtIsNull();
        List<UserResponseDTO> response = Users.stream().map(UserMapper::toDTO).toList();
        return response;
    }
    public UserResponseDTO updateUserById(Long id, UserRequestDTO dto){
        
        User User = UserRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()-> new ResourceNotFoundException("Usuário nao encontrado"));
        if(UserRepository.existsByEmail(dto.getEmail()) && !User.getEmail().equals(dto.getEmail())){
            throw new ResourceAlreadyExistsException("Email já cadastrado.");
        }
        User.setName(dto.getName());
        User.setEmail(dto.getEmail());
        User.setRole(dto.getRole());
        User.setPassword(passwordService.encode(dto.getPassword()));
        User userUpdated = UserRepository.save(User);

        return UserMapper.toDTO(userUpdated);
    }
    public void deleteUser(Long id){

        User User = UserRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()->new ResourceNotFoundException("Usuário não encontrado."));
        
        User.setDeletedAt(LocalDateTime.now());
        UserRepository.save(User);

    }
}

