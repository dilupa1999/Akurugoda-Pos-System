/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import static gui.Login.logger;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.MyMethods;
import model.MySQL;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Dell
 */
public class InvoiceHistory extends javax.swing.JPanel {

    /**
     * Creates new form InvoiceHistory
     */
    public InvoiceHistory() {
        initComponents();

        loadInvoice();
        MyMethods.setTableHeaderFontSize(jTable10, 16);
        MyMethods.setTableHeaderFontSize(jTable11, 16);
    }

   

    private void loadInvoice() {
        String invoiceID = jFormattedTextField12.getText().trim();
        String customerMobile = jTextField28.getText().trim();
        String date = jTextField1.getText().trim();

        try {

            ResultSet resultset = MySQL.execute("SELECT * FROM invoice INNER JOIN customer ON customer.mobile = invoice.customer_mobile "
                    + "WHERE id LIKE '%" + invoiceID + "%' AND customer_mobile LIKE '%" + customerMobile + "%' "
                    + "AND date_time LIKE '%" + date + "%'");

            DefaultTableModel model = (DefaultTableModel) jTable10.getModel();
            model.setRowCount(0);

            Set<String> processedCustomers = new HashSet<>();
            double totalEarnings = 0;
            double totalProfit = 0;
            double totalLoanAmount = 0;
            String customerId = null;
            Map<String, Double> customerLoanMap = new HashMap<>(); // Store calculated loan amounts for customers

            while (resultset.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(resultset.getString("id"));
                vector.add(resultset.getString("customer_mobile"));
                vector.add(resultset.getString("employee_email"));

                String inputDTime = resultset.getString("date_time");

                try {
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date idate = inputFormat.parse(inputDTime);

                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                    String formattedDTime = outputFormat.format(idate);

                    vector.add(formattedDTime);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                vector.add(resultset.getString("paid_amount"));
                vector.add(resultset.getString("discount"));

                //serch items to calculate total of each nvoice
                double loanAmount = 0;
                double buyingTotal = 0;
                double invoiceTotal = 0;
                double discountTotal = 0;

                ResultSet resultset2 = MySQL.execute("SELECT * FROM invoice_history_item WHERE invoice_id='" + resultset.getString("id") + "'");

                while (resultset2.next()) {

                    buyingTotal += resultset2.getDouble("qty") * resultset2.getDouble("buying_price");
                    invoiceTotal += resultset2.getDouble("qty") * resultset2.getDouble("selling_price");

                    customerId = resultset.getString("customer_mobile");

                    if (!processedCustomers.contains(customerId)) {

                        if (!customerLoanMap.containsKey(customerId)) {
                            loanAmount = resultset.getDouble("customer.balance_payment");
                            customerLoanMap.put(customerId, loanAmount);
                            totalLoanAmount += loanAmount;
                        }
                        processedCustomers.add(customerId);
                    }

                }

                discountTotal += resultset.getDouble("discount");

                totalProfit += ((invoiceTotal - buyingTotal) - discountTotal) + loanAmount;
                totalEarnings += (invoiceTotal - discountTotal) + loanAmount;

                vector.add(String.valueOf(invoiceTotal));

                model.addRow(vector);
            }

            jLabel6.setText(String.valueOf(totalProfit));
            jLabel7.setText(String.valueOf(totalEarnings));
            jLabel5.setText(String.valueOf(totalLoanAmount));
            jLabel92.setText("Invoices (" + model.getRowCount() + ")");

        } catch (Exception e) {
            logger.log(Level.WARNING, "loadInvoice_history", e);
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        card8 = new javax.swing.JPanel();
        jPanel95 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jButton40 = new javax.swing.JButton();
        jPanel103 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel97 = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        jFormattedTextField12 = new javax.swing.JFormattedTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel100 = new javax.swing.JPanel();
        jLabel83 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel99 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel82 = new javax.swing.JLabel();
        jTextField28 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable10 = new javax.swing.JTable();
        jLabel92 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel110 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable11 = new javax.swing.JTable();
        jLabel91 = new javax.swing.JLabel();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        card8.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        card8.setLayout(new java.awt.BorderLayout(0, 10));

        jPanel95.setPreferredSize(new java.awt.Dimension(385, 100));
        jPanel95.setLayout(new javax.swing.BoxLayout(jPanel95, javax.swing.BoxLayout.LINE_AXIS));

        jPanel3.setLayout(new java.awt.BorderLayout(10, 0));

        jPanel8.setPreferredSize(new java.awt.Dimension(190, 78));
        jPanel8.setLayout(new java.awt.GridLayout(2, 1, 0, 10));

        jButton40.setBackground(new java.awt.Color(0, 153, 255));
        jButton40.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton40.setForeground(new java.awt.Color(255, 255, 255));
        jButton40.setText("Search Invoice");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton40);

        jPanel103.setOpaque(false);
        jPanel103.setLayout(new java.awt.BorderLayout(5, 0));

        jButton1.setBackground(new java.awt.Color(0, 153, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reload1.png"))); // NOI18N
        jButton1.setPreferredSize(new java.awt.Dimension(45, 31));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel103.add(jButton1, java.awt.BorderLayout.LINE_END);

        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton2.setText("Save report");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel103.add(jButton2, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel103);

        jPanel3.add(jPanel8, java.awt.BorderLayout.EAST);

        jPanel11.setLayout(new java.awt.GridLayout(1, 3, 20, 0));

        jPanel9.setLayout(new java.awt.GridLayout(2, 1, 0, 5));

        jPanel97.setOpaque(false);
        jPanel97.setLayout(new java.awt.BorderLayout(10, 0));

        jLabel80.setBackground(new java.awt.Color(153, 204, 255));
        jLabel80.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel80.setText("Invoice ID");
        jPanel97.add(jLabel80, java.awt.BorderLayout.WEST);

        jFormattedTextField12.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jFormattedTextField12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jFormattedTextField12.setPreferredSize(new java.awt.Dimension(200, 22));
        jFormattedTextField12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField12KeyReleased(evt);
            }
        });
        jPanel97.add(jFormattedTextField12, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel97);

        jPanel6.setLayout(new java.awt.BorderLayout(10, 0));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 255));
        jLabel3.setText("Total profit :");
        jPanel6.add(jLabel3, java.awt.BorderLayout.LINE_START);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("0.00");
        jPanel6.add(jLabel6, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel6);

        jPanel11.add(jPanel9);

        jPanel10.setLayout(new java.awt.GridLayout(2, 1, 0, 5));

        jPanel100.setOpaque(false);
        jPanel100.setLayout(new java.awt.BorderLayout());

        jLabel83.setBackground(new java.awt.Color(153, 204, 255));
        jLabel83.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel83.setText("Date");
        jLabel83.setPreferredSize(new java.awt.Dimension(50, 25));
        jPanel100.add(jLabel83, java.awt.BorderLayout.WEST);

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel100.add(jTextField1, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel100);

        jPanel5.setLayout(new java.awt.BorderLayout(10, 0));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 255));
        jLabel2.setText("Total earnings :");
        jPanel5.add(jLabel2, java.awt.BorderLayout.LINE_START);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("0.00");
        jPanel5.add(jLabel7, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel5);

        jPanel11.add(jPanel10);

        jPanel99.setOpaque(false);
        jPanel99.setLayout(new java.awt.GridLayout(2, 1, 0, 5));

        jPanel12.setLayout(new java.awt.BorderLayout());

        jLabel82.setBackground(new java.awt.Color(153, 204, 255));
        jLabel82.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel82.setText("<html> <p>Customer mobile</p> <html>");
        jLabel82.setPreferredSize(new java.awt.Dimension(90, 25));
        jPanel12.add(jLabel82, java.awt.BorderLayout.WEST);

        jTextField28.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField28.setPreferredSize(new java.awt.Dimension(200, 22));
        jTextField28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField28ActionPerformed(evt);
            }
        });
        jTextField28.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField28KeyReleased(evt);
            }
        });
        jPanel12.add(jTextField28, java.awt.BorderLayout.CENTER);

        jPanel99.add(jPanel12);

        jPanel7.setLayout(new java.awt.BorderLayout(10, 0));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel4.setText("Pending payments: ");
        jPanel7.add(jLabel4, java.awt.BorderLayout.LINE_START);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("0.00");
        jPanel7.add(jLabel5, java.awt.BorderLayout.CENTER);

        jPanel99.add(jPanel7);

        jPanel11.add(jPanel99);

        jPanel3.add(jPanel11, java.awt.BorderLayout.CENTER);

        jPanel95.add(jPanel3);

        card8.add(jPanel95, java.awt.BorderLayout.NORTH);

        jSplitPane1.setDividerLocation(350);
        jSplitPane1.setDividerSize(10);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel1.setPreferredSize(new java.awt.Dimension(950, 250));
        jPanel1.setLayout(new java.awt.BorderLayout(0, 5));

        jTable10.setAutoCreateRowSorter(true);
        jTable10.setFont(new java.awt.Font("Iskoola Pota", 0, 16)); // NOI18N
        jTable10.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Invoice ID", "Customer mobile", "Employee", "Date_time", "Paid_amount", "Discount", "Invoice total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable10.setIntercellSpacing(new java.awt.Dimension(10, 0));
        jTable10.setRowHeight(40);
        jTable10.setSelectionBackground(new java.awt.Color(224, 224, 224));
        jTable10.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTable10.getTableHeader().setReorderingAllowed(false);
        jTable10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable10MouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(jTable10);
        if (jTable10.getColumnModel().getColumnCount() > 0) {
            jTable10.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTable10.getColumnModel().getColumn(5).setPreferredWidth(0);
            jTable10.getColumnModel().getColumn(6).setPreferredWidth(30);
        }

        jPanel1.add(jScrollPane10, java.awt.BorderLayout.CENTER);

        jLabel92.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel92.setText("Invoices (0)");
        jPanel1.add(jLabel92, java.awt.BorderLayout.NORTH);

        jSplitPane1.setTopComponent(jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout(0, 5));

        jPanel110.setBackground(new java.awt.Color(255, 255, 204));

        jTable11.setAutoCreateRowSorter(true);
        jTable11.setFont(new java.awt.Font("Iskoola Pota", 0, 16)); // NOI18N
        jTable11.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Stock ID", "Brand", "Name", "Quantity", "Selling price", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable11.setIntercellSpacing(new java.awt.Dimension(10, 0));
        jTable11.setRowHeight(40);
        jTable11.setSelectionBackground(new java.awt.Color(224, 224, 224));
        jTable11.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTable11.getTableHeader().setReorderingAllowed(false);
        jScrollPane11.setViewportView(jTable11);
        if (jTable11.getColumnModel().getColumnCount() > 0) {
            jTable11.getColumnModel().getColumn(0).setPreferredWidth(20);
            jTable11.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTable11.getColumnModel().getColumn(3).setPreferredWidth(20);
            jTable11.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        javax.swing.GroupLayout jPanel110Layout = new javax.swing.GroupLayout(jPanel110);
        jPanel110.setLayout(jPanel110Layout);
        jPanel110Layout.setHorizontalGroup(
            jPanel110Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        );
        jPanel110Layout.setVerticalGroup(
            jPanel110Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel110, java.awt.BorderLayout.CENTER);

        jLabel91.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel91.setText("Invoice  items (0)");
        jPanel2.add(jLabel91, java.awt.BorderLayout.NORTH);

        jSplitPane1.setRightComponent(jPanel2);

        card8.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        add(card8);
    }// </editor-fold>//GEN-END:initComponents

    private void jTable10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable10MouseClicked
        // TODO add your handling code here:

        String InvoiceID = String.valueOf(jTable10.getValueAt(jTable10.getSelectedRow(), 0));

        try {
            ResultSet resultset = MySQL.execute("SELECT * FROM `invoice_history_item` "
                    + "INNER JOIN `invoice` ON `invoice_history_item`.`invoice_id` = `invoice`.`id` "
                    + "INNER JOIN `customer` ON `invoice`.`customer_mobile` = `customer`.`mobile` WHERE `invoice_id` ='" + InvoiceID + "'");

            DefaultTableModel model = (DefaultTableModel) jTable11.getModel();
            model.setRowCount(0);

            double grnTotal = 0;
            int totalItems = 0;

            boolean found = false;

            while (resultset.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(resultset.getString("invoice_history_item.id"));
                vector.add(resultset.getString("brand_name"));
                vector.add(resultset.getString("name"));
                vector.add(resultset.getString("invoice_history_item.qty"));

                vector.add(resultset.getString("selling_price"));

                double itemTotal = resultset.getDouble("invoice_history_item.qty") * resultset.getDouble("selling_price");

                vector.add(String.valueOf(itemTotal));

                model.addRow(vector);

                grnTotal += itemTotal;
                totalItems++;

                found = true;
            }

            if (!found) {
                model.setRowCount(0);
            }

//            jLabel87.setText(String.valueOf(grnTotal)); //deppricated
            jLabel91.setText("Invoice items (" + String.valueOf(totalItems) + ")");

        } catch (Exception e) {
            logger.log(Level.WARNING, "jTable1_mouseClick", e);
            e.printStackTrace();
        }


    }//GEN-LAST:event_jTable10MouseClicked

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        // TODO add your handling code here:

        loadInvoice();

    }//GEN-LAST:event_jButton40ActionPerformed

    private void jFormattedTextField12KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField12KeyReleased
        // TODO add your handling code here:
        loadInvoice();
    }//GEN-LAST:event_jFormattedTextField12KeyReleased

    private void jTextField28KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField28KeyReleased
        // TODO add your handling code here:
        loadInvoice();
    }//GEN-LAST:event_jTextField28KeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            int row = jTable10.getSelectedRow();

            double netTotal = 0;
            for (int i = 0; i < jTable10.getRowCount(); i++) {
                netTotal += Double.parseDouble(String.valueOf(jTable10.getValueAt(i, 6)));
            }

            //            WE CAN USE ONLY NEDBEANS IDE
            
//            String userDirectory = FileSystems.getDefault()
//                    .getPath("")
//                    .toAbsolutePath()
//                    .toString();
//
//            String tempUrl = "src/reports/delivery_new_invoice_report.jasper"; // for testing
//            String suburl = "src/reports/delivery_invoice_items.jasper"; // for testing
//            
//             WE CAN USE AFTER BUILD

            String userDirectory = FileSystems.getDefault()
                    .getPath("")
                    .toAbsolutePath()
                    .toString();

            String newpath = userDirectory.substring(0, userDirectory.lastIndexOf("\\"));
//            System.out.println(newpath);
//
            String tempUrl = newpath + "\\src\\reports\\delivery_new_invoice_report.jasper";

//accessing data from JASON file

            String JSONurl = userDirectory + "\\lib\\databs.json";

            Object obj = new JSONParser().parse(new FileReader(JSONurl));
            JSONObject j = (JSONObject) obj;

            String db = String.valueOf(j.get("databaseName"));
            String username = String.valueOf(j.get("username"));
            String password = String.valueOf(j.get("password"));

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db, username, password);
            
            java.util.HashMap<String, Object> parameters = new HashMap<>();

            parameters.put("Parameter5", String.valueOf(netTotal));
            parameters.put("Parameter7", date);
            parameters.put("Parameter8", jLabel6.getText());
            parameters.put("Parameter4", jLabel5.getText());
            parameters.put("Parameter3", jLabel7.getText());

            JRTableModelDataSource datasource = new JRTableModelDataSource(jTable10.getModel());

            JasperFillManager.fillReport(tempUrl, parameters, connection);
            JasperPrint report = JasperFillManager.fillReport(tempUrl, parameters, datasource);
            //JasperPrintManager.printReport(report, false); //prirent report dirrectly
            JasperViewer.viewReport(report, false); //for testing

        } catch (Exception e) {
            logger.log(Level.WARNING, "print_invoice_history_btn", e);
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTable11.getModel();

        model.setRowCount(0);
        //jTable10.clearSelection();
//        jLabel87.setText("0.00");

        jFormattedTextField12.setText("");
        jTextField28.setText("");
        jTextField1.setText("");
        jLabel91.setText("Invoice  items (0)");
        loadInvoice();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField28ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField28ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        loadInvoice();
    }//GEN-LAST:event_jTextField1KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel card8;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton40;
    private javax.swing.JFormattedTextField jFormattedTextField12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel100;
    private javax.swing.JPanel jPanel103;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel110;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel95;
    private javax.swing.JPanel jPanel97;
    private javax.swing.JPanel jPanel99;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable10;
    private javax.swing.JTable jTable11;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField28;
    // End of variables declaration//GEN-END:variables
}
