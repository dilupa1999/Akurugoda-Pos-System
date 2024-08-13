/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import static gui.Login.logger;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.font.TextAttribute;
import java.nio.file.FileSystems;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.InvoiceItem;
import static model.MyDateTimeUtils.getCurrentDateTime;
import model.MyMethods;
import model.MySQL;
import model.onlineMYSQL;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Dell
 */
public class Home extends javax.swing.JFrame {

    private java.util.HashMap<String, JButton> buttonMap = new HashMap<>(); //button map
    HashMap<String, InvoiceItem> invoiceItemMap = new HashMap<>();
    HashMap<String, String> paymentMethodMap = new HashMap<>();

    Color defaultButtonColor, defaultTextColor;
    CardLayout cardLayout;

    /**
     * Creates new form Home
     */
    private String buying_price = "";
    
    public void setBuyingPrice(String buyingPrice){
        this.buying_price = buyingPrice;
    }
    
    public JTextField getTextField1() {
        return jTextField1;
    }

    public JTextField getTextField5() {
        return jTextField5;
    }

    public JTextField getTextField3() {
        return jTextField3;
    }

    public JTextField getTextField6() {
        return jTextField6;
    }

    public JTextField getTextField4() {
        return jTextField4;
    }

    public JLabel getjLabel21() {
        return jLabel21;
    }

    private String email, firstname, lastname;

    public Home(String email, String firstname, String lastname) {
        initComponents();

        //full screen
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;

        ImageIcon imageIcon = new ImageIcon(Home.class.getResource("/resources/java1.png"));
        this.setIconImage(imageIcon.getImage());

        Font customFont = new Font("Iskoola Pota", Font.PLAIN, 14);

//             Set the font for the message
        UIManager.put("OptionPane.messageFont", customFont);

        //set default colors
        this.defaultButtonColor = jButton8.getBackground();
        this.defaultTextColor = jButton8.getForeground();

        MyMethods.setTableHeaderFontSize(jTable1, 16);

        GenerateInvoiceNumber();
        GenerateCustomerMobile();

        jButton1.grabFocus();

        jLabel3.setText(this.firstname + " " + this.lastname);

        loadSidebarButtonMap();

        jButton11.setEnabled(false); // print invoice btn

    }

    private void loadSidebarButtonMap() {
        buttonMap.put("btn1", jButton1);
        buttonMap.put("btn2", jButton2);
        buttonMap.put("btn3", jButton3);
        buttonMap.put("btn4", jButton4);
        buttonMap.put("btn5", jButton7);
        buttonMap.put("btn6", jButton8);
        buttonMap.put("btn7", jButton16);
    }

    private void focusSideBarButton(int button) {
        for (int i = 1; i < 8; i++) {
            String key = "btn" + i;
            if (i == button) {
                buttonMap.get(key).setBackground(new Color(0, 153, 255));
                buttonMap.get(key).setForeground(Color.WHITE);
            } else {
                buttonMap.get(key).setBackground(defaultButtonColor);
                buttonMap.get(key).setForeground(defaultTextColor);
            }
        }
    }

   

    private void GenerateInvoiceNumber() {

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

        jTextField2.setText(roundedTime);

    }

    private void GenerateCustomerMobile() {

        long id = System.currentTimeMillis();
        String roundedTime = String.format("%08d", Integer.parseInt(String.valueOf(id).substring(2, 11))); //round off to eight digit

        jTextField1.setText(roundedTime);
    }

    private void loadInvoiceItem() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        double total = 0;
        int count = 1;
        for (InvoiceItem invoice : invoiceItemMap.values()) {

            Vector<String> vector = new Vector<>();

            vector.add(String.valueOf(count++));
            vector.add(invoice.getStockID());
            vector.add(invoice.getBrand());
            vector.add(invoice.getName());
            vector.add(invoice.getQty());
            vector.add(invoice.getSellingPrice());

            double itemTotal = Double.parseDouble(invoice.getSellingPrice()) * Double.parseDouble(invoice.getQty());
            vector.add(String.valueOf(itemTotal));

            total += itemTotal;

            model.addRow(vector);
        }

        jLabel8.setText(String.valueOf(total));
        this.total = total;
    }

    private double total = 0;
    private double discount = 0;
    private double payment = 0;
    private boolean paymentEntered = false;
    private double balance = 0;
    public double balancepayment = 0;

    public void calculate() {

        boolean inputError = false;
        if (!jFormattedTextField2.getText().matches("\\d+(\\.\\d+)?")) {
            if (jFormattedTextField2.getText().equals("")) {
                paymentEntered = false;
                jFormattedTextField2.setForeground(defaultTextColor);
            } else {
                inputError = true;
                jFormattedTextField2.setForeground(Color.RED);
            }
            paymentEntered = false;
        } else {
            paymentEntered = true;

            jFormattedTextField2.setForeground(defaultTextColor);
        }

        if (!jFormattedTextField1.getText().matches("\\d+(\\.\\d+)?")) {
            if (jFormattedTextField1.getText().equals("")) {
                this.discount = 0;
                jFormattedTextField1.setForeground(defaultTextColor);
            } else {
                inputError = true;
                jFormattedTextField1.setForeground(Color.RED);
            }
        } else {
            this.discount = Double.parseDouble(jFormattedTextField1.getText());
            jFormattedTextField1.setForeground(defaultTextColor);
        }

        if (inputError) {
            jLabel15.setText("Invalid payment amount");
            jLabel15.setForeground(Color.RED);
        } else {
            jLabel15.setText("...");
            jLabel15.setForeground(defaultTextColor);
        }

        if (balance < 0) {
//            jButton11.setEnabled(false);
        } else if (invoiceItemMap.size() == 0) {
            jButton11.setEnabled(false);
        } else {
            jButton11.setEnabled(true);
        }

        double total = this.total;
        total -= discount;
        if (total < 0) {
            //discount error
        } else {

            if (total != 0) {
                jFormattedTextField2.setEditable(true);
                if (paymentEntered) {
                    this.payment = Double.parseDouble(jFormattedTextField2.getText());
                } else {
                    this.payment = 0;
                }

                balance = payment - total;

            }
            jLabel13.setText(String.valueOf(total));
        }

        jLabel14.setText(String.valueOf(balancepayment + balance));
        if (balance < 0) {
//            jButton11.setEnabled(false);
        } else if (invoiceItemMap.size() == 0) {
            jButton11.setEnabled(false);
        } else {
            jButton11.setEnabled(true);
        }

    }

    private void clearProductEntry() {
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jFormattedTextField3.setText("");

        jFormattedTextField2.setText("");
        jLabel21.setText(" ");

    }
//

    private void resetInvoice() {

        jTextField1.setText("");
        jFormattedTextField1.setText("");
        jLabel21.setText(" ");
        jLabel8.setText("0.00");
        jLabel14.setText("0.00");
        jLabel13.setText("0.00");
        jLabel22.setText("Invoice items (0)");

        GenerateInvoiceNumber();
        GenerateCustomerMobile();
        clearProductEntry();
        invoiceItemMap.clear();
        loadInvoiceItem();
    }

    public void validatequantityInput() {
        double instock = Double.parseDouble(jLabel21.getText().isEmpty() ? "0" : jLabel21.getText());

        String qty = jFormattedTextField3.getText();

        if (!qty.matches("\\d+(\\.\\d+)?")) {
            if (!qty.equals("")) {
                jFormattedTextField3.setForeground(Color.red);
            }
        } else {
            if (Double.parseDouble(qty) > instock) {
                jFormattedTextField3.setForeground(Color.red);
            } else {
                jFormattedTextField3.setForeground(jLabel1.getForeground());
            }
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

        jPanel1 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jButton15 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        cardPanel = new javax.swing.JPanel();
        card1 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel28 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel29 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jPanel30 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jButton21 = new javax.swing.JButton();
        jPanel31 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jPanel32 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jPanel33 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("<html>Akurugoda Stores Point of Sales system by\n<span style=\"color: #800080;\">\n <b>Nebula Infinite</b>\n</span>\n</html>");
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1280, 789));
        setPreferredSize(new java.awt.Dimension(1269, 720));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName(""); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(297, 720));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10));
        jPanel21.setName("jPanel21"); // NOI18N
        jPanel21.setLayout(new java.awt.GridLayout(7, 1, 0, 5));

        jButton1.setBackground(new java.awt.Color(0, 153, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/invoice2.png"))); // NOI18N
        jButton1.setText("Invoice");
        jButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.setIconTextGap(10);
        jButton1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jButton1FocusGained(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel21.add(jButton1);

        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/grn3.png"))); // NOI18N
        jButton2.setText("Grn");
        jButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.setIconTextGap(10);
        jButton2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jButton2FocusGained(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel21.add(jButton2);

        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/stock1.png"))); // NOI18N
        jButton3.setText("Stock");
        jButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.setIconTextGap(10);
        jButton3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jButton3FocusGained(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel21.add(jButton3);

        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/customer1.png"))); // NOI18N
        jButton4.setText("Customer Registration");
        jButton4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.setIconTextGap(10);
        jButton4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jButton4FocusGained(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel21.add(jButton4);

        jButton16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user1.png"))); // NOI18N
        jButton16.setText("Employee Registration");
        jButton16.setBorderPainted(false);
        jButton16.setIconTextGap(15);
        jButton16.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jButton16FocusGained(evt);
            }
        });
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel21.add(jButton16);

        jButton7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/grnHis1.png"))); // NOI18N
        jButton7.setText("Grn History");
        jButton7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton7.setIconTextGap(10);
        jButton7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jButton7FocusGained(evt);
            }
        });
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel21.add(jButton7);

        jButton8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/grn1.png"))); // NOI18N
        jButton8.setText("Invoice History");
        jButton8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton8.setIconTextGap(10);
        jButton8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jButton8FocusGained(evt);
            }
        });
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel21.add(jButton8);

        jPanel1.add(jPanel21, java.awt.BorderLayout.CENTER);

        jPanel19.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel19.setMinimumSize(new java.awt.Dimension(100, 70));
        jPanel19.setOpaque(false);
        jPanel19.setPreferredSize(new java.awt.Dimension(263, 70));
        jPanel19.setLayout(new java.awt.GridLayout(3, 0));

        jLabel4.setText("Software solution by");
        jPanel19.add(jLabel4);

        jLabel5.setForeground(new java.awt.Color(0, 153, 255));
        jLabel5.setText("nebulainfinite.com");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 0));
        jPanel19.add(jLabel5);

        jLabel25.setForeground(new java.awt.Color(0, 153, 255));
        jLabel25.setText("+94 78 3233 760");
        jLabel25.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 0));
        jPanel19.add(jLabel25);

        jPanel1.add(jPanel19, java.awt.BorderLayout.SOUTH);

        jPanel20.setOpaque(false);
        jPanel20.setPreferredSize(new java.awt.Dimension(297, 140));

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logout1.png"))); // NOI18N
        jButton15.setPreferredSize(new java.awt.Dimension(50, 22));
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 30)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("<html> <p style=\"text-align: center;\">Akuragoda Produce</p> <html>");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
            .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel20Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(91, Short.MAX_VALUE)
                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
            .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel20Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(60, Short.MAX_VALUE)))
        );

        jPanel1.add(jPanel20, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel5.setPreferredSize(new java.awt.Dimension(972, 820));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        cardPanel.setBackground(new java.awt.Color(255, 255, 255));
        cardPanel.setMinimumSize(new java.awt.Dimension(962, 800));
        cardPanel.setName("cardPanel"); // NOI18N
        cardPanel.setPreferredSize(new java.awt.Dimension(962, 800));
        cardPanel.setLayout(new javax.swing.BoxLayout(cardPanel, javax.swing.BoxLayout.LINE_AXIS));

        card1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        card1.setName("card1"); // NOI18N
        card1.setLayout(new java.awt.BorderLayout(0, 10));

        jPanel11.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setFont(new java.awt.Font("Iskoola Pota", 0, 16)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Product ID", "Brand", "Name", "Quantity", "Selling price", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setIntercellSpacing(new java.awt.Dimension(10, 0));
        jTable1.setRowHeight(40);
        jTable1.setSelectionBackground(new java.awt.Color(224, 224, 224));
        jTable1.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTable1MouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(75);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(75);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(0);
        }

        jPanel2.add(jScrollPane1);

        jPanel11.add(jPanel2, java.awt.BorderLayout.CENTER);

        card1.add(jPanel11, java.awt.BorderLayout.CENTER);

        jPanel18.setPreferredSize(new java.awt.Dimension(1013, 400));
        jPanel18.setLayout(new java.awt.BorderLayout(0, 5));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Cashier : ");

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel3.setText("employee@gmail.com");

        jButton9.setBackground(new java.awt.Color(0, 153, 255));
        jButton9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Select customer");
        jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton9.setPreferredSize(new java.awt.Dimension(155, 40));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(0, 153, 255));
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reload1.png"))); // NOI18N
        jButton10.setPreferredSize(new java.awt.Dimension(81, 40));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Customer mobile");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel18.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jPanel9.setLayout(new java.awt.BorderLayout(10, 0));

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel35.setLayout(new java.awt.GridLayout(1, 2));

        jPanel22.setPreferredSize(new java.awt.Dimension(300, 356));

        jPanel25.setLayout(new java.awt.GridLayout(4, 1, 0, 5));

        jPanel27.setLayout(new java.awt.BorderLayout());

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setText("<html>\n<p>Invoice number</p>\n<html>");
        jLabel17.setPreferredSize(new java.awt.Dimension(80, 0));
        jPanel27.add(jLabel17, java.awt.BorderLayout.WEST);

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField2.setPreferredSize(new java.awt.Dimension(220, 22));
        jPanel27.add(jTextField2, java.awt.BorderLayout.CENTER);

        jPanel25.add(jPanel27);

        jPanel28.setLayout(new java.awt.BorderLayout());

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setText("Name");
        jLabel18.setPreferredSize(new java.awt.Dimension(80, 0));
        jPanel28.add(jLabel18, java.awt.BorderLayout.WEST);

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jTextField3.setPreferredSize(new java.awt.Dimension(220, 22));
        jPanel28.add(jTextField3, java.awt.BorderLayout.CENTER);

        jPanel25.add(jPanel28);

        jPanel29.setLayout(new java.awt.BorderLayout());

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setText("Brand");
        jLabel19.setPreferredSize(new java.awt.Dimension(80, 0));
        jPanel29.add(jLabel19, java.awt.BorderLayout.WEST);

        jTextField4.setEditable(false);
        jTextField4.setFont(new java.awt.Font("Iskoola Pota", 0, 18)); // NOI18N
        jTextField4.setPreferredSize(new java.awt.Dimension(220, 22));
        jPanel29.add(jTextField4, java.awt.BorderLayout.CENTER);

        jPanel25.add(jPanel29);

        jPanel30.setLayout(new java.awt.BorderLayout());

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel20.setText("In stock :");
        jLabel20.setPreferredSize(new java.awt.Dimension(80, 0));
        jPanel30.add(jLabel20, java.awt.BorderLayout.WEST);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel21.setText("...");
        jLabel21.setPreferredSize(new java.awt.Dimension(220, 22));
        jPanel30.add(jLabel21, java.awt.BorderLayout.CENTER);

        jPanel25.add(jPanel30);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel35.add(jPanel22);

        jPanel26.setLayout(new java.awt.GridLayout(4, 1, 0, 5));

        jPanel10.setLayout(new java.awt.GridLayout(1, 0));

        jButton21.setBackground(new java.awt.Color(51, 51, 51));
        jButton21.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton21.setForeground(new java.awt.Color(255, 255, 255));
        jButton21.setText("Select product");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton21);

        jPanel26.add(jPanel10);

        jPanel31.setLayout(new java.awt.BorderLayout());

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel22.setText("Product ID");
        jLabel22.setPreferredSize(new java.awt.Dimension(100, 0));
        jPanel31.add(jLabel22, java.awt.BorderLayout.WEST);

        jTextField5.setEditable(false);
        jTextField5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField5.setPreferredSize(new java.awt.Dimension(220, 22));
        jPanel31.add(jTextField5, java.awt.BorderLayout.CENTER);

        jPanel26.add(jPanel31);

        jPanel32.setLayout(new java.awt.BorderLayout());

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel23.setText("Quantity");
        jLabel23.setPreferredSize(new java.awt.Dimension(100, 0));
        jPanel32.add(jLabel23, java.awt.BorderLayout.WEST);

        jFormattedTextField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jFormattedTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField3ActionPerformed(evt);
            }
        });
        jPanel32.add(jFormattedTextField3, java.awt.BorderLayout.CENTER);

        jPanel26.add(jPanel32);

        jPanel33.setLayout(new java.awt.BorderLayout());

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel24.setText("Price");
        jLabel24.setPreferredSize(new java.awt.Dimension(100, 0));
        jPanel33.add(jLabel24, java.awt.BorderLayout.WEST);

        jTextField6.setEditable(false);
        jTextField6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField6.setPreferredSize(new java.awt.Dimension(220, 22));
        jPanel33.add(jTextField6, java.awt.BorderLayout.CENTER);

        jPanel26.add(jPanel33);

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel35.add(jPanel36);

        jPanel3.add(jPanel35, java.awt.BorderLayout.CENTER);

        jPanel6.setPreferredSize(new java.awt.Dimension(653, 100));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel24.setPreferredSize(new java.awt.Dimension(628, 45));
        jPanel24.setLayout(new java.awt.GridLayout(1, 3, 10, 0));

        jButton12.setBackground(new java.awt.Color(0, 153, 255));
        jButton12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("Clear invoice");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel24.add(jButton12);

        jButton13.setBackground(new java.awt.Color(0, 153, 255));
        jButton13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("Clear product");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel24.add(jButton13);

        jButton14.setBackground(new java.awt.Color(0, 153, 255));
        jButton14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setText("Add to invoice");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel24.add(jButton14);

        jPanel6.add(jPanel24, java.awt.BorderLayout.NORTH);

        jLabel16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel16.setText("Invoice items (0)");
        jPanel6.add(jLabel16, java.awt.BorderLayout.PAGE_END);

        jPanel3.add(jPanel6, java.awt.BorderLayout.SOUTH);

        jPanel9.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 0), javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        jPanel4.setPreferredSize(new java.awt.Dimension(300, 0));
        jPanel4.setLayout(new java.awt.GridLayout(7, 1));

        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel7.setBackground(new java.awt.Color(153, 204, 255));
        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel7.setText("Total");
        jLabel7.setOpaque(true);
        jPanel8.add(jLabel7, java.awt.BorderLayout.CENTER);

        jLabel8.setBackground(new java.awt.Color(153, 204, 255));
        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("0.00");
        jLabel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel8.setOpaque(true);
        jLabel8.setPreferredSize(new java.awt.Dimension(200, 25));
        jPanel8.add(jLabel8, java.awt.BorderLayout.EAST);

        jPanel4.add(jPanel8);

        jPanel12.setOpaque(false);
        jPanel12.setLayout(new java.awt.BorderLayout());

        jLabel9.setBackground(new java.awt.Color(153, 204, 255));
        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel9.setText("Discount");
        jLabel9.setOpaque(true);
        jPanel12.add(jLabel9, java.awt.BorderLayout.CENTER);

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField1.setText("0.00");
        jFormattedTextField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jFormattedTextField1.setPreferredSize(new java.awt.Dimension(150, 31));
        jFormattedTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField1KeyReleased(evt);
            }
        });
        jPanel12.add(jFormattedTextField1, java.awt.BorderLayout.EAST);

        jPanel4.add(jPanel12);

        jPanel13.setOpaque(false);
        jPanel13.setLayout(new java.awt.BorderLayout());

        jLabel10.setBackground(new java.awt.Color(153, 204, 255));
        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel10.setText("Total payable");
        jLabel10.setOpaque(true);
        jPanel13.add(jLabel10, java.awt.BorderLayout.CENTER);

        jLabel13.setBackground(new java.awt.Color(153, 204, 255));
        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("0.00");
        jLabel13.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        jLabel13.setOpaque(true);
        jLabel13.setPreferredSize(new java.awt.Dimension(150, 16));
        jPanel13.add(jLabel13, java.awt.BorderLayout.EAST);

        jPanel4.add(jPanel13);

        jPanel14.setOpaque(false);
        jPanel14.setLayout(new java.awt.BorderLayout());

        jLabel11.setBackground(new java.awt.Color(153, 204, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel11.setText("Payment");
        jLabel11.setOpaque(true);
        jPanel14.add(jLabel11, java.awt.BorderLayout.CENTER);

        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jFormattedTextField2.setPreferredSize(new java.awt.Dimension(150, 31));
        jFormattedTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField2KeyReleased(evt);
            }
        });
        jPanel14.add(jFormattedTextField2, java.awt.BorderLayout.EAST);

        jPanel4.add(jPanel14);

        jPanel15.setOpaque(false);
        jPanel15.setLayout(new java.awt.BorderLayout());

        jLabel12.setBackground(new java.awt.Color(153, 204, 255));
        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel12.setText("Balance");
        jLabel12.setOpaque(true);
        jPanel15.add(jLabel12, java.awt.BorderLayout.CENTER);

        jLabel14.setBackground(new java.awt.Color(153, 204, 255));
        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("0.00");
        jLabel14.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        jLabel14.setOpaque(true);
        jLabel14.setPreferredSize(new java.awt.Dimension(200, 16));
        jPanel15.add(jLabel14, java.awt.BorderLayout.LINE_END);

        jPanel4.add(jPanel15);

        jPanel16.setOpaque(false);
        jPanel16.setLayout(new java.awt.BorderLayout());

        jButton11.setBackground(new java.awt.Color(0, 153, 255));
        jButton11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Print invoice");
        jButton11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton11.setPreferredSize(new java.awt.Dimension(200, 40));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel16.add(jButton11, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel16);

        jPanel17.setOpaque(false);
        jPanel17.setLayout(new java.awt.BorderLayout());

        jLabel15.setBackground(new java.awt.Color(153, 204, 255));
        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("...");
        jLabel15.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel15.setOpaque(true);
        jPanel17.add(jLabel15, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel17);

        jPanel9.add(jPanel4, java.awt.BorderLayout.EAST);

        jPanel18.add(jPanel9, java.awt.BorderLayout.CENTER);

        card1.add(jPanel18, java.awt.BorderLayout.PAGE_START);

        cardPanel.add(card1);

        jPanel5.add(cardPanel);

        getContentPane().add(jPanel5, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Stock stock = new Stock();
        cardPanel.removeAll();
        cardPanel.add(stock);
        cardPanel.revalidate();
        cardPanel.repaint();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButton1FocusGained
        // TODO add your handling code here:       

        focusSideBarButton(1);

    }//GEN-LAST:event_jButton1FocusGained

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new Home(this.email, this.firstname, this.lastname);
        cardPanel.removeAll();
        cardPanel.add(card1);
        cardPanel.revalidate();
        cardPanel.repaint();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButton2FocusGained
        // TODO add your handling code here:     
        focusSideBarButton(2);
    }//GEN-LAST:event_jButton2FocusGained

    private void jButton3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButton3FocusGained
        // TODO add your handling code here:

        focusSideBarButton(3);
    }//GEN-LAST:event_jButton3FocusGained

    private void jButton4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButton4FocusGained
        // TODO add your handling code here:
        focusSideBarButton(4);
    }//GEN-LAST:event_jButton4FocusGained

    private void jButton7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButton7FocusGained
        // TODO add your handling code here:
        focusSideBarButton(5);
    }//GEN-LAST:event_jButton7FocusGained

    private void jButton8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButton8FocusGained
        // TODO add your handling code here:
        focusSideBarButton(6);
    }//GEN-LAST:event_jButton8FocusGained

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        SupportFrame sf = new SupportFrame(this, true);
        CustomerRegistration customerregistration = new CustomerRegistration();
        sf.getJPanel().removeAll();
        sf.getJPanel().add(customerregistration);
        sf.getJPanel().revalidate();
        sf.getJPanel().repaint();

        customerregistration.setHome(this);
        customerregistration.setDiaload(sf);

        sf.setVisible(true);

    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Grn grn = new Grn();
        cardPanel.removeAll();
        cardPanel.add(grn);
        cardPanel.revalidate();
        cardPanel.repaint();
        grn.setJLabel43(this.email);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

        cardPanel.removeAll();
        cardPanel.add(new CustomerRegistration());
        cardPanel.revalidate();
        cardPanel.repaint();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:        
        cardPanel.removeAll();
        cardPanel.add(new GrnHistory());
        cardPanel.revalidate();
        cardPanel.repaint();

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        cardPanel.removeAll();
        cardPanel.add(new InvoiceHistory());
        cardPanel.revalidate();
        cardPanel.repaint();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:

        SupportFrame sf = new SupportFrame(this, true);
        Stock stock = new Stock();
        sf.getJPanel().removeAll();
        sf.getJPanel().add(stock);
        sf.getJPanel().revalidate();
        sf.getJPanel().repaint();

        stock.setInvoice(this);
        stock.setDiaload(sf);

        sf.setVisible(true);

        if (jTextField3.getForeground() != jTextField1.getForeground()) {
            jTextField3.setForeground(jTextField1.getForeground());
        }

    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        boolean added = true;

        String productID = jTextField5.getText();
        String brand = jTextField4.getText();
        String name = jTextField3.getText();
        String qty = jFormattedTextField3.getText();
        String sellingPrice = jTextField6.getText();

        if (productID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a product", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (jLabel21.getText() == "...") {
            jTextField3.setForeground(new Color(204, 0, 0));
            JOptionPane.showMessageDialog(this, "Stock has not been added to this product yet", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (Double.parseDouble(jLabel21.getText()) <= 0) {
            jLabel21.setForeground(new Color(204, 0, 0));
            JOptionPane.showMessageDialog(this, "Insufficient stock!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (qty.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter quantity", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (Double.parseDouble(qty) > Double.parseDouble(jLabel21.getText())) {
            JOptionPane.showMessageDialog(this, "Insufficient stock", "Warning", JOptionPane.WARNING_MESSAGE);
            jFormattedTextField3.setText("");
            jFormattedTextField3.grabFocus();
        } else {
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setStockID(productID);
            invoiceItem.setBrand(brand);
            invoiceItem.setName(name);
            invoiceItem.setQty(qty);
            invoiceItem.setBuyingPrice(this.buying_price);

//            try {
//
//                ResultSet resultset = MySQL.execute("SELECT * FROM `product` WHERE `id`= '" + productID + "' ");
//
//                if (resultset.next()) {
//                    invoiceItem.setBuyingPrice(resultset.getString("buying_price"));
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            invoiceItem.setSellingPrice(sellingPrice);

            if (invoiceItemMap.get(productID) == null) {
                invoiceItemMap.put(productID, invoiceItem);
            } else {

                InvoiceItem found = invoiceItemMap.get(productID);

                int response = JOptionPane.showConfirmDialog(this, "Do you want to update the quantity of product : " + found.getName() + "", "Warming", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    if (Double.parseDouble(found.getQty()) + Double.parseDouble(qty) > Double.parseDouble(jLabel21.getText())) {
                        jLabel21.setForeground(new Color(204, 0, 0));
                        JOptionPane.showMessageDialog(this, "Insufficient stock", "Warning", JOptionPane.WARNING_MESSAGE);
                        jFormattedTextField3.setText("");
                        jFormattedTextField3.grabFocus();
                        added = false;
                    } else {
                        jLabel21.setForeground(jTextField1.getForeground());
                        String newQty = String.valueOf(Double.parseDouble(found.getQty()) + Double.parseDouble(qty));
                        found.setQty(newQty);
                    }

                }

            }
            if (added) {
                jLabel16.setText("Invoice items (" + invoiceItemMap.size() + ")");
                loadInvoiceItem();
                clearProductEntry();
                calculate();
                jButton11.setEnabled(true);
            }

        }


    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:     

        String customerMobile = jTextField1.getText();
        String paymemt = jFormattedTextField2.getText();
        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//      
        String discount = jFormattedTextField1.getText();

        try {

            ResultSet resultset = MySQL.execute("SELECT * FROM `customer` WHERE `mobile`='" + customerMobile + "'");
            double balance = Double.parseDouble(jLabel14.getText());
            String balance_payment;
            if (balance < 0) {
                balance_payment = jLabel14.getText();

            } else {
                balance_payment = "0";

            }

            if (!resultset.next()) {
                MySQL.execute("INSERT INTO `customer` (`mobile`,`first_name`,`last_name`,`email`,`balance_payment`,`last_update_time`) VALUES "
                        + "('" + customerMobile + "','Unknown','Customer','no email','" + balance_payment + "','" + getCurrentDateTime() + "')");
            } else {
                MySQL.execute("UPDATE `customer` SET `balance_payment`='" + balance_payment + "', `last_update_time`='" + getCurrentDateTime() + "' WHERE `mobile`='" + customerMobile + "' ");
            }

            if (paymemt.isEmpty() || paymemt.startsWith("0")) {
                paymemt = "0";
            }

            if (discount.isEmpty() || discount.startsWith("0")) {
                discount = "0";
            }

            MySQL.execute("INSERT INTO `invoice` (`id`,`customer_mobile`,`employee_email`,`date_time`,`paid_amount`,`discount`,`last_update_time`) "
                    + " VALUES ('" + jTextField2.getText() + "','" + customerMobile + "','" + email + "','" + dateTime + "',"
                    + "'" + paymemt + "','" + discount + "','" + getCurrentDateTime() + "')");

            System.out.println("invoice success");

            for (InvoiceItem item : invoiceItemMap.values()) {

                MySQL.execute("INSERT INTO `invoice_item` (`qty`,`selling_price`,`product_id`,`invoice_id`) VALUES "
                        + "('" + item.getQty() + "','" + item.getSellingPrice() + "','" + item.getStockID() + "','" + jTextField2.getText() + "')");

                MySQL.execute("INSERT INTO `invoice_history_item` (`name`,`qty`,`buying_price`,`selling_price`,`invoice_id`,`brand_name`) VALUES "
                        + "('" + item.getName() + "','" + item.getQty() + "','" + item.getBuyingPrice() + "','" + item.getSellingPrice() + "','" + jTextField2.getText() + "','" + item.getBrand() + "')");

                MySQL.execute("UPDATE product SET qty=qty-'" + item.getQty() + "', `last_update_time`='" + getCurrentDateTime() + "' WHERE id='" + item.getStockID() + "'");

            }

            String date = new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(new Date());

            //            WE CAN USE ONLY NEDBEANS IDE
//            
//            
//            String userDirectory = FileSystems.getDefault()
//                    .getPath("")
//                    .toAbsolutePath()
//                    .toString();
//
//            String url = userDirectory + "\\src\\reports\\pos2.jasper";

//                String tempUrl = "src/reports/pos2.jasper";

//             WE CAN USE AFTER BUILD

            String userDirectory = FileSystems.getDefault()
                    .getPath("")
                    .toAbsolutePath()
                    .toString();

            String newpath = userDirectory.substring(0, userDirectory.lastIndexOf("\\"));
//            System.out.println(newpath);

            String url = newpath + "\\src\\reports\\pos2.jasper";
            
            java.util.HashMap<String, Object> parameters = new HashMap<>();

            parameters.put("Parameter1", date);
            parameters.put("Parameter2", jTextField2.getText());
            parameters.put("Parameter3", jLabel3.getText());

            parameters.put("Parameter4", jLabel8.getText() + "0");
            parameters.put("Parameter5", jFormattedTextField2.getText() + ".00");
            parameters.put("Parameter6", jLabel14.getText() + "0");
            parameters.put("Parameter7", jFormattedTextField1.getText());

            JRTableModelDataSource datasource = new JRTableModelDataSource(jTable1.getModel());

            JasperPrint report = JasperFillManager.fillReport(url, parameters, datasource);
            JasperPrintManager.printReport(report, false); //print report dirrectly
//            JasperViewer.viewReport(report, false);

            resetInvoice();
            GenerateInvoiceNumber();

        } catch (Exception e) {
            logger.log(Level.WARNING, "Invoice", e);
            e.printStackTrace();

        }
//        }


    }//GEN-LAST:event_jButton11ActionPerformed

    private void jFormattedTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField1KeyReleased
        // TODO add your handling code here:
        calculate();
    }//GEN-LAST:event_jFormattedTextField1KeyReleased

    private void jFormattedTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField2KeyReleased
        // TODO add your handling code here:
        calculate();
    }//GEN-LAST:event_jFormattedTextField2KeyReleased

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:

        clearProductEntry();


    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed

        balancepayment = 0;
        balance = 0;
        int response = JOptionPane.showConfirmDialog(this, "Do you want to clear the invoice?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            resetInvoice();
            jButton11.setEnabled(false);
        }

    }//GEN-LAST:event_jButton12ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jFormattedTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField3ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        int response = JOptionPane.showConfirmDialog(this, "Do you want to log out?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {

            updateLogoutTime();

            new Login().setVisible(true);

            this.dispose();
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void updateLogoutTime(){
        try {

                MySQL.execute("UPDATE `employee_history` SET `Logout_time`='" + getCurrentDateTime() + "', `last_update_time`= '" + getCurrentDateTime() + "'  WHERE `Login_time`='" + Login.logintime + "'");

                System.out.println("logged out success");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    
    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        GenerateCustomerMobile();

    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed

        cardPanel.removeAll();
        cardPanel.add(new EmployeeRegistration());
        cardPanel.revalidate();
        cardPanel.repaint();


    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton16FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButton16FocusGained
        focusSideBarButton(7);
    }//GEN-LAST:event_jButton16FocusGained

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:

        int row = jTable1.getSelectedRow();

        if (evt.getClickCount() == 2) {

            long response = JOptionPane.showConfirmDialog(this, "Do you want to remove this product?", "Conformation", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {

                invoiceItemMap.remove(String.valueOf(jTable1.getValueAt(row, 1)));
                loadInvoiceItem();
                calculate();
                jLabel16.setText("Invoice items (" + invoiceItemMap.size() + ")");

                if (invoiceItemMap.size() == 0) {
                    jLabel14.setText("0.00");
                }

            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseEntered

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        updateLogoutTime();
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel card1;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    public javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
