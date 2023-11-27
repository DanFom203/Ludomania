package ru.itis.ludomania.repositories;


import ru.itis.ludomania.model.User;
import ru.itis.ludomania.repositories.generic.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean findByEmailAndPassword(String email, String password);
}
