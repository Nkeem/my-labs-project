package com.moon.labs.database;

import com.moon.labs.database.config.ApplicationProperties;
import com.moon.labs.database.config.ConnectionFactory;
import com.moon.labs.database.model.Department;
import com.moon.labs.database.repository.DepartmentRepository;
import com.moon.labs.database.repository.DepartmentRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ApplicationProperties properties = new ApplicationProperties();

        try (ConnectionFactory connectionFactory = new ConnectionFactory(properties)) {
            DepartmentRepository repository = new DepartmentRepositoryImpl(connectionFactory);

            log.info("=== СОЗДАНИЕ ЗАПИСЕЙ ===");
            Department itDepartment = new Department("IT", 150000.0);
            Department hrDepartment = new Department("HR", 80000.0);
            Department financeDepartment = new Department("Finance", 120000.0);

            int itId = repository.save(itDepartment);
            int hrId = repository.save(hrDepartment);
            int financeId = repository.save(financeDepartment);

            log.info("Создан department IT с id={}", itId);
            log.info("Создан department HR с id={}", hrId);
            log.info("Создан department Finance с id={}", financeId);

            log.info("=== ПОИСК ПО ID ===");
            Department foundById = repository.findById(itId);
            log.info("Результат findById({}): {}", itId, foundById);

            log.info("=== ПОИСК ПО NAME ===");
            Department foundByName = repository.findByField("HR");
            log.info("Результат findByField(HR): {}", foundByName);

            log.info("=== СПИСОК ВСЕХ DEPARTMENT ===");
            List<Department> allBeforeUpdate = repository.findAll();
            allBeforeUpdate.forEach(department -> log.info("{}", department));

            log.info("=== ОБНОВЛЕНИЕ ===");
            Department departmentToUpdate = new Department(hrId, "Human Resources", 90000.0);
            boolean updated = repository.update(departmentToUpdate);
            log.info("Результат update: {}", updated);

            Department updatedDepartment = repository.findById(hrId);
            log.info("Department после update: {}", updatedDepartment);

            log.info("=== УДАЛЕНИЕ ===");
            repository.deleteById(itId);

            Department afterDelete = repository.findById(itId);
            log.info("Проверка после deleteById({}): {}", itId, afterDelete);

            log.info("=== СПИСОК ПОСЛЕ УДАЛЕНИЯ ===");
            List<Department> allAfterDelete = repository.findAll();
            allAfterDelete.forEach(department -> log.info("{}", department));

        } catch (Exception e) {
            log.error("Ошибка при выполнении приложения", e);
        }
    }
}