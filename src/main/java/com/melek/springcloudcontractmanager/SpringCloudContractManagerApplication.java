package com.melek.springcloudcontractmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class SpringCloudContractManagerApplication{

    private static int counter = 0;

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

    public static void threadCode() {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter++;
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter++;
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Counter: " + counter);
    }

    public static void main(String[] args) {
        threadCode();
        try {
            new SpringCloudContractManagerApplication();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SpringApplication.run(SpringCloudContractManagerApplication.class, args);
    }

}
