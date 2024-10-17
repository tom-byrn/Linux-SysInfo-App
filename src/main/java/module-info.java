module com.project.block1project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.project.block1project to javafx.fxml;
    exports com.project.block1project;
}