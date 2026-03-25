package com.moon.labs.repository;

import com.moon.labs.entity.Department;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterRowMapper(DepartmentMapper.class)
public interface DepartmentDao {

    @SqlUpdate("INSERT INTO department (name, budget) VALUES (:name, :budget)")
    @GetGeneratedKeys
    int save(@Bind("name") String name,
             @Bind("budget") double budget);

    @SqlQuery("SELECT id, name, budget FROM department WHERE id = :id")
    Department findById(@Bind("id") int id);

    @SqlQuery("SELECT id, name, budget FROM department WHERE name = :name ORDER BY id LIMIT 1")
    Department findByField(@Bind("name") String name);

    @SqlQuery("SELECT id, name, budget FROM department ORDER BY id")
    List<Department> findAll();

    @SqlUpdate("UPDATE department SET name = :name, budget = :budget WHERE id = :id")
    int update(@Bind("id") int id,
               @Bind("name") String name,
               @Bind("budget") double budget);

    @SqlUpdate("DELETE FROM department WHERE id = :id")
    void deleteById(@Bind("id") int id);
}
