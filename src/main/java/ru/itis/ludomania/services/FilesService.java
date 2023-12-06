package ru.itis.ludomania.services;

import ru.itis.ludomania.dto.UserDto;
import ru.itis.ludomania.model.FileInfo;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public interface FilesService {
    FileInfo saveFileToStorage(UserDto user, InputStream file, String originalFileName, String contentType, Long size);
    void readFileFromStorage(UUID fileId, OutputStream outputStream);
    FileInfo getFileInfo(UUID fileId);
}
