package com.example.supplychainmayur;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;

public class SupplyChain extends Application {

    // Initializing global variable width, height and header bar
    public static final int width = 700, height = 500, headerBar = 60;

    // Initializing new frame
    Pane bodyPane = new Pane();

    // Initializing login object of class Login
    Login login = new Login();

    // Initializing signUp object of class SignUp
    SignUp signUp = new SignUp();

    // Initializing productDetails object of class ProductDetails
    ProductDetails productDetails = new ProductDetails();

    // Declaring globalLoginButton
    Button globalLoginButton;

    // Declaring getGlobalSignUpButton
    Button getGlobalSignUpButton;

    // Declaring globalSignUpLabel as null
    Label globalSignUpLabel = null;

    // Declaring customerEmailLabel as null
    Label customerEmailLabel = null;

    // Declaring footerBarMessageLabel as null
    Label footerBarMessageLabel = null;

    // Declaring cartMessageLabel as null
    Label cartMessageLabel = new Label("Your Cart is Empty :(");

    // Declaring string customerEmail
    String customerEmail = "";

    // Declaring variable globalLogin as false
    boolean globalLogin = false;

    // Declaring variable inCart as false
    boolean inCart = false;

    // Creating header bar ---->
    private GridPane headerBar() {

        // Initializing TextField and Button
        TextField searchText = new TextField();
        Button searchButton = new Button("Search");
        Button homePageButton = new Button("Home Page");

        // Event handling for searchButton
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String productName = searchText.getText();

                // clear body and put this new pane in the body
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productDetails.getProductsByName(productName));
                globalLoginButton.setVisible(true);
            }
        });

        // Initializing globalSignUpLabel
        globalSignUpLabel = new Label("New User ?");

        // Initializing globalLoginButton
        globalLoginButton = new Button("Log In");

        // Event handling for globalLoginButton
        globalLoginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                // If User have not Logged-in
                if(globalLogin == false){

                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(loginPage());
                    globalLoginButton.setVisible(false);
                    getGlobalSignUpButton.setVisible(true);
                    globalSignUpLabel.setText("New User ?");
                }
                // If User have Logged-in
                else if(globalLogin){

                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().addAll(productDetails.getAllProducts());
                    globalSignUpLabel.setText("New User ?");
                    getGlobalSignUpButton.setDisable(false);
                    getGlobalSignUpButton.setVisible(true);
                    customerEmailLabel.setText("Welcome User !!");
                    globalLoginButton.setText("Log In");
                    globalLoginButton.setVisible(true);
                    footerBarMessageLabel.setText("");
                    globalLogin = false;
                    footerBarMessageLabel.setText("");
                    customerEmail = "";
                }
            }
        });

        // Initializing globalSignUpButton
        getGlobalSignUpButton = new Button("Sign Up");

        // Event handling for globalSignUpButton
        getGlobalSignUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(signUpPage());
                getGlobalSignUpButton.setVisible(false);
                globalLoginButton.setVisible(true);
                globalSignUpLabel.setText("Create your Account");
            }
        });

        // Event handling for homePage
        homePageButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productDetails.getAllProducts());
                globalLoginButton.setVisible(true);
                if(globalLogin==false){
                    getGlobalSignUpButton.setVisible(true);
                    globalSignUpLabel.setText("New User ?");
                    customerEmail = "";
                    customerEmailLabel.setText("Welcome User !!");
                    globalLoginButton.setText("Log In");
                }
                footerBarMessageLabel.setText("");
            }
        });

        // Initializing customerEmailLabel
        customerEmailLabel = new Label("Welcome User !!");

        // Creating new pane, setting its dimension, alignment and style
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), headerBar-10);
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setStyle("-fx-background-color: #87CEEB");
        gridPane.setAlignment(Pos.CENTER_LEFT);

        // Adding Label, Button, TextField in pane
        gridPane.add(searchText, 1, 1);
        gridPane.add(searchButton, 2, 1);
        gridPane.add(globalLoginButton, 3, 1);
        gridPane.add(customerEmailLabel, 4, 1);
        gridPane.add(homePageButton, 25, 1);
        gridPane.add(getGlobalSignUpButton, 30, 1);
        gridPane.add(globalSignUpLabel, 30, 2);

        return gridPane;
    }

    // creating footer bar ---->
    private GridPane footerBar(){

        // Initializing addToCart, buyNow button and viewCart
        Button addToCart = new Button("Add to Cart");
        Button buyNow = new Button("Buy Now");
        Button viewCart = new Button("View Cart");

        // Initializing footerBarMessageLabel
        footerBarMessageLabel = new Label("");

        // Even handling for buyNow
        buyNow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    // If User have not Logged-in
                    if(customerEmail.isEmpty()){
                        footerBarMessageLabel.setTextFill(Color.RED);
                        footerBarMessageLabel.setText("Please Login Into your Account");
                    }
                    // If User have Logged-in
                    else {
                        Product selectedProduct = productDetails.getSelectedProduct();
                        // If User does not have selected any item
                        if(selectedProduct==null){
                            footerBarMessageLabel.setTextFill(Color.RED);
                            footerBarMessageLabel.setText("Please Select any Item to Proceed");
                        }
                        // If User selected any item to buy
                        else if (Order.placeOrder(customerEmail, selectedProduct)){
                            footerBarMessageLabel.setTextFill(Color.GREEN);
                            footerBarMessageLabel.setText("Order Placed :)");
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();  // Exception Handling
                }
            }
        });

        // Event handling for addToCart
        addToCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    // If User have not Logged-in
                    if(customerEmail.isEmpty()){
                        footerBarMessageLabel.setTextFill(Color.RED);
                        footerBarMessageLabel.setText("Please Login Into your Account");
                    }
                    // If User have Logged-in
                    else{
//                        Product selectedProduct = productDetails.getSelectedProduct();
//                        GridPane pane = userCart();
//                        pane.add(selectedProduct);
                        footerBarMessageLabel.setTextFill(Color.GREEN);
                        footerBarMessageLabel.setText("Item Added to your Cart");
                    }
                }catch (Exception e){
                    e.printStackTrace(); // Exception handling
                }
            }
        });

        // Event handling for viewCart
        viewCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    // If User have not Logged-in
                    if(customerEmail.isEmpty()){
                        footerBarMessageLabel.setTextFill(Color.RED);
                        footerBarMessageLabel.setText("Please Login Into your Account");
                    }
                    // If User have Logged-in
                    else{
                        bodyPane.getChildren().clear();
                        bodyPane.getChildren().add(userCart());
                        inCart = true;
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        // Creating new pane, setting its dimension, alignment and style
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), headerBar);
        gridPane.setVgap(5);
        gridPane.setHgap(40);
        gridPane.setTranslateY(height-headerBar+50);
        gridPane.setStyle("-fx-background-color : #87CEEB");
        gridPane.setAlignment(Pos.CENTER_LEFT);

        // Adding Label, Button in pane
        gridPane.add(addToCart, 1,1);
        gridPane.add(buyNow, 3,1);
        gridPane.add(viewCart, 2,1);
        gridPane.add(footerBarMessageLabel,4,1);

        return gridPane;
    }

    // Creating cart
    private GridPane userCart() {

        // Creating new pane, setting its dimension, alignment and style
        GridPane cartPane = new GridPane();
        cartPane.setMinSize(bodyPane.getMinWidth(), bodyPane.getMinHeight());
        cartPane.setVgap(5);
        cartPane.setHgap(5);
        //gridPane.setStyle("-fx-background-color: #C0C0C0");
        cartPane.setAlignment(Pos.CENTER);


        cartPane.add(cartMessageLabel, 0,0);

        return cartPane;
    }

    // Creating login page ---->
    private GridPane loginPage() {

        // Initialize Label
        Label emailLabel = new Label("Email");
        Label passwordLabel = new Label("Password");
        Label messageLabel = new Label("Please Enter Your Details");

        // Initialize TextField
        TextField emailTextField = new TextField();
        PasswordField passwordField = new PasswordField();

        // Initialize loginButton
        Button loginButton = new Button("Log In");

        // Event handling for loginButton
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Getting email and password entered in TextField
                String email = emailTextField.getText();
                String password = passwordField.getText();

                // If email and password entered is correct
                if (login.customerLogin(email, password)) {
                    messageLabel.setTextFill(Color.GREEN);
                    messageLabel.setText(("Login Successful"));
                    customerEmail = email;
                    //globalLoginButton.setVisible(false);
                    globalLoginButton.setText("Log Out");
                    customerEmailLabel.setText("Welcome : " + customerEmail);
                    footerBarMessageLabel.setText("");
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(productDetails.getAllProducts());
                    getGlobalSignUpButton.setVisible(false);
                    globalLoginButton.setVisible(true);
                    globalSignUpLabel.setText("");
                    globalLogin = true;
                }
                // If email and password entered is not correct
                else {
                    messageLabel.setTextFill(Color.RED);
                    messageLabel.setText("Login Failed");
                }
            }
        });

        // Creating new pane, setting its dimension, alignment and style
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), bodyPane.getMinHeight());
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        //gridPane.setStyle("-fx-background-color: #A9A9A9");
        gridPane.setAlignment(Pos.CENTER);


        // Adding Label, TextField and Button in grid pane
        gridPane.add(emailLabel, 0, 0);   // first is x co-ordinate and second is y.
        gridPane.add(emailTextField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 0, 2);
        gridPane.add(messageLabel, 1, 2);

        return gridPane;
    }

    // Creating sign up page ---->
    private GridPane signUpPage() {

        // Initializing Label
        Label firstNameLabel = new Label("First Name");
        Label lastNameLabel = new Label("Last Name");
        Label addressLabel = new Label("Address");
        Label mobileNoLabel = new Label("Mobile No.");
        Label emailLabel = new Label("Email");
        Label passwordLabel = new Label("Password");
        Label messageLabel = new Label("Please Enter Your Details");


        // Initializing TextField
        TextField firstNameTextField = new TextField();
        TextField lastNameTextField = new TextField();
        TextField addressTextField = new TextField();
        TextField mobileNoTextField = new TextField();
        TextField emailTextField = new TextField();
        PasswordField passwordField = new PasswordField();

        // Initializing signUpButton
        Button signUpButton = new Button("Sign Up");

        // Event handling for signUpButton
        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Collecting all the data that user have entered
                String firstName = firstNameTextField.getText();
                String lastName = lastNameTextField.getText();
                String address = addressTextField.getText();
                String mobileNo = mobileNoTextField.getText();
                String email = emailTextField.getText();
                String password = passwordField.getText();

                // If user not entered first name
                if(firstName.isBlank() || firstName.isEmpty()) {
                    messageLabel.setTextFill(Color.RED);
                    messageLabel.setText("Please Enter your First Name");
                }
                // If user not entered last name
                else if(lastName.isBlank() || lastName.isEmpty()){
                    messageLabel.setTextFill(Color.RED);
                    messageLabel.setText("Please Enter Your Last Name");
                }
                // If user not entered mobile number or mobile number is less than 10 digit
                else if(mobileNo.isBlank() || mobileNo.isEmpty() || mobileNo.length()<10){
                    messageLabel.setTextFill(Color.RED);
                    messageLabel.setText("Please Enter Valid Mobile Number");
                }
                // If user not entered email id
                else if(email.isBlank() || email.isEmpty()){
                    messageLabel.setTextFill(Color.RED);
                    messageLabel.setText("Please Enter your Email ID");
                }
                // If user not entered password
                else if (password.isEmpty() || password.isBlank()) {
                    messageLabel.setTextFill(Color.RED);
                    messageLabel.setText("Password can't be Empty");
                }
                // If email id already exits in database
                else if(signUp.customerSignUp(email)){
                    messageLabel.setTextFill(Color.RED);
                    messageLabel.setText(("Email Already Exist"));

                }else {
                    signUp.newSignUp(firstName, lastName, address, mobileNo, email, password);
                    customerEmail = email;
                    getGlobalSignUpButton.setDisable(true);
                    customerEmailLabel.setText("Welcome : " + customerEmail);
                    footerBarMessageLabel.setText("");
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(productDetails.getAllProducts());
                    //globalLoginButton.setVisible(false);
                    globalLoginButton.setText("Log Out");
                    globalSignUpLabel.setText("");
                    globalLogin = true;
                }
            }
        });

        // Creating new pane, setting its dimension, alignment and style
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), bodyPane.getMinHeight());
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        //gridPane.setStyle("-fx-background-color: #C0C0C0");
        gridPane.setAlignment(Pos.CENTER);

        // Adding Label, TextField and Button in pane
        gridPane.add(firstNameLabel, 0, 0);   // first is x co-ordinate and second is y.
        gridPane.add(firstNameTextField, 1, 0);
        gridPane.add(lastNameLabel, 0, 1);
        gridPane.add(lastNameTextField, 1, 1);
        gridPane.add(addressLabel, 0, 2);
        gridPane.add(addressTextField, 1, 2);
        gridPane.add(mobileNoLabel, 0,3);
        gridPane.add(mobileNoTextField,1,3);
        gridPane.add(emailLabel,0,4);
        gridPane.add(emailTextField,1,4);
        gridPane.add(passwordLabel, 0, 5);
        gridPane.add(passwordField, 1, 5);
        gridPane.add(signUpButton, 0, 6);
        gridPane.add(messageLabel, 1, 6);

        return gridPane;
    }

    private Pane createContent() {

        // Initialize frame
        Pane root = new Pane();
        // Setting Dimension of root pane and body pane
        root.setPrefSize(width, height+headerBar);
        bodyPane.setMinSize(width, height);
        bodyPane.setTranslateY(headerBar);


        // Adding all products in body pane
        bodyPane.getChildren().addAll(productDetails.getAllProducts());

        // Adding headerBar, footerBar and body pane in root
        root.getChildren().addAll(headerBar(), bodyPane, footerBar());

        return root;
    }

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}