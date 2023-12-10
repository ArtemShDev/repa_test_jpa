package org.artsh.spring.springboot.spring_data_jpa;

import org.artsh.spring.springboot.spring_data_jpa.dao.EmployeeRepository;
import org.artsh.spring.springboot.spring_data_jpa.entity.Employee;
import org.artsh.spring.springboot.spring_data_jpa.service.EmployeeService;
import org.artsh.spring.springboot.spring_data_jpa.service.EmployeeServiceEmpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeServiceEmpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    void findAllBySurnameTest() {
        Employee emp1 = new Employee("Art", "Kamorov", 200);
        Employee emp2 = new Employee("Anton", "Sidorov", 300);
        List<Employee> source = new ArrayList<>(Arrays.asList(emp1, emp2));

        Mockito.when(employeeRepository.findAllBySurname("orov")).thenReturn(source);

        List<Employee> result = employeeService.findAllBySurname("orov");
        assertTrue(result.containsAll(List.of(
                new Employee("Art", "Kamorov", 200),
                new Employee("Anton", "Sidorov", 300))));

        assertTrue(result.removeAll(List.of(
                new Employee("Art", "Kamorov", 200),
                new Employee("Anton", "Sidorov", 300))) && result.isEmpty());

//        assertArrayEquals(new Employee[]{new Employee("Anton", "Sidorov", 300),
//                new Employee("Art", "Kamorov", 500)}, employeeService.findAllBySurname("orov").toArray());
    }

    @Test
    void deleteEmployeeTest() {
        employeeService.deleteEmployee(1);
        Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(1);
    }
}
