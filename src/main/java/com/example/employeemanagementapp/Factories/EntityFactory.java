package com.example.employeemanagementapp.Factories;

import com.example.employeemanagementapp.Builders.Builder;

public interface EntityFactory {
    <E, B extends Builder<E>> B getBuilder(Class<B> builderType, B builder);
}
