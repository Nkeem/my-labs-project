package com.moon.labs.repository;

import com.moon.labs.entity.Department;

import java.util.List;

public interface DepartmentRepository {
    int save(Department department);

    Department findById(int id);

    Department findByField(String field);

    List<Department> findAll();

    boolean update(Department department);

    void deleteById(int id);
}
