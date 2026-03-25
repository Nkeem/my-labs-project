package com.moon.labs.repository;

import com.moon.labs.entity.Department;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentMapper implements RowMapper<Department> {
    @Override
    public Department map(ResultSet rs, StatementContext ctx) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        double budget = rs.getDouble("budget");
        return new Department(id, name, budget);
    }
}
