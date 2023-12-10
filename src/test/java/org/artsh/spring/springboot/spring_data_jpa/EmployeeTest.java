package org.artsh.spring.springboot.spring_data_jpa;

import org.artsh.spring.springboot.spring_data_jpa.entity.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmployeeTest {

    Employee employee;

    @BeforeEach
    void setup() {
        employee = new Employee("Art", "Shmelev", 200);
    }

    @Test
    void constructorEmployeeTest() {
        //mployee employee = new Employee("Art", "Shmelev", 200);
        assertEquals("Art", employee.getName(),"Name is not equals 'Art'");
        assertEquals("Shmelev", employee.getSurname(), "Surname is not equals 'Shmelev'");
        assertEquals(200, employee.getSalary(), "Salary is not equals 200");
    }

//    @Test
//    void getDepartment2Test() {
//        assertThrows(ClassNotFoundException.class, () -> employee.getDepartment2());
//    }

//    @Test
//    void testGetOsName() throws IOException {
//        Process process;
//        System.out.println(System.getProperty("os.name"));
//        process = Runtime.getRuntime().exec("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe --version");
//        //System.out.println(Arrays.toString(process.info().arguments().get()));
//        //String str = new BufferedReader(new InputStreamReader(process.getInputStream())).readLine();
//        String str = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
//        System.out.println(str);
//
//    }
}
