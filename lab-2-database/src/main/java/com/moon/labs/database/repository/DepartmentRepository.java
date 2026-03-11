package com.moon.labs.database.repository;

import com.moon.labs.database.model.Department;

import java.util.List;

public interface DepartmentRepository {

    int save(Department department);

    Department findById(int id);

    Department findByField(String field);

    List<Department> findAll();

    boolean update(Department department);

    void deleteById(int id);
}