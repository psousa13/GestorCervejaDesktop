package com.gestorcerveja.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL  = "jdbc:postgresql://localhost:5432/GestorCerveja";
    private static final String USER = "postgres";
    private static final String PASS = "8445";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}