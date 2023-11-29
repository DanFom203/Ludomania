package ru.itis.ludomania.repositories;

import ru.itis.ludomania.model.Case;
import ru.itis.ludomania.model.WeaponSkin;
import ru.itis.ludomania.repositories.generic.CrudRepository;

import java.util.Optional;

public interface SkinsRepository extends CrudRepository<WeaponSkin, Integer> {
    Optional<WeaponSkin> findByName(String name);
    Optional<WeaponSkin> findBySkinAndCaseId(Integer skinId, Integer caseId);
}
