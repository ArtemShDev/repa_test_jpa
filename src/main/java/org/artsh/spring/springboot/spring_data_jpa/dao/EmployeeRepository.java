package org.artsh.spring.springboot.spring_data_jpa.dao;

import org.artsh.spring.springboot.spring_data_jpa.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAllBySurnameIsContainingOrSalaryGreaterThan(String surnameSymbols, int minSalary);

    List<Employee> findAllBySurnameIsContainingAndSalaryGreaterThan(String surnameSymbols, int minSalary);

    List<Employee> findAllByName(String name);

    List<Employee> findAllById(int id);

    List<Employee>  findAllBySurname(String surname);

    @Query(nativeQuery = true, value = "select emp.id, emp.name, emp.surname, emp.salary, dep.name department " +
            "from employees as emp join departments dep " +
            "on emp.department_id = dep.id " +
            "where emp.department_id is not null")
    List<Object> getRandomInformation();

    @Query(nativeQuery = true, value = "select emp.id, emp.name, emp.surname, emp.salary, dep.name department " +
            "from employees as emp join departments dep " +
            "on emp.department_id = dep.id " +
            "where emp.department_id = :idDepartment")
    List<Object> getRandomInformationByIdDep(@Param("idDepartment") int idDep);
}
