package com.example.employeemanagementapp.Factories;

import com.example.employeemanagementapp.Builders.Builder;

import java.util.List;

public class EntityFactoryProviderImpl implements EntityFactoryProvider {
    private static final List<EntityFactory> entityFactories = List.of(new EmployeeFactoryImpl());

    private <B> EntityFactory getFactoryByType(Class<B> builderType) {
        EntityFactory entityFactory = entityFactories.stream().filter(e -> builderType.isInstance(e.getBuilder())).findFirst().get();

        return entityFactory;
    }

    @Override
    public <E, B extends Builder<E>> B create(Class<B> builderType) {
        B builder = builderType.cast(getFactoryByType(builderType));

        return builder;
    }
}
