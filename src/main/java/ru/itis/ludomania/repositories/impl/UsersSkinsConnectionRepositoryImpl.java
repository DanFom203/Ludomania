package ru.itis.ludomania.repositories.impl;

import com.zaxxer.hikari.HikariDataSource;
import ru.itis.ludomania.exceptions.CustomException;
import ru.itis.ludomania.model.UsersSkin;
import ru.itis.ludomania.repositories.UsersSkinsRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UsersSkinsConnectionRepositoryImpl implements UsersSkinsRepository {
    private final Connection connection;
    private final static String SQL_SELECT_BY_ID = "select * from users_skins_connection where connection_id = ?;";
    private final static String SQL_SELECT_BY_USER_ID = "select * from users_skins_connection where user_id = ?;";
    private final static String SQL_SELECT_ALL = "select * from users_skins_connection;";
    private final static String SQL_INSERT = "insert into users_skins_connection (user_id, skin_id, same_skin_count) VALUES (?, ?, ?);";
    private final static String SQL_SELECT_BY_USER_AND_SKIN_ID = "select * from users_skins_connection where (user_id = ? AND skin_id = ?);";
    private final static String SQL_UPDATE_PLUS_BY_USER_AND_SKIN_ID = "UPDATE users_skins_connection SET same_skin_count = same_skin_count + 1 WHERE (user_id = ? AND skin_id = ?);";
    private final static String SQL_UPDATE_MINUS_BY_USER_AND_SKIN_ID = "UPDATE users_skins_connection SET same_skin_count = same_skin_count - 1 WHERE (user_id = ? AND skin_id = ?);";
    private final static String SQL_DELETE_BY_USER_AND_SKIN_ID = "DELETE FROM users_skins_connection WHERE (user_id = ? AND skin_id = ?);";
    public UsersSkinsConnectionRepositoryImpl(HikariDataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    private UsersSkin initUsersSkin(ResultSet resultSet) throws SQLException {

        return UsersSkin.builder()
                .id(resultSet.getInt("connection_id"))
                .userId((UUID) resultSet.getObject("user_id"))
                .skinId(resultSet.getInt("skin_id"))
                .sameSkinCount(resultSet.getInt("same_skin_count"))
                .build();
    }
    @Override
    public Optional<UsersSkin> findById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(initUsersSkin(resultSet));
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }
        return Optional.empty();
    }
    @Override
    public List<UsersSkin> findByUserId(UUID userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_USER_ID);
            preparedStatement.setObject(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UsersSkin> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(initUsersSkin(resultSet));
            }
            return result;
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public List<UsersSkin> findAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UsersSkin> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(initUsersSkin(resultSet));
            }
            return result;
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public void save(UsersSkin item) {
        Optional<UsersSkin> savedUsersSkin = findByUserIdAndSkinId(item.getUserId(), item.getSkinId());
        if (savedUsersSkin.isEmpty()) {
            try {
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
                initStatement(statement, item);
                statement.setInt(3, item.getSameSkinCount());
                int affectedRows = statement.executeUpdate();

                if (affectedRows != 1) {
                    throw new CustomException("Cannot insert skin");
                }
            } catch (SQLException throwable) {
                System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
            }
        } else {
            try {
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PLUS_BY_USER_AND_SKIN_ID);
                initStatement(statement, item);
                int affectedRows = statement.executeUpdate();

                if (affectedRows != 1) {
                    throw new CustomException("Cannot insert skin");
                }
            } catch (SQLException throwable) {
                System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
            }
        }
    }

    @Override
    public void delete(Integer id) {}

    private void initStatement(PreparedStatement statement, UsersSkin item) throws SQLException {
        statement.setObject(1, item.getUserId());
        statement.setInt(2, item.getSkinId());
    }

    @Override
    public void delete(UUID userId, int skinId) {
        Optional<UsersSkin> savedUsersSkin = findByUserIdAndSkinId(userId, skinId);
        if (savedUsersSkin.get().getSameSkinCount() == 1) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_USER_AND_SKIN_ID);
                preparedStatement.setObject(1, userId);
                preparedStatement.setInt(2, skinId);
                preparedStatement.executeQuery();
            } catch (SQLException throwable) {
                System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
            }
        } else {
            try {
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MINUS_BY_USER_AND_SKIN_ID);
                statement.setObject(1, userId);
                statement.setInt(2, skinId);
                int affectedRows = statement.executeUpdate();

                if (affectedRows != 1) {
                    throw new CustomException("Cannot update same_skin_count");
                }
            } catch (SQLException throwable) {
                System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
            }
        }
    }

    @Override
    public Optional<UsersSkin> findByUserIdAndSkinId(UUID userId, int skinId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_USER_AND_SKIN_ID);
            preparedStatement.setObject(1, userId);
            preparedStatement.setInt(2, skinId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(initUsersSkin(resultSet));
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException1: " + throwable.getLocalizedMessage());
        }
        return Optional.empty();
    }
}
