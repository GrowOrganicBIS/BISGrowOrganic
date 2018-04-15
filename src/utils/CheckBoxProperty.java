/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Timothy
 */

import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

/**
 * Code is derived from https://www.mkyong.com/java/java-properties-file-examples/
 */

public final class CheckBoxProperty {
    // get the current working directory using System.getProperty("user.dir")
    // and checkbox.properties will be created under the current working directory
    private static final String checkBoxPropertyString = System.getProperty("user.dir") + File.separator + "checkbox.properties";
    private static Properties properties = null;
    private static OutputStream outputStream = null;
    private static InputStream inputStream = null;

    // Constructor to load property
    static {
        try {
            // create an instance of Properties
            properties = new Properties();
            //inputStream = CheckBoxProperty.class.getClassLoader().getResourceAsStream(checkBoxPropertyString);
            // wrap checkBoxPeropetyString in File format so that we could use exists() method in file
            File propertyFile = new File(checkBoxPropertyString);
            System.out.println(checkBoxPropertyString);
            // if the property file does not exist
            if (!propertyFile.exists()) {
                // create file
                Files.createFile(propertyFile.toPath());
            }
            // wrap checkBoxPropertyString into FileInputStream
            inputStream = new FileInputStream(checkBoxPropertyString);
            if (inputStream == null) {
                System.out.println("Sorry, unable to find " + checkBoxPropertyString);
            }
            // inputStream is used to load data into properties
            properties.load(inputStream);
        } catch (Exception e) {
            System.out.println("Initialising constructor CheckBoxProperty: " + e.toString());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                System.out.println("Initialising constructor CheckBoxProperty:: outputStream does not close properly: " + e.toString());
            }
        }
    }

    public static boolean isCheckBoxChecked() {
        // this if is to make sure if there is no such a property called
        // isCheckBoxChecked, it will return false
        if (properties.getProperty("isCheckBoxChecked") == null) {
            return false;
        }

        if (properties.getProperty("isCheckBoxChecked").equals("true")) {
            return true;
        }
        return false;
    }

    public static void updateCheckBoxProperty(String updatedValue) {
        try {
            // Wrap checkBoxPropertyString into FileOutputStream
            outputStream = new FileOutputStream(checkBoxPropertyString);
            // Set the updateValue into properties
            properties.setProperty("isCheckBoxChecked", updatedValue);
            // Update and Apply changes
            properties.store(outputStream, null);
        } catch (Exception e) {
            System.out.println("updateCheckBoxProperty Failed: " + e.toString());
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                System.out.println("updateCheckBoxProperty: outputStream does not close properly: " + e.toString());
            }
        }
    }
}