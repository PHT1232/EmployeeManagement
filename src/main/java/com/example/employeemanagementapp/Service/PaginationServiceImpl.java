package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Repositories.Reposistory;

import java.util.ArrayList;
import java.util.List;

public class PaginationServiceImpl<T> implements PaginationService<T>{
    private final Reposistory<T> thisRepository;

    public PaginationServiceImpl(Reposistory<T> reposistory) {
        thisRepository = reposistory;
    }

    private List<T> fetchListFromDatabase(int numOfRows, int offset) throws Exception {
        return thisRepository.fetchPagination(numOfRows, offset);
    }

    @Override
    public List<T> fetchData(int numOfRows, int page) throws Exception {
        int offset = (page - 1) * numOfRows;

        List<T> returnList;

        System.out.println("fetch from database");
        returnList = fetchListFromDatabase(numOfRows, offset);

        return returnList;
    }
}
