package com.fernando.estoque_api.controller;

import com.fernando.estoque_api.dto.user.UserRequestDTO;
import com.fernando.estoque_api.dto.user.UserResponseDTO;
import com.fernando.estoque_api.service.UserService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public UserResponseDTO createUser(@RequestBody UserRequestDTO user) {
        return userService.createUser(user);
    }
    @GetMapping("/{id}")
    public UserResponseDTO findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }
    @GetMapping("/email/{email}")
    public UserResponseDTO findUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }
    @GetMapping("/")
    public List<UserResponseDTO> findAllUsers() {
        return userService.findAllUsers();
    }
    @PutMapping("/{id}")
    public UserResponseDTO updateUserById(@PathVariable Long id, @RequestBody UserRequestDTO data) {
        return userService.updateUserById(id, data);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
    
}
