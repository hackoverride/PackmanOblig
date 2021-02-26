module com.classroom.packman {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.classroom.packman to javafx.fxml;
    exports com.classroom.packman;
}
