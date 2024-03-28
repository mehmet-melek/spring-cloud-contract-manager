package com.melek.springcloudcontractmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class SpringCloudContractManagerApplication{

    public SpringCloudContractManagerApplication() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "password");

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));
        }

        resultSet.close();
        statement.close();
        connection.close();
    }

    public static void main(String[] args) {
        try {
            new SpringCloudContractManagerApplication();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SpringApplication.run(SpringCloudContractManagerApplication.class, args);
    }

}
