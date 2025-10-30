package com.example.employeemanagementapp.Factories;

import com.example.employeemanagementapp.Builders.Builder;

public interface EntityFactoryProvider {
    <E, B extends Builder<E>> B create(Class<B> builderType);
}
