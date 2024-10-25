package com.project.block1project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import oshi.SystemInfo;
import oshi.hardware.*;


import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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


    //FXML components from the cpu.fxml file & declare anything that needs to be public
    @FXML
    private Label labelCpuName;
    @FXML
    private Label labelCpuPhysicalCores;
    @FXML
    private Label labelCpuLogicalCores;
    @FXML
    private Label labelCpuMaxFrequency;
    @FXML
    private Label labelCpuTemperature;
    @FXML
    private Label labelCpuVoltage;
    @FXML
    private Label labelCpuMicroArchitecture;
    @FXML
    private Label labelFanSpeed;
    @FXML
    private Label labelCpuVendor;
    @FXML
    private LineChart<Number, Number> chartCpuUsage;
    public static XYChart.Series<Number, Number> series; // Declaring the series needed for the Line Chart



    //FXML Components for the memory page
    @FXML
    private Label labelRam;
    @FXML
    private Label labelTotalMemory;
    @FXML
    private Label labelAvailableMemory;
    @FXML
    private Label labelMemoryUsed;
    @FXML
    private Label labelMemorySpeed;


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

                case "memory.fxml":
                    HelloController memoryController = fxmlloader.getController();
                    memoryController.initializeMemoryPage();
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

        // Set the labels with values, ensuring they are not null
        if (labelOS != null) labelOS.setText("Operating System: " + OS);
        if (labelOSBit != null) labelOSBit.setText("OS Bit: " + OS_bit);
        if (labelOSVersion != null) labelOSVersion.setText("OS Version: " + OS_Version);
        if (labelOSArchitecture != null) labelOSArchitecture.setText("OS Architecture: " + OS_Architecture);
        if (labelCountry != null) {
            Locale locale = new Locale("en", country);

            String Country = locale.getDisplayCountry();

            labelCountry.setText("Country: " + Country);
        }
        if (labelLanguageAbbreviation != null){

            Locale locale = new Locale(LanguageAbbreviation);

            String Language = locale.getDisplayLanguage();

            labelLanguageAbbreviation.setText("Language: " + Language);
        }
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

        //Basic CPU Info
        String cpuName = processor.getProcessorIdentifier().getName();
        labelCpuName.setText("CPU: " + cpuName);

        double maxFrequency = processor.getMaxFreq() / 1e9;
        maxFrequency = Math.round(maxFrequency * 100.0) / 100.0;
        labelCpuMaxFrequency.setText("Max Frequency: " + maxFrequency + " GHz");

        int physicalCores = processor.getPhysicalProcessorCount();
        labelCpuPhysicalCores.setText("Physical Cores: " + physicalCores);

        int logicalCores = processor.getLogicalProcessorCount();
        labelCpuLogicalCores.setText("Logical Cores: " + logicalCores);

        double cpuTemperature = hal.getSensors().getCpuTemperature();
        if(cpuTemperature != 0){
        labelCpuTemperature.setText("CPU Temperature: " + cpuTemperature + " °C");
        }
        else{labelCpuTemperature.setText("CPU Temperature: N/A");}

        double cpuVoltage = hal.getSensors().getCpuVoltage();
        if(cpuVoltage != 0){
        labelCpuVoltage.setText("CPU Voltage: " + cpuVoltage + " V");
        }
        else{labelCpuVoltage.setText("CPU Voltage: N/A");}

        try {
            int[] fanSpeeds = hal.getSensors().getFanSpeeds();
            if (fanSpeeds.length > 0) {
                int fanSpeed = fanSpeeds[0];
                labelFanSpeed.setText(String.format("Fan Speed: %d RPM", fanSpeed));
            }
            else{labelFanSpeed.setText("Fan Speed: N/A");}
        } catch (Exception e) {
            // Handle Errors if oshi can't pull fan speeds from computer
            labelFanSpeed.setText("Fan Speed: N/A");
        }

        String microArchitecture = processor.getProcessorIdentifier().getMicroarchitecture();
        labelCpuMicroArchitecture.setText("Microrchitecture: " + microArchitecture);

        String vendorID = processor.getProcessorIdentifier().getVendor();
        labelCpuVendor.setText("Vendor: " + vendorID);


        // CPU Usage Chart
        NumberAxis CPUxAxis = new NumberAxis(0, 60, 1);  // Fixed range from 0 to 60
        NumberAxis CPUyAxis = new NumberAxis(0, 100, 10);  // Fixed range for CPU percentage

        CPUyAxis.setLabel("CPU Usage (%)");
        CPUxAxis.setLabel("Time (Seconds)");
        CPUxAxis.setAutoRanging(false);      // Disable auto-ranging for x-axis
        CPUyAxis.setAutoRanging(false);      // Disable auto-ranging for y-axis

        // Now apply the axes to the existing LineChart from FXML (chartCpuUsage)
        chartCpuUsage.getXAxis().setAutoRanging(false);  // Disable auto-ranging on the x-axis
        chartCpuUsage.getYAxis().setAutoRanging(false);  // Disable auto-ranging on the y-axis

        // Setting the bounds for X and Y axis after fetching them from chartCpuUsage
        NumberAxis xAxis = (NumberAxis) chartCpuUsage.getXAxis();
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(60);

        NumberAxis yAxis = (NumberAxis) chartCpuUsage.getYAxis();
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);

        // Creating a series to track CPU usage
        series = new XYChart.Series<>();
        chartCpuUsage.getData().add(series);

        // Run the method to update CPU usage
        CPU.runCpuUsageChart();



    }



    public void initializeMemoryPage() {
        System.out.println("Memory page initialize() is running");

        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        GlobalMemory memory = hal.getMemory();

        long totalMemory = memory.getTotal();
        long availableMemory = memory.getAvailable();

        if (labelTotalMemory != null) {
            labelTotalMemory.setText("Total Memory: " + totalMemory / (1024 * 1024) + " MB");
        }

        // Get the list of physical memory modules
        List<PhysicalMemory> physicalMemoryList = memory.getPhysicalMemory();
        if (!physicalMemoryList.isEmpty()) {
            // Get the speed of the first module (All modules should be the same speed)
            long memorySpeed = physicalMemoryList.get(0).getClockSpeed();
            if (labelMemorySpeed != null) {
                labelMemorySpeed.setText("Memory Speed: " + memorySpeed + " MHz");
            }
        } else {
            if (labelMemorySpeed != null) {
                labelMemorySpeed.setText("Memory Speed: Unknown");
            }
        }

        if (labelAvailableMemory != null) {
            labelAvailableMemory.setText("Available Memory: " + availableMemory / (1024 * 1024) + " MB");
        }
        if (labelMemoryUsed != null) {
            labelMemoryUsed.setText("Memory Used: " + (totalMemory - availableMemory) / (1024 * 1024) + " MB");
        }
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
