package com.moon.labs;

import com.moon.labs.config.ConnectionFactory;
import com.moon.labs.config.DatabaseMigrator;
import com.moon.labs.entity.Department;
import com.moon.labs.exception.DepartmentNotFoundException;
import com.moon.labs.repository.DepartmentRepository;
import com.moon.labs.repository.DepartmentRepositoryImpl;
import com.moon.labs.service.DepartmentService;
import com.moon.labs.service.DepartmentServiceImpl;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        DatabaseMigrator migrator = new DatabaseMigrator(connectionFactory);
        migrator.runMigrations();

        Jdbi jdbi = Jdbi.create(connectionFactory.getDataSource());
        DepartmentRepository repository = new DepartmentRepositoryImpl(jdbi);
        DepartmentService service = new DepartmentServiceImpl(repository);

        demoAllMethods(service);

        System.out.println("Завершение работы");
    }

    private static void demoAllMethods(DepartmentService service) {
        System.out.println("=== Начальное состояние ===");
        printDepartments(service.findAll());

        int savedId = service.save("IT", 1_500_000.00);
        service.save("HR", 550_000.00);
        service.save("Finance", 2_300_000.00);

        System.out.println("=== После сохранения ===");
        printDepartments(service.findAll());

        Department foundById = service.findById(savedId);
        System.out.println("Найден по id: " + foundById);

        Department foundByField = service.findByField("IT");
        System.out.println("Найден по name: " + foundByField);

        foundById.setName("IT Department");
        foundById.setBudget(1_750_000.00);
        service.update(foundById);
        System.out.println("Обновлён");

        System.out.println("=== После обновления ===");
        System.out.println("Найден по id: " + service.findById(savedId));

        service.deleteById(savedId);
        System.out.println("Удалён с id = " + savedId);

        System.out.println("=== После удаления ===");
        try {
            System.out.println("findById: " + service.findById(savedId));
        } catch (DepartmentNotFoundException e) {
            System.out.println(e.getMessage());
        }

        printDepartments(service.findAll());

        try {
            service.findById(999999);
        } catch (DepartmentNotFoundException e) {
            System.out.println("Ожидаемая ошибка findById: " + e.getMessage());
        }

        try {
            service.deleteById(999999);
        } catch (DepartmentNotFoundException e) {
            System.out.println("Ожидаемая ошибка deleteById: " + e.getMessage());
        }
    }

    private static void printDepartments(List<Department> departments) {
        if (departments.isEmpty()) {
            System.out.println("Список departments пуст");
            return;
        }

        for (Department department : departments) {
            System.out.println(department);
        }
    }
}