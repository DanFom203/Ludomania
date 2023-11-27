package ru.itis.ludomania.repositories.impl;

import com.zaxxer.hikari.HikariDataSource;
import ru.itis.ludomania.model.Case;
import ru.itis.ludomania.repositories.CasesRepository;

import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CasesRepositoryImpl implements CasesRepository {
    private final Connection connection;
    private final static String SQL_SELECT_BY_ID = "select * from cases where id = ?;";

    public CasesRepositoryImpl(HikariDataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    private Case initCase(ResultSet resultSet) throws SQLException {
        Array weaponsArray = resultSet.getArray("list_of_weapons");
        List<String> listOfWeapons = weaponsArray == null ? Collections.emptyList() :
                Arrays.asList((String[]) weaponsArray.getArray());

        return Case.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .listOfWeapons(listOfWeapons)
                .build();
    }

    @Override
    public Optional<Case> findById(Integer id) {
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
//            preparedStatement.setInt(1, id);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (!resultSet.next()) {
//                return Optional.empty();
//            }
//
////            Case case = initCase(resultSet);
////            return Optional.of(case);
////        } catch (SQLException throwable) {
////            System.out.println("SQL CustomException: " + throwable.getLocalizedMessage());
////        }
//
        return Optional.empty();
    }

    @Override
    public List<Case> findAll() {
        return null;
    }

    @Override
    public Case save(Case item) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
