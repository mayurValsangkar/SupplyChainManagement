package com.example.supplychainmayur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class ProductDetails {

   public TableView<Product> productTable;

   public Pane getAllProducts() {

       TableColumn id = new TableColumn("id");
       id.setCellValueFactory(new PropertyValueFactory<>("id"));

       TableColumn name = new TableColumn("Name");
       name.setCellValueFactory(new PropertyValueFactory<>("name"));

       TableColumn price = new TableColumn("Price");
       price.setCellValueFactory(new PropertyValueFactory<>("price"));

       ObservableList<Product> products = Product.getAllProducts();

       productTable = new TableView<>();
       productTable.setItems(products);
       productTable.getColumns().addAll(id, name, price);
       productTable.setMinSize(SupplyChain.width, SupplyChain.height);

       productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       Pane tablePane = new Pane();
       //gridPane.setStyle("-fx-background-color: #C0C0C0");
       tablePane.setMinSize(SupplyChain.width, SupplyChain.height);
       tablePane.getChildren().addAll(productTable);

       return tablePane;
   }

    public Pane getProductsByName(String productName) {

        TableColumn id = new TableColumn("id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));


        ObservableList<Product> products = Product.getProductsByName(productName);

        productTable = new TableView<>();
        productTable.setItems(products);
        productTable.getColumns().addAll(id, name, price);
        productTable.setMinSize(SupplyChain.width, SupplyChain.height);

        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Pane tablePane = new Pane();
        //gridPane.setStyle("-fx-background-color: #C0C0C0");
        tablePane.setMinSize(SupplyChain.width, SupplyChain.height);
        tablePane.getChildren().addAll(productTable);

        return tablePane;
    }

    // Getting selected product by user
    public Product getSelectedProduct(){
       try{
           Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
           return selectedProduct;
       }catch (Exception e){
           e.printStackTrace();
       }

       return null;
    }
}
