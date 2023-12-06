package ru.itis.ludomania.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
@Builder
public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private Date birthdate;
    private double balance;
    private UUID avatarId;
}
