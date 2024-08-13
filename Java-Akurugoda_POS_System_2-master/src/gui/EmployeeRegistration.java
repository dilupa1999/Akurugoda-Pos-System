package gui;

import static gui.Login.logger;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static model.MyDateTimeUtils.getCurrentDateTime;
import model.MyMethods;
import static model.MyMethods.setTableHeaderFontSize;
import model.MySQL;

public class EmployeeRegistration extends javax.swing.JPanel {

    public EmployeeRegistration() {
        initComponents();
        loadtable();
        loadtable2(false);
        jButton31.setEnabled(false);
        
        MyMethods.setTableHeaderFontSize(jTable1, 16);
        MyMethods.setTableHeaderFontSize(jTable5, 16);
        
    }

    private void reset() {

        jTextField18.setEnabled(true);
        jButton31.setEnabled(false);
        jTable5.clearSelection();
        jTextField15.setText("");
        jTextField16.setText("");
        jTextField17.setText("");
        jTextField18.setText("");

    }

    private void loadtable2(boolean v) {

        String email = jTextField17.getText();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        String x;
        if (v == true) {
            x = "AND `Login_time` > '" + format1.format(jDateChooser1.getDate()) + "' AND `Logout_time` < '" + format1.format(jDateChooser2.getDate()) + "'";
        } else {
            x = "";
        }

        try {
            ResultSet resultset = MySQL.execute("SELECT * FROM `employee` INNER JOIN `employee_history` ON `employee_history`.`employee_email`=`employee`.`email`  WHERE  `email` LIKE  '%" + email + "%' " + x + " ");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultset.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(resultset.getString("email"));
                vector.add(resultset.getString("first_name"));
                vector.add(resultset.getString("last_name"));
                vector.add(resultset.getString("type"));
                
                String inputLDTime = resultset.getString("Login_time");
                String inputODTime = resultset.getString("Logout_time");

                try {
                    // Parse the input string as a Date object
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                    
                    
                    Date idate = inputFormat.parse(inputLDTime);
                    String formattedLDTime = outputFormat.format(idate);
                    
                    Date odate = inputFormat.parse(inputODTime);
                    String formattedODTime = outputFormat.format(odate);

                    vector.add(formattedLDTime);
                    
                    if ("1023-01-01 01:01:01".equals(resultset.getString("Logout_time"))) {
                    vector.add("");
                } else {
                
                    vector.add(formattedODTime);
                    
                }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                

                model.addRow(vector);
            }
            jTable1.setModel(model);

        } catch (Exception e) {
            logger.log(Level.WARNING, "SupplierRegistrtion_search", e);
            e.printStackTrace();
        }
    }

    private void loadtable() {
        String fname = jTextField15.getText();
        String lname = jTextField16.getText();
        String email = jTextField17.getText();
        String password = jTextField18.getText();

        try {
            ResultSet resultset = MySQL.execute("SELECT * FROM `employee`  WHERE `email` LIKE '" + email + "%' AND "
                    + "`first_name` LIKE '" + fname + "%' AND `last_name` LIKE '" + lname + "%'  AND `email` LIKE '" + email + "%'");

            DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
            model.setRowCount(0);

            while (resultset.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(resultset.getString("email"));
                vector.add(resultset.getString("first_name"));
                vector.add(resultset.getString("last_name"));
//                vector.add(resultset.getString("password"));

                if (Login.type.equals("superAdmin")) {
                    vector.add(resultset.getString("password"));

                }

                model.addRow(vector);
            }
            jTable5.setModel(model);

        } catch (Exception e) {
            logger.log(Level.WARNING, "SupplierRegistrtion_search", e);
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        card04 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel70 = new javax.swing.JPanel();
        jPanel89 = new javax.swing.JPanel();
        jPanel64 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jTextField18 = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jPanel90 = new javax.swing.JPanel();
        jPanel67 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jPanel68 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jPanel91 = new javax.swing.JPanel();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jPanel66 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel14 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jPanel87 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        card04.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        card04.setLayout(new java.awt.BorderLayout());

        jPanel13.setLayout(new java.awt.BorderLayout());

        jPanel12.setLayout(new java.awt.BorderLayout(10, 0));

        jPanel70.setLayout(new java.awt.GridLayout(1, 3, 20, 0));

        jPanel89.setLayout(new java.awt.GridLayout(3, 0, 0, 5));

        jPanel64.setLayout(new java.awt.BorderLayout());

        jLabel53.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel53.setText("Email");
        jLabel53.setPreferredSize(new java.awt.Dimension(90, 16));
        jPanel64.add(jLabel53, java.awt.BorderLayout.WEST);

        jTextField17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField17ActionPerformed(evt);
            }
        });
        jTextField17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField17KeyReleased(evt);
            }
        });
        jPanel64.add(jTextField17, java.awt.BorderLayout.CENTER);

        jPanel89.add(jPanel64);

        jTextField18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField18ActionPerformed(evt);
            }
        });
        jTextField18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField18KeyReleased(evt);
            }
        });

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel58.setText("Password");
        jLabel58.setPreferredSize(new java.awt.Dimension(90, 16));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField18, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jTextField18, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jPanel89.add(jPanel2);

        jPanel70.add(jPanel89);

        jPanel90.setLayout(new java.awt.GridLayout(3, 0, 0, 5));

        jPanel67.setLayout(new java.awt.BorderLayout());

        jLabel54.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel54.setText("First name");
        jLabel54.setPreferredSize(new java.awt.Dimension(90, 16));
        jPanel67.add(jLabel54, java.awt.BorderLayout.WEST);

        jTextField15.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jTextField15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField15ActionPerformed(evt);
            }
        });
        jTextField15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField15KeyReleased(evt);
            }
        });
        jPanel67.add(jTextField15, java.awt.BorderLayout.CENTER);

        jPanel90.add(jPanel67);

        jPanel68.setLayout(new java.awt.BorderLayout());

        jLabel55.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel55.setText("Last name");
        jLabel55.setPreferredSize(new java.awt.Dimension(90, 16));
        jPanel68.add(jLabel55, java.awt.BorderLayout.WEST);

        jTextField16.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jTextField16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField16KeyReleased(evt);
            }
        });
        jPanel68.add(jTextField16, java.awt.BorderLayout.CENTER);

        jPanel90.add(jPanel68);

        jPanel70.add(jPanel90);

        jPanel12.add(jPanel70, java.awt.BorderLayout.CENTER);

        jPanel91.setLayout(new java.awt.GridLayout(3, 0, 0, 5));

        jButton30.setBackground(new java.awt.Color(0, 153, 255));
        jButton30.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton30.setForeground(new java.awt.Color(255, 255, 255));
        jButton30.setText("Register employee");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });
        jPanel91.add(jButton30);

        jButton31.setBackground(new java.awt.Color(0, 153, 255));
        jButton31.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton31.setForeground(new java.awt.Color(255, 255, 255));
        jButton31.setText("Update employee");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });
        jPanel91.add(jButton31);

        jPanel66.setLayout(new java.awt.BorderLayout());

        jLabel56.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel56.setText("Total invoices : ");
        jLabel56.setPreferredSize(new java.awt.Dimension(130, 16));
        jPanel66.add(jLabel56, java.awt.BorderLayout.WEST);

        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel57.setText("...");
        jLabel57.setPreferredSize(new java.awt.Dimension(100, 25));
        jPanel66.add(jLabel57, java.awt.BorderLayout.CENTER);

        jPanel91.add(jPanel66);

        jPanel12.add(jPanel91, java.awt.BorderLayout.EAST);

        jPanel13.add(jPanel12, java.awt.BorderLayout.CENTER);
        jPanel13.add(jSeparator2, java.awt.BorderLayout.SOUTH);

        card04.add(jPanel13, java.awt.BorderLayout.NORTH);

        jPanel14.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel8.setPreferredSize(new java.awt.Dimension(600, 100));
        jPanel8.setLayout(new java.awt.BorderLayout(10, 0));

        jButton2.setBackground(new java.awt.Color(0, 153, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Find");
        jButton2.setBorderPainted(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton2, java.awt.BorderLayout.CENTER);

        jPanel7.setPreferredSize(new java.awt.Dimension(500, 222));
        jPanel7.setLayout(new java.awt.GridLayout(1, 2, 20, 0));

        jPanel1.setLayout(new java.awt.BorderLayout(5, 0));
        jPanel1.add(jDateChooser1, java.awt.BorderLayout.CENTER);

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel1.setText("From");
        jPanel1.add(jLabel1, java.awt.BorderLayout.WEST);

        jPanel7.add(jPanel1);

        jPanel6.setLayout(new java.awt.BorderLayout(5, 0));
        jPanel6.add(jDateChooser2, java.awt.BorderLayout.CENTER);

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel2.setText("To");
        jPanel6.add(jLabel2, java.awt.BorderLayout.WEST);

        jPanel7.add(jPanel6);

        jPanel8.add(jPanel7, java.awt.BorderLayout.WEST);

        jPanel5.add(jPanel8, java.awt.BorderLayout.WEST);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jTable1.setFont(new java.awt.Font("Iskoola Pota", 0, 16)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Email", "First name", "Last name", "Type", "Login time", "Logout time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(40);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
        }

        jPanel4.add(jScrollPane1);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1021, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel14.add(jPanel10, java.awt.BorderLayout.CENTER);

        jTable5.setAutoCreateRowSorter(true);
        jTable5.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Email", "First Name", "Last name", "Password"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable5.setIntercellSpacing(new java.awt.Dimension(10, 0));
        jTable5.setRowHeight(40);
        jTable5.setSelectionBackground(new java.awt.Color(224, 224, 224));
        jTable5.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTable5.setShowGrid(true);
        jTable5.getTableHeader().setReorderingAllowed(false);
        jTable5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable5MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable5);

        javax.swing.GroupLayout jPanel87Layout = new javax.swing.GroupLayout(jPanel87);
        jPanel87.setLayout(jPanel87Layout);
        jPanel87Layout.setHorizontalGroup(
            jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5)
        );
        jPanel87Layout.setVerticalGroup(
            jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel5.setText("Registered employee");

        jButton1.setBackground(new java.awt.Color(0, 153, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Reset");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1021, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel87, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 229, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel5)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel87, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel14.add(jPanel11, java.awt.BorderLayout.PAGE_START);

        card04.add(jPanel14, java.awt.BorderLayout.CENTER);

        add(card04);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField15ActionPerformed

    }//GEN-LAST:event_jTextField15ActionPerformed

    private void jTextField15KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField15KeyReleased
        loadtable();

    }//GEN-LAST:event_jTextField15KeyReleased

    private void jTextField16KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField16KeyReleased
        loadtable();

    }//GEN-LAST:event_jTextField16KeyReleased

    private void jTextField17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField17ActionPerformed

    }//GEN-LAST:event_jTextField17ActionPerformed

    private void jTextField17KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField17KeyReleased
        loadtable();
    }//GEN-LAST:event_jTextField17KeyReleased

    private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked

        jTextField17.setEditable(false);

        int row = jTable5.getSelectedRow();

        jTextField15.setText(String.valueOf(jTable5.getValueAt(row, 1)));
        jTextField16.setText(String.valueOf(jTable5.getValueAt(row, 2)));
        jTextField17.setText(String.valueOf(jTable5.getValueAt(row, 0)));
        try {

            ResultSet r = MySQL.execute("SELECT COUNT(*) AS rowcount FROM invoice WHERE employee_email='" + String.valueOf(jTable5.getValueAt(row, 0)) + "'");
            r.next();
            int count = r.getInt("rowcount");
            r.close();

            jLabel57.setText(String.valueOf(String.valueOf(count)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        jTextField18.setEnabled(false);
        jButton30.setEnabled(false);
        jButton31.setEnabled(true);

        if (Login.type.equals("superAdmin")) {
            jTextField18.setEnabled(true);

            jTextField18.setText(String.valueOf(jTable5.getValueAt(row, 3)));

        }

        loadtable2(false);


    }//GEN-LAST:event_jTable5MouseClicked

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed

        try {
            String fname = jTextField15.getText().trim();
            String lname = jTextField16.getText().trim();
            String email = jTextField17.getText().trim();
            String password = jTextField18.getText().trim();

            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid email address", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (email.length() > 45) {
                JOptionPane.showMessageDialog(this, "Email length cannot exeed 45 characters", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (!email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
                JOptionPane.showMessageDialog(this, "Invalid email address", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (fname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter first name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (fname.length() > 45) {
                JOptionPane.showMessageDialog(this, "First Name length cannot exeed 45 characters", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (lname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter last name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (lname.length() > 45) {
                JOptionPane.showMessageDialog(this, "Last Name length cannot exeed 45 characters", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (password.length() < 5) {
                JOptionPane.showMessageDialog(this, "Please enter password minimum 5 characters", "Warning", JOptionPane.WARNING_MESSAGE);

            } else if (password.length() > 20) {
                JOptionPane.showMessageDialog(this, "Password length cannot exeed 20 characters", "Warning", JOptionPane.WARNING_MESSAGE);

            } else {

                ResultSet resultset = MySQL.execute("SELECT * FROM `employee`  WHERE `email` ='" + email + "' ");

                if (resultset.next()) {
                    JOptionPane.showMessageDialog(this, "Employee already exsists", "Warning", JOptionPane.WARNING_MESSAGE);

                } else {

                    MySQL.execute("INSERT INTO `employee` (`email`,`first_name`,`last_name`,`type`,`password`,`last_update_time`,`status_id`)"
                            + "VALUES ('" + email + "','" + fname + "','" + lname + "','cashier','" + password + "','" + getCurrentDateTime() + "','1')");
                    reset();
                    loadtable();

                    JOptionPane.showMessageDialog(this, "Supplier successfully registered", "Message", JOptionPane.INFORMATION_MESSAGE);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed

        try {
            String fname = jTextField15.getText().trim();
            String lname = jTextField16.getText().trim();
            String email = jTextField17.getText().trim();
            String password = jTextField18.getText().trim();

            if (fname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter first name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (fname.length() > 45) {
                JOptionPane.showMessageDialog(this, "First Name length cannot exeed 45 characters", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (lname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter last name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (lname.length() > 45) {
                JOptionPane.showMessageDialog(this, "Last Name length cannot exeed 45 characters", "Warning", JOptionPane.WARNING_MESSAGE);

            } else {

                boolean check = false;
                String x = "";
                if (Login.type.equals("superAdmin")) {

                    if (password.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please enter Password", "Warning", JOptionPane.WARNING_MESSAGE);

                    } else if (password.length() < 5) {
                        JOptionPane.showMessageDialog(this, "Please enter password minimum 5 characters", "Warning", JOptionPane.WARNING_MESSAGE);

                    } else if (password.length() > 20) {
                        JOptionPane.showMessageDialog(this, "password length cannot exeed 20 characters", "Warning", JOptionPane.WARNING_MESSAGE);

                    } else {
                        x = ", password='" + password + "'";
                        check = true;
                    }

                } else {
                    check = true;

                }

                if (check == true) {
                    MySQL.execute("UPDATE employee SET first_name='" + fname + "' , last_name='" + lname + "' " + x + ", `last_update_time` = '" + getCurrentDateTime() + "' WHERE  email='" + email + "'   ");
                    reset();
                    loadtable();

                    JOptionPane.showMessageDialog(this, "Supplier successfully updated", "Message", JOptionPane.INFORMATION_MESSAGE);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Oops! please check your Internet connection ", "Error", JOptionPane.ERROR_MESSAGE);

        }


    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        reset();
        loadtable();
        jButton30.setEnabled(true);
        loadtable2(false);
        jTextField17.setEditable(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField18ActionPerformed

    private void jTextField18KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField18KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField18KeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        if (jDateChooser1.getDate() == null) {

            JOptionPane.showMessageDialog(this, "Please select From date", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (jDateChooser2.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select To date", "Warning", JOptionPane.WARNING_MESSAGE);

        } else {
            loadtable2(true);
        }

    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel card04;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel87;
    private javax.swing.JPanel jPanel89;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel90;
    private javax.swing.JPanel jPanel91;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable5;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    // End of variables declaration//GEN-END:variables
}
