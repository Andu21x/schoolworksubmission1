module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    requires javafx.graphics;
    requires java.sql;
    requires log4j;
    requires google.cloud.bigquery;

    opens org.example to javafx.fxml;
    exports org.example;
}