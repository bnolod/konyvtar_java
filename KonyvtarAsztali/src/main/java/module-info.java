module com.konyvtarasztali {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.konyvtarasztali to javafx.fxml;
    exports com.konyvtarasztali;
}