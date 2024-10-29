package com.project.block1project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OSProcess;
import oshi.util.FormatUtil;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;


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
    private Label labelKeyboard;
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
    @FXML
    private Label labelResolution;
    @FXML
    private Label labelDPI;
    @FXML
    private Label labelRefreshRate;


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
    public static XYChart.Series<Number, Number> series; // Declaring the series for the chart so it can be used in CPU class
    @FXML
    private Label labelCacheSizes;



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
    @FXML
    private PieChart memoryPieChart;


    //FXML components for the Operating System Page
    @FXML
    private Label labelOSName;
    @FXML
    private Label labelOSVer;
    @FXML
    private Label labelArchitecture;
    @FXML
    private Label labelProcessCount;
    @FXML
    private Label labelUptime;
    @FXML
    private Label labelKernelMode;
    @FXML
    private Label labelUserMode;
    @FXML
    private ListView<String> listProcessCount;





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

                case "operatingsystem.fxml":
                    HelloController osController = fxmlloader.getController();
                    osController.initializeOperatingSystemPage();
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
        String stringOS = System.getProperty("os.name");
        String osBit = System.getProperty("sun.arch.data.model");
        String osVersion = System.getProperty("os.version");
        String osArchitecture = System.getProperty("os.arch");
        String countryShort = System.getProperty("user.country");
        String languageAbbreviation = System.getProperty("user.language");
        String user = System.getProperty("user.name");
        String country = "N/A";
        String language = "N/A";

        //converts Language and country into their full names
        if(countryShort != null){
            Locale localeCountry = new Locale("en", countryShort);
            country = localeCountry.getDisplayCountry();
        }
        if(languageAbbreviation != null){
            Locale localeCountry = new Locale(languageAbbreviation);
            language = localeCountry.getDisplayLanguage();
        }
        // Set the labels with values, ensuring they are not null
        if (labelOS != null) labelOS.setText("Operating System: " + stringOS);
        if (labelOSBit != null) labelOSBit.setText("OS Bit: " + osBit);
        if (labelOSVersion != null) labelOSVersion.setText("Version: " + osVersion);
        if (labelOSArchitecture != null) labelOSArchitecture.setText("OS Architecture: " + osArchitecture);
        if (labelCountry != null) labelCountry.setText("Country: " + country);
        if (labelKeyboard != null) labelCountry.setText("Keyboard: " + country);
        if (labelLanguageAbbreviation != null) labelLanguageAbbreviation.setText("Language: " + language);
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
            labelBatteryTime.setText(String.format("Time Remaining:%n%d hours %n%d minutes", hours, minutes));
            labelBattery.setText(String.format("Battery: %.1f%%", batteryRemaining));
        }
        if (hours == 0 && minutes == 0) {
            labelBatteryTime.setText(String.format("Time Remaining:%n%nUnknown"));
        }


        // Using native Java methods to get screen devices
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = ge.getScreenDevices();

        for (GraphicsDevice device : devices) {
            Rectangle bounds = device.getDefaultConfiguration().getBounds();
            labelResolution.setText("Screen Width: " + bounds.width + ", Height: " + bounds.height);
        }

        int dpi = Toolkit.getDefaultToolkit().getScreenResolution();
        labelDPI.setText("Pixels Per Inch: " + dpi);

        GraphicsDevice gd = ge.getDefaultScreenDevice();
        int refreshRate = gd.getDisplayMode().getRefreshRate();
        labelRefreshRate.setText("Refresh Rate: " + refreshRate + " Hz");
    }

    //Initialize the CPU page
    public void initializeCPUPage() throws IOException {

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
        labelCpuLogicalCores.setText("Threads: " + logicalCores);

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
        labelCpuMicroArchitecture.setText("Microarchitecture: " + microArchitecture);

        String vendorID = processor.getProcessorIdentifier().getVendor();
        labelCpuVendor.setText("Vendor: " + vendorID);


        //For getting the cpu cache
        //checks if the os is linux
        if(System.getProperty("os.name").equals("Linux")) {

            //Creates ArrayLists
            ArrayList<String> cpuVulnerablitiesArrayList = new ArrayList<>(); //ArrayList for cpu
            // StringBuilder to accumulate results into one string
            StringBuilder cpuCacheWithSpace = new StringBuilder();
            StringBuilder cpuCache = new StringBuilder();

            //Initilizes variables
            int cachePerCoreInt = 0;
            String cpuCachePerCore = "";

            // Create a process to run the lscpu command
            Process lsCpu = Runtime.getRuntime().exec("lscpu");

            // Read the output from the command
            BufferedReader lsCpuReader = new BufferedReader(new InputStreamReader(lsCpu.getInputStream()));
            String cpuLineByLine;

            while ((cpuLineByLine = lsCpuReader.readLine()) != null) {

                //Removes excess whitespaces
                cpuLineByLine = cpuLineByLine.trim();

                //Checks if it starts with cache
                if(cpuLineByLine.contains("cache")) {
                    //removes unnessisary spaces and the d from one of the L1 caches and makes cache begin with a capital C
                    String cpuCacheByCache = cpuLineByLine.replaceAll("  ", "").replaceAll("d", "").replaceAll("cache", "Cache");
                    //this it to prevent duplication of L1
                    if(cpuCacheByCache.contains("L1 ") ||  cpuCacheByCache.contains("L2 ") || cpuCacheByCache.contains("L3 ")){
                        // Splits by using : and takes the second part
                        String cpuCacheMissingSpace = cpuCacheByCache.split(":")[1];
                        //checks if there is a space after the :
                        //Sometimes removing unnessisary spaces removes one of the nessisary ones after the colon
                        if(!cpuCacheMissingSpace.startsWith(" ")){
                            //Uses stringbuilder to add in the missing space
                            cpuCacheWithSpace.append(cpuCacheByCache.split(":")[0]).append(": ").append(cpuCacheMissingSpace);

                        }else{
                            cpuCacheWithSpace = new StringBuilder(cpuCacheByCache);
                        }

                        //Replace Total Cache with cache per core
                        // split based on spaces and take the third item which is the total cache size
                        int totalCacheSize = Integer.parseInt(String.valueOf(cpuCacheWithSpace).split(" ")[2]);

                        // split based on spaces and take the fifth item which is the number of times that cache is used and removes (
                        int numberOfCaches = Integer.parseInt((String.valueOf(cpuCacheWithSpace).split(" ")[4]).replaceAll("\\(", ""));

                        // Detects if the answer if in KiB or MiB
                        if(String.valueOf(cpuCacheWithSpace).contains("KiB")) {
                            //Divides the total cache size by the number of cores
                            cachePerCoreInt = totalCacheSize / numberOfCaches;
                            //Replaces the total cache size with the cache per core
                            cpuCachePerCore = String.valueOf(cpuCacheWithSpace).replaceAll(String.valueOf(totalCacheSize), String.valueOf(cachePerCoreInt));
                        } else if (String.valueOf(cpuCacheWithSpace).contains("MiB")) {
                            //Divides the total cache size by 1024 by the number of cores
                            cachePerCoreInt = (totalCacheSize * 1024) / numberOfCaches;
                            //Replaces the total cache size with the cache per core
                            cpuCachePerCore = String.valueOf(cpuCacheWithSpace).replaceAll("MiB", "KiB").replaceAll(String.valueOf(totalCacheSize), String.valueOf(cachePerCoreInt));
                        }
                        cpuCache.append(cpuCachePerCore + "\n");
                    }
                }
            }
            //cpuCache = String of all cpu caches
            labelCacheSizes.setText(String.format("%s", cpuCache));
        }else{
            labelCacheSizes.setText(String.format("%s%n", "Unknown CPU Cache Size", "Please Switch to Linux"));
        }


        // CPU Usage Chart
        NumberAxis CPUxAxis = new NumberAxis(0, 60, 5);  // Fixed range from 0 to 60 w/ space of 5 in between
        NumberAxis CPUyAxis = new NumberAxis(0, 100, 10);  // Fixed range for CPU percentage w/ space of 10 in between

        CPUyAxis.setLabel("CPU Usage (%)");
        CPUxAxis.setLabel("Time (Seconds)"); //This doesn't actually show on the graph because of how it is styled

        CPUxAxis.setAutoRanging(false);      // Disable auto-ranging for x-axis (For the number axis part)
        CPUyAxis.setAutoRanging(false);      // Disable auto-ranging for y-axis (For the number axis part)

        chartCpuUsage.getXAxis().setAutoRanging(false);  // Disable auto-ranging on the x-axis (For the actual x-axis of the chart)
        chartCpuUsage.getYAxis().setAutoRanging(false);  // Disable auto-ranging on the y-axis (For the actual y-axis of the chart)

        // Setting the bounds for X and Y axis after fetching them from chartCpuUsage
        NumberAxis xAxis = (NumberAxis) chartCpuUsage.getXAxis();
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(60);

        NumberAxis yAxis = (NumberAxis) chartCpuUsage.getYAxis();
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);

        // Creating a series to track CPU usage
        series = new XYChart.Series<>(); //A series is a collection of data points to be plotted on a chart in JavaFX (Similar to an ArrayList but specifically for JavaFX charts)
        chartCpuUsage.getData().add(series);

        // Run the method to update CPU usage (Defined in the CPU class)
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
            labelTotalMemory.setText("Total Memory: " + totalMemory / (1000 * 1000) + " MB");
        }

        // Get the list of physical memory modules
        List<PhysicalMemory> physicalMemoryList = memory.getPhysicalMemory();
        if (!physicalMemoryList.isEmpty()) {
            // Get the speed of the first module (All modules should be the same speed)
            long memorySpeed = physicalMemoryList.getFirst().getClockSpeed();
            if (labelMemorySpeed != null) {
                labelMemorySpeed.setText("Memory Speed: " + memorySpeed + " MHz");
            }
        } else {
            if (labelMemorySpeed != null) {
                labelMemorySpeed.setText("Memory Speed: Unknown");
            }
        }

        if (labelAvailableMemory != null) {
            labelAvailableMemory.setText("Available Memory: " + availableMemory / (1000 * 1000) + " MB");
        }
        if (labelMemoryUsed != null) {
            labelMemoryUsed.setText("Memory Used: " + (totalMemory - availableMemory) / (1000 * 1000) + " MB");

            // make pie chart data
            PieChart.Data usedData = new PieChart.Data("Used", (totalMemory - availableMemory));
            PieChart.Data availableData = new PieChart.Data("Free", availableMemory);
            // add data to the pie chart
            memoryPieChart.getData().addAll(usedData, availableData);
            // set title
            memoryPieChart.setTitle("Memory Usage");
        }
    }

    public void initializeOperatingSystemPage(){

        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor processor = hal.getProcessor();

        labelOSName.setText("Operating System: " + os.getFamily());

        labelOSVer.setText("Version: " + os.getVersionInfo().toString());

        labelArchitecture.setText("Architecture: " + os.getBitness() + "-bit");

        int processCount = os.getProcessCount();
        labelProcessCount.setText("Process Count: " + processCount);

        long upTime = os.getSystemUptime();
        long upTimeSeconds = upTime % 60;
        long upTimeMinutes = (upTime / 60) % 60;
        long upTimeHours = (upTime / 3600);
        labelUptime.setText(String.format("Up Time: %02d:%02d:%02d", upTimeHours, upTimeMinutes, upTimeSeconds)); //Formatted like Hours:Minutes:Seconds where each is 2 digits

            OSProcess[] processes = os.getProcesses().toArray(new OSProcess[0]);
            if (processes.length > 0) {
                OSProcess process = processes[0];

                long[] ticks = processor.getSystemCpuLoadTicks();
                long userModeTime = ticks[CentralProcessor.TickType.USER.getIndex()];
                long kernelModeTime = ticks[CentralProcessor.TickType.SYSTEM.getIndex()]; // SYSTEM refers to kernel mode

                // Convert time from ticks (milliseconds) to readable format
                long totalUserModeTimeInSeconds = userModeTime / processor.getLogicalProcessorCount();
                long totalKernelModeTimeInSeconds = kernelModeTime / processor.getLogicalProcessorCount();

                labelKernelMode.setText("Kernel Mode Time: " + FormatUtil.formatElapsedSecs(totalKernelModeTimeInSeconds));
                labelUserMode.setText("User Mode Time: " + FormatUtil.formatElapsedSecs(totalUserModeTimeInSeconds));
            }
            else {
                labelKernelMode.setText("Kernel Mode Time: N/A");
                labelUserMode.setText("User Mode Time: N/A");
            }

            ObservableList<String> processInfoList = FXCollections.observableArrayList();
            for(int i = 0; i < processCount; i++){

                OSProcess process = processes[i];

                int id = process.getProcessID();
                String name = process.getName();
                long memory = process.getResidentSetSize();

                String processInfo = String.format("ID:%d, Name: %s, Memory: %d KB", id, name, memory / 1000);
                processInfoList.add(processInfo);
            }
            listProcessCount.setItems(processInfoList);
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
