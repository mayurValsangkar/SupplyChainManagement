package com.example.supplychainmayur;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;

public class Login {

    // Hashing password
    private byte[] getSHA(String input) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            return messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
        }catch (Exception e) {
            e.printStackTrace(); // Exception handling
        }
        return null;
    }

    // Encrypting password
    private String getEncryptedPassword(String password) {

        try {
            BigInteger number = new BigInteger(1, getSHA(password));
            // Convert number to string in hexadecimal format
            StringBuilder hexString = new StringBuilder(number.toString(16));
            return hexString.toString();
        }catch (Exception e) {
            e.printStackTrace(); // Exception handling
        }
        return null;
    }

    // Checking if user is member or not
    public boolean customerLogin(String email, String password) {

        String query = String.format("SELECT * FROM customer WHERE email = '%s' AND password = '%s'", email, password);
        try {
            DataBaseConnection dbCon = new DataBaseConnection();
            ResultSet rs = dbCon.getQueryTable(query);

            if(rs != null && rs.next()) {
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace(); // Exception handling
        }
        return false;
    }

    public static void main(String[] args) {
        Login login = new Login();

    }
}


