package com.Abilmansur.EmailSpammer.Converters;

import com.Abilmansur.EmailSpammer.DTO.UserDTO;
import com.Abilmansur.EmailSpammer.Entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {


    public static UserEntity getUserEntity(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getFirstname());
        user.setSurname(userDTO.getLastname());
        user.setAge(userDTO.getAge());
        user.setGender(userDTO.getGender());
        return user;
    }

    public static UserDTO getUserDTO(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setAge(user.getAge());
        userDTO.setId(user.getId());
        userDTO.setGender(user.getGender());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstname(user.getName());
        userDTO.setLastname(user.getSurname());
        userDTO.setGroupId(user.getGroupId());
        return userDTO;
    }

    public static List<UserDTO> getUserDTOList(List<UserEntity> users) {
        return users.stream().map(x -> getUserDTO(x)).collect(Collectors.toList());
    }
}
