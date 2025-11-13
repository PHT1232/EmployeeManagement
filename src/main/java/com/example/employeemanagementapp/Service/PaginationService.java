package com.example.employeemanagementapp.Service;

import java.util.List;

public interface PaginationService<T> {
    List<T> fetchData(int numOfRows, int page) throws Exception;
}
