module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires org.junit.jupiter.api;
    opens GUI to javafx.fxml;
    exports GUI;
}