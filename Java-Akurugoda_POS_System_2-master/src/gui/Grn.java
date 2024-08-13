/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import com.toedter.calendar.JDateChooser;
import static gui.Login.logger;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.GRNitem;
import static model.MyDateTimeUtils.getCurrentDateTime;
import model.MyMethods;
import model.MySQL;
import model.onlineMYSQL;

/**
 *
 * @author Dell
 */
public class Grn extends javax.swing.JPanel {

    HashMap<String, GRNitem> grnItemMap = new HashMap<>();
    Color defaultButtonColor, defaultTextColor;

    private double balance = 0;
    public double balancepayment = 0;

    public void setpayment(String x) {
        jLabel32.setText(x);
        loadGRNitems();

    }

    public JTextField getTextField11() {
        return jTextField11;
    }

    public JTextField getTextField9() {
        return jTextField9;
    }

    public JTextField getTextField10() {
        return jTextField10;
    }

    public JFormattedTextField getFormattedTextField5() {
        return jFormattedTextField5;
    }

    public JFormattedTextField jFormattedTextField3() {
        return jFormattedTextField3;
    }

    public JFormattedTextField jFormattedTextField6() {
        return jFormattedTextField6;
    }

    public JDateChooser jDateChooser1() {
        return jDateChooser1;
    }

    public JDateChooser jDateChooser2() {
        return jDateChooser2;
    }

    public void setJLabel43(String label) {
        jLabel43.setText(label);
    }

    public Grn() {
        
        initComponents();
        GenerateGRNnumber();
        jDateChooser1.setMaxSelectableDate(new Date());
        jDateChooser2.setMinSelectableDate(new Date());

        //jLabel8.setText(Signin.getEmployeeEmail());
        MyMethods.setTableHeaderFontSize(jTable2, 16);
        jButton15.setEnabled(false); //save grn button

        jFormattedTextField5.setEditable(false);

        this.defaultTextColor = jTextField9.getForeground();
        jButton15.setEnabled(false);

    }

   

    private void loadGRNitems() {

        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);
        double total = 0;
        for (GRNitem grn : grnItemMap.values()) {

            Vector<String> vector = new Vector<>();

            vector.add(grn.getProductID());
            vector.add(grn.getBrandName());
            vector.add(grn.getProductName());
            vector.add(String.valueOf(grn.getAddedQty()));
            vector.add(String.valueOf(grn.getBuyingPrice()));
            vector.add(String.valueOf(grn.getSellingPrice()));

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

            double itemTotal = grn.getAddedQty() * grn.getBuyingPrice();
            vector.add(String.valueOf(itemTotal));

            total += itemTotal;

            model.addRow(vector);
        }
        jLabel45.setText("GRN  items (" + model.getRowCount() + ")");
        jLabel26.setText(String.valueOf(total) + "0");

        balance = (0 - total) + balancepayment;

        jLabel32.setText(String.valueOf(balance));

    }

    private void GenerateGRNnumber() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now = LocalDateTime.MAX.now();

        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("MM");
        LocalDateTime now1 = LocalDateTime.MAX.now();

        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd");
        LocalDateTime now2 = LocalDateTime.MAX.now();

        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("HH");
        LocalDateTime now3 = LocalDateTime.MAX.now();

        DateTimeFormatter dtf4 = DateTimeFormatter.ofPattern("mm");
        LocalDateTime now4 = LocalDateTime.MAX.now();

        DateTimeFormatter dtf5 = DateTimeFormatter.ofPattern("ss");
        LocalDateTime now5 = LocalDateTime.MAX.now();

        DateTimeFormatter dtf6 = DateTimeFormatter.ofPattern("SSS");
        LocalDateTime now6 = LocalDateTime.MAX.now();

        String date = dtf.format(now);
        String date2 = dtf1.format(now1);
        String date3 = dtf2.format(now2);
        String date4 = dtf3.format(now3);
        String date5 = dtf4.format(now4);
        String date6 = dtf5.format(now5);

        String date1 = date.substring(3);

        String curdate = date1 + date2 + date3 + date4 + date5 + date6;
        String roundedTime = curdate.substring(0, curdate.length() - 2);

        jTextField8.setText(roundedTime); //round off to eight digit

    }

    private void resetProduct() {
        jTextField11.setText("");
        jFormattedTextField5.setText("");
        jFormattedTextField7.setText("");
        jFormattedTextField3.setText("");
        jFormattedTextField4.setText("");
        jFormattedTextField6.setText("");
        jTextField9.setText(" ");
        jTextField10.setText(" ");
        jDateChooser1.setDate(null);
        jDateChooser2.setDate(null);
        jLabel32.setText("0");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        card2 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        jPanel54 = new javax.swing.JPanel();
        jPanel48 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jPanel51 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jPanel52 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jPanel53 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jPanel39 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel55 = new javax.swing.JPanel();
        jPanel56 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jButton22 = new javax.swing.JButton();
        jPanel57 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jPanel58 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jFormattedTextField5 = new javax.swing.JFormattedTextField();
        jLabel47 = new javax.swing.JLabel();
        jFormattedTextField7 = new javax.swing.JFormattedTextField();
        jPanel11 = new javax.swing.JPanel();
        jFormattedTextField6 = new javax.swing.JFormattedTextField();
        jLabel38 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jPanel45 = new javax.swing.JPanel();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jPanel41 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jButton15 = new javax.swing.JButton();
        jPanel43 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jPanel60 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jPanel59 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel45 = new javax.swing.JLabel();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        card2.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        card2.setName("card2"); // NOI18N
        card2.setLayout(new java.awt.BorderLayout(0, 5));

        jPanel44.setPreferredSize(new java.awt.Dimension(1577, 350));
        jPanel44.setLayout(new java.awt.BorderLayout(0, 5));

        jPanel47.setLayout(new java.awt.BorderLayout(10, 0));

        jPanel54.setLayout(new java.awt.BorderLayout(0, 5));

        jPanel48.setPreferredSize(new java.awt.Dimension(300, 356));
        jPanel48.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        jPanel49.setLayout(new java.awt.GridLayout(5, 1, 0, 5));

        jPanel50.setLayout(new java.awt.BorderLayout());

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel34.setText("<html> <p>GRN number</p> <html>");
        jLabel34.setPreferredSize(new java.awt.Dimension(80, 0));
        jPanel50.add(jLabel34, java.awt.BorderLayout.WEST);

        jTextField8.setEditable(false);
        jTextField8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField8.setPreferredSize(new java.awt.Dimension(220, 22));
        jPanel50.add(jTextField8, java.awt.BorderLayout.CENTER);

        jPanel49.add(jPanel50);

        jPanel51.setLayout(new java.awt.BorderLayout());

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel35.setText("Name");
        jLabel35.setPreferredSize(new java.awt.Dimension(80, 0));
        jPanel51.add(jLabel35, java.awt.BorderLayout.WEST);

        jTextField9.setEditable(false);
        jTextField9.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jTextField9.setPreferredSize(new java.awt.Dimension(220, 22));
        jPanel51.add(jTextField9, java.awt.BorderLayout.CENTER);

        jPanel49.add(jPanel51);

        jPanel52.setLayout(new java.awt.BorderLayout());

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel36.setText("Brand");
        jLabel36.setPreferredSize(new java.awt.Dimension(80, 0));
        jPanel52.add(jLabel36, java.awt.BorderLayout.WEST);

        jTextField10.setEditable(false);
        jTextField10.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jTextField10.setPreferredSize(new java.awt.Dimension(220, 22));
        jPanel52.add(jTextField10, java.awt.BorderLayout.CENTER);

        jPanel49.add(jPanel52);

        jPanel53.setLayout(new java.awt.BorderLayout());

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel37.setText("<html> <p>Buying price</p> <html>");
        jLabel37.setPreferredSize(new java.awt.Dimension(80, 0));
        jPanel53.add(jLabel37, java.awt.BorderLayout.WEST);

        jFormattedTextField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel53.add(jFormattedTextField3, java.awt.BorderLayout.CENTER);

        jPanel49.add(jPanel53);

        jPanel39.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("M.F.D.");
        jLabel4.setPreferredSize(new java.awt.Dimension(80, 16));
        jPanel39.add(jLabel4, java.awt.BorderLayout.WEST);

        jDateChooser1.setDateFormatString("yyyy-MM-dd");
        jDateChooser1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel39.add(jDateChooser1, java.awt.BorderLayout.CENTER);

        jPanel49.add(jPanel39);

        jPanel48.add(jPanel49);

        jPanel55.setLayout(new java.awt.GridLayout(5, 1, 0, 5));

        jPanel56.setLayout(new java.awt.BorderLayout());

        jLabel46.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel46.setText("<html> <p>Company</p> <html>");
        jLabel46.setPreferredSize(new java.awt.Dimension(90, 0));
        jPanel56.add(jLabel46, java.awt.BorderLayout.PAGE_START);

        jButton22.setBackground(new java.awt.Color(51, 51, 51));
        jButton22.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton22.setForeground(new java.awt.Color(255, 255, 255));
        jButton22.setText("Select product");
        jButton22.setPreferredSize(new java.awt.Dimension(45, 45));
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });
        jPanel56.add(jButton22, java.awt.BorderLayout.PAGE_END);

        jPanel55.add(jPanel56);

        jPanel57.setLayout(new java.awt.BorderLayout());

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel39.setText("<html> <p>Product ID</p> <html>");
        jLabel39.setPreferredSize(new java.awt.Dimension(90, 0));
        jPanel57.add(jLabel39, java.awt.BorderLayout.WEST);

        jTextField11.setEditable(false);
        jTextField11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField11.setPreferredSize(new java.awt.Dimension(220, 22));
        jTextField11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField11ActionPerformed(evt);
            }
        });
        jPanel57.add(jTextField11, java.awt.BorderLayout.CENTER);

        jPanel55.add(jPanel57);

        jPanel58.setLayout(new java.awt.BorderLayout());

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel41.setText("Quantity");
        jLabel41.setPreferredSize(new java.awt.Dimension(90, 0));
        jPanel58.add(jLabel41, java.awt.BorderLayout.WEST);

        jFormattedTextField5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jFormattedTextField5.setMargin(new java.awt.Insets(2, 5, 2, 6));
        jFormattedTextField5.setMaximumSize(new java.awt.Dimension(40, 33));
        jFormattedTextField5.setMinimumSize(new java.awt.Dimension(40, 33));
        jFormattedTextField5.setName(""); // NOI18N
        jFormattedTextField5.setPreferredSize(new java.awt.Dimension(60, 33));
        jFormattedTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField5ActionPerformed(evt);
            }
        });
        jPanel58.add(jFormattedTextField5, java.awt.BorderLayout.CENTER);

        jLabel47.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel47.setText("Quantity");
        jLabel47.setPreferredSize(new java.awt.Dimension(90, 0));
        jPanel58.add(jLabel47, java.awt.BorderLayout.PAGE_END);

        jFormattedTextField7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jFormattedTextField7.setMargin(new java.awt.Insets(2, 6, 2, 5));
        jFormattedTextField7.setMaximumSize(new java.awt.Dimension(50, 33));
        jFormattedTextField7.setMinimumSize(new java.awt.Dimension(50, 33));
        jFormattedTextField7.setName(""); // NOI18N
        jFormattedTextField7.setPreferredSize(new java.awt.Dimension(120, 33));
        jFormattedTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField7ActionPerformed(evt);
            }
        });
        jPanel58.add(jFormattedTextField7, java.awt.BorderLayout.LINE_END);

        jPanel55.add(jPanel58);

        jPanel11.setLayout(new java.awt.BorderLayout());

        jFormattedTextField6.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField6.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel11.add(jFormattedTextField6, java.awt.BorderLayout.CENTER);

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel38.setText("<html> <p>Selling price</p> <html>");
        jLabel38.setPreferredSize(new java.awt.Dimension(90, 0));
        jPanel11.add(jLabel38, java.awt.BorderLayout.WEST);

        jPanel55.add(jPanel11);

        jPanel38.setLayout(new java.awt.BorderLayout());

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel27.setText("E.X.P.");
        jLabel27.setPreferredSize(new java.awt.Dimension(90, 0));
        jPanel38.add(jLabel27, java.awt.BorderLayout.WEST);

        jDateChooser2.setDateFormatString("yyyy-MM-dd");
        jDateChooser2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel38.add(jDateChooser2, java.awt.BorderLayout.CENTER);

        jPanel55.add(jPanel38);

        jPanel48.add(jPanel55);

        jPanel54.add(jPanel48, java.awt.BorderLayout.CENTER);

        jPanel45.setPreferredSize(new java.awt.Dimension(628, 45));
        jPanel45.setLayout(new java.awt.GridLayout(1, 3, 10, 0));

        jButton16.setBackground(new java.awt.Color(0, 153, 255));
        jButton16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton16.setForeground(new java.awt.Color(255, 255, 255));
        jButton16.setText("Clear GRN");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel45.add(jButton16);

        jButton17.setBackground(new java.awt.Color(0, 153, 255));
        jButton17.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton17.setForeground(new java.awt.Color(255, 255, 255));
        jButton17.setText("Clear input");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jPanel45.add(jButton17);

        jButton18.setBackground(new java.awt.Color(0, 153, 255));
        jButton18.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton18.setForeground(new java.awt.Color(255, 255, 255));
        jButton18.setText("Add to GRN");
        jButton18.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                jButton18ComponentMoved(evt);
            }
        });
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        jPanel45.add(jButton18);

        jPanel54.add(jPanel45, java.awt.BorderLayout.SOUTH);

        jPanel47.add(jPanel54, java.awt.BorderLayout.CENTER);

        jPanel23.setBackground(new java.awt.Color(153, 204, 255));
        jPanel23.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel23.setPreferredSize(new java.awt.Dimension(270, 0));
        jPanel23.setLayout(new java.awt.GridLayout(6, 1, 0, 5));

        jPanel37.setOpaque(false);
        jPanel37.setLayout(new java.awt.BorderLayout());

        jLabel25.setBackground(new java.awt.Color(153, 204, 255));
        jLabel25.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Total");
        jLabel25.setOpaque(true);
        jPanel37.add(jLabel25, java.awt.BorderLayout.CENTER);

        jLabel26.setBackground(new java.awt.Color(153, 204, 255));
        jLabel26.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("0.00");
        jLabel26.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        jLabel26.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel26.setOpaque(true);
        jLabel26.setPreferredSize(new java.awt.Dimension(200, 25));
        jPanel37.add(jLabel26, java.awt.BorderLayout.EAST);

        jPanel23.add(jPanel37);

        jPanel40.setOpaque(false);
        jPanel40.setLayout(new java.awt.BorderLayout());

        jLabel30.setBackground(new java.awt.Color(153, 204, 255));
        jLabel30.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Payment");
        jLabel30.setOpaque(true);
        jPanel40.add(jLabel30, java.awt.BorderLayout.CENTER);

        jFormattedTextField4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jFormattedTextField4.setPreferredSize(new java.awt.Dimension(150, 22));
        jFormattedTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField4KeyReleased(evt);
            }
        });
        jPanel40.add(jFormattedTextField4, java.awt.BorderLayout.EAST);

        jPanel23.add(jPanel40);

        jPanel41.setOpaque(false);
        jPanel41.setLayout(new java.awt.BorderLayout());

        jLabel31.setBackground(new java.awt.Color(153, 204, 255));
        jLabel31.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("Balance");
        jLabel31.setOpaque(true);
        jPanel41.add(jLabel31, java.awt.BorderLayout.CENTER);

        jLabel32.setBackground(new java.awt.Color(153, 204, 255));
        jLabel32.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel32.setText("0.00");
        jLabel32.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        jLabel32.setOpaque(true);
        jLabel32.setPreferredSize(new java.awt.Dimension(150, 16));
        jPanel41.add(jLabel32, java.awt.BorderLayout.EAST);

        jPanel23.add(jPanel41);

        jPanel42.setOpaque(false);
        jPanel42.setLayout(new java.awt.BorderLayout());

        jButton15.setBackground(new java.awt.Color(51, 51, 51));
        jButton15.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setText("Save GRN");
        jButton15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton15.setPreferredSize(new java.awt.Dimension(200, 40));
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel42.add(jButton15, java.awt.BorderLayout.CENTER);

        jPanel23.add(jPanel42);

        jPanel43.setOpaque(false);
        jPanel43.setLayout(new java.awt.BorderLayout());

        jLabel33.setBackground(new java.awt.Color(153, 204, 255));
        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("...");
        jLabel33.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel33.setOpaque(true);
        jLabel33.setPreferredSize(new java.awt.Dimension(9, 40));
        jPanel43.add(jLabel33, java.awt.BorderLayout.CENTER);

        jPanel23.add(jPanel43);

        jPanel47.add(jPanel23, java.awt.BorderLayout.EAST);

        jPanel44.add(jPanel47, java.awt.BorderLayout.CENTER);

        jPanel60.setPreferredSize(new java.awt.Dimension(1577, 45));
        jPanel60.setLayout(new java.awt.BorderLayout());

        jPanel3.setPreferredSize(new java.awt.Dimension(320, 124));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel42.setText("Cashier : ");
        jLabel42.setPreferredSize(new java.awt.Dimension(80, 25));
        jPanel3.add(jLabel42, java.awt.BorderLayout.WEST);

        jLabel43.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel43.setText(" ");
        jLabel43.setMaximumSize(new java.awt.Dimension(35, 25));
        jLabel43.setMinimumSize(new java.awt.Dimension(35, 25));
        jLabel43.setPreferredSize(new java.awt.Dimension(35, 25));
        jPanel3.add(jLabel43, java.awt.BorderLayout.CENTER);

        jPanel60.add(jPanel3, java.awt.BorderLayout.WEST);

        jPanel44.add(jPanel60, java.awt.BorderLayout.NORTH);

        card2.add(jPanel44, java.awt.BorderLayout.NORTH);

        jPanel59.setLayout(new java.awt.BorderLayout(0, 5));

        jTable2.setAutoCreateRowSorter(true);
        jTable2.setFont(new java.awt.Font("Iskoola Pota", 0, 16)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Brand", "Name", "Quantity", "Buying price", "Selling price", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setIntercellSpacing(new java.awt.Dimension(10, 0));
        jTable2.setRowHeight(40);
        jTable2.setSelectionBackground(new java.awt.Color(224, 224, 224));
        jTable2.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(3).setPreferredWidth(0);
        }

        jPanel59.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jLabel45.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel45.setText("GRN  items (0)");
        jPanel59.add(jLabel45, java.awt.BorderLayout.NORTH);

        card2.add(jPanel59, java.awt.BorderLayout.CENTER);

        add(card2);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:

        String qty = jFormattedTextField5.getText();
        String addedQty = jFormattedTextField7.getText();
        String buying_price = jFormattedTextField3.getText();
        String selling_price = jFormattedTextField6.getText();
        Date mfg = jDateChooser1.getDate();
        Date exp = jDateChooser2.getDate();

//        System.out.println(mfg);
//        System.out.println(exp);
        //add validations
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        if (jTextField11.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select the product", "Warning", JOptionPane.WARNING_MESSAGE);
            jButton22.grabFocus();
        } else if (addedQty.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter quantity", "Warning", JOptionPane.WARNING_MESSAGE);
            jFormattedTextField5.grabFocus();
        } else if (buying_price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter buying price", "Warning", JOptionPane.WARNING_MESSAGE);
            jFormattedTextField3.grabFocus();
        } else if (selling_price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter selling price", "Warning", JOptionPane.WARNING_MESSAGE);
            jFormattedTextField6.grabFocus();
        } else {
            try {
                if (mfg == null) {
                    mfg = format1.parse("0000-00-00");
                }

                if (exp == null) {
                    exp = format1.parse("0000-00-00");
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "date", e);
            }

            jButton15.setEnabled(true); //save grn button

            GRNitem grnItem = new GRNitem();

            grnItem.setProductID(jTextField11.getText());
            grnItem.setBrandName(jTextField10.getText());
            grnItem.setProductName(jTextField9.getText());
            grnItem.setQty(Double.parseDouble(qty));
            grnItem.setAddedQty(Double.parseDouble(addedQty));
            grnItem.setBuyingPrice(Double.parseDouble(buying_price));
            grnItem.setSellingPrice(Double.parseDouble(selling_price));
            grnItem.setMfd(mfg);
            grnItem.setExp(exp);

//            System.out.println(String.valueOf(mfg));
            if (grnItemMap.get(jTextField11.getText()) == null) {
                grnItemMap.put(jTextField11.getText(), grnItem);
                resetProduct();
            } else {
                GRNitem found = grnItemMap.get(jTextField11.getText());

                if (Double.parseDouble(buying_price) == grnItem.getBuyingPrice()
                        && Double.parseDouble(selling_price) == found.getSellingPrice()
                        && mfg.compareTo(found.getMfd()) == 0
                        && exp.compareTo(found.getExp()) == 0) {

                    found.setAddedQty(found.getAddedQty() + Double.parseDouble(addedQty));
                    resetProduct();
                } else {
                    JOptionPane.showMessageDialog(this, "GRN item already exists with different dates and prices", "Entry error", JOptionPane.ERROR_MESSAGE);
                }
            }
            jButton15.setEnabled(true);
            loadGRNitems();

        }


    }//GEN-LAST:event_jButton18ActionPerformed

    private void jFormattedTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField4KeyReleased
        // TODO add your handling code here:
        boolean inputError = false;

        if (Double.parseDouble(jLabel26.getText()) != 0) {

            if (!jFormattedTextField4.getText().matches("\\d+(\\.\\d+)?")) {
                if (jFormattedTextField4.getText().equals("")) {
                    jFormattedTextField4.setForeground(defaultTextColor);
                    jLabel32.setText(jFormattedTextField4.getText());
                } else {
                    inputError = true;
                    jFormattedTextField4.setForeground(Color.RED);
                }

            } else {
                jFormattedTextField4.setForeground(defaultTextColor);
                double balance = Double.parseDouble(jFormattedTextField4.getText()) - Double.parseDouble(jLabel26.getText());

                balance = balance + balancepayment;
                jLabel32.setForeground(jLabel33.getForeground());
                jLabel32.setText(String.valueOf(balance));
            }

            if (inputError) {
                jLabel33.setText("Invalid payment amount");
                jLabel33.setForeground(Color.RED);
                jButton15.setEnabled(false);
            } else {
                jLabel33.setText("...");
                jLabel33.setForeground(defaultTextColor);
                jButton15.setEnabled(true);
            }
        }
        //System.out.println(Double.valueOf(payment));

    }//GEN-LAST:event_jFormattedTextField4KeyReleased

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        resetProduct();
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:

        int response = JOptionPane.showConfirmDialog(this, "Do you want to clear the current grn items?", "Clear GRN", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {

            grnItemMap.clear();
            resetProduct();
            jTextField9.setText(" ");
            jFormattedTextField4.setText("");
            jLabel32.setText("0.00");
            jLabel26.setText("0.00");
            loadGRNitems();
            balancepayment = 0;
            jButton15.setEnabled(false); //save grn button
        }


    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed

        try {
            String grnNumber = jTextField8.getText();
            String employeeEmail = jLabel43.getText();
            String paidAmount = jFormattedTextField4.getText();

            if (paidAmount.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter paid amount", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

                MySQL.execute("INSERT INTO `grn`(`id`,`employee_email`,`date_time`,`paid_amount`,`last_update_time`)"
                        + " VALUES('" + grnNumber + "','" + employeeEmail + "'"
                        + ",'" + getCurrentDateTime() + "','" + paidAmount + "','" + getCurrentDateTime() + "')");

                for (GRNitem grnItem : grnItemMap.values()) {

                    Double availableQty = grnItem.getQty();
                    Double addedQty = grnItem.getAddedQty();
                    Double newQty = availableQty + addedQty;

                    System.out.println(grnItem.getProductID());

                    MySQL.execute("UPDATE `product` SET `selling_price`='" + grnItem.getSellingPrice() + "', `buying_price`='" + grnItem.getBuyingPrice() + "',"
                            + " `qty`='" + newQty + "', `mfg`='" + format1.format(grnItem.getMfd()) + "', `exp`='" + format1.format(grnItem.getExp()) + "', "
                            + "`last_update_time`='" + getCurrentDateTime() + "' WHERE `id`='" + grnItem.getProductID() + "' ");

                    MySQL.execute("INSERT INTO `grn_item` (`qty`,`buying_price`,`product_id`,`grn_id`) "
                            + "VALUES ('" + addedQty + "','" + grnItem.getBuyingPrice() + "', '" + grnItem.getProductID() + "', '" + grnNumber + "')");

                    MySQL.execute("INSERT INTO `grn_history_item` (`name`, `qty`, `buying_price`, `brand_name`, `grn_id`) "
                            + "VALUES ('" + grnItem.getProductName() + "', '" + addedQty + "', '" + grnItem.getBuyingPrice() + "', '" + grnItem.getBrandName() + "', '" + grnNumber + "')");

                }

                resetProduct();
                grnItemMap.clear();
                jLabel32.setText("0.00");

                jFormattedTextField4.setText("");
                jFormattedTextField6.setText("");

                loadGRNitems();
                JOptionPane.showMessageDialog(this, "GRN successfully added", "Success", JOptionPane.INFORMATION_MESSAGE);

                GenerateGRNnumber();
                jButton15.setEnabled(false);
            }

        } catch (Exception e) {
            logger.log(Level.WARNING, "GRN", e);
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton15ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:

        if (evt.getClickCount() == 2) {
            int response = JOptionPane.showConfirmDialog(this, "Do you want to remove this item?", "Remove item", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                grnItemMap.remove(String.valueOf(jTable2.getValueAt(jTable2.getSelectedRow(), 0)));
                loadGRNitems();
            }
        }


    }//GEN-LAST:event_jTable2MouseClicked

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jButton18ComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jButton18ComponentMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton18ComponentMoved

    private void jFormattedTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField5ActionPerformed

    private void jFormattedTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField7ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:
        SupportFrame sf = new SupportFrame(new Home("", "", ""), true);

        Stock stock = new Stock();

        sf.getJPanel().removeAll();
        sf.getJPanel().add(stock);
        sf.getJPanel().revalidate();
        sf.getJPanel().repaint();

        stock.setGrn(this);
        stock.setDiaload(sf);

        sf.setVisible(true);

    }//GEN-LAST:event_jButton22ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel card2;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton22;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JFormattedTextField jFormattedTextField5;
    private javax.swing.JFormattedTextField jFormattedTextField6;
    private javax.swing.JFormattedTextField jFormattedTextField7;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
