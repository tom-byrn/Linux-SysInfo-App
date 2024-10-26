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
            // StringBuilder to accumulate results
            StringBuilder numberOfFunctionsForEachBus = new StringBuilder();
            StringBuilder numberOfFunctionsForEachDevice = new StringBuilder();
            //Creates a new HashSet
            Set<String> pciBuses = new HashSet<>();


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


                //count number of unique Pci Buses
                pciBuses.add(busId.substring(0, busId.indexOf(':')));

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
                //if else statement for correct grammar function for one ane functions for plural
                if (entry.getValue() == 1) {
                    numberOfFunctionsForEachBus.append("Bus ").append(entry.getKey()).append(" has ").append(entry.getValue()).append(" function\n");
                } else{
                    numberOfFunctionsForEachBus.append("Bus ").append(entry.getKey()).append(" has ").append(entry.getValue()).append(" functions\n");
                }
            }
            // Build the result string
            for (Map.Entry<String, Integer> entry : pciDeviceFunctionCount.entrySet()) {
                //if else statement for correct grammar function for one ane functions for plural
                if (entry.getValue() == 1) {
                    numberOfFunctionsForEachDevice.append("Device ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" function\n");
                } else{
                    numberOfFunctionsForEachDevice.append("Device ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" functions\n");
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

            // Converts pciBuses.size() into an int
            int noOfBuses = pciBuses.size();

            // Print the output
            for (String arrayEntry : lSPCIOutputArray) {
                System.out.println(arrayEntry);
            }

            System.out.println("There are " + functionCountTotal + " PCIe Functions\n");

            System.out.println("Number of PCIe buses: " + noOfBuses + "\n");


            System.out.println(functionsPerBus);
            System.out.println(functionsPerDevice);

            //noOfBuses = int of the total number Buses
            //functionCountTotal = int with the total number of functions
            //lSPCIOutputArray = Array of the output of lspci -vvv -nn
            //lspciOutput = ArrayList of the output of lspci -vvv -nn
            //functionsPerBus = String of how many pci functions each bus has
            //functionPerDevice = String of how many pci functions each device has
        }
    }
}




