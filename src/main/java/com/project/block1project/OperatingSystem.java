//just a random comment
package com.project.block1project;

//Imports Locale, Has a list of all country and language abbreviations
import java.util.Locale;



//New Class, OS
public class OperatingSystem {
    public static void main(String[] args) {

        //Get Operating system properties and save them as strings
        String OS = System.getProperty("os.name");
        String OS_bit = System.getProperty("sun.arch.data.model");
        String OS_Version = System.getProperty("os.version");
        String OS_Architecture = System.getProperty("os.arch");
        String country = System.getProperty("user.country");
        String LanguageAbbreviation = System.getProperty("user.language");
        String user = System.getProperty("user.name");

        if (country == null || LanguageAbbreviation == null) {
            System.err.println("Country or language system properties are not set.");
            return;
        }


        //Converts country code into country name
        Locale locale = new Locale(LanguageAbbreviation, country);
        String CountryName = locale.getDisplayCountry();
        String language = locale.getDisplayLanguage();

        //Formatting for OS type and build
        System.out.printf("%s%s%s%s%s%n%s%s%n%s%s%n","OS = ", OS, " ", OS_bit, "-bit ","Version = ", OS_Version, "OS Architecture = ", OS_Architecture );

        //Formatting fo country, language and user
        System.out.printf("%s%s%n", "Country = ", CountryName);
        System.out.printf("%s%s%n", "System Language = ", language);
        System.out.printf("%s%s%n",  "Current User = ", user);



    }
}
