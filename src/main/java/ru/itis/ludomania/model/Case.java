package ru.itis.ludomania.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Case {
    private int id;
    private String name;
    private double price;
    private int skinsCount;
}
