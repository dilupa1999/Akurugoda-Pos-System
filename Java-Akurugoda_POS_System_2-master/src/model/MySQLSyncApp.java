/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

//import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
//import gui.Home;
import gui.Home;
import gui.Login;
import static gui.Login.logger;
import model.MySQL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class MySQLSyncApp {

    private Timer syncTimer;
    private boolean isSyncRunning = false;

    public void startSync() {

        //System.out.println("sync start");

        syncTimer = new Timer();
        syncTimer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                if (!isSyncRunning) {

                    if (MyMethods.isInternetConnectionAvailable()) {
                        isSyncRunning = true;
                        syncData();
                    }

                    isSyncRunning = false;
                }
            }
        }, 0, 20000); // Run every 10 seconds

    }

    public void syncData() {

        try {
             
            System.out.println("run");

            updateAndInsertBrand();
            System.out.println("onlinebrand");
            brandSync();
            System.out.println("localbrand");

            updateAndInsertProduct();
            System.out.println("onlineproduct");
            productSync();
            System.out.println("localproduct");

            updateAndInsertCustomer();
            System.out.println("onlineCustomer");
            customerSync();
            System.out.println("localCustomer");

            updateAndInsertEmployee();
            System.out.println("onlineemployee");
            employeeSync();
            System.out.println("localemployee");
            
            insertStatus();
            System.out.println("onlineStatus");
            updateAndInsertStatus();
            System.out.println("localStatus");

            updateAndInsertEmployeeHistory();
            System.out.println("onlineEmployeeHistory");
            employeeHistorySync();
            System.out.println("localEmployeeHistory");

            updateAndInsertInvoiceData();
            System.out.println("onlineInvoice");
            invoiceSync();
            System.out.println("localInvoice");
            
            updateAndInsertInvoiceItemData();
            System.out.println("onlineInvoiceItem");
            invoiceItemSync();
            System.out.println("localInvoiceItem");
            
            updateAndInsertInvoiceHistoryItemData();
            System.out.println("onlineInvoiceHistoryItem");
            invoiceHistoryItemSync();
            System.out.println("localInvoiceHistoryItem");

            updateAndInsertGrnData();
            System.out.println("onlineGrn");
            grnSync();
            System.out.println("localGrn");
            
            updateAndInsertGrnItemData();
            System.out.println("onlineGrnItem");
            grnItemSync();
            System.out.println("localGrnItem");
            
            updateAndInsertGrnHistoryItemData();
            System.out.println("onlineGrnHistoryItem");
            grnHistoryItemSync();
             System.out.println("localGrnHistoryItem");

            System.out.println("Data synchronized successfully!");

        } catch (Exception e) {

            if (Login.errorMsg == 1) {
//                HomeDelivery home = new HomeDelivery("", "", "");
//                JOptionPane.showMessageDialog(home, "Oops! please check your Internet connection ", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Login.errorMsg == 2) {
                Home home2 = new Home("", "", "");
                JOptionPane.showMessageDialog(home2, "Oops! please check your Internet connection ", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }

    }
    
   private void updateAndInsertEmployee() {
        try {
            ResultSet localResultSet = MySQL.execute("SELECT * FROM `employee`");

            while (localResultSet.next()) {
                String email = localResultSet.getString("email");
                String firstName = localResultSet.getString("first_name");
                String lastName = localResultSet.getString("last_name");
                String type = localResultSet.getString("type");
                String password = localResultSet.getString("password");
                int statusId = localResultSet.getInt("status_id");
                Timestamp localLastUpdateTime = localResultSet.getTimestamp("last_update_time");

                // Retrieve the corresponding record from the online database
                ResultSet onlineResultSet = onlineMYSQL.execute("SELECT * FROM `employee` WHERE `email` = '" + email + "'");

                if (onlineResultSet.next()) {
                    Timestamp onlineLastUpdateTime = onlineResultSet.getTimestamp("last_update_time");

                    // Compare the last update times to determine if an update is necessary
                    if (localLastUpdateTime.after(onlineLastUpdateTime)) {
                        // Update the online employee table with local data, including updating last_update_time
                        try {
                            // Extract data from the local result set
                            String firstNameValue = localResultSet.getString("first_name");
                            String lastNameValue = localResultSet.getString("last_name");
                            String typeValue = localResultSet.getString("type");
                            String passwordValue = localResultSet.getString("password");

                            // Construct the update SQL statement with last_update_time
                            String updateSQL = "UPDATE `employee` SET `first_name` = '" + firstNameValue + "', "
                                    + "`last_name` = '" + lastNameValue + "', `type` = '" + typeValue + "', "
                                    + "`password` = '" + passwordValue + "', `last_update_time` = '" + localLastUpdateTime + "' "
                                    + "WHERE `email` = '" + email + "'";
                            onlineMYSQL.execute(updateSQL);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    // The record doesn't exist in the online database, so insert it into the online database
                    try {
                        // Insert the record into the online employee table
                        // Construct the insert SQL statement
                        String insertSQL = "INSERT INTO `employee` (`email`, `first_name`, `last_name`, `type`, `password`, `last_update_time`, `status_id`) "
                                + "VALUES ('" + email + "', '" + firstName + "', '" + lastName + "', '" + type + "', '" + password + "', '" + localLastUpdateTime + "', '" + statusId + "')";
                        onlineMYSQL.execute(insertSQL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void employeeSync() {
    try {
        ResultSet resultSet = onlineMYSQL.execute("SELECT * FROM `employee`");

        while (resultSet.next()) {
            String email = resultSet.getString("email");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String type = resultSet.getString("type");
            String password = resultSet.getString("password");
            String lastUpdateTime = resultSet.getString("last_update_time");

            ResultSet resultSetEmployee = MySQL.execute("SELECT * FROM `employee` WHERE `email` = '" + email + "'");
            
            String onlineStatusId = resultSet.getString("status_id");

            if (resultSetEmployee.next()) {
                // The employee record exists in the local database
                String localStatusId = resultSetEmployee.getString("status_id");

                if (!localStatusId.equals(onlineStatusId)) {
                    // Update the local status_id to match the online status_id
                    try {
                        String updateSQL = "UPDATE `employee` SET `status_id` = '" + onlineStatusId + "' WHERE `email` = '" + email + "'";
                        MySQL.execute(updateSQL); // Update the local status_id
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // The employee record doesn't exist in the local database, so insert it
                try {
                    String insertSQL = "INSERT INTO `employee` (`email`, `first_name`, `last_name`, `type`, `password`, `status_id`, `last_update_time`) "
                            + "VALUES ('" + email + "', '" + firstName + "', '" + lastName + "', '" + type + "', '" + password + "', '" + onlineStatusId + "', '" + lastUpdateTime + "')";
                    MySQL.execute(insertSQL); // Insert into the local database
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    
    private void insertStatus() {
    try {
        ResultSet resultSet = MySQL.execute("SELECT * FROM `status`");

        while (resultSet.next()) {
            int statusId = resultSet.getInt("id");
            String statusName = resultSet.getString("status_n");

            ResultSet resultSetOnline = onlineMYSQL.execute("SELECT * FROM `status` WHERE `id` = " + statusId);

            if (resultSetOnline.next()) {
                // The status record exists in the online database
            } else {
                // The status record doesn't exist in the online database, so insert it
                try {
                    String insertSQL = "INSERT INTO `status` (`id`, `status_n`) "
                            + "VALUES (" + statusId + ", '" + statusName + "')";
                    onlineMYSQL.execute(insertSQL); // Insert into the online database
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    private void updateAndInsertStatus() {
    try {
        ResultSet onlineResultSet = onlineMYSQL.execute("SELECT * FROM `status`");

        while (onlineResultSet.next()) {
            int statusId = onlineResultSet.getInt("id");
            String statusName = onlineResultSet.getString("status_n");

            // Retrieve the corresponding record from the local database
            ResultSet localResultSet = MySQL.execute("SELECT * FROM `status` WHERE `id` = " + statusId);

            if (localResultSet.next()) {
                String localStatus = localResultSet.getString("status_n");

                // Compare the status_n values to determine if an update is necessary
                if (!statusName.equals(localStatus)) {
                    // Update the local status table with the online status_n value
                    try {
                        // Construct the update SQL statement
                        String updateSQL = "UPDATE `status` SET `status_n` = '" + statusName + "' WHERE `id` = " + statusId;
                        MySQL.execute(updateSQL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // The record doesn't exist in the local database, so insert it into the local status table
                try {
                    // Insert the record into the local status table
                    // The `id` column is AUTO_INCREMENT, so it will be automatically generated.
                    // Construct the insert SQL statement without specifying the `id` column.
                    String insertSQL = "INSERT INTO `status` (`id`,`status_n`) VALUES (" + statusId + ", '" + statusName + "')";
                    MySQL.execute(insertSQL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    private void updateAndInsertCustomer() {
        try {
            ResultSet localResultSet = MySQL.execute("SELECT * FROM `customer`");

            while (localResultSet.next()) {
                String mobile = localResultSet.getString("mobile");
                String firstName = localResultSet.getString("first_name");
                String lastName = localResultSet.getString("last_name");
                String email = localResultSet.getString("email");
                double balancePayment = localResultSet.getDouble("balance_payment");
                Timestamp localLastUpdateTime = localResultSet.getTimestamp("last_update_time");

                // Retrieve the corresponding record from the online database
                ResultSet onlineResultSet = onlineMYSQL.execute("SELECT * FROM `customer` WHERE `mobile` = '" + mobile + "'");

                if (onlineResultSet.next()) {
                    Timestamp onlineLastUpdateTime = onlineResultSet.getTimestamp("last_update_time");

                    // Compare the last update times to determine if an update is necessary
                    if (localLastUpdateTime.after(onlineLastUpdateTime)) {
                        // Update the online customer table with local data, including updating last_update_time
                        try {
                            // Extract data from the local result set
                            String firstNameValue = localResultSet.getString("first_name");
                            String lastNameValue = localResultSet.getString("last_name");
                            String emailValue = localResultSet.getString("email");
                            double balancePaymentValue = localResultSet.getDouble("balance_payment");

                            // Construct the update SQL statement with last_update_time
                            String updateSQL = "UPDATE `customer` SET `first_name` = '" + firstNameValue + "', "
                                    + "`last_name` = '" + lastNameValue + "', `email` = '" + emailValue + "', "
                                    + "`balance_payment` = " + balancePaymentValue + ", `last_update_time` = '" + localLastUpdateTime + "' "
                                    + "WHERE `mobile` = '" + mobile + "'";
                            onlineMYSQL.execute(updateSQL);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    // The record doesn't exist in the online database, so insert it into the online database
                    try {
                        // Insert the record into the online customer table
                        // Construct the insert SQL statement
                        String insertSQL = "INSERT INTO `customer` (`mobile`, `first_name`, `last_name`, `email`, `balance_payment`, `last_update_time`) "
                                + "VALUES ('" + mobile + "', '" + firstName + "', '" + lastName + "', '" + email + "', " + balancePayment + ", '" + localLastUpdateTime + "')";
                        onlineMYSQL.execute(insertSQL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void customerSync() {
        try {
            ResultSet resultSet = onlineMYSQL.execute("SELECT * FROM `customer`");

            while (resultSet.next()) {
                String mobile = resultSet.getString("mobile");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                double balancePayment = resultSet.getDouble("balance_payment");
                Timestamp lastUpdateTime = resultSet.getTimestamp("last_update_time");

                ResultSet resultSetCustomer = MySQL.execute("SELECT * FROM `customer` WHERE `mobile` = '" + mobile + "'");

                if (resultSetCustomer.next()) {
                    // The customer record exists in the local database
                } else {
                    // The customer record doesn't exist in the local database, so insert it
                    try {
                        String insertSQL = "INSERT INTO `customer` (`mobile`, `first_name`, `last_name`, `email`, `balance_payment`, `last_update_time`) "
                                + "VALUES ('" + mobile + "', '" + firstName + "', '" + lastName + "', '" + email + "', " + balancePayment + ", '" + lastUpdateTime + "')";
                        MySQL.execute(insertSQL); // Insert into the local database
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateAndInsertEmployeeHistory() {
        try {
            ResultSet localResultSet = MySQL.execute("SELECT * FROM `employee_history`");

            while (localResultSet.next()) {
                int employeeId = localResultSet.getInt("id");
                Timestamp loginTime = localResultSet.getTimestamp("Login_time");
                Timestamp logoutTime = localResultSet.getTimestamp("Logout_time");
                Timestamp localLastUpdateTime = localResultSet.getTimestamp("last_update_time");
                String employeeEmail = localResultSet.getString("employee_email");

                // Retrieve the corresponding record from the online database
                ResultSet onlineResultSet = onlineMYSQL.execute("SELECT * FROM `employee_history` WHERE `id` = " + employeeId);

                if (onlineResultSet.next()) {
                    Timestamp onlineLastUpdateTime = onlineResultSet.getTimestamp("last_update_time");

                    // Compare the last update times to determine if an update is necessary
                    if (localLastUpdateTime.after(onlineLastUpdateTime)) {
                        // Update the online employee_history table with local data, including updating last_update_time
                        try {
                            // Extract data from the local result set
                            String loginTimeValue = localResultSet.getString("Login_time");
                            String logoutTimeValue = localResultSet.getString("Logout_time");
                            String employeeEmailValue = localResultSet.getString("employee_email");

                            // Construct the update SQL statement with last_update_time
                            String updateSQL = "UPDATE `employee_history` SET `Login_time` = '" + loginTimeValue + "', `Logout_time` = '" + logoutTimeValue + "', `last_update_time` = '" + localLastUpdateTime + "', `employee_email` = '" + employeeEmailValue + "' WHERE `id` = " + employeeId;
                            onlineMYSQL.execute(updateSQL);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    // The record doesn't exist in the online database, so insert it into the online database
                    try {
                        // Insert the record into the online employee_history table
                        // Construct the insert SQL statement
                        String insertSQL = "INSERT INTO `employee_history` (`id`, `Login_time`, `Logout_time`, `last_update_time`, `employee_email`) VALUES (" + employeeId + ", '" + loginTime + "', '" + logoutTime + "', '" + localLastUpdateTime + "', '" + employeeEmail + "')";
                        onlineMYSQL.execute(insertSQL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void employeeHistorySync() {
        try {
            ResultSet resultSet = onlineMYSQL.execute("SELECT * FROM `employee_history`");

            while (resultSet.next()) {
                int employee_history_id = resultSet.getInt("id");
                Timestamp loginTime = resultSet.getTimestamp("Login_time");
                Timestamp logoutTime = resultSet.getTimestamp("Logout_time");
                Timestamp lastUpdateTime = resultSet.getTimestamp("last_update_time");
                String employee_email = resultSet.getString("employee_email");

                ResultSet resultSetEmployeeHistory = MySQL.execute("SELECT * FROM `employee_history` WHERE `id` = '" + employee_history_id + "' ");

                if (resultSetEmployeeHistory.next()) {
                    // The Employee History record exists in the local database
                } else {
                    // The Employee History record doesn't exist in the local database, so insert it
                    try {
                        String insertSQL = "INSERT INTO `employee_history` (`id`, `Login_time`, `Logout_time`, `last_update_time`, `employee_email`) "
                                + "VALUES ('" + employee_history_id + "', '" + loginTime + "', '" + logoutTime + "', '" + lastUpdateTime + "', '" + employee_email + "')";
                        MySQL.execute(insertSQL); // Insert into the local database
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateAndInsertBrand() {
        try {
            ResultSet localResultSet = MySQL.execute("SELECT * FROM `brand`");

            while (localResultSet.next()) {
                int brandId = localResultSet.getInt("id");
                String name = localResultSet.getString("name");
                Timestamp localLastUpdateTime = localResultSet.getTimestamp("last_update_time");

                // Retrieve the corresponding record from the online database
                ResultSet onlineResultSet = onlineMYSQL.execute("SELECT * FROM `brand` WHERE `id` = " + brandId);

                if (onlineResultSet.next()) {
                    Timestamp onlineLastUpdateTime = onlineResultSet.getTimestamp("last_update_time");

                    // Compare the last update times to determine if an update is necessary
                    if (localLastUpdateTime.after(onlineLastUpdateTime)) {
                        // Update the online brand table with local data, including updating last_update_time
                        try {
                            // Construct the update SQL statement with last_update_time
                            String updateSQL = "UPDATE `brand` SET `name` = '" + name + "', `last_update_time` = '" + localLastUpdateTime + "' WHERE `id` = " + brandId;
                            onlineMYSQL.execute(updateSQL);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    // The record doesn't exist in the online database, so insert it into the online database
                    try {
                        // Insert the record into the online brand table
                        // Construct the insert SQL statement
                        String insertSQL = "INSERT INTO `brand` (`id`, `name`, `last_update_time`) VALUES ('" + brandId + "', '" + name + "', '" + localLastUpdateTime + "')";
                        onlineMYSQL.execute(insertSQL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void brandSync() {
        try {
            ResultSet onlineResultSet = onlineMYSQL.execute("SELECT * FROM `brand`");

            while (onlineResultSet.next()) {
                int brandId = onlineResultSet.getInt("id");
                String name = onlineResultSet.getString("name");
                Timestamp lastUpdateTime = onlineResultSet.getTimestamp("last_update_time");

                // Check if the brand record exists in the local database
                ResultSet localResultSet = MySQL.execute("SELECT * FROM `brand` WHERE `id` = '" + brandId + "'");

                if (!localResultSet.next()) {
                    // The brand record doesn't exist in the local database, so insert it
                    try {
                        // Construct the insert SQL statement with new columns
                        String insertSQL = "INSERT INTO `brand` "
                                + "(`id`, `name`, `last_update_time`) "
                                + "VALUES ('" + brandId + "', '" + name + "', '" + lastUpdateTime + "')";
                        MySQL.execute(insertSQL); // Insert into the local database
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateAndInsertProduct() {
        try {
            ResultSet localResultSet = MySQL.execute("SELECT * FROM `product`");

            while (localResultSet.next()) {
                String productId = localResultSet.getString("id");
                String name = localResultSet.getString("name");
                int brandId = localResultSet.getInt("brand_id");
                double sellingPrice = localResultSet.getDouble("selling_price");
                double buyingPrice = localResultSet.getDouble("buying_price");
                double qty = localResultSet.getDouble("qty");
                Date mfg = localResultSet.getDate("mfg");
                Date exp = localResultSet.getDate("exp");
                Timestamp localLastUpdateTime = localResultSet.getTimestamp("last_update_time");

                // Retrieve the corresponding record from the online database
                ResultSet onlineResultSet = onlineMYSQL.execute("SELECT * FROM `product` WHERE `id` = '" + productId + "'");

                if (onlineResultSet.next()) {
                    Timestamp onlineLastUpdateTime = onlineResultSet.getTimestamp("last_update_time");

                    // Compare the last update times to determine if an update is necessary
                    if (localLastUpdateTime.after(onlineLastUpdateTime)) {
                        // Update the online product table with local data, including updating last_update_time
                        try {
                            // Construct the update SQL statement with last_update_time
                            String updateSQL = "UPDATE `product` SET "
                                    + "`name` = '" + name + "', "
                                    + "`brand_id` = " + brandId + ", "
                                    + "`selling_price` = " + sellingPrice + ", "
                                    + "`buying_price` = " + buyingPrice + ", "
                                    + "`qty` = " + qty + ", "
                                    + "`mfg` = '" + mfg + "', "
                                    + "`exp` = '" + exp + "', "
                                    + "`last_update_time` = '" + localLastUpdateTime + "' "
                                    + "WHERE `id` = '" + productId + "'";
                            onlineMYSQL.execute(updateSQL);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    // The record doesn't exist in the online database, so insert it into the online database
                    try {
                        // Construct the insert SQL statement with new columns
                        String insertSQL = "INSERT INTO `product` "
                                + "(`id`, `name`, `brand_id`, `selling_price`, `buying_price`, `qty`, `mfg`, `exp`, `last_update_time`) "
                                + "VALUES ('" + productId + "', '" + name + "', " + brandId + ", " + sellingPrice + ", "
                                + buyingPrice + ", " + qty + ", '" + mfg + "', '" + exp + "', '" + localLastUpdateTime + "')";
                        onlineMYSQL.execute(insertSQL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void productSync() {
        try {
            ResultSet onlineResultSet = onlineMYSQL.execute("SELECT * FROM `product`");

            while (onlineResultSet.next()) {
                String productId = onlineResultSet.getString("id");
                String name = onlineResultSet.getString("name");
                int brandId = onlineResultSet.getInt("brand_id");
                double sellingPrice = onlineResultSet.getDouble("selling_price");
                double buyingPrice = onlineResultSet.getDouble("buying_price");
                double qty = onlineResultSet.getDouble("qty");
                Date mfg = onlineResultSet.getDate("mfg");
                Date exp = onlineResultSet.getDate("exp");
                Timestamp lastUpdateTime = onlineResultSet.getTimestamp("last_update_time");

                // Check if the product record exists in the local database
                ResultSet localResultSet = MySQL.execute("SELECT * FROM `product` WHERE `id` = '" + productId + "'");

                if (!localResultSet.next()) {
                    // The product record doesn't exist in the local database, so insert it
                    try {
                        // Construct the insert SQL statement with new columns
                        String insertSQL = "INSERT INTO `product` "
                                + "(`id`, `name`, `brand_id`, `selling_price`, `buying_price`, `qty`, `mfg`, `exp`, `last_update_time`) "
                                + "VALUES ('" + productId + "', '" + name + "', " + brandId + ", " + sellingPrice + ", "
                                + buyingPrice + ", " + qty + ", '" + mfg + "', '" + exp + "', '" + lastUpdateTime + "')";
                        MySQL.execute(insertSQL); // Insert into the local database
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void updateAndInsertGrnData() {
    try {
        ResultSet localResultSet = MySQL.execute("SELECT * FROM `grn`");

        while (localResultSet.next()) {
            int grnId = localResultSet.getInt("id");
            String employeeEmail = localResultSet.getString("employee_email");
            Timestamp dateTime = localResultSet.getTimestamp("date_time");
            Double paidAmount = localResultSet.getDouble("paid_amount");
            Timestamp localLastUpdateTime = localResultSet.getTimestamp("last_update_time");

            // Retrieve the corresponding record from the online database
            ResultSet onlineResultSet = onlineMYSQL.execute("SELECT * FROM `grn` WHERE `id` = " + grnId);

            if (onlineResultSet.next()) {
                Timestamp onlineLastUpdateTime = onlineResultSet.getTimestamp("last_update_time");

                // Compare the last update times to determine if an update is necessary
                if (localLastUpdateTime.after(onlineLastUpdateTime)) {
                    // Update the online grn table with local data, including updating last_update_time
                    try {
                        // Construct the update SQL statement with last_update_time
                        String updateSQL = "UPDATE `grn` SET `employee_email` = '" + employeeEmail + "', `date_time` = '" + dateTime + "', `paid_amount` = " + paidAmount + ", `last_update_time` = '" + localLastUpdateTime + "' WHERE `id` = " + grnId;
                        onlineMYSQL.execute(updateSQL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // The record doesn't exist in the online database, so insert it into the online database
                try {
                    // Insert the record into the online grn table
                    // Construct the insert SQL statement
                    String insertSQL = "INSERT INTO `grn` (`id`, `employee_email`, `date_time`, `paid_amount`, `last_update_time`) VALUES (" + grnId + ", '" + employeeEmail + "', '" + dateTime + "', " + paidAmount + ", '" + localLastUpdateTime + "')";
                    onlineMYSQL.execute(insertSQL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private Timestamp getLastUpdateTimeFromLocalGrnTable(int grnId) {
    try {
        String query = "SELECT `last_update_time` FROM `grn` WHERE `id` = " + grnId;
        ResultSet resultSet = MySQL.execute(query);

        if (resultSet.next()) {
            return resultSet.getTimestamp("last_update_time");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

private Timestamp getLastUpdateTimeFromOnlineGrnTable(int grnId) {
    try {
        String query = "SELECT `last_update_time` FROM `grn` WHERE `id` = " + grnId;
        ResultSet resultSet = onlineMYSQL.execute(query);

        if (resultSet.next()) {
            return resultSet.getTimestamp("last_update_time");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

private void updateAndInsertGrnHistoryItemData() {
    try {
        ResultSet localResultSet = MySQL.execute("SELECT * FROM `grn_history_item`");

        while (localResultSet.next()) {
            int id = localResultSet.getInt("id");
            double qty = localResultSet.getDouble("qty");
            double buyingPrice = localResultSet.getDouble("buying_price");
            String name = localResultSet.getString("name");
            int grnId = localResultSet.getInt("grn_id");
            String brandName = localResultSet.getString("brand_name");

            // Get the last_update_time from the grn table
            Timestamp grnLastUpdateTimelocal = getLastUpdateTimeFromLocalGrnTable(grnId);
            Timestamp grnLastUpdateTimeOnline = getLastUpdateTimeFromOnlineGrnTable(grnId);

            // Construct the update SQL statement with last_update_time
            String updateSQL = "UPDATE `grn_history_item` SET `qty` = " + qty + ", `buying_price` = " + buyingPrice + ", `name` = '" + name + "', `grn_id` = " + grnId + ", `brand_name` = '" + brandName + "' WHERE `id` = " + id;

            if (grnLastUpdateTimelocal.after(grnLastUpdateTimeOnline)) {
                // Update the online grn_history_item table with local data, including updating last_update_time
                try {
                    onlineMYSQL.execute(updateSQL);
                } catch (SQLIntegrityConstraintViolationException duplicateKeyException) {
                    // Handle the case where the record already exists by attempting an update
                    // You can choose to log, skip, or handle the conflict in your preferred way.
                    // For example:
                    System.out.println("Record with id " + id + " already exists. Attempting update...");
                    onlineMYSQL.execute(updateSQL); // Attempt to update
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // The record doesn't exist in the online database, so insert it into the online grn_history_item table
                try {
                    // Construct the insert SQL statement
                    String insertSQL = "INSERT INTO `grn_history_item` (`id`, `qty`, `buying_price`, `name`, `grn_id`, `brand_name`) VALUES (" + id + ", " + qty + ", " + buyingPrice + ", '" + name + "', " + grnId + ", '" + brandName + "')";
                    onlineMYSQL.execute(insertSQL);
                } catch (SQLIntegrityConstraintViolationException duplicateKeyException) {
                    // Handle the case where the record already exists by attempting an update
                    System.out.println("Record with id " + id + " already exists. Attempting update...");
                    onlineMYSQL.execute(updateSQL); // Attempt to update
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


private void updateAndInsertGrnItemData() {
    try {
        ResultSet localResultSet = MySQL.execute("SELECT * FROM `grn_item`");

        while (localResultSet.next()) {
            int id = localResultSet.getInt("id");
            double qty = localResultSet.getDouble("qty");
            double buyingPrice = localResultSet.getDouble("buying_price");
            String productId = localResultSet.getString("product_id");
            int grnId = localResultSet.getInt("grn_id");

            // Get the last_update_time from the grn table
            Timestamp grnLastUpdateTimelocal = getLastUpdateTimeFromLocalGrnTable(grnId);
            Timestamp grnLastUpdateTimeOnline = getLastUpdateTimeFromOnlineGrnTable(grnId);

            // Construct the update SQL statement with last_update_time
            String updateSQL = "UPDATE `grn_item` SET `qty` = " + qty + ", `buying_price` = " + buyingPrice + ", `product_id` = '" + productId + "', `grn_id` = " + grnId + " WHERE `id` = " + id;

            if (grnLastUpdateTimelocal.after(grnLastUpdateTimeOnline)) {
                // Update the online grn_item table with local data, including updating last_update_time
                try {
                    onlineMYSQL.execute(updateSQL);
                } catch (SQLIntegrityConstraintViolationException duplicateKeyException) {
                    // Handle the case where the record already exists by attempting an update
                    System.out.println("Record with id " + id + " already exists. Attempting update...");
                    onlineMYSQL.execute(updateSQL); // Attempt to update
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // The record doesn't exist in the online database, so insert it into the online grn_item table
                try {
                    // Construct the insert SQL statement
                    String insertSQL = "INSERT INTO `grn_item` (`id`, `qty`, `buying_price`, `product_id`, `grn_id`) VALUES (" + id + ", " + qty + ", " + buyingPrice + ", '" + productId + "', " + grnId + ")";
                    onlineMYSQL.execute(insertSQL);
                } catch (SQLIntegrityConstraintViolationException duplicateKeyException) {
                    // Handle the case where the record already exists by attempting an update
                    System.out.println("Record with id " + id + " already exists. Attempting update...");
                    onlineMYSQL.execute(updateSQL); // Attempt to update
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


private void grnSync() {
    try {
        ResultSet resultSet = onlineMYSQL.execute("SELECT * FROM `grn`");

        while (resultSet.next()) {
            int grnId = resultSet.getInt("id");
            String employeeEmail = resultSet.getString("employee_email");
            Timestamp dateTime = resultSet.getTimestamp("date_time");
            Double paidAmount = resultSet.getDouble("paid_amount");
            Timestamp lastUpdateTime = resultSet.getTimestamp("last_update_time");

            ResultSet resultSetLocal = MySQL.execute("SELECT * FROM `grn` WHERE `id` = " + grnId);

            if (resultSetLocal.next()) {
                // The grn record exists in the local database
            } else {
                // The grn record doesn't exist in the local database, so insert it
                try {
                    String insertSQL = "INSERT INTO `grn` (`id`, `employee_email`, `date_time`, `paid_amount`, `last_update_time`) "
                            + "VALUES (" + grnId + ", '" + employeeEmail + "', '" + dateTime + "', " + paidAmount + ", '" + lastUpdateTime + "')";
                    MySQL.execute(insertSQL); // Insert into the local database
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void grnItemSync() {
    try {
        ResultSet resultSet = onlineMYSQL.execute("SELECT * FROM `grn_item`");

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            double qty = resultSet.getDouble("qty");
            double buyingPrice = resultSet.getDouble("buying_price");
            String productId = resultSet.getString("product_id");
            int grnId = resultSet.getInt("grn_id");

            ResultSet resultSetLocal = MySQL.execute("SELECT * FROM `grn_item` WHERE `id` = " + id);

            if (resultSetLocal.next()) {
                // The grn_item record exists in the local database
            } else {
                // The grn_item record doesn't exist in the local database, so insert it
                try {
                    String insertSQL = "INSERT INTO `grn_item` (`id`, `qty`, `buying_price`, `product_id`, `grn_id`) "
                            + "VALUES (" + id + ", " + qty + ", " + buyingPrice + ", '" + productId + "', " + grnId + ")";
                    MySQL.execute(insertSQL); // Insert into the local database
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


private void grnHistoryItemSync() {
    try {
        ResultSet resultSet = onlineMYSQL.execute("SELECT * FROM `grn_history_item`");

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double qty = resultSet.getDouble("qty");
            double buyingPrice = resultSet.getDouble("buying_price");
            int grnId = resultSet.getInt("grn_id");
            String brandName = resultSet.getString("brand_name");

            ResultSet resultSetLocal = MySQL.execute("SELECT * FROM `grn_history_item` WHERE `id` = " + id);

            if (resultSetLocal.next()) {
                // The grn_history_item record exists in the local database
            } else {
                // The grn_history_item record doesn't exist in the local database, so insert it
                try {
                    String insertSQL = "INSERT INTO `grn_history_item` (`id`, `name`, `qty`, `buying_price`, `grn_id`, `brand_name`) "
                            + "VALUES (" + id + ", '" + name + "', " + qty + ", " + buyingPrice + ", " + grnId + ", " + brandName + ")";
                    MySQL.execute(insertSQL); // Insert into the local database
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void updateAndInsertInvoiceData() {
    try {
        ResultSet localResultSet = MySQL.execute("SELECT * FROM `invoice`");

        while (localResultSet.next()) {
            int invoiceId = localResultSet.getInt("id");
            String customerMobile = localResultSet.getString("customer_mobile");
            String employeeEmail = localResultSet.getString("employee_email");
            Timestamp dateTime = localResultSet.getTimestamp("date_time");
            Double paidAmount = localResultSet.getDouble("paid_amount");
            Double discount = localResultSet.getDouble("discount");
            Timestamp localLastUpdateTime = localResultSet.getTimestamp("last_update_time");

            // Retrieve the corresponding record from the online database
            ResultSet onlineResultSet = onlineMYSQL.execute("SELECT * FROM `invoice` WHERE `id` = " + invoiceId);

            if (onlineResultSet.next()) {
                Timestamp onlineLastUpdateTime = onlineResultSet.getTimestamp("last_update_time");

                // Compare the last update times to determine if an update is necessary
                if (localLastUpdateTime.after(onlineLastUpdateTime)) {
                    // Update the online invoice table with local data, including updating last_update_time
                    try {
                        // Construct the update SQL statement with last_update_time
                        String updateSQL = "UPDATE `invoice` SET `customer_mobile` = '" + customerMobile + "', `employee_email` = '" + employeeEmail + "', `date_time` = '" + dateTime + "', `paid_amount` = " + paidAmount + ", `discount` = " + discount + ", `last_update_time` = '" + localLastUpdateTime + "' WHERE `id` = " + invoiceId;
                        onlineMYSQL.execute(updateSQL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // The record doesn't exist in the online database, so insert it into the online database
                try {
                    // Insert the record into the online invoice table
                    // Construct the insert SQL statement
                    String insertSQL = "INSERT INTO `invoice` (`id`, `customer_mobile`, `employee_email`, `date_time`, `paid_amount`, `discount`, `last_update_time`) VALUES (" + invoiceId + ", '" + customerMobile + "', '" + employeeEmail + "', '" + dateTime + "', " + paidAmount + ", " + discount + ", '" + localLastUpdateTime + "')";
                    onlineMYSQL.execute(insertSQL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private Timestamp getLastUpdateTimeFromLocalInvoiceTable(int invoiceId) {
    try {
        String query = "SELECT `last_update_time` FROM `invoice` WHERE `id` = " + invoiceId;
        ResultSet resultSet = MySQL.execute(query);

        if (resultSet.next()) {
            return resultSet.getTimestamp("last_update_time");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}


private Timestamp getLastUpdateTimeFromOnlineInvoiceTable(int invoiceId) {
    try {
        String query = "SELECT `last_update_time` FROM `invoice` WHERE `id` = " + invoiceId;
        ResultSet resultSet = onlineMYSQL.execute(query);

        if (resultSet.next()) {
            return resultSet.getTimestamp("last_update_time");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

private void updateAndInsertInvoiceHistoryItemData() {
    try {
        ResultSet localResultSet = MySQL.execute("SELECT * FROM `invoice_history_item`");

        while (localResultSet.next()) {
            int id = localResultSet.getInt("id");
            String name = localResultSet.getString("name");
            double qty = localResultSet.getDouble("qty");
            double buyingPrice = localResultSet.getDouble("buying_price");
            double sellingPrice = localResultSet.getDouble("selling_price");
            String brandName = localResultSet.getString("brand_name");
            int invoiceId = localResultSet.getInt("invoice_id");

            // Get the last_update_time from the invoice table
            Timestamp invoiceLastUpdateTimeLocal = getLastUpdateTimeFromLocalInvoiceTable(invoiceId);
            Timestamp invoiceLastUpdateTimeOnline = getLastUpdateTimeFromOnlineInvoiceTable(invoiceId);

            // Construct the update SQL statement with last_update_time
            String updateSQL = "UPDATE `invoice_history_item` SET `name` = '" + name + "', `qty` = " + qty + ", `buying_price` = " + buyingPrice + ", `selling_price` = " + sellingPrice + ", `brand_name` = '" + brandName + "', `invoice_id` = " + invoiceId + " WHERE `id` = " + id;

            if (invoiceLastUpdateTimeLocal.after(invoiceLastUpdateTimeOnline)) {
                // Update the online invoice_history_item table with local data, including updating last_update_time
                try {
                    onlineMYSQL.execute(updateSQL);
                } catch (SQLIntegrityConstraintViolationException duplicateKeyException) {
                    // Handle the case where the record already exists by attempting an update
                    System.out.println("Record with id " + id + " already exists. Attempting update...");
                    onlineMYSQL.execute(updateSQL); // Attempt to update
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // The record doesn't exist in the online database, so insert it into the online invoice_history_item table
                try {
                    // Construct the insert SQL statement
                    String insertSQL = "INSERT INTO `invoice_history_item` (`id`, `name`, `qty`, `buying_price`, `selling_price`, `brand_name`, `invoice_id`) VALUES ('" + id + "', '" + name + "', '" + qty + "', '" + buyingPrice + "', '" + sellingPrice + "','" + brandName + "', '" + invoiceId + "')";
                    onlineMYSQL.execute(insertSQL);
                } catch (SQLIntegrityConstraintViolationException duplicateKeyException) {
                    // Handle the case where the record already exists by attempting an update
                    System.out.println("Record with id " + id + " already exists. Attempting update...");
                    onlineMYSQL.execute(updateSQL); // Attempt to update
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void updateAndInsertInvoiceItemData() {
    try {
        ResultSet localResultSet = MySQL.execute("SELECT * FROM `invoice_item`");

        while (localResultSet.next()) {
            int id = localResultSet.getInt("id");
            double qty = localResultSet.getDouble("qty");
            double sellingPrice = localResultSet.getDouble("selling_price");
            String productId = localResultSet.getString("product_id");
            int invoiceId = localResultSet.getInt("invoice_id");

            // Get the last_update_time from the invoice table
            Timestamp invoiceLastUpdateTimeLocal = getLastUpdateTimeFromLocalInvoiceTable(invoiceId);
            Timestamp invoiceLastUpdateTimeOnline = getLastUpdateTimeFromOnlineInvoiceTable(invoiceId);

            // Construct the update SQL statement with last_update_time
            String updateSQL = "UPDATE `invoice_item` SET `qty` = " + qty + ", `selling_price` = " + sellingPrice + ", `product_id` = '" + productId + "', `invoice_id` = " + invoiceId + " WHERE `id` = " + id;

            if (invoiceLastUpdateTimeLocal.after(invoiceLastUpdateTimeOnline)) {
                // Update the online invoice_item table with local data, including updating last_update_time
                try {
                    onlineMYSQL.execute(updateSQL);
                } catch (SQLIntegrityConstraintViolationException duplicateKeyException) {
                    // Handle the case where the record already exists by attempting an update
                    System.out.println("Record with id " + id + " already exists. Attempting update...");
                    onlineMYSQL.execute(updateSQL); // Attempt to update
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // The record doesn't exist in the online database, so insert it into the online invoice_item table
                try {
                    // Construct the insert SQL statement
                    String insertSQL = "INSERT INTO `invoice_item` (`id`, `qty`, `selling_price`, `product_id`, `invoice_id`) VALUES (" + id + ", " + qty + ", " + sellingPrice + ", '" + productId + "', " + invoiceId + ")";
                    onlineMYSQL.execute(insertSQL);
                } catch (SQLIntegrityConstraintViolationException duplicateKeyException) {
                    // Handle the case where the record already exists by attempting an update
                    System.out.println("Record with id " + id + " already exists. Attempting update...");
                    onlineMYSQL.execute(updateSQL); // Attempt to update
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void invoiceSync() {
    try {
        ResultSet resultSet = onlineMYSQL.execute("SELECT * FROM `invoice`");

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String customerMobile = resultSet.getString("customer_mobile"); // Assuming this corresponds to customer mobile
            String employeeEmail = resultSet.getString("employee_email");
            Timestamp dateTime = resultSet.getTimestamp("date_time");
            Double paidAmount = resultSet.getDouble("paid_amount");
            Double discount = resultSet.getDouble("discount"); // Assuming this corresponds to discount
            Timestamp lastUpdateTime = resultSet.getTimestamp("last_update_time");

            ResultSet resultSetLocal = MySQL.execute("SELECT * FROM `invoice` WHERE `id` = " + id);

            if (resultSetLocal.next()) {
                // The invoice record exists in the local database
            } else {
                // The invoice record doesn't exist in the local database, so insert it
                try {
                    String insertSQL = "INSERT INTO `invoice` (`id`, `customer_mobile`, `employee_email`, `date_time`, `paid_amount`, `discount`, `last_update_time`) "
                            + "VALUES (" + id + ", '" + customerMobile + "', '" + employeeEmail + "', '" + dateTime + "', " + paidAmount + ", " + discount + ", '" + lastUpdateTime + "')";
                    MySQL.execute(insertSQL); // Insert into the local database
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void invoiceItemSync() {
    try {
        ResultSet resultSet = onlineMYSQL.execute("SELECT * FROM `invoice_item`");

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            double qty = resultSet.getDouble("qty");
            double sellingPrice = resultSet.getDouble("selling_price"); // Assuming this corresponds to selling_price
            String productId = resultSet.getString("product_id");
            int invoiceId = resultSet.getInt("invoice_id"); // Assuming this corresponds to invoice_id

            ResultSet resultSetLocal = MySQL.execute("SELECT * FROM `invoice_item` WHERE `id` = " + id);

            if (resultSetLocal.next()) {
                // The invoice_item record exists in the local database
            } else {
                // The invoice_item record doesn't exist in the local database, so insert it
                try {
                    String insertSQL = "INSERT INTO `invoice_item` (`id`, `qty`, `selling_price`, `product_id`, `invoice_id`) "
                            + "VALUES (" + id + ", " + qty + ", " + sellingPrice + ", '" + productId + "', " + invoiceId + ")";
                    MySQL.execute(insertSQL); // Insert into the local database
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void invoiceHistoryItemSync() {
    try {
        ResultSet resultSet = onlineMYSQL.execute("SELECT * FROM `invoice_history_item`");

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double qty = resultSet.getDouble("qty");
            double buyingPrice = resultSet.getDouble("buying_price");
            double sellingPrice = resultSet.getDouble("selling_price"); // Assuming this corresponds to selling_price
            String brandName = resultSet.getString("brand_name");
            int invoiceId = resultSet.getInt("invoice_id"); // Assuming this corresponds to invoice_id

            ResultSet resultSetLocal = MySQL.execute("SELECT * FROM `invoice_history_item` WHERE `id` = " + id);

            if (resultSetLocal.next()) {
                // The invoice_history_item record exists in the local database
            } else {
                // The invoice_history_item record doesn't exist in the local database, so insert it
                try {
                    String insertSQL = "INSERT INTO `invoice_history_item` (`id`, `name`, `qty`, `buying_price`, `selling_price`, `invoice_id`, `brand_name`) "
                            + "VALUES (" + id + ", '" + name + "', " + qty + ", " + buyingPrice + ", " + sellingPrice + ", " + invoiceId + ", " + brandName + ")";
                    MySQL.execute(insertSQL); // Insert into the local database
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
