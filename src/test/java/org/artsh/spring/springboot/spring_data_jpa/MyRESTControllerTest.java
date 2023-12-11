package org.artsh.spring.springboot.spring_data_jpa;

import static org.assertj.core.api.BDDAssertions.and;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import org.artsh.spring.springboot.spring_data_jpa.controller.MyRESTController;
import org.artsh.spring.springboot.spring_data_jpa.entity.Employee;
import org.artsh.spring.springboot.spring_data_jpa.service.EmployeeServiceEmpl;
import org.assertj.core.internal.bytebuddy.matcher.ElementMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;

//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;

@ExtendWith(MockitoExtension.class)
public class MyRESTControllerTest {

    @Mock
    private EmployeeServiceEmpl employeeService;
    @InjectMocks
    private MyRESTController controller;

    // Эмулятор http-запросов для контроллера
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void showAllEmployeesByIdTest() throws Exception {
        Mockito.when(employeeService.getEmployee(1)).thenReturn(new Employee("Test", "Testovich", 100));
        mockMvc
                .perform(get("/api/employees/{id}", 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.surname").value("Testovich"))
                .andExpect(jsonPath("$.salary").value(100));
        Mockito.verify(employeeService, Mockito.times(1)).getEmployee(1);
    }

    @Test
    void showAllEmployeesTest() throws Exception {
        Employee emp1 = new Employee("Art", "Shmelev", 200);
        Employee emp2 = new Employee("Maks", "Kamarov", 400);
        Mockito.when(employeeService.getAllEmployees()).thenReturn(List.of(emp1, emp2));
        mockMvc
                .perform(get("/api/employees"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0].name").value("Art"))
                .andExpect(jsonPath("$.[0].surname").value("Shmelev"))
                .andExpect(jsonPath("$.[0].salary").value(200))
                .andExpect(jsonPath("$.[1].name").value("Maks"))
                .andExpect(jsonPath("$.[1].surname").value("Kamarov"))
                .andExpect(jsonPath("$.[1].salary").value(400));
        Mockito.verify(employeeService, Mockito.times(1)).getAllEmployees();
    }

    @Test
    void showRandomInfoTest() throws Exception {
        Mockito.when(employeeService.getRandomInfo()).thenReturn(List.of(
                Map.of("user", "admin", "role", "admin", "age", "35"),
                Map.of("user", "Artem", "role", "user", "age", "38")));
        mockMvc
                .perform(get("/api/info"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[?(@.user==\"admin\")].age").value("35"))
                .andExpect(jsonPath("$.[?(@.user==\"Artem\")].age").value("38"));
        Mockito.verify(employeeService, Mockito.times(1)).getRandomInfo();
    }

    @Test
    void showRandomInfoByIdDepTest_Success() throws Exception {
        Mockito.when(employeeService.getRandomInfoByIdDep(1)).thenReturn(List.of(
                Map.of("user", "admin", "role", "admin", "age", "35")));
        mockMvc
                .perform(get("/api/info/{idDep}", 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[?(@.user==\"admin\")].age").value("35"));
        Mockito.verify(employeeService, Mockito.times(1)).getRandomInfoByIdDep(1);
    }

    @Test
    void showRandomInfoByIdDepTest_Exception() throws Exception {
        Mockito.when(employeeService.getRandomInfoByIdDep(3)).thenReturn(List.of());
        mockMvc
                .perform(get("/api/info/{idDep}", 3))
                .andExpect(status().is4xxClientError())
                //.andExpect(content().string(Matchers.containsString("id = '3'")))
                .andExpect(jsonPath("$").value(Matchers.containsString("id = '3'")));
        Mockito.verify(employeeService, Mockito.times(1)).getRandomInfoByIdDep(3);
    }

    @Test
    void showRandomTest_Success() throws Exception {
        Employee emp1 = new Employee("Art", "Shmelev", 500);
        Employee emp2 = new Employee("Anton", "Shmonov", 600);
        Employee emp3 = Mockito.mock(Employee.class);
        Assertions.assertEquals(0, emp3.getSalary());
        Assertions.assertNull(emp3.getSurname());
        Mockito.when(employeeService.getRandom("Shm", 200)).thenReturn(List.of(emp1, emp2));
        mockMvc
                .perform(get("/api/employees/random")
                        .param("surname", "Shm")
                        .param("minSalary", "200"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[?(@.salary==500)].surname").value("Shmelev"))
                .andExpect(jsonPath("$.[?(@.salary==600)].surname").value("Shmonov"));
        Mockito.verify(employeeService, Mockito.times(1)).getRandom("Shm", 200);
    }

    @Test
    void showRandomTest_Exception() throws Exception {
        Mockito.when(employeeService.getRandom("Noname", 200)).thenReturn(List.of());
        mockMvc
                .perform(get("/api/employees/random")
                        .param("surname", "Noname")
                        .param("minSalary", "200"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").value(Matchers.matchesRegex(".*Noname.*200.*")));
        Mockito.verify(employeeService, Mockito.times(1)).getRandom("Noname", 200);
    }

    @Test
    void addNewEmployeeTest() throws Exception {
        Employee emp = new Employee("Art", "Kamorov", 200);
        mockMvc
                .perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value("Art"))
                .andExpect(jsonPath("$.surname").value("Kamorov"))
                .andExpect(jsonPath("$.salary").value(200));
        Mockito.verify(employeeService, Mockito.times(1)).saveEmployee(emp);
    }

    @Owner("Shmelev Artem")
    @Link(value = "Link to testcase", url = "https://microsoft.com")
    @Epic("Unit tests")
    @Feature("Controllers unit tests")
    @Description("Test description")
    @DisplayName("Name of test: 'updateEmployeeTest'")
    //@ParameterizedTest(name = "{default_display_name}")
    @Test
    void updateEmployeeTest() {
        Allure.step("Проверка Rest-контроллера: операция 'updateEmployee(@RequestBody Employee employee)'",
                () -> {
                    Employee emp = new Employee("Art", "Kamorov", 300);
                    mockMvc
                            .perform(put("/api/employees")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(emp)))
                            .andExpect(status().is2xxSuccessful())
                            .andExpect(jsonPath("$.name").value("Art"))
                            .andExpect(jsonPath("$.surname").value("Kamorov"))
                            .andExpect(jsonPath("$.salary").value(300));
                    Mockito.verify(employeeService, Mockito.times(1)).saveEmployee(emp);
                });
    }

}
