package ru.itis.ludomania.repositories.impl;

import com.zaxxer.hikari.HikariDataSource;
import ru.itis.ludomania.exceptions.CustomException;
import ru.itis.ludomania.model.WeaponSkin;
import ru.itis.ludomania.repositories.SkinsRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkinsRepositoryImpl implements SkinsRepository {
    private final Connection connection;
    private final static String SQL_SELECT_BY_ID = "select * from skins where id = ?;";
    private final static String SQL_SELECT_BY_CASE_ID = "SELECT * FROM skins WHERE case_id = ?;";
    private final static String SQL_SELECT_ALL = "select * from skins;";
    private final static String SQL_INSERT = "insert into skins (name, caseId, rarity, price) VALUES (?, ?, ?, ?);";
    private final static String SQL_SELECT_BY_NAME = "select * from skins where name = ?;";
    private final static String SQL_DELETE_BY_ID = "DELETE FROM skins WHERE id = ?;";
    private final static String SQL_SELECT_BY_SKIN_AND_CASE_ID = "SELECT * FROM skins WHERE (id = ? AND case_id = ?);";
    public SkinsRepositoryImpl(HikariDataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }
    private WeaponSkin initSkin(ResultSet resultSet) throws SQLException {

        return WeaponSkin.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .caseId(resultSet.getInt("case_id"))
                .rarity(resultSet.getString("rarity"))
                .price(resultSet.getDouble("price"))
                .build();
    }
    @Override
    public List<WeaponSkin> findByCaseId(Integer caseId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_CASE_ID);
            preparedStatement.setInt(1, caseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<WeaponSkin> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(initSkin(resultSet));
            }
            return result;
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }
        return new ArrayList<>();
    }
    @Override
    public Optional<WeaponSkin> findById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(initSkin(resultSet));
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }
        return Optional.empty();
    }
    @Override
    public Optional<WeaponSkin> findBySkinAndCaseId(Integer skinId, Integer caseId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_SKIN_AND_CASE_ID);
            preparedStatement.setInt(1, skinId);
            preparedStatement.setInt(2, caseId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(initSkin(resultSet));
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<WeaponSkin> findAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<WeaponSkin> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(initSkin(resultSet));
            }
            return result;
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public void save(WeaponSkin item) {
        Optional<WeaponSkin> savedSkin = findByName(item.getName());
        if (savedSkin.isEmpty()) {
            try {
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
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

    private void initStatement(PreparedStatement statement, WeaponSkin item) throws SQLException {
        statement.setString(1, item.getName());
        statement.setInt(2, item.getCaseId());
        statement.setString(3, item.getRarity());
        statement.setDouble(4, item.getPrice());
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
    public Optional<WeaponSkin> findByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_NAME);
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(initSkin(resultSet));
        } catch (SQLException throwable) {
            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
        }
        return Optional.empty();
    }
}
