package ru.itis.ludomania.services;

import ru.itis.ludomania.dto.SignUpForm;
import ru.itis.ludomania.dto.UserDto;
import ru.itis.ludomania.model.User;

public interface UserMapper {
    UserDto toDto(User user);
    User toUser(UserDto dto);
    User toUser(SignUpForm dto);
}
