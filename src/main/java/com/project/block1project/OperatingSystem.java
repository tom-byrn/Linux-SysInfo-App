package com.project.block1project;


import java.util.Locale;

public class OperatingSystem {
    public static void main(String[] args) {
        String OS = System.getProperty("os.name");
        String OS_bit = System.getProperty("sun.arch.data.model");
        String OS_Version = System.getProperty("os.version");
        String OS_Architecture = System.getProperty("os.arch");
        String country = System.getProperty("user.country");
        String LanguageAbbreviation = System.getProperty("user.language");
        String user = System.getProperty("user.name");

        //checks if the country or language system properties are not set
        if (country == null || LanguageAbbreviation == null) {
            System.err.println("Country or language system properties are not set.");
            return;
        }

        Locale locale = new Locale(LanguageAbbreviation, country);
        String CountryName = locale.getDisplayCountry();
        String language = locale.getDisplayLanguage();

        System.out.printf("%s%s%s%s%s%n%s%s%n%s%s%n", "OS = ", OS, " ", OS_bit, "-bit ", "Version = ", OS_Version, "OS Architecture = ", OS_Architecture);
        System.out.printf("%s%s%n", "Country = ", CountryName);
        System.out.printf("%s%s%n", "System Language = ", language);
        System.out.printf("%s%s%n", "Current User = ", user);
    }
}
