package ru.itis.ludomania.services;

import ru.itis.ludomania.dto.SignInForm;
import ru.itis.ludomania.dto.SignUpForm;
import ru.itis.ludomania.dto.UserDto;
import ru.itis.ludomania.exceptions.CustomException;

public interface AuthorizationService {
    UserDto signUp(SignUpForm form) throws CustomException;
    UserDto signIn(SignInForm form) throws CustomException;
}
