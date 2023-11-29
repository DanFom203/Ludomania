package ru.itis.ludomania.services.impl;

import ru.itis.ludomania.model.Case;
import ru.itis.ludomania.model.UsersSkin;
import ru.itis.ludomania.model.WeaponSkin;
import ru.itis.ludomania.repositories.CasesRepository;
import ru.itis.ludomania.repositories.SkinsRepository;
import ru.itis.ludomania.repositories.UsersRepository;
import ru.itis.ludomania.repositories.UsersSkinsRepository;
import ru.itis.ludomania.services.ValidationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class OpenCaseService {
    private ValidationService validator;
    private CasesRepository casesRepository;
    private SkinsRepository skinsRepository;
    private UsersRepository usersRepository;
    private UsersSkinsRepository usersSkinsRepository;

    public OpenCaseService( ValidationService validator,
                            CasesRepository casesRepository,
                            SkinsRepository skinsRepository,
                            UsersRepository usersRepository,
                            UsersSkinsRepository usersSkinsRepository) {
        this.validator = validator;
        this.casesRepository = casesRepository;
        this.skinsRepository = skinsRepository;
        this.usersRepository = usersRepository;
        this.usersSkinsRepository = usersSkinsRepository;
    }

    public WeaponSkin openCase(UUID userId, int caseId) {
        int skinsCount = casesRepository.findById(caseId).get().getSkinsCount();
        int randomSkinId;
        Random random = new Random();
        if (caseId == 1) {
            randomSkinId = random.nextInt(skinsCount);
        } else {
            int previousCaseSkinsCount = casesRepository.findById(caseId - 1).get().getSkinsCount();
            randomSkinId = random.nextInt(skinsCount) + previousCaseSkinsCount;
        }
        WeaponSkin droppedWeaponSkin = skinsRepository.findBySkinAndCaseId(randomSkinId, caseId).get();
        saveDroppedWeaponSkin(userId, droppedWeaponSkin);
        return droppedWeaponSkin;
    }

    private void saveDroppedWeaponSkin(UUID userId, WeaponSkin droppedWeaponSkin) {
        UsersSkin us = UsersSkin.builder()
                .id(1)
                .userId(userId)
                .skinId(droppedWeaponSkin.getId())
                .build();

        usersSkinsRepository.save(us);
    }

    public List<Case> getCasesList() {
        List<Case> cases = new ArrayList<>();
        if (casesRepository.findAll() != null) {
            cases = casesRepository.findAll();
        }
        return cases;
    }
}
