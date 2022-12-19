package com.example.supplychainmayur;

import java.sql.*;

public class DataBaseConnection {

    private static final String databaseUrl = "jdbc:mysql://localhost:3306/supply_chain_dec";
    private static final String userName = "root";
    private static final String password = "S@vew0rld";

    // Getting statement from database
    public Statement getStatement() {

        Statement statement = null;
        Connection conn;
        try {
            conn = DriverManager.getConnection(databaseUrl, userName, password);
            statement = conn.createStatement();
        }catch (Exception e) {
            e.printStackTrace(); //Exception handling
        }
        return statement;
    }

    // Fetching info from database
    public ResultSet getQueryTable(String query) {
        Statement statement = getStatement();
        try {
            return statement.executeQuery(query);
        }catch (Exception e) {
            e.printStackTrace(); // Exception handling
        }
        return null;
    }

    // Update database
    public int executeUpdate(String query) {
        Statement statement = getStatement();
        try {
            return statement.executeUpdate(query);
        }catch (Exception e) {
            e.printStackTrace(); // Exception handling
        }
        return 0;
    }

    public static void main(String[] args) {

        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        ResultSet rs = dataBaseConnection.getQueryTable("select email, first_name from customer");

        try {
            while (rs.next()) {
                System.out.println(rs.getString("email") + " " + rs.getString("first_name"));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Exeption handling
        }
    }
}
