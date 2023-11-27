package ru.itis.ludomania.repositories.impl;

import com.zaxxer.hikari.HikariDataSource;
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
    private final static String SQL_INSERT = "insert into users (first_name, last_name, email, password, birthdate) VALUES (?, ?, ?, ?, ?);";
    private final static String SQL_SELECT_BY_ID = "select * from users where id = ?;";
    private final static String SQL_SELECT_BY_EMAIL = "select * from users where email = ?";
    private final static String SQL_DELETE_BY_ID = "DELETE FROM users WHERE id = ?;";
    private final static String SQL_UPDATE = "update users set email = ?, password = ?, first_name = ?, last_name = ?, birthdate = ? where id = ?;";
    private final static String SQL_SELECT_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";
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
    public User save(User item) {
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
                return item;
            } catch (SQLException throwable) {
                System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
            }
            return item;
        } else {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        SQL_UPDATE
                );
                initStatement(statement, item);
                statement.setString(6, String.valueOf(savedUser.get().getId()));
                int affectedRows = statement.executeUpdate();

                if (affectedRows != 1) {
                    throw new SQLException("Cannot insert account");
                }
                return item;
            } catch (SQLException throwable) {
                System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
            }
            return item;
        }
    }

    private void initStatement(PreparedStatement statement, User item) throws SQLException {
        statement.setString(1, item.getFirstName());
        statement.setString(2, item.getLastName());
        statement.setString(3, item.getEmail());
        statement.setString(4, item.getPasswordHash());
        statement.setDate(5, item.getBirthdate());
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
}
