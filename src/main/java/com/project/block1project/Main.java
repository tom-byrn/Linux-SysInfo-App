package com.project.block1project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1728, 972);

        HelloController controller = fxmlLoader.getController();
        controller.setStage(primaryStage);

        primaryStage.setTitle("System Information");
        primaryStage.setScene(scene);

        primaryStage.setMinWidth(1728);
        primaryStage.setMinHeight(972);
        primaryStage.setMaxWidth(1728);
        primaryStage.setMaxHeight(972);

        primaryStage.show();

        controller.initializeHomePage();
    }

    public static void main(String[] args) {
        launch();
    }
}