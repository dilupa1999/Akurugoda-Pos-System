/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import gui.Home;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Dell
 */
public class MyMethods {
    
    //upload your image file to a desired dirrectory
    public static void copyUploadedImage(File sourceFile,String copyToPath){
        
        File targetDirectory = new File(copyToPath); // Replace with your target directory path eg - "F:\\GitHubNew\\POS_test2\\src\\product_images"

        // Check if the source file exists
        if (sourceFile.exists()) {
            // Create the target directory if it doesn't exist
            if (!targetDirectory.exists()) {
                targetDirectory.mkdirs();
            }

            try {
                // Construct the target file path
                Path targetPath = targetDirectory.toPath().resolve(sourceFile.getName());

                // Move the file to the new location
                Files.copy(sourceFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("File moved successfully to: " + targetPath.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Source file does not exist.");
        }
    }    
    
   
    
    //image setter
    public static ImageIcon resizeImage(int width,int height, String path) {
        ImageIcon image = null;
        byte[] pic = null;

        if (path != null) {
            image = new ImageIcon(path);
        } else {
            image = new ImageIcon(pic);
        }

        Image img = image.getImage();
        Image adjestedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(adjestedImage);
        return icon;
    }
    
    //table header font size changer
    public static void setTableHeaderFontSize(JTable table, int value) {
        JTableHeader header = table.getTableHeader();
        header.setFont(new java.awt.Font("Segoe UI Semibold", 0, value));        
        setTableHeaderBackground(table, new Color(232, 239, 249));
        styleTables(table);
    }
    
    //table header background color
    public static void setTableHeaderBackground(JTable table, Color color) {
        JTableHeader header = table.getTableHeader();
        header.setBackground(color);        
    }
    
    //style tables add cell spacing and row height and grid
    public static void styleTables(JTable table) {
        table.setIntercellSpacing(new java.awt.Dimension(10, 0));
        table.setRowHeight(40);
        table.setShowGrid(true);        
    }
    
     //InternetConnectionChecker
    public static boolean isInternetConnectionAvailable() {
        String urlToCheck = "https://www.google.com"; // Replace with a reliable website
        
        try {
            URL url = new URL(urlToCheck);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");

            int responseCode = connection.getResponseCode();

            // A response code in the range of 200-399 typically indicates a successful connection.
            return responseCode >= 200 && responseCode < 400;
        } catch (IOException e) {
            return false; // An exception occurred, indicating no internet connection.
        }
    }
       
}
