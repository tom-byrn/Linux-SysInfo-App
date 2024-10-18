package com.project.block1project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloController {
    private Stage stage;

    public void setStage(Stage stage){
        this.stage = stage;
    }


    //Creating a change scene method
    @FXML
    private void changeScene(String fxmlFile){
        try{
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(fxmlFile)); //Load the fxml file given
            Scene scene = new Scene(fxmlloader.load(), 960, 540);

            //New Controller object, allows stages to be switched multiple times
            HelloController newController = fxmlloader.getController();
            if(newController != null){
                newController.setStage(stage);
            }

            stage.setTitle("System Information");
            stage.setScene(scene);
            stage.setMinWidth(960);
            stage.setMinHeight(577);
            stage.setMaxWidth(960);
            stage.setMaxHeight(577);

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onHomeButtonClick() {changeScene("home.fxml");}

    @FXML
    protected void onCPUButtonClick() {changeScene("cpu.fxml");}

    @FXML
    protected void onMemoryButtonClick() {changeScene("memory.fxml");}

    @FXML
    protected void onOperatingSystemButtonClick() {changeScene("operatingsystem.fxml");}

    @FXML
    protected void onPeripheralsButtonClick() {changeScene("peripherals.fxml");}

}