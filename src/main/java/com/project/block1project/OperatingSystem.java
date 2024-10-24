//just a random comment
package com.project.block1project;

//Imports Locale, Has a list of all country and language abbreviations

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


//New Class, OS
public class OperatingSystem {

    public static void main(String[] args) throws IOException {
        String OS;



        // Checks if OS is linux
        if(Objects.equals(System.getProperty("os.name"), "Linux")){

            // Execute the uname-a command
            Process Uname = Runtime.getRuntime().exec("uname -a");

            // Read the output of the command
            BufferedReader ReadUname = new BufferedReader(new InputStreamReader(Uname.getInputStream()));

            //Changes OS to ReadUname
            OS = ReadUname.readLine();
        }else{
            OS = System.getProperty("os.name");
        }
    }

    @FXML
    public void initialize() {


    }

}
