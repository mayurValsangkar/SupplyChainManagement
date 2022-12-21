package com.example.supplychainmayur;

public class Order {

    public static boolean placeOrder(String customerEmail, Product product) {

        DataBaseConnection dataBaseConnection = new DataBaseConnection();

        // If User bought something, entering data into database
        String query = String.format("INSERT INTO orders (customer_id, product_id) VALUES ((SELECT customer_id FROM customer WHERE email = '%s'), %s)", customerEmail, product.getId());

        int rowCount = 0;
        try{
            rowCount = dataBaseConnection.executeUpdate(query);

        }catch (Exception e) {
            e.printStackTrace();  // Exception handling
        }
        return rowCount!=0;
    }


}
