package com.example.employeemanagementapp.Factories;

import com.example.employeemanagementapp.Builders.Builder;

public class EntityFactoryImpl implements EntityFactory {
    @Override
    public  <E, B extends Builder<E>> B getBuilder(Class<B> builderType, B buildert) {
        B builder = builderType.cast(buildert);
        return builder;
    }
}
