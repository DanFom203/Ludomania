package ru.itis.ludomania.model;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;
@Data
@Builder
public class UsersSkin {
    private int id;
    private UUID userId;
    private int skinId;
    private int sameSkinCount;
}
