package ru.itis.ludomania.repositories.impl;

import com.zaxxer.hikari.HikariDataSource;
import ru.itis.ludomania.exceptions.CustomException;
import ru.itis.ludomania.model.User;
import ru.itis.ludomania.repositories.UsersRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UsersRepositoryImpl implements UsersRepository {

    private final Connection connection;
    private final static String SQL_SELECT_ALL = "select * from users;";
    private final static String SQL_INSERT = "insert into users (first_name, last_name, email, password, birthdate, avatar_id) VALUES (?, ?, ?, ?, ?, ?);";
    private final static String SQL_SELECT_BY_ID = "select * from users where id = ?;";
    private final static String SQL_SELECT_BY_EMAIL = "select * from users where email = ?";
    private final static String SQL_DELETE_BY_ID = "DELETE FROM users WHERE id = ?;";
    private final static String SQL_PLUS_UPDATE_BALANCE_BY_ID = "UPDATE users SET balance = balance + ? WHERE id = ?;";
    private final static String SQL_MINUS_UPDATE_BALANCE_BY_ID = "UPDATE users SET balance = balance - ? WHERE id = ?;";
    private final static String SQL_SELECT_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";
    private final static String SQL_UPDATE_AVATAR = "update users set avatar_id = ? where email = ?";
    public UsersRepositoryImpl(HikariDataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    private User initUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id((UUID) resultSet.getObject("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .email(resultSet.getString("email"))
                .passwordHash(resultSet.getString("password"))
                .birthdate(resultSet.getDate("birthdate"))
                .balance(resultSet.getDouble("balance"))
                .avatarId((UUID) resultSet.getObject("avatar_id"))
                .build();
    }

    @Override
    public boolean findByEmailAndPassword(String email, String pass) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_EMAIL_AND_PASSWORD);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }

        return false;
    }

    @Override
    public Optional<User> findById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }

            User user = initUser(resultSet);
            return Optional.of(user);
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> result = new ArrayList<>();
            while (resultSet.next()) {
                User user = initUser(resultSet);
                result.add(user);
            }
            return result;
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_EMAIL);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            User user = initUser(resultSet);
            return Optional.of(user);
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }
        return Optional.empty();
    }

    @Override
    public void save(User item) {
        Optional<User> savedUser = findByEmail(item.getEmail());
        if (savedUser.isEmpty()) {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        SQL_INSERT, new String[]{"id"}
                );
                initStatement(statement, item);
                int affectedRows = statement.executeUpdate();

                if (affectedRows != 1) {
                    throw new SQLException("Cannot insert account");
                }

                try (ResultSet generatedIds = statement.getGeneratedKeys()) {
                    if (generatedIds.next()) {
                        item.setId((UUID) generatedIds.getObject("id"));
                    } else {
                        throw new SQLException("Cannot retrieve id");
                    }
                }
            } catch (SQLException throwable) {
                System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
            }
        }
    }

    private void initStatement(PreparedStatement statement, User item) throws SQLException {
        statement.setString(1, item.getFirstName());
        statement.setString(2, item.getLastName());
        statement.setString(3, item.getEmail());
        statement.setString(4, item.getPasswordHash());
        statement.setDate(5, item.getBirthdate());
        statement.setObject(6, item.getAvatarId());
    }

    @Override
    public void delete(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }
    }

    @Override
    public void updateUserBalance(UUID id, double balance, String operation) {
        if (operation.equals("plus")) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_PLUS_UPDATE_BALANCE_BY_ID);
                preparedStatement.setDouble(1, balance);
                preparedStatement.setObject(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException throwable) {
                System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
            }
        } else if (operation.equals("minus")) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_MINUS_UPDATE_BALANCE_BY_ID);
                preparedStatement.setDouble(1, balance);
                preparedStatement.setObject(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException throwable) {
                System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
            }
        }
    }

    @Override
    public void updateAvatarForUser(String email, UUID fileId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_AVATAR);
            preparedStatement.setObject(1, fileId);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }
    }
}
