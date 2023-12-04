package ru.itis.ludomania.model;

import lombok.*;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Case {
    private int id;
    private String name;
    private double price;
    private int skinsCount;
}
