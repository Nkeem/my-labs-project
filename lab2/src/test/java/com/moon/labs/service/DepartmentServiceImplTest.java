package com.moon.labs.service;

import com.moon.labs.entity.Department;
import com.moon.labs.exception.DepartmentNotFoundException;
import com.moon.labs.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DepartmentServiceImplTest {

    private DepartmentRepository repository;
    private DepartmentService service;

    @BeforeEach
    void setUp() {
        repository = mock(DepartmentRepository.class);
        service = new DepartmentServiceImpl(repository);
    }

    @Test
    void testSave() {
        when(repository.save(any(Department.class))).thenReturn(7);

        int id = service.save("IT", 1500000.0);

        assertEquals(7, id);
        verify(repository).save(any(Department.class));
    }

    @Test
    void testFindByIdFound() {
        Department department = new Department(1, "IT", 1500000.0);
        when(repository.findById(1)).thenReturn(department);

        Department result = service.findById(1);

        assertEquals(department, result);
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findById(1)).thenReturn(null);

        assertThrows(DepartmentNotFoundException.class, () -> service.findById(1));
    }

    @Test
    void testFindByFieldFound() {
        Department department = new Department(2, "HR", 550000.0);
        when(repository.findByField("HR")).thenReturn(department);

        Department result = service.findByField("HR");

        assertEquals(department, result);
    }

    @Test
    void testFindByFieldNotFound() {
        when(repository.findByField("Unknown")).thenReturn(null);

        assertThrows(DepartmentNotFoundException.class, () -> service.findByField("Unknown"));
    }

    @Test
    void testFindAll() {
        Department first = new Department(1, "IT", 1500000.0);
        Department second = new Department(2, "HR", 550000.0);
        when(repository.findAll()).thenReturn(List.of(first, second));

        List<Department> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals(first, result.get(0));
        assertEquals(second, result.get(1));
    }

    @Test
    void testUpdateOk() {
        Department department = new Department(1, "IT Department", 1750000.0);
        when(repository.update(department)).thenReturn(true);

        service.update(department);

        verify(repository).update(department);
    }

    @Test
    void testUpdateNotFound() {
        Department department = new Department(10, "Unknown", 1000.0);
        when(repository.update(department)).thenReturn(false);

        assertThrows(DepartmentNotFoundException.class, () -> service.update(department));
    }

    @Test
    void testDeleteByIdOk() {
        Department department = new Department(3, "Finance", 2300000.0);
        when(repository.findById(3)).thenReturn(department);

        service.deleteById(3);

        verify(repository).deleteById(3);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(repository.findById(3)).thenReturn(null);

        assertThrows(DepartmentNotFoundException.class, () -> service.deleteById(3));
        verify(repository, never()).deleteById(3);
    }
}