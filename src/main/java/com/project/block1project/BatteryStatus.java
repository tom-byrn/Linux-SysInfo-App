package com.project.block1project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BatteryStatus {

    public static void main(String[] args) throws IOException {

        String batteryVendor = "";
        String batterySize = "";
        String powerDraw = "";
        String timeRemaining = "";
        String batteryState = "";
        String batteryCharge = "";
        String batteryVoltage = "";
        String temperature = "";


        // Execute the command to get battery information
        Process upowerCommand = Runtime.getRuntime().exec("upower -i /org/freedesktop/UPower/devices/battery_BAT1");
        BufferedReader readUpower = new BufferedReader(new InputStreamReader(upowerCommand.getInputStream()));
        String upowerLineByLine;

        // Read the output
        while ((upowerLineByLine = readUpower.readLine()) != null) {
            upowerLineByLine.trim();
            if(upowerLineByLine.contains("vendor")) {
                batteryVendor = upowerLineByLine.replaceAll(" ", "").replaceAll("vendor:", "");
                batteryVendor = "Vendor: " + batteryVendor;
                System.out.println(batteryVendor);
            }
            if(upowerLineByLine.contains("energy-full") && !upowerLineByLine.contains("energy-full-design")){
                batterySize = upowerLineByLine.replaceAll(" ", "").replaceAll("Wh","").replaceAll("energy-full:", "");
                batterySize = "Battery Capacity: "+batterySize + " Wh";
                System.out.println(batterySize);
            }
            if(upowerLineByLine.contains("energy-rate")){
                powerDraw = upowerLineByLine.replaceAll(" ", "").replaceAll("W","").replaceAll("energy-rate:", "");;
                powerDraw = "Current Power Draw: " + powerDraw + " W";
                System.out.println(powerDraw);
            }
            if(upowerLineByLine.contains("time to empty")){
                timeRemaining = upowerLineByLine.replaceAll("  ", "").replaceAll("time to empty:", "").replaceAll(" time to empty:", "");
                timeRemaining = "Time Remaining: " + timeRemaining;
                System.out.println(timeRemaining);
            }
            if(upowerLineByLine.contains("state")){
                batteryState = upowerLineByLine.replaceAll(" ", "").replaceAll("state:", "");
                batteryState = "State: " + batteryState;
                System.out.println(batteryState);
            }
            if(upowerLineByLine.contains("energy:")) {
                batteryCharge = upowerLineByLine.replaceAll(" ", "").replaceAll("energy:", "").replaceAll("Wh","");
                batteryCharge = "Battery Charge: " + batteryCharge + " Wh";
                System.out.println(batteryCharge);
            }
            if(upowerLineByLine.contains("voltage:")) {
                batteryVoltage = upowerLineByLine.replaceAll(" ", "").replaceAll("voltage:", "").replaceAll("V","");
                batteryVoltage = "Battery Voltage: " + batteryVoltage + " V";
                System.out.println(batteryVoltage);
            }
            if(upowerLineByLine.contains("temperature")) {
                temperature = upowerLineByLine.replaceAll(" ", "").replaceAll("temperature:", "").replaceAll("degreesC", "");
                temperature = "Temperature: " + temperature + " Degrees Celsius";
                System.out.println(temperature);
            }

        }

    }
}
