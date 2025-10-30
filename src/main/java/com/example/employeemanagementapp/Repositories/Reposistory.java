package com.example.employeemanagementapp.Repositories;

import com.example.employeemanagementapp.Mapper.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Reposistory<T> {
    private final Connection connection;
    private final RowMapper<T> rowMapper;
    private final String tableName;

    public Reposistory(Connection connection, RowMapper<T> rowMapper, String tableName) {
        this.connection = connection;
        this.rowMapper = rowMapper;
        this.tableName = tableName;
    }

    protected List<T> findAll() throws SQLException {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;

        try (Statement statement = connection.createStatement();ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rowMapper.mapRow(rs));
            }
        }

        return list;
    }

    protected int insert(String sql, Object... params) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return statement.executeUpdate();
        }
    }
}
