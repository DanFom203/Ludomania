package ru.itis.ludomania.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class Case {
    private int id;
    private String name;
    private List<String> listOfWeapons;
}
