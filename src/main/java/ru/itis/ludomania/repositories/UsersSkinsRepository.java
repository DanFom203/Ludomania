package ru.itis.ludomania.repositories;

import ru.itis.ludomania.model.User;
import ru.itis.ludomania.model.UsersSkin;
import ru.itis.ludomania.model.WeaponSkin;
import ru.itis.ludomania.repositories.generic.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsersSkinsRepository extends CrudRepository<UsersSkin, Integer> {

    Optional<UsersSkin> findByUserIdAndSkinId(UUID userId, int skinId);
    void delete(UUID userId, int skinId);
    List<UsersSkin> findByUserId(UUID userId);
}
