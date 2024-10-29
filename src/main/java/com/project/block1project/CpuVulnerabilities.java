package com.project.block1project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CpuVulnerabilities {
    public static void main(String[] args) throws IOException {

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

        for (String result : cpuVulnerablitiesArray) {
            System.out.println(result);
        }
        //cpuVulnerablitiesArray = Array with all cpu vulnerabilities and warnings next to the ones you're vulnerable to and have no mitgations
    }
}
