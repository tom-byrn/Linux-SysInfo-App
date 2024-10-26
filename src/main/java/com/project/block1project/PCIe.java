package com.project.block1project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


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


            // Execute the lspci command
            Process lSPCI = Runtime.getRuntime().exec("lspci -vvv -nn");
            Process noPCIDevices = Runtime.getRuntime().exec("lspci -nn");

            // Read the output of the command lspci -vvv -nn
            BufferedReader pCIReader = new BufferedReader(new InputStreamReader(lSPCI.getInputStream()));
            //read lspci output
            BufferedReader noPCIFunctionsReader = new BufferedReader(new InputStreamReader(noPCIDevices.getInputStream()));
            //Used to temporarily stores each line from lspci -nn. Stores one and then the next and so on
            String counterLineByLine;

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
                    numberOfFunctionsForEachBus.append("Bus ").append(entry.getKey()).append(" has ").append(entry.getValue()).append(" function\n");
                } else{
                    numberOfFunctionsForEachBus.append("Bus ").append(entry.getKey()).append(" has ").append(entry.getValue()).append(" functions\n");
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
                //Adds the Bus and number of times it occours to the linked hash map
                pciDevicePerBusCount.put(listOfBusesRepeatedForNoOfDevices, pciDevicePerBusCount.getOrDefault(listOfBusesRepeatedForNoOfDevices, 0) + 1);

                //if else statement for correct grammar function for one and functions for plural
                if (entryDeviceFunctionCount.getValue() == 1) {
                    numberOfFunctionsForEachDevice.append("Device ").append(entryDeviceFunctionCount.getKey()).append(" has ").append(entryDeviceFunctionCount.getValue()).append(" function\n");
                } else{
                    numberOfFunctionsForEachDevice.append("Device ").append(entryDeviceFunctionCount.getKey()).append(" has ").append(entryDeviceFunctionCount.getValue()).append(" functions\n");
                }

            }

            // Build the result string for number of devices per bus
            for (Map.Entry<String, Integer> entryDevicesPerFunction : pciDevicePerBusCount.entrySet()){
                //if else statement for correct grammar device for one and devices for plural
                if (entryDevicesPerFunction.getValue() == 1) {
                    devicesPerBus.append("Bus ").append(entryDevicesPerFunction.getKey()).append(" has ").append(entryDevicesPerFunction.getValue()).append(" device\n");
                } else{
                    devicesPerBus.append("Bus ").append(entryDevicesPerFunction.getKey()).append(" has ").append(entryDevicesPerFunction.getValue()).append(" devices\n");
                }
            }
            // Save the result as a string
            String functionsPerBus = numberOfFunctionsForEachBus.toString();
            String functionsPerDevice = numberOfFunctionsForEachDevice.toString();


            //Creates an ArrayList for lspci -vvv -nn
            List<String> lspciOutput = new ArrayList<>();
            //Adds each line to the ArrayList
            String justARandomStringWithToMakeThisFunctionWork = "";
            while ((justARandomStringWithToMakeThisFunctionWork = pCIReader.readLine()) != null) {
                lspciOutput.add(justARandomStringWithToMakeThisFunctionWork);
            }

            // Convert ArrayList to Array
            String[] lSPCIOutputArray = lspciOutput.toArray(new String[0]);

            // Converts pciBusesTotal.size() into an int
            int noOfBusesTotal = pciBusesTotal.size();

            // Converts pciDevicesTotal.size() into an int
            int noOfDevicesTotal = pciDevicesTotal.size();

          /*  // Print the output
            for (String arrayEntry : lSPCIOutputArray) {
                    System.out.println(arrayEntry);
            }*/

            System.out.println("\nThere are " + functionCountTotal + " PCIe Functions\n");
            System.out.println("Number of PCIe buses: " + noOfBusesTotal + "\n");
            System.out.println("Number of unique PCI devices: " + noOfDevicesTotal +  "\n");
            System.out.println(devicesPerBus);


            System.out.println(functionsPerBus);
            System.out.println(functionsPerDevice);

            //noOfBusesTotal = int of the total number Buses
            //functionCountTotal = int with the total number of functions
            //lSPCIOutputArray = Array of the output of lspci -vvv -nn
            //lspciOutput = ArrayList of the output of lspci -vvv -nn
            //functionsPerBus = String of how many pci functions each bus has
            //functionPerDevice = String of how many pci functions each device has
            //noOfDevicesTotal = int of the total number of pcie devices connected
            //devicesPerBus = String for the number of devices connected to each bus
        }
    }
}




