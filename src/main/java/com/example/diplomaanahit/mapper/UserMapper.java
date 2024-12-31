package com.example.diplomaanahit.mapper;


import com.example.diplomaanahit.dtos.UserDTO;
import com.example.diplomaanahit.entities.UserEntity;
import org.springframework.stereotype.Component;



@Component
public class UserMapper {
    public UserDTO userDTOFromUserEntity(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setLoginDate(userEntity.getLoginDate());
        return userDTO;
    }
}
