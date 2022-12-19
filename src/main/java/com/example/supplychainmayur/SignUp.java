package com.example.supplychainmayur;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;

public class SignUp {

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

    // Signing up new user and entering user data into database
    public void newSignUp(String firstName, String lastName, String address, String mobileNo, String email, String password) {

        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        String query = String.format("INSERT INTO customer (first_name, last_name, address, mobile, email, password) values('"+firstName+"', '"+lastName+"', '"+address+"', '"+mobileNo+"', '"+email+"', '"+password+"')");

        int rowCount = 0;
        try{
            rowCount = dataBaseConnection.executeUpdate(query);

        }catch (Exception e) {
            e.printStackTrace(); // Exception handling
        }
    }

    public boolean customerSignUp(String email) {

        // Checking if email id already exits in database
        String query = String.format("SELECT * FROM customer WHERE email = '%s'", email);
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
        Login signUp = new Login();
    }
}

