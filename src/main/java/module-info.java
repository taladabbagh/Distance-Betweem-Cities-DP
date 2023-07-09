module com.example.proj1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.proj1 to javafx.fxml;
    exports com.example.proj1;
}