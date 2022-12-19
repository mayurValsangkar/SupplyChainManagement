module com.example.supplychainmayur {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.supplychainmayur to javafx.fxml;
    exports com.example.supplychainmayur;
}