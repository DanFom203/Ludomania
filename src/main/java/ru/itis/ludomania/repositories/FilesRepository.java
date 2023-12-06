package ru.itis.ludomania.repositories;

import ru.itis.ludomania.model.FileInfo;
import ru.itis.ludomania.repositories.generic.CrudRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FilesRepository extends CrudRepository<FileInfo, UUID> {
    List<FileInfo> getAll();
    FileInfo saveFile(FileInfo item) throws SQLException;
    Optional<FileInfo> findByStorageAndOriginalName (String sName, String oName);
}
