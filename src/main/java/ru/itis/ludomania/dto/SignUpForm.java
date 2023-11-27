package ru.itis.ludomania.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.Instant;

@Data
@Builder
public class SignUpForm {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date birthdate;
}
