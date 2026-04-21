package com.moon.labs.service;

import com.moon.labs.entity.Department;
import com.moon.labs.exception.DepartmentNotFoundException;
import com.moon.labs.repository.DepartmentRepository;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentServiceImpl(DepartmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public int save(String name, double budget) {
        Department department = new Department(0, name, budget);
        return repository.save(department);
    }

    @Override
    public Department findById(int id) {
        Department department = repository.findById(id);
        if (department == null) {
            throw new DepartmentNotFoundException("Department with id=" + id + " not found");
        }
        return department;
    }

    @Override
    public Department findByField(String field) {
        Department department = repository.findByField(field);
        if (department == null) {
            throw new DepartmentNotFoundException("Department with name='" + field + "' not found");
        }
        return department;
    }

    @Override
    public List<Department> findAll() {
        return repository.findAll();
    }

    @Override
    public void update(Department department) {
        boolean updated = repository.update(department);
        if (!updated) {
            throw new DepartmentNotFoundException("Department with id=" + department.getId() + " not found");
        }
    }

    @Override
    public void deleteById(int id) {
        Department existingDepartment = repository.findById(id);
        if (existingDepartment == null) {
            throw new DepartmentNotFoundException("Department with id=" + id + " not found");
        }

        repository.deleteById(id);
    }
}