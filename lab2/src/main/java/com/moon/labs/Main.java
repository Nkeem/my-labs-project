package com.moon.labs;

import com.moon.labs.config.ConnectionFactory;
import com.moon.labs.config.DatabaseMigrator;
import com.moon.labs.entity.Department;
import com.moon.labs.repository.DepartmentRepository;
import com.moon.labs.repository.DepartmentRepositoryImpl;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        DatabaseMigrator migrator = new DatabaseMigrator(connectionFactory);
        migrator.runMigrations();

        Jdbi jdbi = Jdbi.create(connectionFactory.getDataSource());
        DepartmentRepository repository = new DepartmentRepositoryImpl(jdbi);

        demoAllMethods(repository);

        System.out.println("Завершение работы");
    }

    private static void demoAllMethods(DepartmentRepository repository) {
        System.out.println("=== Начальное состояние ===");
        printDepartments(repository.findAll());

        Department department1 = new Department(0, "IT", 1_500_000.00);
        Department department2 = new Department(0, "HR", 550_000.00);
        Department department3 = new Department(0, "Finance", 2_300_000.00);

        int savedId = repository.save(department1);
        repository.save(department2);
        repository.save(department3);

        System.out.println("=== После сохранения ===");
        printDepartments(repository.findAll());

        Department foundById = repository.findById(savedId);
        System.out.println("Найден по id: " + foundById);

        Department foundByField = repository.findByField("IT");
        System.out.println("Найден по name: " + foundByField);

        if (foundById != null) {
            foundById.setName("IT Department");
            foundById.setBudget(1_750_000.00);
            boolean updated = repository.update(foundById);
            System.out.println("Обновлён: " + updated);
        }

        System.out.println("=== После обновления ===");
        System.out.println("Найден по id: " + repository.findById(savedId));

        repository.deleteById(savedId);
        System.out.println("Удалён с id = " + savedId);

        System.out.println("=== После удаления ===");
        System.out.println("findById: " + repository.findById(savedId));
        printDepartments(repository.findAll());
    }

    private static void printDepartments(List<Department> departments) {
        if (departments.isEmpty()) {
            System.out.println("Список departments пуст");
            return;
        }

        for (Department department : departments) {
            System.out.println(department);
        }
        System.out.println("End this");
    }
}
