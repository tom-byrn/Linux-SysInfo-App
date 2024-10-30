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
import java.awt.im.InputContext;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


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
    private Label labelEndian;
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
    //Battery Stuff
    @FXML
    private Label labelBatteryVendor;
    @FXML
    private Label labelBatterySize;
    @FXML
    private Label labelPowerDraw;
    @FXML
    private Label labelTimeRemaining;
    @FXML
    private Label labelBatteryCharge;
    @FXML
    private Label labelBatteryVoltage;
    @FXML
    private Label labelTemperature;
    @FXML
    private Label labelLinkSpeed;
    @FXML
    private Label labelInterfaceName;
    @FXML
    private Label labelMacAddress;


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
    @FXML
    private ListView<String> listCPUVulnerabilities;



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
    @FXML
    private Label labelUsedSwapMemory;
    @FXML
    private Label labelDiskModel;
    @FXML
    private Label labelDiskSize;


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

    //FXML components for Peripherals Page
    @FXML
    private ListView<String> listPcie;
    @FXML
    private Label labelDevicesAmount;
    @FXML
    private Label labelTotalFunctions;
    @FXML
    private Label labelBusesAmount;
    @FXML
    private Label labelDevicesPerBus;
    @FXML
    private Label labelFunctionsPerBus;
    @FXML
    private Label labelFunctionsPerDevice;
    @FXML
    private Label labelDevicesAmountUsb;
    @FXML
    private Label labelTotalFunctionsUsb;
    @FXML
    private Label labelDevicesPerBusUsb;
    @FXML
    private Label labelBusesAmountUsb;
    @FXML
    private ListView<String> listUSB;







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

                case "cpu2.fxml":
                    HelloController cpu2Controller = fxmlloader.getController();
                    cpu2Controller.initializeCPU2Page();
                    break;

                case "memory.fxml":
                    HelloController memoryController = fxmlloader.getController();
                    memoryController.initializeMemoryPage();
                    break;

                case "operatingsystem.fxml":
                    HelloController osController = fxmlloader.getController();
                    osController.initializeOperatingSystemPage();
                    break;

                case "peripherals.fxml":
                    HelloController peripheralsController = fxmlloader.getController();
                    peripheralsController.initializePeripheralsPage();

                case "peripherals2.fxml":
                    HelloController peripheralsUSBController = fxmlloader.getController();
                    peripheralsUSBController.initializePeripheralsUSBPage();


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
    public void initializeHomePage() throws IOException {
        System.out.println("Home page initialize() is running");

        // Get system properties
        String stringOS = System.getProperty("os.name");
        String osBit = System.getProperty("sun.arch.data.model");
        String osVersion = System.getProperty("os.version");
        String osArchitecture = System.getProperty("os.arch");
        String countryShort = System.getProperty("user.country");
        String languageAbbreviation = System.getProperty("user.language");
        String endian = System.getProperty("sun.cpu.endian");
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
        //Gets Keyboard layout
        InputContext keyboard = InputContext.getInstance();

        // Set the labels with values, ensuring they are not null
        if (labelOS != null) labelOS.setText("Kernal: " + stringOS);
        if (labelOSBit != null) labelOSBit.setText("Bits: " + osBit +  "-bit");
        if (labelOSVersion != null) labelOSVersion.setText("Version: " + osVersion);
        if (labelOSArchitecture != null) labelOSArchitecture.setText("Architecture: " + osArchitecture);
        if (labelEndian != null) labelEndian.setText("Endian: " + endian + " endian");
        if (labelCountry != null) labelCountry.setText("Country: " + country);
        if (labelKeyboard != null) labelKeyboard.setText("Keyboard: " + keyboard.getLocale());
        if (labelLanguageAbbreviation != null) labelLanguageAbbreviation.setText("Language: " + language);
        if (labelUser != null) labelUser.setText("User: " + user);

        //Battery Info
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        PowerSource[] powerSources = hal.getPowerSources().toArray(new PowerSource[0]);
        PowerSource battery = powerSources[0];
        String batteryVendor = "";
        String batterySize = "";
        String powerDraw = "";
        String timeRemaining = "";
        String batteryCharge = "";
        String batteryVoltage = "";
        String temperature = "";


        // Execute the command to get battery information from system using upower
        Process upowerCommand = Runtime.getRuntime().exec("upower -i /org/freedesktop/UPower/devices/battery_BAT1");
        // Reads the battery power info
        BufferedReader readUpower = new BufferedReader(new InputStreamReader(upowerCommand.getInputStream()));
        String upowerLineByLine;

        // Read the output
        while ((upowerLineByLine = readUpower.readLine()) != null) {
            upowerLineByLine.trim();
            if(upowerLineByLine.contains("vendor")) {
                batteryVendor = upowerLineByLine.replaceAll(" ", "").replaceAll("vendor:", "");
                batteryVendor = "Vendor: " + batteryVendor;
                labelBatteryVendor.setText(batteryVendor);
                System.out.println(batteryVendor);
            }
            if(upowerLineByLine.contains("energy-full") && !upowerLineByLine.contains("energy-full-design")){
                batterySize = upowerLineByLine.replaceAll(" ", "").replaceAll("Wh","").replaceAll("energy-full:", "");
                batterySize = "Battery Capacity: "+batterySize + " Wh";
                labelBatterySize.setText(batterySize);
                System.out.println(batterySize);
            }
            if(upowerLineByLine.contains("energy-rate")){
                powerDraw = upowerLineByLine.replaceAll(" ", "").replaceAll("W","").replaceAll("energy-rate:", "");;
                powerDraw = "Current Power Draw: " + powerDraw + " W";
                labelPowerDraw.setText(powerDraw);
                System.out.println(powerDraw);
            }
            if(upowerLineByLine.contains("time to empty")){
                timeRemaining = upowerLineByLine.replaceAll("  ", "").replaceAll(" time to empty:", "").replaceAll("time to empty:", "");
                timeRemaining = "Time Remaining: " + timeRemaining;
                labelTimeRemaining.setText(timeRemaining);
                System.out.println(timeRemaining);
            }

            if(upowerLineByLine.contains("energy:")) {
                batteryCharge = upowerLineByLine.replaceAll(" ", "").replaceAll("energy:", "").replaceAll("Wh","");
                batteryCharge = "Battery Charge: " + batteryCharge + " Wh";
                labelBatteryCharge.setText(batteryCharge);
                System.out.println(batteryCharge);
            }
            if(upowerLineByLine.contains("voltage:")) {
                batteryVoltage = upowerLineByLine.replaceAll(" ", "").replaceAll("voltage:", "").replaceAll("V","");
                batteryVoltage = "Battery Voltage: " + batteryVoltage + " V";
                labelBatteryVoltage.setText(batteryVoltage);
                System.out.println(batteryVoltage);
            }
            if(upowerLineByLine.contains("temperature")) {
                temperature = upowerLineByLine.replaceAll(" ", "").replaceAll("temperature:", "").replaceAll("degreesC", "");
                temperature = "Temperature: " + temperature + " °C";
                labelTemperature.setText(temperature);
                System.out.println(temperature);
            }

        }

        double batteryRemaining = battery.getRemainingCapacityPercent() * 100;  // Battery remaining percentage

        batteryBar.setProgress(batteryRemaining / 100.0);


        // Display battery information
        if (labelBattery != null) {
            labelBattery.setText(String.format("Battery: %.1f%%", batteryRemaining));
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

        // Network info
        List<NetworkIF> networkInterfaces = si.getHardware().getNetworkIFs();

        NetworkIF net = networkInterfaces.getFirst();

        String interfaceName = net.getName();

        if (interfaceName.contains("en")) {
            interfaceName = "Ethernet";
        }   else {interfaceName = "Wi-Fi"; }


        String macAddress = net.getMacaddr();
        long linkSpeed = net.getSpeed();

        if (linkSpeed > 0) {
            linkSpeed /= (1000 * 1000);
        }

        if (labelLinkSpeed != null) {
            labelLinkSpeed.setText("Link Speed: " + linkSpeed + " Mbps");}
        if (labelInterfaceName != null) {
            labelInterfaceName.setText("Interface: " + interfaceName );}
        if (labelMacAddress != null) {
            labelMacAddress.setText("MAC Address: " + macAddress );}
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

        // Commenting this out because of a 8.8 MiB glitch
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
                        // Replace Integer.parseInt with Double.parseDouble to handle decimal numbers
                        double totalCacheSizeDouble = Double.parseDouble(String.valueOf(cpuCacheWithSpace).split(" ")[2]);
                        int totalCacheSize = (int) totalCacheSizeDouble; // Cast to int if needed


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

        // */

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

        // swap memroy
        VirtualMemory swapMemory = memory.getVirtualMemory();

        long usedSwap = swapMemory.getSwapUsed();

        if (labelUsedSwapMemory != null) {
            labelUsedSwapMemory.setText("Used Swap Memory: " + usedSwap / (1000 * 1000)  + " MB");
        }

        // Disk info
        List<HWDiskStore> disk = hal.getDiskStores();
        String model = "Unknown";
        long diskSize = 0;

        if (!disk.isEmpty()) {
            HWDiskStore diskStores = disk.getFirst();

            model = disk.getFirst().getModel();
            diskSize = disk.getFirst().getSize();
        }


        if (labelDiskModel != null) {
            labelDiskModel.setText("Disk Model: " + model );}

        if (labelDiskSize != null) {
            labelDiskSize.setText("Disk Size: " + diskSize / (1000 * 1000 * 1000) +" GB");}
    }

    public void initializeOperatingSystemPage(){

        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor processor = hal.getProcessor();

        labelOSName.setText("Operating System: " + os.getFamily());

        labelOSVer.setText("Version" + os.getVersionInfo().toString());

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

    public void initializePeripheralsPage() throws IOException {

        if ((System.getProperty("os.name").equals("Linux"))) {

            // Map to store the count of functions for each PCI bus and device.
            // Changed from HashMap to Linked Hash Map to keep things in order
            Map<String, Integer> pciBusFunctionCount = new LinkedHashMap<>();
            Map<String, Integer> pciDeviceFunctionCount = new LinkedHashMap<>();
            Map<String, Integer> pciDevicePerBusCount = new LinkedHashMap<>();
            // StringBuilder to accumulate results into one string
            StringBuilder numberOfFunctionsForEachBus = new StringBuilder();
            StringBuilder numberOfFunctionsForEachDevice = new StringBuilder();
            StringBuilder devicesPerBus = new StringBuilder();
            //Creates a new ArrayList for Strings
            List<String> lspciOutputArrayList = new ArrayList<>();
            ArrayList<String> pciBusesTotal = new ArrayList<>();
            ArrayList<String> pciDevicesTotal = new ArrayList<>();
            List<String> vendorIds = new ArrayList<>(); // Creates an arraylist for vendor Ids
            List<String> vendorNames = new ArrayList<>(); // creates an array list for vendor names
            ArrayList<String> pciImportantDeviceInfoArrayList = new ArrayList<>();  // List to store results for a bunch of stats


            // Execute the lspci command
            Process lSPCI = Runtime.getRuntime().exec("lspci -vvv -nn");
            Process noPCIDevices = Runtime.getRuntime().exec("lspci -nn");

            // Read the output of the command lspci -vvv -nn
            BufferedReader pCIReader = new BufferedReader(new InputStreamReader(lSPCI.getInputStream()));


            //read lspci output
            BufferedReader noPCIFunctionsReader = new BufferedReader(new InputStreamReader(noPCIDevices.getInputStream()));
            //Used to temporarily stores each line from lspci -nn. Stores one and then the next and so on
            String counterLineByLine;


            // Load vendor IDs from the file
            BufferedReader vendorIdFileReader = new BufferedReader(new FileReader("vendor_ids.txt"));
            String idFileLineByLine;

            // Read each line from the file and populate Arraylists for Vendor and VendorIds
            while ((idFileLineByLine = vendorIdFileReader.readLine()) != null) {
                //Splits text in file
                String[] fileVendorIdSplitVendor = idFileLineByLine.split("///////////////");

                vendorIds.add(fileVendorIdSplitVendor[0].toLowerCase()); // Store vendor ID
                vendorNames.add(fileVendorIdSplitVendor[1]); // Store vendor name
            }

            //Create variables for bus vendor and product id
            //Required if they're nested in other statements
            String busInfo = "";
            String vendorId = "";
            String productId = "";
            String vendorName = "";
            String currentBusId = "";

            String lSPCIvvvString = "";

            // Regex to find PCI bus info, vendor and product IDs
            // Finds String string [XXXX:XXXX]
            Pattern regexPattern = Pattern.compile("^(\\S+)\\s+.*\\[([0-9a-f]{4}):([0-9a-f]{4})]");

            while ((lSPCIvvvString = pCIReader.readLine()) != null) {

                //Adds each line from lspci -vvv -nn  to the ArrayList
                lspciOutputArrayList.add(lSPCIvvvString);


                //Removes trailing whitespaces E.g \n
                lSPCIvvvString = lSPCIvvvString.trim();

                // Capture bus ID line. Uses regex to find XX:XX.X
                if (lSPCIvvvString.matches("[0-9]{2}:[0-9]{2}\\.[0-9].*")) {
                    // Get the bus ID. Splits the bus to word by word
                    currentBusId = lSPCIvvvString.split(" ")[0];
                }

                // Capture kernel driver in use line
                if (lSPCIvvvString.startsWith("Kernel driver in use:")) {
                    String driverForCurrentDevice = lSPCIvvvString.split(":")[1].trim();  // Get the driver name. Splits by using :
                    // Store the result if both bus ID and driver are available
                    if (currentBusId != null) {
                        //adds kernal driver to arraylist
                        pciImportantDeviceInfoArrayList.add("\t\tKernal Driver: " + driverForCurrentDevice);
                    }
                }
                //Capture Subsystem
                if(lSPCIvvvString.contains("Subsystem: ")){
                    // Get the subsystem name. Splits by using :
                    String subsystemForCurrentDeviceSquare = lSPCIvvvString.split(": ")[1];
                    //Removes the [AAAA:AAAA]
                    String subsystemForCurrentDevice = subsystemForCurrentDeviceSquare.replaceAll("\\[[0-9a-fA-F]{4}:[0-9a-fA-F]{4}]", "");
                    // Adds Subsystem to arraylist
                    pciImportantDeviceInfoArrayList.add("\t\tSubsystem: " + subsystemForCurrentDevice);
                }

                //Capture Subsystem Vendor and Device id
                if(lSPCIvvvString.contains("Subsystem: ")){
                    // Finds [XXXX:XXXX]
                    Pattern regexPatternSubsystem = Pattern.compile("\\[([0-9a-f]{4}):([0-9a-f]{4})]");
                    // find [AAAA:AAAA]
                    Matcher findRegexSubSystem = regexPatternSubsystem.matcher(lSPCIvvvString);

                    if (findRegexSubSystem.find()) {
                        vendorId = findRegexSubSystem.group(1); // Get the vendor ID
                        productId = findRegexSubSystem.group(2); // Get the product ID
                    }

                    pciImportantDeviceInfoArrayList.add("\t\tSubsystem Vendor ID: " + vendorId +
                            ", Subsystem ProductID: " + productId);

                    // Adds Subsystem to arraylist
                    // pciImportantDeviceInfoArrayList.add("\t\tSubsystem: " + subsystemForCurrentDevice);
                }

                Matcher FindRegex = regexPattern.matcher(lSPCIvvvString);
                if (FindRegex.find()) {
                    busInfo = FindRegex.group(1); // Get the bus info
                    vendorId = FindRegex.group(2); // Get the vendor ID
                    productId = FindRegex.group(3); // Get the product ID
                }

                //Checks for bus ID line. Uses regex to find XX:XX.X
                if (lSPCIvvvString.matches("[0-9]{2}:[0-9]{2}\\.[0-9].*")) {
                    // Search for the vendor name
                    // For loop searches for vendorId in file line by line
                    for (int counterForVendroId = 0; counterForVendroId < vendorIds.size(); counterForVendroId++) {
                        if (vendorIds.get(counterForVendroId).equals(vendorId)) {
                            vendorName = vendorNames.get(counterForVendroId); // Found the vendor name
                            break; //leaves the for loop cause vendorId is found. Improves performance
                        }
                    }
                    //adds buslocation, vendor id, product id, and vendor to arraylist
                    pciImportantDeviceInfoArrayList.add("\n" + busInfo + "\tVendor ID: " + vendorId + ", Product ID: " + productId);
                    pciImportantDeviceInfoArrayList.add("\t\tVendor Name: " + (vendorName != null ? vendorName : "Vendor ID not found. Please check the ID."));
                    // Detects XX:XX:X using regex
                    if (lSPCIvvvString.matches("[0-9]{2}:[0-9]{2}\\.[0-9].*")) {
                        // Finds XX.X Device [XXXX]. Splits by using :
                        String intIntSpaceDeviceSquareBrackets = lSPCIvvvString.split(":")[1].trim();

                        // Replace the matched pattern with an empty string
                        //Detects Int.IntSpace using regex and replaces it with nothing
                        String deviceNameSquareBrackets = intIntSpaceDeviceSquareBrackets.replaceAll("\\d+\\.\\d+\\s", "");
                        //Detects [AAAA] and replaces it with nothing where A is a letter or number
                        String deviceName = deviceNameSquareBrackets.replaceAll("\\[[0-9a-fA-F]{4}]", "");
                        // Store the deviceName into the Important Array list
                        pciImportantDeviceInfoArrayList.add("\t\tDevice Name: " + deviceName);
                    }
                }
            }

            //Counts number of outputs from lspci
            int functionCountTotal = 0;
            while ((counterLineByLine = noPCIFunctionsReader.readLine()) != null) {
                functionCountTotal++;

                // Read the output line by line and store it in an array
                // Example output: "00:1f.0  SATA controller: Intel Corporation 82801AA SATA Controller"
                String[] wordByWord = counterLineByLine.split(" ");

                //Creates a temporary string for the first word in each line
                //This should be the bus:device:function E.g. 00:00:00
                String busId = wordByWord[0];

                //Split busId into two parts - AA:BB.BB
                String[] busSplitDeviceFunction = busId.split(":"); // Split bus from device

                // Check for unique bus ID
                if (!pciBusesTotal.contains(busSplitDeviceFunction[0])) {
                    pciBusesTotal.add(busSplitDeviceFunction[0]); // Add unique bus ID
                }

                //split busId into 2 parts - aa:aa.bb
                String[] deviceSplitFunction = busId.split("\\."); // Split bus from device

                //checks if device is already in Arraylist
                if (!pciDevicesTotal.contains(deviceSplitFunction[0])) {
                    pciDevicesTotal.add(deviceSplitFunction[0]); // Add unique device Id
                }

                // Get the bus ID (first part) and stores it in an array
                String busIdFunction = wordByWord[0].split(":")[0];

                // Count functions for each bus
                pciBusFunctionCount.put(busIdFunction, pciBusFunctionCount.getOrDefault(busIdFunction, 0) + 1);

                // Get the Device ID (Second part) and stores it in an array
                String deviceId = wordByWord[0].split("\\.")[0];

                // Count functions for each device
                pciDeviceFunctionCount.put(deviceId, pciDeviceFunctionCount.getOrDefault(deviceId, 0) + 1);


            }

            // Build the result string for number of functions for each pci bus
            for (Map.Entry<String, Integer> entry : pciBusFunctionCount.entrySet()) {
                //if else statement for correct grammar function for one and functions for plural
                if (entry.getValue() == 1) {
                    numberOfFunctionsForEachBus.append("Bus ").append(entry.getKey()).append(" has ").append(entry.getValue())
                            .append(" function\n");
                } else {
                    numberOfFunctionsForEachBus.append("Bus ").append(entry.getKey()).append(" has ").append(entry.getValue())
                            .append(" functions\n");
                }
            }
            // Build the result string for number of functions for each device
            for (Map.Entry<String, Integer> entryDeviceFunctionCount : pciDeviceFunctionCount.entrySet()) {

                //Creates a temporary string for Bus:Device = NoOfFunctions for each Device
                String busDevicesEqualsNoFunctions = String.valueOf(entryDeviceFunctionCount);
                //Creates an array for Bus,Device=NoOfFunctions
                String[] busSplitDevice = busDevicesEqualsNoFunctions.split(":");
                //Remove Device=NoOfFunctions leaving only the Bus which is repeated once for each device
                String listOfBusesRepeatedForNoOfDevices = busSplitDevice[0].split("\\.")[0];

                //Adds the Bus and number of times it occurs to the linked hash map
                pciDevicePerBusCount.put(listOfBusesRepeatedForNoOfDevices,
                        pciDevicePerBusCount.getOrDefault(listOfBusesRepeatedForNoOfDevices, 0) + 1);

                //if else statement for correct grammar function for one and functions for plural
                if (entryDeviceFunctionCount.getValue() == 1) {
                    numberOfFunctionsForEachDevice.append("Device ").append(entryDeviceFunctionCount.getKey())
                            .append(" has ").append(entryDeviceFunctionCount.getValue()).append(" function\n");
                } else {
                    numberOfFunctionsForEachDevice.append("Device ").append(entryDeviceFunctionCount.getKey())
                            .append(" has ").append(entryDeviceFunctionCount.getValue()).append(" functions\n");
                }

            }

            // Build the result string for number of devices per bus
            for (Map.Entry<String, Integer> entryDevicesPerFunction : pciDevicePerBusCount.entrySet()) {
                //if else statement for correct grammar device for one and devices for plural
                if (entryDevicesPerFunction.getValue() == 1) {
                    devicesPerBus.append("Bus ").append(entryDevicesPerFunction.getKey())
                            .append(" has ").append(entryDevicesPerFunction.getValue()).append(" device\n");
                } else {
                    devicesPerBus.append("Bus ").append(entryDevicesPerFunction.getKey())
                            .append(" has ").append(entryDevicesPerFunction.getValue()).append(" devices\n");
                }
            }
            // Save the result as a string
            String functionsPerBus = numberOfFunctionsForEachBus.toString();
            String functionsPerDevice = numberOfFunctionsForEachDevice.toString();
            String noDevicesPerBus = String.valueOf(devicesPerBus);


            // Convert ArrayList to Array
            String[] lSPCIOutputArray = lspciOutputArrayList.toArray(new String[0]);
            String[] pciImportantDeviceInfoArray = pciImportantDeviceInfoArrayList.toArray(new String[0]);

            // Converts pciBusesTotal.size() into an int
            int noOfBusesTotal = pciBusesTotal.size();
            // Converts pciDevicesTotal.size() into an int
            int noOfDevicesTotal = pciDevicesTotal.size();

          /*  // Print the output
            for (String arrayEntry : lSPCIOutputArray) {
                    System.out.println(arrayEntry);
            }*/

            //for (String result : pciImportantDeviceInfoArray) {
            //    System.out.println(result);
            //}



            //Adding pciImportantDeviceInfoArray to an Array List to be used in GUI
            //List<String> pciInfoArrayListForGUI = new ArrayList<>();
            //pciInfoArrayListForGUI.addAll(pciImportantDeviceInfoArrayList);
            //System.out.println("ARRAY LIST FOR GUI: " + pciInfoArrayListForGUI);

            //noOfBusesTotal = int of the total number Buses
            //functionCountTotal = int with the total number of functions
            //lSPCIOutputArray = Array of the output of lspci -vvv -nn not really nessesary because of other outputs
            //lspciOutputArrayList = ArrayList of the output of lspci -vvv -nn not really nessesary because of other outputs
            //functionsPerBus = String of how many pci functions each bus has
            //functionPerDevice = String of how many pci functions each device has
            //noOfDevicesTotal = int of the total number of pcie devices connected
            //noDevicesPerBus = String for the number of devices connected to each bus
            //pciImportantDeviceInfoArray = String Array Containing Bus location, vendor Id, Product Id, Vendor Name, kernal driver, device name, subsystem informatnoin
            //pciImportantDeviceInfoArrayList = List for GUI

            // Convert ArrayList to ObservableList
            ObservableList<String> pciListForGUI = FXCollections.observableArrayList(pciImportantDeviceInfoArrayList);

            // Set the ObservableList as the items for the ListView
            System.out.println("PCI LIST FOR GUI" + pciListForGUI);
            if(listPcie != null)listPcie.setItems(pciListForGUI);

            if(labelDevicesAmount != null)labelDevicesAmount.setText("Number of PCI Devices: " + noOfDevicesTotal);

            if(labelTotalFunctions != null)labelTotalFunctions.setText("Total Number of Functions across PCI Devices: " + functionCountTotal);

            if(labelBusesAmount != null)labelBusesAmount.setText("Number of Buses: " + noOfBusesTotal);

            if(labelDevicesPerBus != null)labelDevicesPerBus.setText("Number of Devices Per Bus:\n\n" + noDevicesPerBus);

            if(labelFunctionsPerBus != null)labelFunctionsPerBus.setText("No. Functions Per Bus:\n\n" + functionsPerBus);

            if(labelFunctionsPerDevice != null)labelFunctionsPerDevice.setText("No. Functions Per Device:\n\n" + functionsPerDevice);

            if(labelDevicesPerBus != null)labelDevicesPerBus.setText("No. Devices Per Bus: \n\n" + devicesPerBus);


        }



    }

    public void initializePeripheralsUSBPage() throws IOException {

        //Setting up USB Page
        //Creates ArrayLists
        List<String> lsUSBOutputArrayList = new ArrayList<>(); //List for lsusb
        List<String> usbImportantInfoArrayList = new ArrayList<>(); // List to store results for a bunch of stats
        // Map to store the count of devices per bus
        Map<String, Integer> usbDevicesPerBusCount = new LinkedHashMap<>();
        // String builder
        StringBuilder devicesPerUsbBus = new StringBuilder();

        // Create a process to run the lsusb command
        Process lsUSB = Runtime.getRuntime().exec("lsusb");
        Process lsUSB_vvv = Runtime.getRuntime().exec("lsusb -vvv");

        // Read the output from the command
        BufferedReader lsUSBReader = new BufferedReader(new InputStreamReader(lsUSB.getInputStream()));
        BufferedReader lsUSB_vvvReader = new BufferedReader(new InputStreamReader(lsUSB_vvv.getInputStream()));
        String usb_VVVLineByLine;
        String usbLineByLine;

        // Counts number of usb devices
        int usbDeviceCount = 0;
        while ((usbLineByLine = lsUSBReader.readLine()) != null) {
            usbDeviceCount++;

            lsUSBOutputArrayList.add(usbLineByLine);

            // Splits up into each individual word then inputs the second word into a string
            String wordByWordUsb = usbLineByLine.trim().split(" ")[1];

            // Count devices for each bus
            usbDevicesPerBusCount.put(wordByWordUsb, usbDevicesPerBusCount.getOrDefault(wordByWordUsb, 0) + 1);
        }

        while ((usb_VVVLineByLine = lsUSB_vvvReader.readLine()) != null) {
            usb_VVVLineByLine = usb_VVVLineByLine.trim();

            //Capture Bus and Device numbers
            //Checks if it starts with Bus and contains device and : ID
            if(usb_VVVLineByLine.startsWith("Bus ") && usb_VVVLineByLine.contains("Device ") && usb_VVVLineByLine.contains(": ID")) {
                //Splits based on :
                String usbBusNumberDeviceNumber = usb_VVVLineByLine.split(":")[0];
                // Store the device and bus info into the array list
                usbImportantInfoArrayList.add(usbBusNumberDeviceNumber);
            }

            //Captures the vendor and vendor id
            //Checks if it starts with idProduct          0x
            if(usb_VVVLineByLine.startsWith("idVendor           0x")){
                //Splits based on 0x and saves second string
                String usbProductAndProductID = usb_VVVLineByLine.split("0x")[1];
                //Splits based on a space and saves first string
                String idVendor = usbProductAndProductID.split(" ")[0];
                //Removes the AAAA
                String usbVendorName = usbProductAndProductID.replaceAll("[0-9a-fA-F]{4} ", "");
                //Stores the product and product id into the arraylist
                usbImportantInfoArrayList.add("Vendor ID: " + idVendor);
                usbImportantInfoArrayList.add("Vendor: " + usbVendorName);

            }

            //Captures the product and product id
            //Checks if it starts with idProduct          0x
            if(usb_VVVLineByLine.startsWith("idProduct          0x")){
                //Splits based on 0x and saves second string
                String usbProductAndProductID = usb_VVVLineByLine.split("0x")[1];
                //Splits based on a space and saves first string
                String idProduct = usbProductAndProductID.split(" ")[0];
                //Removes the AAAA
                String usbProductName = usbProductAndProductID.replaceAll("[0-9a-fA-F]{4} ", "");
                //Stores the product and product id into the arraylist
                usbImportantInfoArrayList.add("Product ID: " + idProduct);
                usbImportantInfoArrayList.add("Device Name: " + usbProductName);

            }


        }

        // Build the result string for number of devices per bus
        for (Map.Entry<String, Integer> entryDevicesPerUsbBus : usbDevicesPerBusCount.entrySet()) {
            //if else statement for correct grammar device for one and devices for plural
            if (entryDevicesPerUsbBus.getValue() == 1) {
                devicesPerUsbBus.append("Bus ").append(entryDevicesPerUsbBus.getKey())
                        .append(" has ").append(entryDevicesPerUsbBus.getValue()).append(" device\n");
            } else {
                devicesPerUsbBus.append("Bus ").append(entryDevicesPerUsbBus.getKey())
                        .append(" has ").append(entryDevicesPerUsbBus.getValue()).append(" devices\n");
            }
        }

        // Convert ArrayList to Array
        String[] lsUSBOutputArray = lsUSBOutputArrayList.toArray(new String[0]);
        String[] usbImportantDeviceInfoArray = usbImportantInfoArrayList.toArray(new String[0]);

        int totalNoOfUsbBuses = usbDevicesPerBusCount.size();

        // Print the output
        // for (String arrayEntry : lsUSBOutputArray) {
        //  System.out.println(arrayEntry);
        //  }

        for (String result : usbImportantDeviceInfoArray) {
            System.out.println(result);
        }

        System.out.println("\nThis computer has " + usbDeviceCount + " USB devices connected\n");

        System.out.println(devicesPerUsbBus);
        System.out.println("There are " + totalNoOfUsbBuses + " USB buses");

        //lsUSBOutputArray = array containing lsusb, nothing really importaing in this usbImportantInfoArray covers all of this
        //usbDeviceCount = int with the number of devices connected to the computer
        //devicesPerUsbBus = Sting containing the number of devices connected to each unique Usb bus
        //totalNoOfUsbBuses = int with the total number of usb buses
        //usbImportantInfoArray = array containing bus id, device id, vendor id, vendor name, product id, product name


        ObservableList<String> usbInfoArrayListForGui = FXCollections.observableArrayList(usbImportantInfoArrayList);
        if (listUSB != null) {
            listUSB.setItems(usbInfoArrayListForGui);
        }
        if (labelBusesAmountUsb != null) {
            labelBusesAmountUsb.setText("Number of USB Buses: " + totalNoOfUsbBuses);
            System.out.println("TOTAL NO BUSES: " + totalNoOfUsbBuses);
        }
        if (labelDevicesPerBusUsb != null) {
            labelDevicesPerBusUsb.setText("Number of Devices Per Bus:\n\n" + devicesPerUsbBus);
        }


    }

    public void initializeCPU2Page() throws IOException {
        //Creates ArrayLists
        ArrayList<String> cpuVulnerablitiesArrayList = new ArrayList<>(); //ArrayList for cpu

        // Create a process to run the lscpu command
        Process lsCpu = Runtime.getRuntime().exec("lscpu");

        // Read the output from the command
        BufferedReader lsCpuReader = new BufferedReader(new InputStreamReader(lsCpu.getInputStream()));
        String cpuLineByLine;

        while ((cpuLineByLine = lsCpuReader.readLine()) != null) {

            //Removes excess whitespaces
            cpuLineByLine = cpuLineByLine.trim();

            //Checks if it starts with Vulnerability
            if(cpuLineByLine.startsWith("Vulnerability")) {
                //Removes the vulnerability part at the start
                String cpuVulnerabilities = cpuLineByLine.replaceAll("Vulnerability ", "");

                //Creates Variable for checking if a cpu has any vulnerabilities
                String cpuVulnerabilityChecker = cpuVulnerabilities.split(":")[1];

                //Checks if the cpu is Vulnerable to any vulnerabilities
                if(cpuVulnerabilityChecker.contains("Vulnerable")){
                    String cpuVulnerability = cpuVulnerabilities.split(":")[0];
                    cpuVulnerablitiesArrayList.add("WARNING, your Cpu is vulnerable to " + cpuVulnerability.toUpperCase() + ", WARNING");
                }else{
                    cpuVulnerablitiesArrayList.add(cpuVulnerabilities);
                }
            }

        }

        // Convert ArrayList to Array
        String[] cpuVulnerablitiesArray = cpuVulnerablitiesArrayList.toArray(new String[0]);

        //cpuVulnerablitiesArray = Array with all cpu vulnerabilities and warnings next to the ones you're vulnerable to and have no mitgations

        //Setting up GUI
        ObservableList<String> cpuVulnerabilitiesForGUI = FXCollections.observableArrayList(cpuVulnerablitiesArrayList);
        if(listCPUVulnerabilities != null)listCPUVulnerabilities.setItems(cpuVulnerabilitiesForGUI);
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
    protected void onCPU2ButtonClick() {
        changeScene("cpu2.fxml");
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

    @FXML
    protected void onUSBButtonClick() {
        changeScene("peripherals2.fxml");
    }

}
