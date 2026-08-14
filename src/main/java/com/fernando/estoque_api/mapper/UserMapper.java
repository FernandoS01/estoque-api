package com.fernando.estoque_api.mapper;

import com.fernando.estoque_api.dto.user.UserResponseDTO;
import com.fernando.estoque_api.entity.User;;

public class UserMapper {
    public UserResponseDTO toDTO(User user){
    UserResponseDTO response = new UserResponseDTO();
    response.setName(user.getName());
    response.setRole(user.getRole());

    return response;
    }

}
