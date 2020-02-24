package com.example.jdbcservlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Application {
    public static void main(String[] args) {

        String sql = "insert into subjects values(2, 'history')";
        try (Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/petproject-testing?user=root&password=root");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String subjectName = resultSet.getString("subject_name");
                System.out.println(id + " " + subjectName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
