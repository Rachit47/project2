package com.springbootproject.librarium365.config;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DBConnectionConfig {

    private static final String URL = "jdbc:mysql://localhost:3306/libraryDB";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database Connection successful.");
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver is missing.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Connection failure: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnectionStatic() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Static database connection successfull.");
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver is missing.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Static connection failure occured " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}