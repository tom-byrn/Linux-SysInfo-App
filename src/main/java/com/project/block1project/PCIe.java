package com.project.block1project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class PCIe {


    public static void main(String[] args) throws IOException {

        //Checks if OS is linux
        if (Objects.equals(System.getProperty("os.name"), "Linux")) {
            // Execute the lspci command
            Process LSPCI = Runtime.getRuntime().exec("lspci -vv");

            // Read the output of the command
            BufferedReader PCIReader = new BufferedReader(new InputStreamReader(LSPCI.getInputStream()));
            String PCIPrintLine;

            //prints out the PCI device info line by line
            System.out.println("PCIe Devices:");
            while ((PCIPrintLine = PCIReader.readLine()) != null) {
                System.out.println(PCIPrintLine);


            }
        }
    }
}