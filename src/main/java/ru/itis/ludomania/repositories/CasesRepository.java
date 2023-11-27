package ru.itis.ludomania.repositories;

import ru.itis.ludomania.model.Case;
import ru.itis.ludomania.model.User;
import ru.itis.ludomania.repositories.generic.CrudRepository;

import java.util.Optional;

public interface CasesRepository extends CrudRepository<Case, Integer> {
}
