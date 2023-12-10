package org.artsh.spring.springboot.spring_data_jpa.controller;

import com.sun.istack.NotNull;
import org.artsh.spring.springboot.spring_data_jpa.entity.Employee;
import org.artsh.spring.springboot.spring_data_jpa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class MyRESTController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> showAllEmployees() {
        List<Employee> list = employeeService.getAllEmployees();
        return list;
    }

    @GetMapping("/info")
    public List<?> showRandomInfo() {
        return employeeService.getRandomInfo();
    }

    @GetMapping("/info/{idDep}")
    public List<?> showRandomInfoByIdDep(@PathVariable int idDep) {
        List<?> elements = employeeService.getRandomInfoByIdDep(idDep);
        if (elements.isEmpty()) {
            throw new NoSuchElementException("Пользователей с департаментом id = '" + idDep + "' не найдено");
        }
        return elements;
    }

    @GetMapping("/employees/random")
    public List<?> showRandom(@RequestParam(required = false) String surname, @RequestParam(required = false) int minSalary) {
        List<?> elements = employeeService.getRandom(surname, minSalary);
        if (elements.isEmpty()) {
            throw new NoSuchElementException("Пользователей с содержанием в фамилии '" + surname + "' и с зарплатой более чем '" + minSalary + "' не найдено");
        }
        return elements;
    }

    @GetMapping("/employees/{id}")
    public Employee showAllEmployeesById(@PathVariable int id) {
        return employeeService.getEmployee(id);
    }

    @PostMapping("/employees")
    public Employee addNewEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        return employee;
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        return employee;
    }

    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable int id) {
        Employee employee = employeeService.getEmployee(id);
        employeeService.deleteEmployee(id);
        return "Employee with ID " + id + " was deleted.";
    }

    @GetMapping("/employees/name/{name}")
    public List<Employee> showAllEmployeesByName(@PathVariable String name) {
        List<Employee> list = employeeService.findAllByName(name);
        return list;
    }

    @GetMapping("/employees/surname/{surname}")
    public List<Employee> showAllEmployeesBySurname(@PathVariable String surname) {
        List<Employee> list = employeeService.findAllBySurname(surname);
        return list;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleException(NoSuchElementException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage() + " : " + Arrays.toString(exception.getStackTrace()));
    }
}
