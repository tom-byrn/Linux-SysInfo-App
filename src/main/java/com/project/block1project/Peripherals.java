package com.project.block1project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Peripherals extends HelloController {
    public static void main(String[] args) throws IOException {

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
                usbImportantInfoArrayList.add("Vendor ID: " + idVendor + "\nVendor: " + usbVendorName);

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
                usbImportantInfoArrayList.add("Product ID: " + idProduct + "\nDevice Name: " + usbProductName + "\n");

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
    }
}
