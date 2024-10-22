package com.project.block1project;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Home {


    @FXML
    private Label labelOS;

    @FXML
    private Label labelOSBit;

    @FXML
    private Label labelOSVersion;

    @FXML
    private Label labelOSArchitecture;

    @FXML
    private Label labelCountry;

    @FXML
    private Label labelLanguageAbbreviation;

    @FXML
    private Label labelUser;

    @FXML
    public void initialize(){
        System.out.println("Home.java initialize() is running");

        // Get system properties
        String OS = System.getProperty("os.name");
        String OS_bit = System.getProperty("sun.arch.data.model");
        String OS_Version = System.getProperty("os.version");
        String OS_Architecture = System.getProperty("os.arch");
        String country = System.getProperty("user.country");
        String LanguageAbbreviation = System.getProperty("user.language");
        String user = System.getProperty("user.name");

        //Print statements to test if this is being run
        System.out.println(OS);
        System.out.println(OS_bit);

        // Set the labels with values
        //labelOS.setText("Operating System: " + OS);
        if (labelOSBit != null) labelOSBit.setText("OS Bit: " + OS_bit);
        if (labelOSVersion != null) labelOSVersion.setText("OS Version: " + OS_Version);
        if (labelOSArchitecture != null) labelOSArchitecture.setText("OS Architecture: " + OS_Architecture);
        if (labelCountry != null) labelCountry.setText("Country: " + country);
        if (labelLanguageAbbreviation != null) labelLanguageAbbreviation.setText("Language Abbreviation: " + LanguageAbbreviation);
        if (labelUser != null) labelUser.setText("User: " + user);

    }
}
