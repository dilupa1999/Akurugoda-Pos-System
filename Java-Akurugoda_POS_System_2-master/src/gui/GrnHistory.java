/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import static gui.Login.logger;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.MySQL;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import java.util.Set;
import java.util.HashSet;
import model.MyMethods;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Dell
 */
public class GrnHistory extends javax.swing.JPanel {

    /**
     * Creates new form GrnHistory
     */
    public GrnHistory() {
        initComponents();
        loadGRN();
        resetGRN();

        MyMethods.setTableHeaderFontSize(jTable8, 16);
        MyMethods.setTableHeaderFontSize(jTable9, 16);

    }

    GrnHistory(String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void resetGRN() {
        jTextField25.setText("");
        jLabel89.setText("GRN items (0)");
        jTextField1.setText("");
        jFormattedTextField10.setText("");
        DefaultTableModel model = (DefaultTableModel) jTable8.getModel();
        model.setRowCount(0);

    }

    private void searchGRN() {
        String grnID = jTextField1.getText();
        String employeeEmail = jTextField25.getText();
        String date = jFormattedTextField10.getText().trim();

        try {

            ResultSet rs = MySQL.execute("SELECT * FROM grn INNER JOIN employee ON employee.email=grn.employee_email WHERE grn.id LIKE '%" + grnID + "%' "
                    + "AND grn.date_time LIKE '%" + date + "%'  AND employee.email LIKE '%" + employeeEmail + "%' ");

            DefaultTableModel dtm = (DefaultTableModel) jTable9.getModel();
            dtm.setRowCount(0);

            double totalExpenses = 0;

            while (rs.next()) {
                Vector v = new Vector();

                v.add(rs.getString("grn.id"));
                v.add(rs.getString("employee_email"));

                String inputDTime = rs.getString("date_time");

                try {
                    // Parse the input string as a Date object
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date idate = inputFormat.parse(inputDTime);

                    // Format the Date object as "07:29:08 pm"
                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                    String formattedDTime = outputFormat.format(idate);

                    v.add(formattedDTime);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                v.add(rs.getString("paid_amount"));

                //serch items to calculate total of each grn
                ResultSet resultset2 = MySQL.execute("SELECT * FROM `grn_history_item` WHERE `grn_id`='" + rs.getString("grn.id") + "'");

                double grnTotal = 0;

                while (resultset2.next()) {
                    grnTotal += resultset2.getDouble("grn_history_item.qty") * resultset2.getDouble("buying_price");
                }

                totalExpenses += grnTotal;
                v.add(String.valueOf(grnTotal));

                dtm.addRow(v);
            }

            jLabel77.setText(String.valueOf(totalExpenses));
            jLabel70.setText("GRNs (" + dtm.getRowCount() + ")");

        } catch (Exception e) {
            logger.log(Level.WARNING, "GRN_history_search", e);
            e.printStackTrace();
        }
    }

    private void loadGRN() {
        try {
            ResultSet resultset = MySQL.execute("SELECT * FROM `grn` "
                    + "INNER JOIN `employee` ON `grn`.`employee_email`=`employee`.`email`");

            DefaultTableModel model = (DefaultTableModel) jTable9.getModel();
            model.setRowCount(0);

            double totalExpenses = 0;

            while (resultset.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(resultset.getString("grn.id"));
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

                //serch items to calculate total of each grn
                ResultSet resultset2 = MySQL.execute("SELECT * FROM `grn_history_item` WHERE `grn_id`='" + resultset.getString("grn.id") + "'");

                double grnTotal = 0;

                while (resultset2.next()) {
                    grnTotal += resultset2.getDouble("grn_history_item.qty") * resultset2.getDouble("buying_price");
                }

                totalExpenses += grnTotal;
                vector.add(String.valueOf(grnTotal));

                model.addRow(vector);
            }

            jLabel77.setText(String.valueOf(totalExpenses));
            jLabel70.setText("GRNs (" + model.getRowCount() + ")");

        } catch (Exception e) {
            logger.log(Level.WARNING, "loadGRN", e);
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

        card7 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel109 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jLabel89 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel78 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton39 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel84 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel86 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jFormattedTextField10 = new javax.swing.JFormattedTextField();
        jLabel76 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel85 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable9 = new javax.swing.JTable();
        jLabel70 = new javax.swing.JLabel();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        card7.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        card7.setLayout(new java.awt.BorderLayout(0, 10));

        jPanel5.setLayout(new java.awt.BorderLayout(0, 5));

        jPanel109.setBackground(new java.awt.Color(255, 255, 204));

        jTable8.setAutoCreateRowSorter(true);
        jTable8.setFont(new java.awt.Font("Iskoola Pota", 0, 16)); // NOI18N
        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Stock ID", "Brand", "Name", "Quantity", "Buying price", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable8.setRowHeight(40);
        jTable8.setSelectionBackground(new java.awt.Color(224, 224, 224));
        jTable8.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTable8.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(jTable8);
        if (jTable8.getColumnModel().getColumnCount() > 0) {
            jTable8.getColumnModel().getColumn(0).setPreferredWidth(20);
            jTable8.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTable8.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        javax.swing.GroupLayout jPanel109Layout = new javax.swing.GroupLayout(jPanel109);
        jPanel109.setLayout(jPanel109Layout);
        jPanel109Layout.setHorizontalGroup(
            jPanel109Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 1099, Short.MAX_VALUE)
        );
        jPanel109Layout.setVerticalGroup(
            jPanel109Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel109, java.awt.BorderLayout.CENTER);

        jLabel89.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel89.setText("GRN  items (0)");
        jPanel5.add(jLabel89, java.awt.BorderLayout.PAGE_START);

        card7.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel6.setPreferredSize(new java.awt.Dimension(1407, 450));
        jPanel6.setLayout(new java.awt.BorderLayout(0, 10));

        jPanel78.setPreferredSize(new java.awt.Dimension(300, 150));
        jPanel78.setLayout(new java.awt.BorderLayout(20, 0));

        jPanel2.setPreferredSize(new java.awt.Dimension(200, 93));
        jPanel2.setLayout(new java.awt.GridLayout(3, 1, 0, 5));

        jButton39.setBackground(new java.awt.Color(0, 153, 255));
        jButton39.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton39.setForeground(new java.awt.Color(255, 255, 255));
        jButton39.setText("Search GRNs");
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton39);

        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton1.setText("Reset");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);

        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton2.setText("Save report");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);

        jPanel78.add(jPanel2, java.awt.BorderLayout.EAST);

        jPanel10.setPreferredSize(new java.awt.Dimension(500, 180));
        jPanel10.setLayout(new java.awt.GridLayout(1, 2, 20, 0));

        jPanel11.setPreferredSize(new java.awt.Dimension(1000, 180));
        jPanel11.setLayout(new java.awt.GridLayout(3, 0, 0, 5));

        jPanel84.setOpaque(false);
        jPanel84.setLayout(new java.awt.BorderLayout());

        jLabel73.setBackground(new java.awt.Color(153, 204, 255));
        jLabel73.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel73.setText("Employee");
        jLabel73.setPreferredSize(new java.awt.Dimension(150, 25));
        jPanel84.add(jLabel73, java.awt.BorderLayout.WEST);

        jTextField25.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jTextField25.setPreferredSize(new java.awt.Dimension(200, 22));
        jTextField25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField25KeyReleased(evt);
            }
        });
        jPanel84.add(jTextField25, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel84);

        jPanel3.setLayout(new java.awt.BorderLayout());
        jPanel11.add(jPanel3);

        jPanel86.setOpaque(false);
        jPanel86.setLayout(new java.awt.BorderLayout());

        jLabel71.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(0, 153, 255));
        jLabel71.setText("Total Expenses");
        jLabel71.setPreferredSize(new java.awt.Dimension(150, 25));
        jPanel86.add(jLabel71, java.awt.BorderLayout.WEST);

        jLabel77.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("0.00");
        jPanel86.add(jLabel77, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel86);

        jPanel10.add(jPanel11);

        jPanel12.setLayout(new java.awt.BorderLayout(0, 5));

        jPanel7.setLayout(new java.awt.BorderLayout(0, 5));

        jPanel8.setPreferredSize(new java.awt.Dimension(1272, 47));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jFormattedTextField10.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jFormattedTextField10.setPreferredSize(new java.awt.Dimension(200, 22));
        jFormattedTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField10ActionPerformed(evt);
            }
        });
        jFormattedTextField10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField10KeyReleased(evt);
            }
        });
        jPanel8.add(jFormattedTextField10, java.awt.BorderLayout.CENTER);

        jLabel76.setBackground(new java.awt.Color(153, 204, 255));
        jLabel76.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel76.setText("<html>\n  <body>\n    <span style=\"font-size: 15px;\">Date</span>\n    <div style=\"text-align: right;\">\n      <span style=\"font-size: 10px; color: green;\">(YYYY-MM-DD)</span>\n    </div>\n  </body>\n</html>\n");
        jLabel76.setPreferredSize(new java.awt.Dimension(130, 25));
        jPanel8.add(jLabel76, java.awt.BorderLayout.WEST);

        jPanel7.add(jPanel8, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel7.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel9.setPreferredSize(new java.awt.Dimension(1284, 47));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel9.add(jTextField1, java.awt.BorderLayout.CENTER);

        jLabel72.setBackground(new java.awt.Color(153, 204, 255));
        jLabel72.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel72.setText("GRN ID");
        jLabel72.setPreferredSize(new java.awt.Dimension(130, 25));
        jPanel9.add(jLabel72, java.awt.BorderLayout.WEST);

        jPanel12.add(jPanel9, java.awt.BorderLayout.NORTH);

        jPanel10.add(jPanel12);

        jPanel78.add(jPanel10, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel78, java.awt.BorderLayout.NORTH);

        jPanel4.setLayout(new java.awt.BorderLayout(0, 5));

        jPanel85.setLayout(new java.awt.BorderLayout());

        jTable9.setAutoCreateRowSorter(true);
        jTable9.setFont(new java.awt.Font("Iskoola Pota", 0, 16)); // NOI18N
        jTable9.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "GRN ID", "Employee", "Date amd Time", "Paid amount", "GRN Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable9.setIntercellSpacing(new java.awt.Dimension(5, 0));
        jTable9.setRowHeight(40);
        jTable9.setSelectionBackground(new java.awt.Color(224, 224, 224));
        jTable9.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTable9.getTableHeader().setReorderingAllowed(false);
        jTable9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable9MouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(jTable9);
        if (jTable9.getColumnModel().getColumnCount() > 0) {
            jTable9.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable9.getColumnModel().getColumn(1).setPreferredWidth(40);
            jTable9.getColumnModel().getColumn(3).setPreferredWidth(0);
            jTable9.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        jPanel85.add(jScrollPane9, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel85, java.awt.BorderLayout.CENTER);

        jLabel70.setBackground(new java.awt.Color(153, 204, 255));
        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel70.setText("GRNs (0)");
        jPanel4.add(jLabel70, java.awt.BorderLayout.PAGE_START);

        jPanel6.add(jPanel4, java.awt.BorderLayout.CENTER);

        card7.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        add(card7);
    }// </editor-fold>//GEN-END:initComponents

    private void jTable9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable9MouseClicked
        // TODO add your handling code here:

        jButton2.setEnabled(true);

        String grnID = String.valueOf(jTable9.getValueAt(jTable9.getSelectedRow(), 0));

        try {
            ResultSet resultset = MySQL.execute("SELECT * FROM `grn_history_item` "
                    + "INNER JOIN `grn` ON `grn_history_item`.`grn_id`=`grn`.`id` "
                    + "WHERE `grn_id`='" + grnID + "'");

            DefaultTableModel model = (DefaultTableModel) jTable8.getModel();
            model.setRowCount(0);

            double grnTotal = 0;
            int totalItems = 0;

            while (resultset.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(resultset.getString("grn_history_item.id"));
                vector.add(resultset.getString("brand_name"));
                vector.add(resultset.getString("name"));
                vector.add(resultset.getString("grn_history_item.qty"));
                vector.add(resultset.getString("buying_price"));

                double itemTotal = resultset.getDouble("grn_history_item.qty") * resultset.getDouble("buying_price");

                vector.add(String.valueOf(itemTotal));

                model.addRow(vector);

                grnTotal += itemTotal;
                totalItems++;

            }

            jLabel77.setText(String.valueOf(grnTotal));
            jLabel89.setText("GRN  items (" + totalItems + ")");

        } catch (Exception e) {
            logger.log(Level.WARNING, "GRN_history_load", e);
            e.printStackTrace();
        }


    }//GEN-LAST:event_jTable9MouseClicked

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        // TODO add your handling code here:
        searchGRN();

    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        resetGRN();
        loadGRN();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jFormattedTextField10KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField10KeyReleased
        // TODO add your handling code here:
        searchGRN();
    }//GEN-LAST:event_jFormattedTextField10KeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        // TODO add your handling code here:
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            int row = jTable9.getSelectedRow();
            double netTotal = 0;
            for (int i = 0; i < jTable9.getRowCount(); i++) {
                netTotal += Double.parseDouble(String.valueOf(jTable9.getValueAt(i, 4)));
            }

            //            WE CAN USE ONLY NEDBEANS IDE
            
//            String userDirectory = FileSystems.getDefault()
//                    .getPath("")
//                    .toAbsolutePath()
//                    .toString();
//
////            String url = userDirectory + "\\src\\reports\\new_delivery_grn_report.jasper";
//            String url = "src/reports/new_delivery_grn_report.jasper"; // for testing
//            String suburl = "src/reports/delivery_grn_items.jasper"; // for testing
//
//            String tempUrl = "src/reports/grn_report.jasper";
//             WE CAN USE AFTER BUILD

            String userDirectory = FileSystems.getDefault()
                    .getPath("")
                    .toAbsolutePath()
                    .toString();

            String newpath = userDirectory.substring(0, userDirectory.lastIndexOf("\\"));
            
            String tempUrl = newpath + "\\src\\reports\\new_delivery_grn_report.jasper";
            
//            String url = "src/reports/new_delivery_grn_report.jasper"; // for testing
//            String suburl = "src/reports/delivery_grn_items.jasper"; // for testingpath);
            
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

            JRTableModelDataSource datasource = new JRTableModelDataSource(jTable9.getModel());

            JasperFillManager.fillReport(tempUrl, parameters, connection);
            JasperPrint report = JasperFillManager.fillReport(tempUrl, parameters, datasource);
            //JasperPrintManager.printReport(report, false); //print report dirrectly
            JasperViewer.viewReport(report, false); //for testing

        } catch (Exception e) {
            logger.log(Level.WARNING, "print_grn_report", e);
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        searchGRN();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField25KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField25KeyReleased
        // TODO add your handling code here: 
        DefaultTableModel model = (DefaultTableModel) jTable8.getModel();
        model.setRowCount(0);
        searchGRN();
    }//GEN-LAST:event_jTextField25KeyReleased

    private void jFormattedTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField10ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel card7;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton39;
    private javax.swing.JFormattedTextField jFormattedTextField10;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel109;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel78;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel84;
    private javax.swing.JPanel jPanel85;
    private javax.swing.JPanel jPanel86;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable8;
    private javax.swing.JTable jTable9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField25;
    // End of variables declaration//GEN-END:variables
}
