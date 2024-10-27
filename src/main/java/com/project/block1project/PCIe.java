package com.project.block1project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PCIe {

    public void main(String[] args) throws IOException {

        //Checks if OS is linux
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
            ArrayList<String> pciBusesTotal = new ArrayList<>();
            ArrayList<String> pciDevicesTotal = new ArrayList<>();
            List<String> vendorIds = new ArrayList<>(); // Creates an arraylist for vendor Ids
            List<String> vendorNames = new ArrayList<>(); // creates an array list for vendor names
            List<String> pciImportantDeviceInfoArrayList = new ArrayList<>(); // List to store results for a bunch of stats



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

            //Creates an ArrayList for lspci -vvv -nn
            List<String> lspciOutputArrayList = new ArrayList<>();
            String lSPCIvvvString = "";

            // Regex to find PCI bus info, vendor and product IDs
            // Specific regex copied from stack overflow
            Pattern RegexPattern = Pattern.compile("^(\\S+)\\s+.*\\[([0-9a-f]{4}):([0-9a-f]{4})]");

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
                // Use regex to find bus info, vendor and product IDs
                Matcher FindRegex = RegexPattern.matcher(lSPCIvvvString);
                if (FindRegex.find()) {
                    busInfo = FindRegex.group(1); // Get the bus info
                    vendorId = FindRegex.group(2); // Get the vendor ID
                    productId = FindRegex.group(3); // Get the product ID
                }

                //Checks for bus ID line. Uses regex to find XX:XX.X
                if(lSPCIvvvString.matches("[0-9]{2}:[0-9]{2}\\.[0-9].*")) {
                    // Search for the vendor name
                    // For loop searches for vendorId in file line by line
                    for (int counterForVendroId = 0; counterForVendroId < vendorIds.size(); counterForVendroId++) {
                        if (vendorIds.get(counterForVendroId).equals(vendorId)) {
                            vendorName = vendorNames.get(counterForVendroId); // Found the vendor name
                            break; //leaves the for loop cause vendorId is found. Improves performance
                        }

                    }
                    //adds buslocation, vendor id, product id, and vendor to arraylist
                    pciImportantDeviceInfoArrayList.add("\n" + busInfo + "\tVendor ID: " + vendorId + ", Product ID: " + productId + "\n"
                            + "\t\tVendor Name: " + (vendorName != null ? vendorName : "Vendor ID not found. Please check the ID.") );
                }
            }

            //Counts number of outputs from lspci
            int functionCountTotal = 0;
            while ((counterLineByLine = noPCIFunctionsReader.readLine()) != null) {
                functionCountTotal++;

                // Read the output line by line and store it in an array
                // Example output: "00:1f.0  SATA controller: Intel Corporation 82801AA SATA Controller"
                String[] wordByWord = counterLineByLine.split( " ");

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
                } else{
                    numberOfFunctionsForEachBus.append("Bus ").append(entry.getKey()).append(" has ").append(entry.getValue())
                            .append(" functions\n");
                }
            }
            // Build the result string for number of functions for each device
            for (Map.Entry<String, Integer> entryDeviceFunctionCount : pciDeviceFunctionCount.entrySet()) {

                //Creates a temporary string for Bus:Device = NoOfFunctions for each Device
                String busDevicesEqualsNoFunctions = String.valueOf(entryDeviceFunctionCount);
                //Creates an array for Bus,Device=NoOfFunctions
                String[] busSplitDevice= busDevicesEqualsNoFunctions.split(":");
                //Remove Device=NoOfFunctions leaving only the Bus which is repeated once for each device
                String listOfBusesRepeatedForNoOfDevices = busSplitDevice[0].split("\\.")[0];

                //Adds the Bus and number of times it occurs to the linked hash map
                pciDevicePerBusCount.put(listOfBusesRepeatedForNoOfDevices,
                        pciDevicePerBusCount.getOrDefault(listOfBusesRepeatedForNoOfDevices, 0) + 1);

                //if else statement for correct grammar function for one and functions for plural
                if (entryDeviceFunctionCount.getValue() == 1) {
                    numberOfFunctionsForEachDevice.append("Device ").append(entryDeviceFunctionCount.getKey())
                            .append(" has ").append(entryDeviceFunctionCount.getValue()).append(" function\n");
                } else{
                    numberOfFunctionsForEachDevice.append("Device ").append(entryDeviceFunctionCount.getKey())
                            .append(" has ").append(entryDeviceFunctionCount.getValue()).append(" functions\n");
                }

            }

            // Build the result string for number of devices per bus
            for (Map.Entry<String, Integer> entryDevicesPerFunction : pciDevicePerBusCount.entrySet()){
                //if else statement for correct grammar device for one and devices for plural
                if (entryDevicesPerFunction.getValue() == 1) {
                    devicesPerBus.append("Bus ").append(entryDevicesPerFunction.getKey())
                            .append(" has ").append(entryDevicesPerFunction.getValue()).append(" device\n");
                } else{
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

            for (String result : pciImportantDeviceInfoArray) {
                System.out.println(result);
            }

            System.out.println("\nThere are " + functionCountTotal + " PCIe Functions\n");
            System.out.println("Number of PCIe buses: " + noOfBusesTotal + "\n");
            System.out.println("Number of unique PCI devices: " + noOfDevicesTotal +  "\n");
            System.out.println(noDevicesPerBus);
            System.out.println(functionsPerBus);
            System.out.println(functionsPerDevice);

            //noOfBusesTotal = int of the total number Buses
            //functionCountTotal = int with the total number of functions
            //lSPCIOutputArray = Array of the output of lspci -vvv -nn
            //lspciOutputArrayList = ArrayList of the output of lspci -vvv -nn
            //functionsPerBus = String of how many pci functions each bus has
            //functionPerDevice = String of how many pci functions each device has
            //noOfDevicesTotal = int of the total number of pcie devices connected
            //noDevicesPerBus = String for the number of devices connected to each bus
            //pciImportantDeviceInfoArray = Array Containing Bus location, vendor Id, Product Id and Vendor Name and kernal driver
        }
    }
}




