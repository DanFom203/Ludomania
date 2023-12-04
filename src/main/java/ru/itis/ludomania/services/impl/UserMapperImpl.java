package ru.itis.ludomania.services.impl;

import ru.itis.ludomania.dto.SignUpForm;
import ru.itis.ludomania.dto.UserDto;
import ru.itis.ludomania.model.User;
import ru.itis.ludomania.services.UserMapper;

public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .birthdate(user.getBirthdate())
                .build();
    }

    @Override
    public User toUser(UserDto dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .birthdate(dto.getBirthdate())
                .build();
    }

    @Override
    public User toUser(SignUpForm dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .birthdate(dto.getBirthdate())
                .passwordHash(dto.getPassword())
                .build();
    }
}
