package org.artsh.spring.springboot.spring_data_jpa.service;

import com.sun.istack.NotNull;
import org.artsh.spring.springboot.spring_data_jpa.entity.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    void saveEmployee(Employee employee);
    Employee getEmployee(int id);
    void deleteEmployee(int id);
    public List<Employee> findAllByName(String name);
    List<Employee> findAllBySurname(String name);

    List<?> getRandomInfo();
    List<?> getRandomInfoByIdDep(int idDep);
    List<Employee> getRandom(String surnameSymbols, int minSalary);
}
