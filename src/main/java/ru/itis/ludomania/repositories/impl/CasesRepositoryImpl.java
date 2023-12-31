package ru.itis.ludomania.repositories.impl;

import com.zaxxer.hikari.HikariDataSource;
import ru.itis.ludomania.exceptions.CustomException;
import ru.itis.ludomania.model.Case;
import ru.itis.ludomania.repositories.CasesRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CasesRepositoryImpl implements CasesRepository {
    private final Connection connection;
    private final static String SQL_SELECT_BY_ID = "select * from cases where id = ?;";
    private final static String SQL_SELECT_ALL = "select * from cases;";
    private final static String SQL_INSERT = "insert into cases (name, price, skins_count) VALUES (?, ?, ?);";
    private final static String SQL_SELECT_BY_NAME = "select * from cases where name = ?;";
    private final static String SQL_DELETE_BY_ID = "DELETE FROM cases WHERE id = ?;";
    public CasesRepositoryImpl(HikariDataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    private Case initCase(ResultSet resultSet) throws SQLException {

        return Case.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .price(resultSet.getDouble("price"))
                .skinsCount(resultSet.getInt("skins_count"))
                .build();
    }

    @Override
    public Optional<Case> findById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(initCase(resultSet));
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Case> findAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Case> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(initCase(resultSet));
            }
            return result;
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }
        return new ArrayList<>();
    }



    @Override
    public void save(Case item) {
        Optional<Case> savedCase = findByName(item.getName());
        if (savedCase.isEmpty()) {
            try {
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
                initStatement(statement, item);
                int affectedRows = statement.executeUpdate();

                if (affectedRows != 1) {
                    throw new CustomException("Cannot insert account");
                }
            } catch (SQLException throwable) {
                System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
            }
        }
    }

    private void initStatement(PreparedStatement statement, Case item) throws SQLException {
        statement.setString(1, item.getName());
        statement.setDouble(2, item.getPrice());
        statement.setInt(3, item.getSkinsCount());
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
    public Optional<Case> findByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_NAME);
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(initCase(resultSet));
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }
        return Optional.empty();
    }
}
