package com.project.block1project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollBar;
import javafx.stage.Stage;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PowerSource;


import java.io.IOException;

public class HelloController {
    private Stage stage;

    // FXML components from the home.fxml file
    @FXML
    private Label labelOS;
    @FXML
    private Label labelOSBit;
    @FXML
    private Label labelOSVersion;
    @FXML
    private Label labelOSArchitecture;
    @FXML
    private Label labelCountry;
    @FXML
    private Label labelLanguageAbbreviation;
    @FXML
    private Label labelUser;
    @FXML
    private ProgressBar batteryBar;
    @FXML
    private Label labelBattery;
    @FXML
    private Label labelBatteryTime;

    //FXML components from the cpu.fcml file
    @FXML
    private Label labelCpuName;
    @FXML
    private Label labelCpuPhysicalCores;
    @FXML
    private Label labelCpuLogicalCores;
    @FXML
    private Label labelCpuMaxFrequency;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Creating a change scene method
    @FXML
    private void changeScene(String fxmlFile) {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(fxmlFile)); // Load the FXML file given
            Scene scene = new Scene(fxmlloader.load(), 1728, 972);

            // Switch case for running fxml files
            switch (fxmlFile) {
                case "home.fxml":
                    HelloController homeController = fxmlloader.getController();
                    homeController.initializeHomePage();
                    break;

                case "cpu.fxml":
                    HelloController cpuController = fxmlloader.getController();
                    cpuController.initializeCPUPage();
                    break;

                default:
                    // Gives an error if there's no matching fxml file
                    System.out.println("No matching FXML file found.");
                    break;
            }


            // New Controller object, allows stages to be switched multiple times
            HelloController newController = fxmlloader.getController();
            if (newController != null) {
                newController.setStage(stage);
            }

            stage.setTitle("System Information");
            stage.setScene(scene);
            stage.setMinWidth(1728);
            stage.setMinHeight(972);
            stage.setMaxWidth(1728);
            stage.setMaxHeight(972);

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Initialize the Home page (moved functionality from Home.java)
    @FXML
    public void initializeHomePage() {
        System.out.println("Home page initialize() is running");

        // Get system properties
        String OS = System.getProperty("os.name");
        String OS_bit = System.getProperty("sun.arch.data.model");
        String OS_Version = System.getProperty("os.version");
        String OS_Architecture = System.getProperty("os.arch");
        String country = System.getProperty("user.country");
        String LanguageAbbreviation = System.getProperty("user.language");
        String user = System.getProperty("user.name");

        // Print statements to test if this is being run
        System.out.println("OS: " + OS);
        System.out.println("OS Bit: " + OS_bit);

        // Set the labels with values, ensuring they are not null
        if (labelOS != null) labelOS.setText("Operating System: " + OS);
        if (labelOSBit != null) labelOSBit.setText("OS Bit: " + OS_bit);
        if (labelOSVersion != null) labelOSVersion.setText("OS Version: " + OS_Version);
        if (labelOSArchitecture != null) labelOSArchitecture.setText("OS Architecture: " + OS_Architecture);
        if (labelCountry != null) labelCountry.setText("Keyboard Layout: " + country);
        if (labelLanguageAbbreviation != null) labelLanguageAbbreviation.setText("Language: " + LanguageAbbreviation);
        if (labelUser != null) labelUser.setText("User: " + user);

        //Battery Info
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        PowerSource[] powerSources = hal.getPowerSources().toArray(new PowerSource[0]);
        PowerSource battery = powerSources[0];

        double batteryRemaining = battery.getRemainingCapacityPercent() * 100;  // Battery remaining percentage
        double batteryTimeRemaining = battery.getTimeRemainingInstant();  // Time remaining in seconds

        batteryBar.setProgress(batteryRemaining / 100.0);

        // Convert time remaining to minutes and hours
        long hours = (long) (batteryTimeRemaining / 3600);
        long minutes = (long) ((batteryTimeRemaining % 3600) / 60);

        // Display battery information
        if (labelBattery != null) {
            labelBatteryTime.setText(String.format("Time left: %d hours %d minutes", hours, minutes));
            labelBattery.setText(String.format("Battery: %.1f%%", batteryRemaining));
        }
        if(hours == 0 && minutes == 0){
            labelBatteryTime.setText("Time Left: Unknown");
        }

    }

    //Initialize the CPU page
    public void initializeCPUPage(){

        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor processor = hal.getProcessor();

        String cpuName = processor.getProcessorIdentifier().getName();
        labelCpuName.setText("CPU: " + cpuName);

    }

    @FXML
    protected void onHomeButtonClick() {
        changeScene("home.fxml");
    }

    @FXML
    protected void onCPUButtonClick() {
        changeScene("cpu.fxml");
    }

    @FXML
    protected void onMemoryButtonClick() {
        changeScene("memory.fxml");
    }

    @FXML
    protected void onOperatingSystemButtonClick() {
        changeScene("operatingsystem.fxml");
    }

    @FXML
    protected void onPeripheralsButtonClick() {
        changeScene("peripherals.fxml");
    }


}
