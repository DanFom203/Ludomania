package ru.itis.ludomania.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeaponSkin {
    private int id;
    private String name;
    private int caseId;
    private String rarity;
    private double price;
}
