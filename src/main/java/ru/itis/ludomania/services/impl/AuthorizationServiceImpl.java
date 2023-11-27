package ru.itis.ludomania.services.impl;

import lombok.AllArgsConstructor;
import ru.itis.ludomania.dto.SignInForm;
import ru.itis.ludomania.dto.SignUpForm;
import ru.itis.ludomania.dto.UserDto;
import ru.itis.ludomania.exceptions.CustomException;
import ru.itis.ludomania.model.User;
import ru.itis.ludomania.repositories.UsersRepository;
import ru.itis.ludomania.services.AuthorizationService;
import ru.itis.ludomania.services.PasswordEncoder;
import ru.itis.ludomania.services.UserMapper;

import java.util.Optional;

@AllArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private UsersRepository usersRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto signUp(SignUpForm form) throws CustomException {
        if (form.getEmail() == null) {
            throw new CustomException("Email cannot be null");
        }
        Optional<User> optionalUser = usersRepository.findByEmail(form.getEmail());
        if (optionalUser.isPresent()) {
            throw new CustomException("User with email " + form.getEmail() + " already exist");
        }
        form.setPassword(passwordEncoder.encode(form.getPassword()));
        User user = userMapper.toUser(form);
        User savedUser = usersRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto signIn(SignInForm form) throws CustomException {
        if(form.getEmail() == null) {
            throw new CustomException("Email cannot be null");
        }
        Optional<User> optionalUser = usersRepository.findByEmail(form.getEmail());
        if(optionalUser.isEmpty()) {
            throw new CustomException("User with email " + form.getEmail() + " not found.");
        }
        User user = optionalUser.get();
        if(!passwordEncoder.matches(form.getPassword(), user.getPasswordHash())) {
            throw new CustomException("Wrong password");
        }
        return userMapper.toDto(user);
    }
}
