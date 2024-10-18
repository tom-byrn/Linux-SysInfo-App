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
        Scene scene = new Scene(fxmlLoader.load(), 960, 577);

        HelloController controller = fxmlLoader.getController();
        controller.setStage(primaryStage);

        primaryStage.setTitle("System Information");
        primaryStage.setScene(scene);

        primaryStage.setMinWidth(960);
        primaryStage.setMinHeight(577);
        primaryStage.setMaxWidth(960);
        primaryStage.setMaxHeight(577);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}