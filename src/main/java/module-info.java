module com.project.block1project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.github.oshi;
    requires bluecove;
    requires java.desktop;
    requires javafx.base;


    opens com.project.block1project to javafx.fxml;
    exports com.project.block1project;
}
