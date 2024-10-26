package com.project.block1project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class PCIe {

    public void main(String[] args) throws IOException {

        //Checks if OS is linux
        if ((System.getProperty("os.name").equals("Linux"))) {

            // Map to store the count of functions for each PCI bus and device
            Map<String, Integer> pciBusFunctionCount = new HashMap<>();
            Map<String, Integer> pciDeviceFunctionCount = new HashMap<>();
            // StringBuilder to accumulate results
            StringBuilder NumberOfFunctionsForEachBus = new StringBuilder();
            StringBuilder NumberOfFunctionsForEachDevice = new StringBuilder();
            //Creates a new HashSet
            Set<String> pciBuses = new HashSet<>();


            // Execute the lspci command
            Process LSPCI = Runtime.getRuntime().exec("lspci -vvv -nn");
            Process NoPCIDevices = Runtime.getRuntime().exec("lspci -nn");

            // Read the output of the command lspci -vvv -nn
            BufferedReader PCIReader = new BufferedReader(new InputStreamReader(LSPCI.getInputStream()));
            //read lspci output
            BufferedReader NoPCIFunctionsReader = new BufferedReader(new InputStreamReader(NoPCIDevices.getInputStream()));
            String CounterLineByLine;

            //Counts number of outputs from lspci
            int FunctionCountTotal = 0;
            while ((CounterLineByLine = NoPCIFunctionsReader.readLine()) != null) {
                FunctionCountTotal++;

                // Read the output line by line
                // Example output: "00:1f.0  SATA controller: Intel Corporation 82801AA SATA Controller"
                String[] WordByWord = CounterLineByLine.split( " ");

                String busId = WordByWord[0];
                //count number of unique Pci Buses
                pciBuses.add(busId.substring(0, busId.indexOf(':')));

                // Get the bus ID (first part) and stores it in an array
                String BusIdFunction = WordByWord[0].split(":")[0];

                // Count functions for each bus
                pciBusFunctionCount.put(BusIdFunction, pciBusFunctionCount.getOrDefault(BusIdFunction, 0) + 1);

                // Get the Device ID (Second part) and stores it in an array
                String DeviceId = WordByWord[0].split("\\.")[0];

                // Count functions for each device
                pciDeviceFunctionCount.put(DeviceId, pciDeviceFunctionCount.getOrDefault(DeviceId, 0) + 1);

            }

            // Build the result string for number of functions for each pci bus
            for (Map.Entry<String, Integer> entry : pciBusFunctionCount.entrySet()) {
                //if else statement for correct grammar function for one ane functions for plural
                if (entry.getValue() == 1) {
                    NumberOfFunctionsForEachBus.append("Bus ").append(entry.getKey()).append(" has ").append(entry.getValue()).append(" function\n");
                } else{
                    NumberOfFunctionsForEachBus.append("Bus ").append(entry.getKey()).append(" has ").append(entry.getValue()).append(" functions\n");
                }
            }
            // Build the result string
            for (Map.Entry<String, Integer> entry : pciDeviceFunctionCount.entrySet()) {
                //if else statement for correct grammar function for one ane functions for plural
                if (entry.getValue() == 1) {
                    NumberOfFunctionsForEachDevice.append("Device ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" function\n");
                } else{
                    NumberOfFunctionsForEachDevice.append("Device ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" functions\n");
                }
            }
            // Save the result as a string
            String FunctionsPerBus = NumberOfFunctionsForEachBus.toString();
            String FunctionsPerDevice = NumberOfFunctionsForEachDevice.toString();


            //Creates an ArrayList for lspci -vvv -nn
            List<String> lspciOutput = new ArrayList<>();
            //Adds each line to the ArrayList
            String JustARandomStringWithToMakeThisFunctionWork = "";
            while ((JustARandomStringWithToMakeThisFunctionWork = PCIReader.readLine()) != null) {
                lspciOutput.add(JustARandomStringWithToMakeThisFunctionWork);
            }

            // Convert ArrayList to Array
            String[] LSPCIOutputArray = lspciOutput.toArray(new String[0]);

            // Print the output
            for (String entry : LSPCIOutputArray) {
                System.out.println(entry);
            }

            System.out.println("There are " + FunctionCountTotal + " PCIe Functions");

            System.out.println("Number of PCIe buses: " + pciBuses.size());


            System.out.println(FunctionsPerBus);
            System.out.println(FunctionsPerDevice);

            //pciBuses.size() = int of the total number Buses
            //FunctionCountTotal = int with the total number of functions
            //LSPCIOutputArray = Array of the output of lspci -vvv -nn
            //lspciOutput = ArrayList of the output of lspci -vvv -nn
            //FunctionsPerBus = String of how many pci functions each bus has
            //FunctionPerDevice = String of how many pci functions each device has
        }
    }
}




