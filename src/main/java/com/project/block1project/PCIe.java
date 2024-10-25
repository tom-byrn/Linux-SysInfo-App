package com.project.block1project;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.ArrayList;


public class PCIe {




    public static void main(String[] args) throws IOException {

        //Checks if OS is linux
        if (Objects.equals(System.getProperty("os.name"), "Linux")) {


            // Execute the lspci command
            Process LSPCI = Runtime.getRuntime().exec("lspci -vvv -nn");
            Process NoPCIDevices = Runtime.getRuntime().exec("lspci");

            // Read the output of the command
            BufferedReader PCIReader = new BufferedReader(new InputStreamReader(LSPCI.getInputStream()));
            String PCIPrintLine;

            BufferedReader NoPCIDevicesReader = new BufferedReader(new InputStreamReader(NoPCIDevices.getInputStream()));

            int DeviceCount = 0;
            while ((NoPCIDevicesReader.readLine()) != null) {
                DeviceCount++;
            }


            //prints out the PCI device info line by line
            System.out.println("There are " + DeviceCount + " PCIe devices connected");

            //Create PCI Array
            String[] array =new String[261] ;



            while ((PCIPrintLine = PCIReader.readLine()) != null) {

               array [260] = PCIPrintLine;

            }

            for(int counter = 1; counter<=array.length; counter++){

                System.out.println(counter + array[260]);

            }
            //System.out.println(PCIPrintLine);
        }
    }
}



