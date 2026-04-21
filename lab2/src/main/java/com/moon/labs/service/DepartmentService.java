package com.moon.labs.service;

import com.moon.labs.entity.Department;

import java.util.List;

public interface DepartmentService {
    int save(String name, double budget);

    Department findById(int id);

    Department findByField(String field);

    List<Department> findAll();

    void update(Department department);

    void deleteById(int id);
}