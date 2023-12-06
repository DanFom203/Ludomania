package ru.itis.ludomania.model;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class FileInfo {
    private UUID id;
    private String originalFileName;
    private String storageFileName;
    private Long size;
    private String type;
}
