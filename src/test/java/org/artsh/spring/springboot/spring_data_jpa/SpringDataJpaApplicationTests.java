package org.artsh.spring.springboot.spring_data_jpa;

import org.artsh.spring.springboot.spring_data_jpa.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SpringDataJpaApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(System.getProperty("os.name"));
    }


//    Connection connection;
//
//    @Test
//    void contextLoads() throws Exception {
//        if (connection == null) {
//            initConnection();
//        }
//        findAll();
//
//    }
//
//    public List<Employee> findAll() {
//        List<Employee> employees = new ArrayList<>();
//        try (PreparedStatement statement = connection.prepareStatement("select * from employees")) {
//            try (ResultSet resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    employees.add(new Employee(
//                            resultSet.getString("name"),
//                            resultSet.getString("surname"),
//                            resultSet.getString("department"),
//                            resultSet.getInt("salary")
//                    ));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return employees;
//    }
//
//
//    public void initConnection() throws Exception {
//        //Class.forName("org.postgresql.Driver");
//        Class.forName("org.postgresql.Driver");
//        String url = "jdbc:mysql://localhost:3306/my_db?useSSL=false";
//        String login = "bestuser";
//        String password = "bestuser";
//        connection = DriverManager.getConnection(url, login, password);
//    }


}
