package org.artsh.spring.springboot.spring_data_jpa.service;

import com.sun.istack.NotNull;
import org.artsh.spring.springboot.spring_data_jpa.dao.EmployeeRepository;
import org.artsh.spring.springboot.spring_data_jpa.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmployeeServiceEmpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployee(int id) {
        Optional<Employee> opt = employeeRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> findAllByName(String name) {
        return employeeRepository.findAllByName(name);
    }

    @Override
    public List<Employee> findAllBySurname(String surname) {
        return employeeRepository.findAllBySurname(surname);
    }

    @Override
    public List<Object> getRandomInfo() {
        return employeeRepository.getRandomInformation();
    }

    @Override
    public List<Object> getRandomInfoByIdDep(int idDep) throws NoSuchElementException {
        return employeeRepository.getRandomInformationByIdDep(idDep);
    }

    @Override
    public List<Employee> getRandom(String surnameSymbols, int minSalary) {
        return employeeRepository.findAllBySurnameIsContainingAndSalaryGreaterThan(surnameSymbols, minSalary);
    }


}
