package com.project.block1project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class PCIe {




    public void main(String[] args) throws IOException {

        //Checks if OS is linux
        if (Objects.equals(System.getProperty("os.name"), "Linux")) {


            // Execute the lspci command
            Process LSPCI = Runtime.getRuntime().exec("lspci -vvv -nn");
            Process NoPCIDevices = Runtime.getRuntime().exec("lspci -nn");

            // Read the output of the command
            BufferedReader PCIReader = new BufferedReader(new InputStreamReader(LSPCI.getInputStream()));

            //read lspci output(Used for finding number of pcie device Functions)
            BufferedReader NoPCIFunctionsReader = new BufferedReader(new InputStreamReader(NoPCIDevices.getInputStream()));
            Set<String> pciBuses = new HashSet<>();
            String CounterLineByLine;


            //Counts number of outputs from lspci
            int FunctionCount = 0;
            while ((CounterLineByLine = NoPCIFunctionsReader.readLine()) != null) {
                FunctionCount++;

                // Extract the bus ID from the output
                String[] WordByWord = CounterLineByLine.split( " ");
                if (WordByWord.length > 0) {
                    String busId = WordByWord[0];
                    //count number of unique Pci Buses
                    pciBuses.add(busId.substring(0, busId.indexOf(':')));
                }
            }


            //Creates an ArrayList
            List<String> lspciOutput = new ArrayList<>();
            String LineByLine;


            //Adds each line to the ArrayList
            while ((LineByLine = PCIReader.readLine()) != null) {
                lspciOutput.add(LineByLine);
            }

            // Convert ArrayList to Array
            String[] LSPCIOutputArray = lspciOutput.toArray(new String[0]);

            // Print the output
            for (String entry : LSPCIOutputArray) {
                System.out.println(entry);
            }
            System.out.println("There are " + FunctionCount + " PCIe Functions");

            System.out.println("Number of PCIe buses: " + pciBuses.size());

            //pciBuses.size() = int of the total number Buses
            //FunctionCount = int with the total number of functions
            //LSPCIOutputArray = Array of the output of lspci -vvv -nn
            //lspciOutput = ArrayList of the output of lspci -vvv -nn
        }
    }
}




