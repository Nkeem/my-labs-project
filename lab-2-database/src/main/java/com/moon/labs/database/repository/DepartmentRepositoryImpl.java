package com.moon.labs.database.repository;

import com.moon.labs.database.config.ConnectionFactory;
import com.moon.labs.database.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepositoryImpl implements DepartmentRepository {

    private static final Logger log = LoggerFactory.getLogger(DepartmentRepositoryImpl.class);

    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS department (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL UNIQUE,
                budget DOUBLE PRECISION NOT NULL CHECK (budget >= 0)
            )
            """;

    private static final String INSERT_SQL = """
            INSERT INTO department (name, budget)
            VALUES (?, ?)
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT id, name, budget
            FROM department
            WHERE id = ?
            """;

    private static final String FIND_BY_NAME_SQL = """
            SELECT id, name, budget
            FROM department
            WHERE name = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, name, budget
            FROM department
            ORDER BY id
            """;

    private static final String UPDATE_SQL = """
            UPDATE department
            SET name = ?, budget = ?
            WHERE id = ?
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM department
            WHERE id = ?
            """;

    private final ConnectionFactory connectionFactory;

    public DepartmentRepositoryImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        initTable();
    }

    private void initTable() {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_SQL)
        ) {
            statement.executeUpdate();
            log.info("Таблица department готова к работе");
        } catch (SQLException e) {
            throw new IllegalStateException("Не удалось создать таблицу department", e);
        }
    }

    @Override
    public int save(Department department) {
        validateDepartmentForSave(department);

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, department.getName());
            statement.setDouble(2, department.getBudget());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Сохранение department не выполнилось, ни одна строка не добавлена");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    department.setId(id);
                    log.info("Department сохранен: {}", department);
                    return id;
                }
            }

            throw new SQLException("Сохранение department выполнено, но id не был получен");
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при сохранении department", e);
        }
    }

    @Override
    public Department findById(int id) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)
        ) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Department department = mapRow(resultSet);
                    log.info("Department найден по id={}: {}", id, department);
                    return department;
                }
            }

            log.info("Department с id={} не найден", id);
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при поиске department по id", e);
        }
    }

    @Override
    public Department findByField(String field) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME_SQL)
        ) {
            statement.setString(1, field);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Department department = mapRow(resultSet);
                    log.info("Department найден по name={}: {}", field, department);
                    return department;
                }
            }

            log.info("Department с name='{}' не найден", field);
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при поиске department по name", e);
        }
    }

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                departments.add(mapRow(resultSet));
            }

            log.info("Получено departments: {}", departments.size());
            return departments;
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при получении списка department", e);
        }
    }

    @Override
    public boolean update(Department department) {
        validateDepartmentForUpdate(department);

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)
        ) {
            statement.setString(1, department.getName());
            statement.setDouble(2, department.getBudget());
            statement.setInt(3, department.getId());

            int updatedRows = statement.executeUpdate();
            boolean updated = updatedRows > 0;

            if (updated) {
                log.info("Department обновлен: {}", department);
            } else {
                log.info("Department для обновления не найден, id={}", department.getId());
            }

            return updated;
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при обновлении department", e);
        }
    }

    @Override
    public void deleteById(int id) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_SQL)
        ) {
            statement.setInt(1, id);
            int deletedRows = statement.executeUpdate();

            if (deletedRows > 0) {
                log.info("Department с id={} удален", id);
            } else {
                log.info("Department с id={} не найден, удалять нечего", id);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Ошибка при удалении department по id", e);
        }
    }

    private Department mapRow(ResultSet resultSet) throws SQLException {
        return new Department(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDouble("budget")
        );
    }

    private void validateDepartmentForSave(Department department) {
        if (department == null) {
            throw new IllegalArgumentException("Department не должен быть null");
        }
        if (department.getName() == null || department.getName().isBlank()) {
            throw new IllegalArgumentException("Название department не должно быть пустым");
        }
        if (department.getBudget() < 0) {
            throw new IllegalArgumentException("Бюджет department не может быть отрицательным");
        }
    }

    private void validateDepartmentForUpdate(Department department) {
        validateDepartmentForSave(department);
        if (department.getId() == null) {
            throw new IllegalArgumentException("Для update у department должен быть id");
        }
    }
}