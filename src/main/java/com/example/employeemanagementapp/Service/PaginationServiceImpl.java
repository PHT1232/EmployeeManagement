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

        List<T> returnList = new ArrayList<>();

        if (page >= currentNumberOfPageInList) {
            int offset = (page - 1) * numOfRows;

            inMemoryList.addAll(fetchListFromDatabase(10, offset));
        } else {
            int start = (page - 1) * numOfRows;
            int end = Math.min(start + numOfRows, inMemoryList.size());

            returnList.addAll(inMemoryList.subList(start, end));
        }

        return returnList;
    }
}
