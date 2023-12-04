package ru.itis.ludomania.services.impl;

import ru.itis.ludomania.model.Case;
import ru.itis.ludomania.model.UsersSkin;
import ru.itis.ludomania.model.WeaponSkin;
import ru.itis.ludomania.repositories.CasesRepository;
import ru.itis.ludomania.repositories.SkinsRepository;
import ru.itis.ludomania.repositories.UsersRepository;
import ru.itis.ludomania.repositories.UsersSkinsRepository;

import java.util.*;

public class OpenCaseService {
    private CasesRepository casesRepository;
    private SkinsRepository skinsRepository;
    private UsersRepository usersRepository;
    private UsersSkinsRepository usersSkinsRepository;

    public OpenCaseService(
                            CasesRepository casesRepository,
                            SkinsRepository skinsRepository,
                            UsersRepository usersRepository,
                            UsersSkinsRepository usersSkinsRepository) {
        this.casesRepository = casesRepository;
        this.skinsRepository = skinsRepository;
        this.usersRepository = usersRepository;
        this.usersSkinsRepository = usersSkinsRepository;
    }

    public List<Case> getCasesList() {
        List<Case> cases = new ArrayList<>();
        if (casesRepository.findAll() != null) {
            cases = casesRepository.findAll();
        }
        return cases;
    }

    public Case getCaseById(Integer caseId) {
        return casesRepository.findById(caseId).get();
    }
    public WeaponSkin getSkinByName(String skinName) {
        return skinsRepository.findByName(skinName).get();
    }

    public List<WeaponSkin> getSkinsForCase(Integer caseId) {
        return skinsRepository.findByCaseId(caseId);
    }
    public List<WeaponSkin> getRandomSkins(List<WeaponSkin> skinsList) {
        int skinsCount = skinsList.size();
        List<WeaponSkin> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 32; i++) {
            list.add(skinsList.get(random.nextInt(skinsCount)));
        }
        return list;
    }
    public void saveDroppedWeaponSkin(UUID userId, WeaponSkin droppedWeaponSkin) {
        UsersSkin us = UsersSkin.builder()
                .id(1)
                .userId(userId)
                .skinId(droppedWeaponSkin.getId())
                .build();

        usersSkinsRepository.save(us);
    }

    public List<WeaponSkin> getUsersSkins(UUID userId) {
        List<UsersSkin> list = usersSkinsRepository.findByUserId(userId);
        List<WeaponSkin> resultList = new ArrayList<>();
        for (UsersSkin elem: list) {
            Optional<WeaponSkin> optSkin = skinsRepository.findById(elem.getSkinId());
            optSkin.ifPresent(resultList::add);
        }
        return resultList;
    }

}
