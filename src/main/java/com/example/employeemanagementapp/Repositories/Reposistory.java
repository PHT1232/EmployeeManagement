package com.example.employeemanagementapp.Repositories;

import com.example.employeemanagementapp.Entities.Employee;
import com.example.employeemanagementapp.Entities.Projects;
import com.example.employeemanagementapp.Mapper.RowMapper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Reposistory<T> {
    protected Connection connection;
    protected RowMapper<T> rowMapper;
    protected String tableName;

    public Reposistory() {
    }

    private PreparedStatement preparedInsertStatement(T entity) throws Exception {
        Class<?> entityType = entity.getClass();
        Field[] fields = entityType.getDeclaredFields();

        StringBuilder inputFields = new StringBuilder();
        StringBuilder params = new StringBuilder();

        for (int i = 0; i < fields.length; i++) {
            inputFields.append(fields[i].getName());
            params.append("?");
            if (i < fields.length -1) {
               inputFields.append(", ");
               params.append(", ");
            }
        }

        String query = "INSERT INTO " + tableName + " (" + inputFields + ") VALUES(" + params + ")";

        PreparedStatement statement = connection.prepareStatement(query);

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            statement.setObject(i + 1, fields[i].get(entity));
        }

        return statement;
    }

    private PreparedStatement preparedUpdateStatement(T entity) throws Exception {
        Class<?> entityType = entity.getClass();
        Field[] fields = entityType.getDeclaredFields();

        StringBuilder inputFields = new StringBuilder();

        for (int i = 0; i < fields.length; i++) {
            inputFields.append(fields[i].getName() + "= ?");
            if (i < fields.length -1) {
                inputFields.append(", ");
            }
        }

        fields[0].setAccessible(true);

        String query = "UPDATE " + tableName + " set " + inputFields + " WHERE " + fields[0].getName() + " = " + fields[0].get(entity);

        PreparedStatement statement = connection.prepareStatement(query);

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            statement.setObject(i + 1, fields[i].get(entity));
        }

        return statement;
    }

    private PreparedStatement preparedDeleteStatement(T entity) throws Exception {
        Class<?> entityType = entity.getClass();
        Field fields = entityType.getDeclaredFields()[0];

        fields.setAccessible(true);
        String query = "DELETE FROM " + tableName + " WHERE " + fields.getName() + " = " + fields.get(entity);
        PreparedStatement statement = connection.prepareStatement(query);

        return statement;
    }


    public Reposistory<T> Mapper(RowMapper<T> rowmapper) {
        this.rowMapper = rowmapper;
        return this;
    }

    public Reposistory<T> TableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public Reposistory<T> DatabaseConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public Reposistory<T> build() {
        return this;
    }

    public List<T> findAll() throws Exception {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;

        try (Statement statement = connection.createStatement();ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rowMapper.mapRow(rs));
            }
        }

        return list;
    }

    public T findById(int id, String idField) throws Exception {
        T employee = null;
        String sql = "SELECT * FROM " + tableName + " WHERE " + idField + " = " + id;

        try (Statement statement = connection.createStatement();ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                employee = rowMapper.mapRow(rs);
            }
        }

        return employee;
    }

    public List<T> fetchPagination(int numOfRow, int offset) throws Exception {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " LIMIT " + numOfRow + " OFFSET " + offset;

        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rowMapper.mapRow(rs));
            }
        }

        return list;
    }

    public int update(T entity) throws Exception {
        try (PreparedStatement statement = preparedUpdateStatement(entity)) {
            return statement.executeUpdate();
        }
    }

    public int delete(T entity) throws Exception {
        try (PreparedStatement statement = preparedDeleteStatement(entity)) {
            return statement.executeUpdate();
        }
    }

    public int insert(T entity) throws Exception {
       try (PreparedStatement statement = preparedInsertStatement(entity)) {
           return statement.executeUpdate();
       }
    }
}
