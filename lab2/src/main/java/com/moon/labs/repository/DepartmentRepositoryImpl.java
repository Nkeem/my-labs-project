package com.moon.labs.repository;

import com.moon.labs.entity.Department;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.List;

public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final DepartmentDao dao;

    public DepartmentRepositoryImpl(Jdbi jdbi) {
        jdbi.installPlugin(new SqlObjectPlugin());
        this.dao = jdbi.onDemand(DepartmentDao.class);
    }

    @Override
    public int save(Department department) {
        try {
            return dao.save(department.getName(), department.getBudget());
        } catch (Exception e) {
            throw new RuntimeException("Failed to save department", e);
        }
    }

    @Override
    public Department findById(int id) {
        try {
            return dao.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch department by id", e);
        }
    }

    @Override
    public Department findByField(String field) {
        try {
            return dao.findByField(field);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch department by name", e);
        }
    }

    @Override
    public List<Department> findAll() {
        try {
            return dao.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all departments", e);
        }
    }

    @Override
    public boolean update(Department department) {
        try {
            int updatedRows = dao.update(department.getId(), department.getName(), department.getBudget());
            return updatedRows > 0;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update department", e);
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            dao.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete department", e);
        }
    }
}
