package com.example.employeemanagementapp.Service;

import com.example.employeemanagementapp.Repositories.Reposistory;

import java.util.ArrayList;
import java.util.List;

public class PaginationServiceImpl<T> implements PaginationService<T>{
    private final Reposistory<T> thisRepository;
    private List<T> inMemoryList = new ArrayList<>();

    public PaginationServiceImpl(Reposistory<T> reposistory) {
        thisRepository = reposistory;
    }

    private List<T> fetchListFromDatabase(int numOfRows, int offset) throws Exception {
        return thisRepository.fetchPagination(numOfRows, offset);
    }

    @Override
    public List<T> fetchData(int numOfRows, int page) throws Exception {
        int currentNumberOfPageInList = inMemoryList.size() / numOfRows;
        int offset = (page - 1) * numOfRows;

        List<T> returnList;

        if (page > currentNumberOfPageInList) {
            System.out.println("fetch from database");
            returnList = fetchListFromDatabase(numOfRows, offset);
            inMemoryList.addAll(fetchListFromDatabase(numOfRows, offset));
        } else {
            System.out.println("fetch from memory");
            int end = Math.min(offset + numOfRows, inMemoryList.size());
            returnList = inMemoryList.subList(offset, end);
        }

        return returnList;
    }
}
