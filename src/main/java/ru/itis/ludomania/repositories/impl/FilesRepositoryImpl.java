package ru.itis.ludomania.repositories.impl;

import com.zaxxer.hikari.HikariDataSource;
import ru.itis.ludomania.model.FileInfo;
import ru.itis.ludomania.model.User;
import ru.itis.ludomania.repositories.FilesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FilesRepositoryImpl implements FilesRepository {

    private final static String SQL_INSERT = "insert into file_info (storage_file_name, original_file_name, type, size) values (?, ?, ?, ?)";
    private final static String SQL_UPDATE = "update file_info set storage_file_name = ?, original_file_name = ?, type = ?, size = ? where id = ?";
    private final static String SQL_SELECT_BY_ID = "select * from file_info where id = ?";
    private final static String SQL_SELECT_ALL = "select * from file_info";
    private final static String SQL_SELECT_BY_STORAGE_AND_ORIGINAL_NAME = "select * from file_info where storage_file_name = ? and original_file_name = ?;";
    private final Connection connection;

    public FilesRepositoryImpl(HikariDataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    @Override
    public Optional<FileInfo> findByStorageAndOriginalName (String sName, String oName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_STORAGE_AND_ORIGINAL_NAME);
            preparedStatement.setString(1, sName);
            preparedStatement.setString(2, oName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            FileInfo file = initFile(resultSet);
            return Optional.of(file);
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }
        return Optional.empty();
    }

    private FileInfo initFile(ResultSet resultSet) throws SQLException {
        return FileInfo.builder()
                .id((UUID) resultSet.getObject("id"))
                .originalFileName(resultSet.getString("original_file_name"))
                .storageFileName(resultSet.getString("storage_file_name"))
                .size(resultSet.getLong("size"))
                .type(resultSet.getString("type"))
                .build();
    }

    @Override
    public FileInfo saveFile(FileInfo item) throws SQLException {
        Optional<FileInfo> savedFile = findById(item.getId());

        try {
            if (savedFile.isEmpty()) {
                try (PreparedStatement statement = connection.prepareStatement(
                        SQL_INSERT
                )) {
                    initStatement(statement, item);
                    int affectedRows = statement.executeUpdate();

                    if (affectedRows != 1) {
                        throw new SQLException("Cannot save file");
                    }
                }
            } else {
                try (PreparedStatement statement = connection.prepareStatement(
                        SQL_UPDATE
                )) {
                    initStatement(statement, item);
                    statement.setObject(5, item.getId());
                    int affectedRows = statement.executeUpdate();

                    if (affectedRows != 1) {
                        throw new SQLException("Cannot save file");
                    }
                }
            }
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
            throw throwable;
        }

        return item;
    }

    private void initStatement(PreparedStatement statement, FileInfo item) throws SQLException {
        statement.setString(1, item.getStorageFileName());
        statement.setString(2, item.getOriginalFileName());
        statement.setString(3, item.getType());
        statement.setLong(4, item.getSize());
    }

    @Override
    public Optional<FileInfo> findById(UUID id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }

            FileInfo file = initFile(resultSet);
            return Optional.of(file);
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<FileInfo> findAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<FileInfo> result = new ArrayList<>();
            while (resultSet.next()) {
               FileInfo file = initFile(resultSet);
                result.add(file);
            }
            return result;
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public void save(FileInfo item) {}

    @Override
    public void delete(UUID id) {}

    @Override
    public List<FileInfo> getAll() {
        return null;
    }
}
