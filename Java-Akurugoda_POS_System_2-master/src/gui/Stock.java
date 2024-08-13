/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import static gui.Login.logger;
import static gui.Login.logintime;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.InvoiceItem;
import model.MySQL;
import model.MySQLSyncApp;
import model.onlineMYSQL;
import static model.MyDateTimeUtils.getCurrentDateTime;
import model.MyMethods;

/**
 *
 * @author Dell
 */
public class Stock extends javax.swing.JPanel {

    /**
     * Creates new form Stock
     */
    HashMap<String, String> brandMap = new HashMap<>();

    private Grn grn;
    private JDialog dailog;

    public void setGrn(Grn grn) {
        this.grn = grn;
    }

    private Home invoice;

    public void setInvoice(Home invoice) {
        this.invoice = invoice;
    }

    public void setDiaload(JDialog dailog) {
        this.dailog = dailog;
    }

    public Stock() {
        initComponents();
        jPanel32.setVisible(false);
        loadBranbs();
        loadProducts();
        loadStock();

        MyMethods.setTableHeaderFontSize(jTable3, 16);

        jButton28.setEnabled(false);
    }

    private void loadStock() {
        try {

            String query = "SELECT * FROM `product` INNER JOIN `brand` ON `product`.`brand_id`=`brand`.`id`";

//            if (query.contains("WHERE")) {
//                query += "AND ";
//            } else {
//                query += "WHERE ";
//            }
            double min_price = 0;
            double max_price = 0;

            if (!jFormattedTextField7.getText().isEmpty()) {
                min_price = Double.parseDouble(jFormattedTextField7.getText());
            }

            if (!jFormattedTextField8.getText().isEmpty()) {
                max_price = Double.parseDouble(jFormattedTextField8.getText());
            }

            if (min_price > 0 && max_price == 0) {
                query += "AND `product`.`selling_price` > '" + min_price + "'";
            } else if (min_price == 0 && max_price > 0) {
                query += "AND `product`.`selling_price` < '" + max_price + "'";
            } else if (min_price > 0 && max_price > 0) {
                query += "AND (`product`.`selling_price` > '" + min_price + "' AND `product`.`selling_price`<'" + max_price + "')";
            }

            Date start = null;
            Date end = null;

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

            if (jDateChooser3.getDate() != null) {

                start = jDateChooser3.getDate();
                query += "AND `exp`>'" + format1.format(start) + "' ";
            }
            if (jDateChooser4.getDate() != null) {

                end = jDateChooser4.getDate();
                query += "AND `exp`<'" + format1.format(end) + "' ";
            }

            query += "ORDER BY";

            query = query.replace("WHERE ORDER BY", " ORDER BY ");
            query = query.replace("WHERE AND", " WHERE ");
            query = query.replace("AND AND", " AND ");

            query = query.replace("AND ORDER BY", " ORDER BY ");

            if (jComboBox2.getSelectedIndex() == 0) {
                query += " `product`.`id` ASC";

            } else if (jComboBox2.getSelectedIndex() == 1) {
                query += " `product`.`id` DESC";

            } else if (jComboBox2.getSelectedIndex() == 2) {
                query += " `brand`.`name` ASC";

            } else if (jComboBox2.getSelectedIndex() == 3) {
                query += " `brand`.`name` DESC";

            } else if (jComboBox2.getSelectedIndex() == 4) {
                query += " `product`.`name` ASC";

            } else if (jComboBox2.getSelectedIndex() == 5) {
                query += " `product`.`name` DESC";

            } else if (jComboBox2.getSelectedIndex() == 6) {
                query += " `selling_price` ASC";

            } else if (jComboBox2.getSelectedIndex() == 7) {
                query += " `selling_price` DESC";

            } else if (jComboBox2.getSelectedIndex() == 8) {
                query += " `qty` ASC";

            } else if (jComboBox2.getSelectedIndex() == 9) {
                query += " `qty` DESC";
            }

            //System.out.println(query+"");
            ResultSet resultset = MySQL.execute(query);

            DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
            model.setRowCount(0);

            while (resultset.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(resultset.getString("product.id"));
                vector.add(resultset.getString("brand.name"));
                vector.add(resultset.getString("product.name"));

                if ("-1.0".equals(resultset.getString("buying_price"))) {
                    vector.add("");
                } else {
                    vector.add(resultset.getString("buying_price"));
                }

                if ("-1.0".equals(resultset.getString("selling_price"))) {
                    vector.add("");
                } else {
                    vector.add(resultset.getString("selling_price"));
                }

                if ("-1.0".equals(resultset.getString("qty"))) {
                    vector.add("");
                } else {
                    vector.add(resultset.getString("qty"));
                }

                if ("1023-01-01".equals(resultset.getString("mfg"))) {
                    vector.add("");
                } else {
                    vector.add(resultset.getString("mfg"));
                }

                if ("1023-01-01".equals(resultset.getString("exp"))) {
                    vector.add("");
                } else {
                    vector.add(resultset.getString("exp"));
                }

                model.addRow(vector);
            }

            jLabel48.setText("Product stocks (" + model.getRowCount() + ")");

        } catch (Exception e) {
            logger.log(Level.WARNING, "loadStock", e);
            e.printStackTrace();
        }
    }

    private void loadProducts() {
        String product = jFormattedTextField9.getText().trim();
        String brand = "";
        if (jComboBox1.getSelectedIndex() != 0) {
            brand = String.valueOf(jComboBox1.getSelectedItem());
        }

        try {
            ResultSet resultset = MySQL.execute("SELECT * FROM `product` INNER JOIN `brand` ON `product`.`brand_id`=`brand`.`id` WHERE "
                    + "(`product`.`id` LIKE '%" + product + "%' OR `product`.`name` LIKE '%" + product + "%') AND `brand`.`name` LIKE '%" + brand + "%'");

            DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
            model.setRowCount(0);

            while (resultset.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(resultset.getString("product.id"));
                vector.add(resultset.getString("brand.name"));
                vector.add(resultset.getString("product.name"));

                if ("-1.0".equals(resultset.getString("buying_price"))) {
                    vector.add("");
                } else {
                    vector.add(resultset.getString("buying_price"));
                }

                if ("-1.0".equals(resultset.getString("selling_price"))) {
                    vector.add("");
                } else {
                    vector.add(resultset.getString("selling_price"));
                }

                if ("-1.0".equals(resultset.getString("qty"))) {
                    vector.add("");
                } else {
                    vector.add(resultset.getString("qty"));
                }

                if ("1023-01-01".equals(resultset.getString("mfg"))) {
                    vector.add("");
                } else {
                    vector.add(resultset.getString("mfg"));
                }

                if ("1023-01-01".equals(resultset.getString("exp"))) {
                    vector.add("");
                } else {
                    vector.add(resultset.getString("exp"));
                }

                model.addRow(vector);
            }

        } catch (Exception e) {
            logger.log(Level.WARNING, "loadProduct", e);
            e.printStackTrace();
        }
    }

    private void loadBranbs() {
        try {
            ResultSet resultset = MySQL.execute("SELECT * FROM `brand`");
            Vector<String> vector = new Vector<>();

            while (resultset.next()) {

                vector.add(resultset.getString("name"));

                brandMap.put(resultset.getString("name"), resultset.getString("id"));
            }

            jComboBox1.setModel(new DefaultComboBoxModel(vector));

        } catch (Exception e) {
            logger.log(Level.WARNING, "loadBrands", e);
            e.printStackTrace();
        }
    }

    private void resetProductInput() {
        jTextField1.setText("");
        jFormattedTextField9.setText("");
        jTextField5.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jFormattedTextField13.setText("");
        jDateChooser1.setDate(null);
        jDateChooser2.setDate(null);
        jComboBox1.setSelectedIndex(0);
        jButton28.setEnabled(false);
        jButton26.setEnabled(true);
        jTextField1.setEditable(true);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        card3 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton28 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jButton29 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jButton27 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jPanel61 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jFormattedTextField9 = new javax.swing.JFormattedTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel62 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jPanel15 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jFormattedTextField13 = new javax.swing.JFormattedTextField();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jButton23 = new javax.swing.JButton();
        jPanel30 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jPanel31 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jFormattedTextField7 = new javax.swing.JFormattedTextField();
        jPanel23 = new javax.swing.JPanel();
        jFormattedTextField8 = new javax.swing.JFormattedTextField();
        jLabel29 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jButton25 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jPanel29 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jPanel25 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jPanel32 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        card3.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        card3.setName("card3"); // NOI18N
        card3.setLayout(new java.awt.BorderLayout(0, 10));

        jPanel19.setPreferredSize(new java.awt.Dimension(1178, 160));
        jPanel19.setLayout(new java.awt.BorderLayout(0, 10));
        jPanel19.add(jSeparator1, java.awt.BorderLayout.SOUTH);

        jPanel3.setLayout(new java.awt.BorderLayout(10, 0));

        jPanel4.setPreferredSize(new java.awt.Dimension(185, 397));
        jPanel4.setLayout(new java.awt.BorderLayout(0, 5));

        jPanel6.setPreferredSize(new java.awt.Dimension(248, 95));
        jPanel6.setLayout(new java.awt.GridLayout(2, 1, 0, 5));

        jButton28.setBackground(new java.awt.Color(0, 153, 255));
        jButton28.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton28.setForeground(new java.awt.Color(255, 255, 255));
        jButton28.setText("Update product");
        jButton28.setPreferredSize(new java.awt.Dimension(162, 45));
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton28);

        jPanel7.setLayout(new java.awt.BorderLayout());

        jButton29.setBackground(new java.awt.Color(0, 153, 255));
        jButton29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reload1.png"))); // NOI18N
        jButton29.setPreferredSize(new java.awt.Dimension(45, 40));
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton29, java.awt.BorderLayout.EAST);

        jButton26.setBackground(new java.awt.Color(0, 153, 255));
        jButton26.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton26.setForeground(new java.awt.Color(255, 255, 255));
        jButton26.setText("Add product");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton26, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel7);

        jPanel4.add(jPanel6, java.awt.BorderLayout.NORTH);

        jPanel17.setLayout(new java.awt.BorderLayout());

        jButton27.setBackground(new java.awt.Color(0, 153, 255));
        jButton27.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton27.setForeground(new java.awt.Color(255, 255, 255));
        jButton27.setText("Add brand");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        jPanel17.add(jButton27, java.awt.BorderLayout.LINE_START);

        jPanel4.add(jPanel17, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel4, java.awt.BorderLayout.EAST);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel8.setLayout(new java.awt.GridLayout(1, 2));

        jPanel10.setLayout(new java.awt.GridLayout(3, 1, 0, 5));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel1.add(jTextField1, java.awt.BorderLayout.CENTER);

        jLabel51.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel51.setText("Product ID");
        jLabel51.setMaximumSize(new java.awt.Dimension(50, 25));
        jLabel51.setMinimumSize(new java.awt.Dimension(50, 25));
        jLabel51.setPreferredSize(new java.awt.Dimension(90, 25));
        jPanel1.add(jLabel51, java.awt.BorderLayout.WEST);

        jPanel10.add(jPanel1);

        jPanel61.setPreferredSize(new java.awt.Dimension(0, 40));
        jPanel61.setLayout(new java.awt.BorderLayout());

        jLabel49.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("<html><p>Product name</p></html>");
        jLabel49.setMaximumSize(new java.awt.Dimension(50, 25));
        jLabel49.setMinimumSize(new java.awt.Dimension(50, 25));
        jLabel49.setPreferredSize(new java.awt.Dimension(90, 25));
        jPanel61.add(jLabel49, java.awt.BorderLayout.WEST);

        jFormattedTextField9.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jFormattedTextField9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField9KeyReleased(evt);
            }
        });
        jPanel61.add(jFormattedTextField9, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel61);

        jPanel14.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Quantity");
        jLabel5.setPreferredSize(new java.awt.Dimension(90, 25));
        jPanel14.add(jLabel5, java.awt.BorderLayout.LINE_START);

        jTextField5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel14.add(jTextField5, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel14);

        jPanel8.add(jPanel10);

        jPanel11.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 20));
        jPanel11.setLayout(new java.awt.GridLayout(3, 1, 0, 5));

        jPanel12.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("<html><p>Buying price</p></html>");
        jLabel1.setPreferredSize(new java.awt.Dimension(60, 25));
        jPanel12.add(jLabel1, java.awt.BorderLayout.LINE_START);

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel12.add(jTextField2, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel12);

        jPanel13.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("<html><p>Selling price</p></html>");
        jLabel2.setPreferredSize(new java.awt.Dimension(60, 25));
        jPanel13.add(jLabel2, java.awt.BorderLayout.LINE_START);

        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel13.add(jTextField3, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel13);

        jPanel62.setPreferredSize(new java.awt.Dimension(0, 40));
        jPanel62.setLayout(new java.awt.BorderLayout());

        jLabel52.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel52.setText("Brand");
        jLabel52.setPreferredSize(new java.awt.Dimension(60, 25));
        jPanel62.add(jLabel52, java.awt.BorderLayout.WEST);

        jComboBox1.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jPanel62.add(jComboBox1, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel62);

        jPanel8.add(jPanel11);

        jPanel5.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel9.setPreferredSize(new java.awt.Dimension(200, 397));
        jPanel9.setLayout(new java.awt.GridLayout(3, 1, 0, 5));

        jPanel16.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("MFG");
        jLabel6.setPreferredSize(new java.awt.Dimension(45, 25));
        jPanel16.add(jLabel6, java.awt.BorderLayout.LINE_START);

        jDateChooser2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel16.add(jDateChooser2, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel16);

        jPanel15.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("EXP");
        jLabel3.setPreferredSize(new java.awt.Dimension(45, 25));
        jPanel15.add(jLabel3, java.awt.BorderLayout.LINE_START);

        jDateChooser1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel15.add(jDateChooser1, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel15);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jFormattedTextField13.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jPanel2.add(jFormattedTextField13, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel2);

        jPanel5.add(jPanel9, java.awt.BorderLayout.EAST);

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel19.add(jPanel3, java.awt.BorderLayout.CENTER);

        card3.add(jPanel19, java.awt.BorderLayout.NORTH);

        jPanel20.setLayout(new java.awt.BorderLayout(0, 10));

        jTable3.setAutoCreateRowSorter(true);
        jTable3.setFont(new java.awt.Font("Iskoola Pota", 0, 16)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Brand", "Product Name", "Buying Price", "Selling Price", "Quantity", "MFG", "EXP"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setIntercellSpacing(new java.awt.Dimension(10, 0));
        jTable3.setRowHeight(40);
        jTable3.setSelectionBackground(new java.awt.Color(224, 224, 224));
        jTable3.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTable3.getTableHeader().setReorderingAllowed(false);
        jTable3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jTable3MouseDragged(evt);
            }
        });
        jTable3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTable3FocusLost(evt);
            }
        });
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTable3MouseEntered(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jPanel20.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel26.setPreferredSize(new java.awt.Dimension(1020, 100));
        jPanel26.setLayout(new java.awt.GridLayout(2, 1, 0, 5));

        jPanel18.setPreferredSize(new java.awt.Dimension(0, 40));
        jPanel18.setLayout(new java.awt.BorderLayout(10, 0));

        jButton23.setBackground(new java.awt.Color(0, 153, 255));
        jButton23.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton23.setForeground(new java.awt.Color(255, 255, 255));
        jButton23.setText("Find");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });
        jPanel18.add(jButton23, java.awt.BorderLayout.EAST);

        jPanel30.setLayout(new java.awt.BorderLayout());

        jPanel21.setPreferredSize(new java.awt.Dimension(300, 66));
        jPanel21.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Sort ");
        jPanel21.add(jLabel4, java.awt.BorderLayout.WEST);

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Product ID ASC", "Product ID DESC", "Band  ASC", "Band DESC", "Name ASC", "Name DESC", "Selling price ASC", "Selling price DESC", "Quantity ASC", "Quantity DESC" }));
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });
        jPanel21.add(jComboBox2, java.awt.BorderLayout.CENTER);

        jPanel30.add(jPanel21, java.awt.BorderLayout.WEST);

        jPanel31.setPreferredSize(new java.awt.Dimension(400, 47));
        jPanel31.setLayout(new java.awt.BorderLayout(10, 0));

        jPanel22.setLayout(new java.awt.BorderLayout());

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("Selling price");
        jLabel28.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        jLabel28.setMaximumSize(new java.awt.Dimension(50, 25));
        jLabel28.setMinimumSize(new java.awt.Dimension(50, 25));
        jLabel28.setPreferredSize(new java.awt.Dimension(120, 25));
        jPanel22.add(jLabel28, java.awt.BorderLayout.WEST);

        jFormattedTextField7.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField7.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField7.setText("0.00");
        jFormattedTextField7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jFormattedTextField7.setPreferredSize(new java.awt.Dimension(50, 31));
        jFormattedTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField7ActionPerformed(evt);
            }
        });
        jPanel22.add(jFormattedTextField7, java.awt.BorderLayout.CENTER);

        jPanel31.add(jPanel22, java.awt.BorderLayout.CENTER);

        jPanel23.setPreferredSize(new java.awt.Dimension(150, 76));
        jPanel23.setLayout(new java.awt.BorderLayout(10, 0));

        jFormattedTextField8.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField8.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField8.setText("0.00");
        jFormattedTextField8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel23.add(jFormattedTextField8, java.awt.BorderLayout.CENTER);

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("To");
        jPanel23.add(jLabel29, java.awt.BorderLayout.WEST);

        jPanel31.add(jPanel23, java.awt.BorderLayout.EAST);

        jPanel30.add(jPanel31, java.awt.BorderLayout.EAST);

        jPanel18.add(jPanel30, java.awt.BorderLayout.CENTER);

        jPanel26.add(jPanel18);

        jPanel46.setPreferredSize(new java.awt.Dimension(0, 40));
        jPanel46.setLayout(new java.awt.BorderLayout());

        jLabel48.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel48.setText("Product stocks");
        jPanel46.add(jLabel48, java.awt.BorderLayout.WEST);

        jPanel28.setLayout(new java.awt.BorderLayout(10, 0));

        jPanel27.setPreferredSize(new java.awt.Dimension(230, 76));
        jPanel27.setLayout(new java.awt.BorderLayout());

        jButton25.setBackground(new java.awt.Color(0, 153, 255));
        jButton25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reload1.png"))); // NOI18N
        jButton25.setPreferredSize(new java.awt.Dimension(50, 40));
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });
        jPanel27.add(jButton25, java.awt.BorderLayout.EAST);

        jButton24.setBackground(new java.awt.Color(0, 153, 255));
        jButton24.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton24.setForeground(new java.awt.Color(255, 255, 255));
        jButton24.setText("Search product");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });
        jPanel27.add(jButton24, java.awt.BorderLayout.CENTER);

        jPanel28.add(jPanel27, java.awt.BorderLayout.EAST);

        jPanel29.setLayout(new java.awt.BorderLayout(10, 0));

        jPanel24.setLayout(new java.awt.BorderLayout());

        jLabel46.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel46.setText("E.X.P Date");
        jLabel46.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        jLabel46.setMaximumSize(new java.awt.Dimension(50, 25));
        jLabel46.setMinimumSize(new java.awt.Dimension(50, 25));
        jLabel46.setPreferredSize(new java.awt.Dimension(50, 25));
        jPanel24.add(jLabel46, java.awt.BorderLayout.CENTER);

        jDateChooser3.setDateFormatString("yyyy-MM-dd");
        jDateChooser3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jDateChooser3.setPreferredSize(new java.awt.Dimension(190, 22));
        jPanel24.add(jDateChooser3, java.awt.BorderLayout.EAST);

        jPanel29.add(jPanel24, java.awt.BorderLayout.CENTER);

        jPanel25.setPreferredSize(new java.awt.Dimension(200, 76));
        jPanel25.setLayout(new java.awt.BorderLayout(10, 0));

        jLabel47.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setText("To");
        jPanel25.add(jLabel47, java.awt.BorderLayout.WEST);

        jDateChooser4.setDateFormatString("yyyy-MM-dd");
        jDateChooser4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel25.add(jDateChooser4, java.awt.BorderLayout.CENTER);

        jPanel29.add(jPanel25, java.awt.BorderLayout.EAST);

        jPanel28.add(jPanel29, java.awt.BorderLayout.CENTER);

        jPanel46.add(jPanel28, java.awt.BorderLayout.CENTER);

        jPanel26.add(jPanel46);

        jPanel20.add(jPanel26, java.awt.BorderLayout.NORTH);

        jPanel32.setForeground(new java.awt.Color(255, 255, 255));
        jPanel32.setPreferredSize(new java.awt.Dimension(100, 50));
        jPanel32.setLayout(new java.awt.GridLayout());

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Select product");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel32.add(jButton1);

        jPanel20.add(jPanel32, java.awt.BorderLayout.PAGE_END);

        card3.add(jPanel20, java.awt.BorderLayout.CENTER);

        add(card3);
    }// </editor-fold>//GEN-END:initComponents

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        // TODO add your handling code here:

        String productId = jTextField1.getText();
        String brand = String.valueOf(jComboBox1.getSelectedItem());
        String productName = jFormattedTextField9.getText();
        String sellingPrice = jTextField3.getText();
        String buyingPrice = jTextField2.getText();
        String qty = jTextField5.getText();
        Date mfg = jDateChooser2.getDate();
        Date exp = jDateChooser1.getDate();

        if (productId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter product id", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (productName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter product name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (productName.length() > 45) {
            JOptionPane.showMessageDialog(this, "Product Name length cannot exeed 45 characters", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                ResultSet resultset = MySQL.execute("SELECT * FROM `product` WHERE `id`='" + productId + "' OR `name`='" + productName + "' ");
                if (resultset.next()) {
                    JOptionPane.showMessageDialog(this, "Product already exists", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {

                    String mysqlmfgDate;
                    String mysqlexpDate;

                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

                    if (mfg != null) {

                        mysqlmfgDate = String.valueOf(format1.format(mfg));

                    } else {

                        int year = 1023;
                        int month = 01;
                        int day = 01;

                        Date customDate = new Date(year - 1900, month - 1, day);

                        // Format the custom date
                        mysqlmfgDate = String.valueOf(format1.format(customDate));

                    }

                    if (exp != null) {

                        mysqlexpDate = String.valueOf(format1.format(exp));

                    } else {

                        int year = 1023;
                        int month = 01;
                        int day = 01;

                        Date customDate = new Date(year - 1900, month - 1, day);

                        // Format the custom date
                        mysqlexpDate = String.valueOf(format1.format(customDate));

                    }

                    if (!isDouble(sellingPrice)) {
                        sellingPrice = String.valueOf(-1.0);
                    }

                    if (!isDouble(buyingPrice)) {
                        buyingPrice = String.valueOf(-1.0);
                    }

                    if (!isDouble(qty)) {
                        qty = String.valueOf(-1.0);

                    }

                    MySQL.execute("INSERT INTO `product` (`id`,`name`,`brand_id`,`selling_price`,`buying_price`,`qty`,`mfg`,`exp`,`last_update_time`) VALUES "
                            + "('" + productId + "','" + productName + "','" + brandMap.get(brand) + "','" + sellingPrice + "',"
                            + "'" + buyingPrice + "','" + qty + "','" + mysqlmfgDate + "','" + mysqlexpDate + "','" + getCurrentDateTime() + "')");

                    resetProductInput();
                    loadProducts();
                    JOptionPane.showMessageDialog(this, "Product successfully registered", "Success", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception e) {
                logger.log(Level.WARNING, "Product_registration", e);
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Oops! something went wrong", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }


    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:

        String brand = jFormattedTextField13.getText();

        if (brand.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a brand", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (brand.length() > 45) {
            JOptionPane.showMessageDialog(this, "Name length cannot exeed 45 characters", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                ResultSet resultset = MySQL.execute("SELECT * FROM `brand` WHERE `name`='" + brand + "'");

                if (resultset.next()) {
                    JOptionPane.showMessageDialog(this, "Brand already exists", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {

                    if (jComboBox1.getSelectedIndex() == 0) {
                        MySQL.execute("INSERT INTO `brand` (`name`,`last_update_time`) VALUES ('" + brand + "','" + getCurrentDateTime() + "')");

                        JOptionPane.showMessageDialog(this, "Brand successfully added", "Message", JOptionPane.INFORMATION_MESSAGE);
                    } else {

                        int response = JOptionPane.showConfirmDialog(this, "Do you want to update this brand?", "Update brand", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                        if (response == JOptionPane.YES_OPTION) {

                            MySQL.execute("UPDATE `brand` SET `name`='" + brand + "', `last_update_time`='" + getCurrentDateTime() + "' WHERE `name`='" + String.valueOf(jComboBox1.getSelectedItem()) + "'");
                            JOptionPane.showMessageDialog(this, "Brand successfully updated", "Message", JOptionPane.INFORMATION_MESSAGE);

                        }
                    }

                    loadBranbs();

                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "update_brand", e);
                e.printStackTrace();
            }
        }


    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // TODO add your handling code here:

        String minPrice = jFormattedTextField7.getText();
        String maxPrice = jFormattedTextField8.getText();

        if (Double.parseDouble(minPrice) > Double.parseDouble(maxPrice)) {
            JOptionPane.showMessageDialog(this, "Min value is grater than max value", "Warning", JOptionPane.WARNING_MESSAGE);
            loadStock();
        }

        loadStock();


    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
        loadStock();
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        // TODO add your handling code here:        

        String productId = jTextField1.getText();
        String brand = String.valueOf(jComboBox1.getSelectedItem());
        String productName = jFormattedTextField9.getText();
        String sellingPrice = jTextField3.getText();
        String buyingPrice = jTextField2.getText();
        String qty = jTextField5.getText();
        Date mfg = jDateChooser2.getDate();
        Date exp = jDateChooser1.getDate();

        if (productName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter product name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (productName.length() > 45) {
            JOptionPane.showMessageDialog(this, "Product Name length cannot exeed 45 characters", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                ResultSet resultset = MySQL.execute("SELECT * FROM `product` WHERE `name`='" + productName + "' AND `id` <> '" + productId + "' ");
                if (resultset.next()) {
                    JOptionPane.showMessageDialog(this, "Product already exists", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {

                    String mysqlmfgDate;
                    String mysqlexpDate;

                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

                    if (mfg != null) {

                        mysqlmfgDate = String.valueOf(format1.format(mfg));

                    } else {

                        int year = 1023;
                        int month = 01;
                        int day = 01;

                        Date customDate = new Date(year - 1900, month - 1, day);

                        // Format the custom date
                        mysqlmfgDate = String.valueOf(format1.format(customDate));

                    }

                    if (exp != null) {

                        mysqlexpDate = String.valueOf(format1.format(exp));

                    } else {

                        int year = 1023;
                        int month = 01;
                        int day = 01;

                        Date customDate = new Date(year - 1900, month - 1, day);

                        // Format the custom date
                        mysqlexpDate = String.valueOf(format1.format(customDate));

                    }

                    if (!isDouble(sellingPrice)) {
                        sellingPrice = String.valueOf(-1.0);
                    }

                    if (!isDouble(buyingPrice)) {
                        buyingPrice = String.valueOf(-1.0);
                    }

                    if (!isDouble(qty)) {
                        qty = String.valueOf(-1.0);

                    }

                    MySQL.execute("UPDATE `product` SET `name` = '" + productName + "',`brand_id`='" + brandMap.get(brand) + "',"
                            + "`selling_price`='" + sellingPrice + "',`buying_price`= '" + buyingPrice + "',`qty`='" + qty + "',`mfg`='" + mysqlmfgDate + "', "
                            + "`exp`='" + mysqlexpDate + "',`last_update_time`='" + getCurrentDateTime() + "' WHERE `id` = '" + productId + "' ");

                    resetProductInput();
                    loadProducts();
                    JOptionPane.showMessageDialog(this, "Product successfully Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception e) {
                logger.log(Level.WARNING, "Product_registration", e);
                e.printStackTrace();
            }

        }


    }//GEN-LAST:event_jButton28ActionPerformed

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        // TODO add your handling code here:

        loadStock();
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        // TODO add your handling code here:

        resetProductInput();
        loadProducts();
        loadStock();
jPanel32.setVisible(false);

    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:
jPanel32.setVisible(false);
        resetProductInput();
        jFormattedTextField7.setText("0.00");
        jFormattedTextField8.setText("0.00");
        jDateChooser3.setDate(null);
        jDateChooser4.setDate(null);
        loadProducts();
        loadStock();


    }//GEN-LAST:event_jButton25ActionPerformed

    private void selectProduct() {

        int row = jTable3.getSelectedRow();
        if (row != -1) {
            if (invoice != null) {

                invoice.getTextField5().setText(String.valueOf(jTable3.getValueAt(row, 0)));
                invoice.getTextField4().setText(String.valueOf(jTable3.getValueAt(row, 1)));
                invoice.getTextField3().setText(String.valueOf(jTable3.getValueAt(row, 2)));
                invoice.getTextField6().setText(String.valueOf(jTable3.getValueAt(row, 4)));
                invoice.setBuyingPrice(String.valueOf(jTable3.getValueAt(row, 3)));

                if (String.valueOf(jTable3.getValueAt(row, 4)) != "") {
                    if (Double.parseDouble(String.valueOf(jTable3.getValueAt(row, 4))) <= 0) {
                        invoice.getjLabel21().setText(String.valueOf(jTable3.getValueAt(row, 5)));
                        invoice.getjLabel21().setForeground(new Color(204, 0, 0));

                    } else {
                        invoice.getjLabel21().setText(String.valueOf(jTable3.getValueAt(row, 5)));
                        invoice.getjLabel21().setForeground(jTextField1.getForeground());

                    }
                }

            } else if (grn != null) {

                grn.getTextField9().setText(String.valueOf(jTable3.getValueAt(row, 2)));
                grn.getTextField11().setText(String.valueOf(jTable3.getValueAt(row, 0)));
                grn.getTextField10().setText(String.valueOf(jTable3.getValueAt(row, 1)));

                String qtyField = String.valueOf(jTable3.getValueAt(row, 5));
                if (qtyField == "") {
                    grn.getFormattedTextField5().setText("0");
                } else {
                    grn.getFormattedTextField5().setText(String.valueOf(jTable3.getValueAt(row, 5)));
                }
                grn.jFormattedTextField3().setText(String.valueOf(jTable3.getValueAt(row, 3)));
                grn.jFormattedTextField6().setText(String.valueOf(jTable3.getValueAt(row, 4)));

                // Assuming jTable3.getValueAt(row, 4) is a String representing a date
                String MFGdateString = (String) jTable3.getValueAt(row, 6);
                String EXPdateString = (String) jTable3.getValueAt(row, 7);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the date format as needed

                if (MFGdateString == "") {

                    try {

                        String dateString = "0001-01-01";

                        Date date = dateFormat.parse(dateString);

                        grn.jDateChooser1().setDate(date);

                    } catch (Exception e) {
                        // Handle the parsing exception here
                        e.printStackTrace();
                    }

                } else {

                    try {

                        Date date1 = dateFormat.parse(MFGdateString);
                        grn.jDateChooser1().setDate(date1);

                    } catch (Exception e) {
                        // Handle the parsing exception here
                        e.printStackTrace();
                    }

                }

                if (EXPdateString == "") {
                    try {

                        String dateString = "0001-02-01";

                        Date date = dateFormat.parse(dateString);

                        grn.jDateChooser2().setDate(date);

                    } catch (Exception e) {
                        // Handle the parsing exception here
                        e.printStackTrace();
                    }

                } else {

                    try {

                        Date date2 = dateFormat.parse(EXPdateString);
                        grn.jDateChooser2().setDate(date2);

                    } catch (Exception e) {
                        // Handle the parsing exception here
                        e.printStackTrace();
                    }

                }

            }

            if (dailog != null) {
                dailog.dispose();
            }
        }

    }
    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:

        jTextField1.setEditable(false);

        try {

            if (evt.getClickCount() == 2) {
                selectProduct();
            }

            if (grn != null || invoice != null) {
                jPanel32.setVisible(true);
            }

            jButton28.setEnabled(true);
            int row = jTable3.getSelectedRow();
            
            
            String id =String.valueOf(jTable3.getValueAt(row, 0));

            jTextField1.setText(String.valueOf(jTable3.getValueAt(row, 0)));
            jFormattedTextField9.setText(String.valueOf(jTable3.getValueAt(row, 2)));
            jTextField2.setText(String.valueOf(jTable3.getValueAt(row, 3)));
            jTextField3.setText(String.valueOf(jTable3.getValueAt(row, 4)));
            jTextField5.setText(String.valueOf(jTable3.getValueAt(row, 5)));

            // Assuming jTable3.getValueAt(row, 4) is a String representing a date
            String MFGdateString = (String) jTable3.getValueAt(row, 6);
            String EXPdateString = (String) jTable3.getValueAt(row, 7);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the date format as needed

            if (MFGdateString == "") {
                try {
                    String dateString = "0001-01-01";

                    Date date = dateFormat.parse(dateString);

                    jDateChooser2.setDate(date);
                } catch (Exception e) {
                    // Handle the parsing exception here
                    e.printStackTrace();
                }
            } else {

                try {

                    Date date1 = dateFormat.parse(MFGdateString);
                    jDateChooser2.setDate(date1);

                } catch (Exception e) {
                    // Handle the parsing exception here
                    e.printStackTrace();
                }

            }

            if (EXPdateString == "") {
                try {
                    String dateString = "0001-02-01";

                    Date date = dateFormat.parse(dateString);

                    jDateChooser1.setDate(date);
                } catch (Exception e) {
                    // Handle the parsing exception here
                    e.printStackTrace();
                }
            } else {

                try {

                    Date date2 = dateFormat.parse(EXPdateString);
                    jDateChooser1.setDate(date2);

                } catch (Exception e) {
                    // Handle the parsing exception here
                    e.printStackTrace();
                }

            }

            jComboBox1.setSelectedItem(String.valueOf(jTable3.getValueAt(row, 1)));

            jButton26.setEnabled(false);
            
        

        } catch (Exception e) {
            logger.log(Level.WARNING, "jTable3_mouseclick", e);
            e.printStackTrace();
        }


    }//GEN-LAST:event_jTable3MouseClicked

    private void jFormattedTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField7ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
        if (grn == null && invoice == null) {
               loadProducts();
            }
        

    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jFormattedTextField9KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField9KeyReleased
        loadProducts();
    }//GEN-LAST:event_jFormattedTextField9KeyReleased

    private void jTable3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable3MouseEntered

    private void jTable3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable3MouseDragged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int row = jTable3.getSelectedRow();
        if (row != -1) {
            jPanel32.setVisible(true);
            selectProduct();
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable3FocusLost
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTable3FocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel card3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private javax.swing.JFormattedTextField jFormattedTextField13;
    private javax.swing.JFormattedTextField jFormattedTextField7;
    private javax.swing.JFormattedTextField jFormattedTextField8;
    private javax.swing.JFormattedTextField jFormattedTextField9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
