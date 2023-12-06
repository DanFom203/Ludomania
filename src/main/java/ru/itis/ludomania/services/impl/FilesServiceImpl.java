package ru.itis.ludomania.services.impl;

import ru.itis.ludomania.dto.UserDto;
import ru.itis.ludomania.exceptions.CustomException;
import ru.itis.ludomania.model.FileInfo;
import ru.itis.ludomania.repositories.UsersRepository;
import ru.itis.ludomania.repositories.FilesRepository;
import ru.itis.ludomania.services.FilesService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class FilesServiceImpl implements FilesService {

    private final String path;
    private final FilesRepository filesRepository;
    private final UsersRepository usersRepository;

    public FilesServiceImpl(String path, FilesRepository filesRepository, UsersRepository usersRepository) {
        this.path = path;
        this.filesRepository = filesRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public FileInfo saveFileToStorage(UserDto user, InputStream inputStream, String originalFileName, String contentType, Long size) {
        FileInfo fileInfo = new FileInfo(
                null,
                originalFileName,
                UUID.randomUUID().toString(),
                size,
                contentType
        );
        try {
            Files.copy(inputStream, Paths.get(path + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]));
            fileInfo = filesRepository.saveFile(fileInfo);
            UUID fileInfoId = filesRepository.findByStorageAndOriginalName(fileInfo.getStorageFileName(), fileInfo.getOriginalFileName()).get().getId();
            fileInfo.setId(fileInfoId);
            usersRepository.updateAvatarForUser(user.getEmail(),fileInfoId);
        } catch (IOException | SQLException e) {
            throw new CustomException(e.getLocalizedMessage());
        }

        return fileInfo;
    }

    @Override
    public void readFileFromStorage(UUID fileId, OutputStream outputStream) {
        Optional<FileInfo> optionalFileInfo = filesRepository.findById(fileId);
        FileInfo fileInfo = optionalFileInfo.orElseThrow(() -> new CustomException("File not found"));

        File file = new File(path + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]);
        try {
            Files.copy(file.toPath(), outputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public FileInfo getFileInfo(UUID fileId) {
        return filesRepository.findById(fileId).orElseThrow(() -> new CustomException("File not found"));
    }
}
