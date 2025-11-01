package com.example.employeemanagementapp.Mapper;

import java.sql.ResultSet;

public interface RowMapper<T> {
    T mapRow(ResultSet resultSet) throws Exception;
}
