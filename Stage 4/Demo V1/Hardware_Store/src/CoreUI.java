
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author trons
 */
public class CoreUI extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public CoreUI() {
        initComponents();
        
        Inventory.loadInventory();
        Purchase.loadPurchases();
        Staff.loadStaff();
        
        loadInventoryTable();
        
        String username = Main.CURRENT_PROFILE.getUsername();
        lblAccountName.setText(username);
        
        pnlInventory.setVisible(true);
        pnlOrders.setVisible(false);
        pnlStaff.setVisible(false);
        pnlProfiles.setVisible(false);
        
        this.setSize(740, 420);
        this.setVisible(true);
    }
    
    @Override
    public void dispose() {
        Inventory.updateInventory();
        Purchase.updatePurchases();
        Staff.updateStaff();
        
        super.dispose();
    }
    
    //--------------------------------------------------------------------------
    // Inventory Screen
    //--------------------------------------------------------------------------
    
    public void loadInventoryTable() {
        DefaultTableModel model = (DefaultTableModel) invTable.getModel();
        model.setRowCount(0);
        
        for (Item item : Inventory.inventory) {
            model.addRow(new Object[]{item.getName(), item.getLocation(), item.getStock(), item.getUPrice()});
        }
    }
    
    public void editInventory() {
        try {
            int row = invTable.getSelectedRow();

            if (row != -1) {
                String itemName = txtName.getText();
                String location = txtLocation.getText();
                spnStock.commitEdit();
                int stock = (int) spnStock.getValue();
                spnPrice.commitEdit();
                int uprice = (int) spnPrice.getValue();

                if ((itemName.equals("")) || (location.equals(""))){
                    return;
                } else {
                    Inventory.addItem(itemName, location, stock, uprice);

                    //DefaultTableModel model = (DefaultTableModel) invTable.getModel();

                    invTable.setValueAt(itemName, row, 0);
                    invTable.setValueAt(location, row, 1);
                    invTable.setValueAt(stock, row, 2);
                    invTable.setValueAt(uprice, row, 3);
                }
            }

        } catch (ParseException ex) {
            Logger.getLogger(CoreUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addInvItem() {
        String itemName = txtName.getText();
        String itemLocation = txtLocation.getText();
        int itemStock = (int)(spnStock.getValue());
        int itemPrice = (int)(spnPrice.getValue());
        
        if ((itemName.equals("")) || (itemLocation.equals(""))){
            return;
        }
        
        Inventory.addItem(itemName, itemLocation, itemStock, itemPrice);
        
        DefaultTableModel model = (DefaultTableModel) invTable.getModel();
        model.addRow(new Object[]{itemName, itemLocation, itemStock, itemPrice});
    }
    
    public void removeInvItem() {
        DefaultTableModel model = (DefaultTableModel) invTable.getModel();
        try {
            int row = invTable.getSelectedRow();

            if (row != -1) {
                String itemName = String.valueOf(invTable.getValueAt(row, 0));
                Inventory.removeItem(itemName);

                model.removeRow(row);
            }
        } catch (Exception e) {
            return;
        }
    }
    
    //--------------------------------------------------------------------------
    // Orders Screen
    //--------------------------------------------------------------------------
    
    public void loadOrderTable() {
        DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
        model.setRowCount(0);
        
        for (Purchase order : Purchase.purchaseHistory) {
            model.addRow(new Object[]{order.getID() + " | " + order.getDate()});
        }
        
        showOrderInfo(Purchase.purchaseHistory.get(0));
    }
    
    public void editOrder() {
        int row = orderTable.getSelectedRow();

        if (row != -1) {
            String[] info = ((String) orderTable.getValueAt(row, 0)).split(" | ");

            String itemName = txtItem.getText();
            int amount = (int) spnAmount.getValue();
            int cost = (int) spnCost.getValue();
            String date = "";
            if (txtDate.isEnabled() == true) {
                date = txtDate.getText();
            } else {
                date = LocalDate.now().toString();
            }

            Purchase.editOrder(Integer.parseInt(info[0]), itemName, amount, cost, date);
            loadOrderTable();
        }
    }
    
    public void addOrder() {
        /*
         * item name
         * amount
         * price
         * -- automatically 
         */
        
        String itemName = txtItem.getText();
        int amount = (int)spnAmount.getValue();
        int price = (int)spnCost.getValue();
        String date = "";
        if (txtDate.isEnabled() == true) {
            date = txtDate.getText();
        } else {
            date = LocalDate.now().toString();
        }

        Purchase.makeOrder(itemName, amount, price, date);

        loadOrderTable();
    }
    
    public void removeOrder() {
        int row = orderTable.getSelectedRow();

        if (row != -1) {
            Purchase.purchaseHistory.remove(row);
            loadOrderTable();
        }
    }
    
    public void showOrderInfo() {
        Object value = orderTable.getValueAt(orderTable.getSelectedRow(),orderTable.getSelectedColumn());
        System.out.println(value);
        // i feel like this line MAY be a bit volatile.. i dunno.... i expect it to break in some way, but it works.
        // clearly it is fine and there will be no problems ever whatsoever -tristen
        Purchase p = Purchase.purchaseHistory.get(orderTable.getSelectedRow());
        jTextArea1.setText(
            "=== ORDER ===\nID :"+p.getID()+"\nDate: "+p.getDate()+"\nItem: "+p.getItem()+"\nAmount: "+p.getAmount()+"\nCost: "+p.getCost()+"\nUser: "+p.getUser());
    }
    public void showOrderInfo(Purchase p) {
        jTextArea1.setText(
            "=== ORDER ===\nID :"+p.getID()+"\nDate: "+p.getDate()+"\nItem: "+p.getItem()+"\nAmount: "+p.getAmount()+"\nCost: "+p.getCost()+"\nUser: "+p.getUser());
    }
    
    //--------------------------------------------------------------------------
    // Staff Screen
    //--------------------------------------------------------------------------
    
    public void loadStaffTable() {
        DefaultTableModel model = (DefaultTableModel) staffTable.getModel();
        model.setRowCount(0);
        
        for (Employee emp : Staff.employees) {
            model.addRow(new Object[]{emp.getName(), emp.getPosition(), emp.getWage()});
        }
    }
    
    public void editStaff() {
        int row = staffTable.getSelectedRow();

        if (row != -1) {
            String name = String.valueOf(staffTable.getValueAt(row, 0));

            String newName = txtStaffName.getText();
            String newPosition = txtPosition.getText();
            float newWage = (float)(int)spnWage.getValue();

            Staff.editEmp(name, newName, newPosition, newWage);

            loadStaffTable();
        }
    }
    
    public void addStaff() {
        String name = txtStaffName.getText();
        String position = txtPosition.getText();
        float wage = (float)(int)spnWage.getValue();

        Staff.addEmp(name, position, wage);

        loadStaffTable();
    }
    
    public void removeStaff() {
        int row = staffTable.getSelectedRow();

        if (row != -1) {
            String name = String.valueOf(staffTable.getValueAt(row, 0));

            Staff.removeEmp(name);
            loadStaffTable();
        }
    }
    
    //--------------------------------------------------------------------------
    // Profiles Screen
    //--------------------------------------------------------------------------
    
    public void loadProfilesTable() {
        DefaultTableModel model = (DefaultTableModel) profileTable.getModel();
        model.setRowCount(0);
        
        for (Profile prof : Main.profiles) {
            model.addRow(new Object[]{prof.getUsername(), prof.getPassword(), prof.getAuthority()});
        }
    }
    
    public void editProfile() {
        int row = profileTable.getSelectedRow();

        if (row != -1) {
            String username = String.valueOf(profileTable.getValueAt(row, 0));

            String newUsername = txtUsername.getText();
            String newPassword = txtPassword.getText();
            int newAuth = (int)spnAuth.getValue();

            Main.editProfile(username, newUsername, newPassword, newAuth);

            loadProfilesTable();
        }
    }
    
    public void addProfile() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        int auth = (int)spnAuth.getValue();

        Main.addProfile(username, password, auth);

        loadProfilesTable();
    }
    
    public void removeProfile() {
        int row = profileTable.getSelectedRow();

        if (row != -1) {
            String username = String.valueOf(profileTable.getValueAt(row, 0));

            Main.removeProfile(username);
            loadProfilesTable();
        }
    }
    
    //--------------------------------------------------------------------------
    // the rest
    //--------------------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblMenuName = new javax.swing.JLabel();
        lblCurrentMenu = new javax.swing.JLabel();
        pnlMenuButtons = new javax.swing.JPanel();
        btnOrders = new javax.swing.JButton();
        btnStaff = new javax.swing.JButton();
        btnProfiles = new javax.swing.JButton();
        btnInventory = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnLogout = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        lblCurrentAccount = new javax.swing.JLabel();
        lblAccountName = new javax.swing.JLabel();
        pnlOrders = new javax.swing.JPanel();
        orderTableContainer = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        txtItem = new javax.swing.JTextField();
        lblItem = new javax.swing.JLabel();
        lblAmount = new javax.swing.JLabel();
        lblCost = new javax.swing.JLabel();
        spnAmount = new javax.swing.JSpinner();
        spnCost = new javax.swing.JSpinner();
        lbl$2 = new javax.swing.JLabel();
        btnAdd3 = new javax.swing.JButton();
        btnRemove3 = new javax.swing.JButton();
        btnEdit3 = new javax.swing.JButton();
        orderInfo = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        txtDate = new javax.swing.JTextField();
        lblDate = new javax.swing.JLabel();
        btnDate = new javax.swing.JButton();
        pnlProfiles = new javax.swing.JPanel();
        profileTableContainer = new javax.swing.JScrollPane();
        profileTable = new javax.swing.JTable();
        txtUsername = new javax.swing.JTextField();
        lblUsername = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        lblAuth = new javax.swing.JLabel();
        spnAuth = new javax.swing.JSpinner();
        btnAdd2 = new javax.swing.JButton();
        btnRemove2 = new javax.swing.JButton();
        btnEdit2 = new javax.swing.JButton();
        txtPassword = new javax.swing.JTextField();
        pnlStaff = new javax.swing.JPanel();
        staffTableContainer = new javax.swing.JScrollPane();
        staffTable = new javax.swing.JTable();
        txtStaffName = new javax.swing.JTextField();
        lblStaffName = new javax.swing.JLabel();
        lblPosition = new javax.swing.JLabel();
        lblWage = new javax.swing.JLabel();
        spnWage = new javax.swing.JSpinner();
        lbl$1 = new javax.swing.JLabel();
        btnAdd1 = new javax.swing.JButton();
        btnRemove1 = new javax.swing.JButton();
        btnEdit1 = new javax.swing.JButton();
        txtPosition = new javax.swing.JTextField();
        pnlInventory = new javax.swing.JPanel();
        invTableContainer = new javax.swing.JScrollPane();
        invTable = new javax.swing.JTable();
        txtName = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        lblLocation = new javax.swing.JLabel();
        txtLocation = new javax.swing.JTextField();
        lblStock = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        spnStock = new javax.swing.JSpinner();
        spnPrice = new javax.swing.JSpinner();
        lbl$ = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(700, 380));
        setSize(new java.awt.Dimension(700, 380));
        getContentPane().setLayout(null);

        lblMenuName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblMenuName.setText("Inventory");
        lblMenuName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lblMenuName);
        lblMenuName.setBounds(14, 6, 98, 37);

        lblCurrentMenu.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lblCurrentMenu.setForeground(new java.awt.Color(153, 153, 153));
        lblCurrentMenu.setText("[CURRENT MENU]");
        getContentPane().add(lblCurrentMenu);
        lblCurrentMenu.setBounds(14, 44, 98, 14);

        btnOrders.setText("Orders");
        btnOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdersActionPerformed(evt);
            }
        });

        btnStaff.setText("Staff");
        btnStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStaffActionPerformed(evt);
            }
        });

        btnProfiles.setText("Profiles");
        btnProfiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfilesActionPerformed(evt);
            }
        });

        btnInventory.setText("Inventory");
        btnInventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventoryActionPerformed(evt);
            }
        });

        btnLogout.setText("Log Out");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        lblCurrentAccount.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lblCurrentAccount.setForeground(new java.awt.Color(153, 153, 153));
        lblCurrentAccount.setText("[CURRENT ACCOUNT]");

        lblAccountName.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lblAccountName.setForeground(new java.awt.Color(153, 153, 153));
        lblAccountName.setText("account");

        javax.swing.GroupLayout pnlMenuButtonsLayout = new javax.swing.GroupLayout(pnlMenuButtons);
        pnlMenuButtons.setLayout(pnlMenuButtonsLayout);
        pnlMenuButtonsLayout.setHorizontalGroup(
            pnlMenuButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMenuButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOrders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStaff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInventory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(btnProfiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlMenuButtonsLayout.createSequentialGroup()
                        .addGroup(pnlMenuButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLogout)
                            .addGroup(pnlMenuButtonsLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(pnlMenuButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAccountName, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCurrentAccount))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMenuButtonsLayout.setVerticalGroup(
            pnlMenuButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnInventory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnOrders)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnStaff)
                .addGap(12, 12, 12)
                .addComponent(btnProfiles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(lblCurrentAccount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAccountName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout)
                .addGap(12, 12, 12))
        );

        getContentPane().add(pnlMenuButtons);
        pnlMenuButtons.setBounds(0, 70, 126, 310);

        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Orders"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        orderTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        orderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orderTableMouseClicked(evt);
            }
        });
        orderTableContainer.setViewportView(orderTable);

        lblItem.setText("ITEM PURCHASED");

        lblAmount.setText("AMOUNT PURCHASED");

        lblCost.setText("COST OF PURCHASE");

        lbl$2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl$2.setForeground(new java.awt.Color(153, 153, 153));
        lbl$2.setText("$");

        btnAdd3.setText("Add");
        btnAdd3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd3ActionPerformed(evt);
            }
        });

        btnRemove3.setText("Remove");
        btnRemove3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemove3ActionPerformed(evt);
            }
        });

        btnEdit3.setText("Edit");
        btnEdit3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEdit3ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("[[ ORDER 1 ]]\nDATE: \n8/7/24\nPURCHASE OF: \n\"Screwdriver\"\nAMOUNT: 44\nCOST: \n$22000\nBY USER: \njoseju");
        jTextArea1.setAlignmentX(1.0F);
        jTextArea1.setAlignmentY(1.0F);
        orderInfo.setViewportView(jTextArea1);

        lblDate.setText("DATE");

        btnDate.setText("Current Date");
        btnDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlOrdersLayout = new javax.swing.GroupLayout(pnlOrders);
        pnlOrders.setLayout(pnlOrdersLayout);
        pnlOrdersLayout.setHorizontalGroup(
            pnlOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrdersLayout.createSequentialGroup()
                .addContainerGap(173, Short.MAX_VALUE)
                .addGroup(pnlOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(spnAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblAmount)
                        .addComponent(lblCost)
                        .addGroup(pnlOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnEdit3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnRemove3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnAdd3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlOrdersLayout.createSequentialGroup()
                                .addComponent(spnCost, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl$2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(pnlOrdersLayout.createSequentialGroup()
                            .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDate))
                        .addComponent(lblItem, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(orderTableContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(orderInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlOrdersLayout.setVerticalGroup(
            pnlOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrdersLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(orderInfo)
                    .addComponent(orderTableContainer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOrdersLayout.createSequentialGroup()
                        .addComponent(lblItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblAmount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCost)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spnCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl$2))
                        .addGap(14, 14, 14)
                        .addGroup(pnlOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDate)
                            .addComponent(btnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btnAdd3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemove3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEdit3)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        getContentPane().add(pnlOrders);
        pnlOrders.setBounds(0, 0, 688, 368);

        profileTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Password", "Authority"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        profileTableContainer.setViewportView(profileTable);

        lblUsername.setText("USERNAME");

        lblPassword.setText("PASSWORD");

        lblAuth.setText("AUTH LEVEL");

        btnAdd2.setText("Add");
        btnAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd2ActionPerformed(evt);
            }
        });

        btnRemove2.setText("Remove");
        btnRemove2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemove2ActionPerformed(evt);
            }
        });

        btnEdit2.setText("Edit");
        btnEdit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEdit2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlProfilesLayout = new javax.swing.GroupLayout(pnlProfiles);
        pnlProfiles.setLayout(pnlProfilesLayout);
        pnlProfilesLayout.setHorizontalGroup(
            pnlProfilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProfilesLayout.createSequentialGroup()
                .addGap(0, 182, Short.MAX_VALUE)
                .addGroup(pnlProfilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProfilesLayout.createSequentialGroup()
                        .addGroup(pnlProfilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPassword)
                            .addComponent(lblAuth)
                            .addGroup(pnlProfilesLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addGroup(pnlProfilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnEdit2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnRemove2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAdd2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProfilesLayout.createSequentialGroup()
                        .addComponent(spnAuth, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(146, 146, 146)))
                .addComponent(profileTableContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlProfilesLayout.setVerticalGroup(
            pnlProfilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProfilesLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlProfilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(profileTableContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlProfilesLayout.createSequentialGroup()
                        .addComponent(lblUsername)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPassword)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblAuth)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnAuth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btnAdd2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemove2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEdit2)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        getContentPane().add(pnlProfiles);
        pnlProfiles.setBounds(0, 0, 691, 368);

        staffTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Location", "Wage"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        staffTableContainer.setViewportView(staffTable);

        lblStaffName.setText("EMPLOYEE NAME");

        lblPosition.setText("POSITION");

        lblWage.setText("WAGE");

        lbl$1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl$1.setForeground(new java.awt.Color(153, 153, 153));
        lbl$1.setText("$");

        btnAdd1.setText("Add");
        btnAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd1ActionPerformed(evt);
            }
        });

        btnRemove1.setText("Remove");
        btnRemove1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemove1ActionPerformed(evt);
            }
        });

        btnEdit1.setText("Edit");
        btnEdit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEdit1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlStaffLayout = new javax.swing.GroupLayout(pnlStaff);
        pnlStaff.setLayout(pnlStaffLayout);
        pnlStaffLayout.setHorizontalGroup(
            pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStaffLayout.createSequentialGroup()
                .addGap(0, 171, Short.MAX_VALUE)
                .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtStaffName, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStaffName, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPosition)
                    .addComponent(lblWage)
                    .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEdit1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRemove1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlStaffLayout.createSequentialGroup()
                            .addComponent(spnWage, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbl$1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(staffTableContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlStaffLayout.setVerticalGroup(
            pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStaffLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(staffTableContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlStaffLayout.createSequentialGroup()
                        .addComponent(lblStaffName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStaffName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPosition)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblWage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spnWage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl$1))
                        .addGap(29, 29, 29)
                        .addComponent(btnAdd1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemove1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEdit1)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        getContentPane().add(pnlStaff);
        pnlStaff.setBounds(0, 0, 680, 368);

        invTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Location", "Stock", "Unit Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        invTableContainer.setViewportView(invTable);
        if (invTable.getColumnModel().getColumnCount() > 0) {
            invTable.getColumnModel().getColumn(0).setResizable(false);
            invTable.getColumnModel().getColumn(1).setResizable(false);
            invTable.getColumnModel().getColumn(2).setResizable(false);
            invTable.getColumnModel().getColumn(3).setResizable(false);
        }

        lblName.setText("ITEM NAME");

        lblLocation.setText("LOCATION");

        lblStock.setText("CURRENT STOCK");

        lblPrice.setText("UNIT PRICE");

        lbl$.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl$.setForeground(new java.awt.Color(153, 153, 153));
        lbl$.setText("$");

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlInventoryLayout = new javax.swing.GroupLayout(pnlInventory);
        pnlInventory.setLayout(pnlInventoryLayout);
        pnlInventoryLayout.setHorizontalGroup(
            pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInventoryLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtLocation)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRemove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblStock, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnStock, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlInventoryLayout.createSequentialGroup()
                                .addComponent(spnPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl$, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(invTableContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlInventoryLayout.setVerticalGroup(
            pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInventoryLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(invTableContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlInventoryLayout.createSequentialGroup()
                        .addComponent(lblName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblLocation)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStock)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPrice)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spnPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl$))
                        .addGap(18, 18, 18)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemove)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEdit)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        getContentPane().add(pnlInventory);
        pnlInventory.setBounds(150, 6, 550, 368);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdersActionPerformed
        // TODO add your handling code here:
        if (pnlOrders.isVisible() == false) {
            loadOrderTable();
            
            Inventory.updateInventory();
            Main.updateProfiles();
            Staff.updateStaff();

            pnlInventory.setVisible(false);
            pnlOrders.setVisible(true);
            pnlStaff.setVisible(false);
            pnlProfiles.setVisible(false);

            lblMenuName.setText("Orders");
        }
    }//GEN-LAST:event_btnOrdersActionPerformed

    private void btnStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStaffActionPerformed
        // TODO add your handling code here:
        if (pnlStaff.isVisible() == false) {
            loadStaffTable();
            
            Inventory.updateInventory();
            Purchase.updatePurchases();
            Main.updateProfiles();

            pnlInventory.setVisible(false);
            pnlOrders.setVisible(false);
            pnlStaff.setVisible(true);
            pnlProfiles.setVisible(false);

            lblMenuName.setText("Staff");
        }
    }//GEN-LAST:event_btnStaffActionPerformed

    private void btnProfilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfilesActionPerformed
        // TODO add your handling code here:
        if (pnlProfiles.isVisible() == false) {
            loadProfilesTable();
            
            Inventory.updateInventory();
            Purchase.updatePurchases();
            Staff.updateStaff();

            pnlInventory.setVisible(false);
            pnlOrders.setVisible(false);
            pnlStaff.setVisible(false);
            pnlProfiles.setVisible(true);

            lblMenuName.setText("Profiles");
        }
    }//GEN-LAST:event_btnProfilesActionPerformed

    private void btnInventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventoryActionPerformed
        // TODO add your handling code here:
        if (pnlInventory.isVisible() == false) {
            loadInventoryTable();
            
            Main.updateProfiles();
            Purchase.updatePurchases();
            Staff.updateStaff();

            pnlInventory.setVisible(true);
            pnlOrders.setVisible(false);
            pnlStaff.setVisible(false);
            pnlProfiles.setVisible(false);

            lblMenuName.setText("Inventory");
        }
    }//GEN-LAST:event_btnInventoryActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        new LoginUI().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEdit1ActionPerformed
        // TODO add your handling code here:
        // STAFF EDIT BUTTON
        editStaff();
    }//GEN-LAST:event_btnEdit1ActionPerformed

    private void btnEdit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEdit2ActionPerformed
        // TODO add your handling code here:
        // PROFILE EDIT BUTTON
        editProfile();
    }//GEN-LAST:event_btnEdit2ActionPerformed

    private void btnEdit3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEdit3ActionPerformed
        // TODO add your handling code here:
        // ORDER EDIT BUTTON
        editOrder();
    }//GEN-LAST:event_btnEdit3ActionPerformed

    private void btnAdd3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd3ActionPerformed
        // TODO add your handling code here:
        // ORDER ADD BUTTON
        addOrder();
    }//GEN-LAST:event_btnAdd3ActionPerformed

    private void btnRemove3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemove3ActionPerformed
        // TODO add your handling code here:
        // ORDER REMOVE BUTTON
        removeOrder();
    }//GEN-LAST:event_btnRemove3ActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        // INVENTORY ADD BUTTON
        addInvItem();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        // TODO add your handling code here:
        // INVENTORY REMOVE BUTTON
        removeInvItem();
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        // INVENTORY EDIT BUTTON
        editInventory();
    }//GEN-LAST:event_btnEditActionPerformed

    // update the info box when clicking on an order
    private void orderTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderTableMouseClicked
        // TODO add your handling code here:
        showOrderInfo();
    }//GEN-LAST:event_orderTableMouseClicked

    private void btnDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDateActionPerformed
        // TODO add your handling code here:
        if (txtDate.isEnabled() == true) {
            txtDate.setEnabled(false);
        } else {
            txtDate.setEnabled(true);
        }
    }//GEN-LAST:event_btnDateActionPerformed

    private void btnAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd1ActionPerformed
        // TODO add your handling code here:
        // ADD STAFF BUTTON
        addStaff();
    }//GEN-LAST:event_btnAdd1ActionPerformed

    private void btnRemove1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemove1ActionPerformed
        // TODO add your handling code here:
        // REMOVE STAFF BUTTON
        removeStaff();
    }//GEN-LAST:event_btnRemove1ActionPerformed

    private void btnAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd2ActionPerformed
        // TODO add your handling code here:
        // PROFILE ADD BUTTON
        addProfile();
    }//GEN-LAST:event_btnAdd2ActionPerformed

    private void btnRemove2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemove2ActionPerformed
        // TODO add your handling code here:
        // PROFILE REMOVE BUTTON
        removeProfile();
    }//GEN-LAST:event_btnRemove2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAdd1;
    private javax.swing.JButton btnAdd2;
    private javax.swing.JButton btnAdd3;
    private javax.swing.JButton btnDate;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnEdit1;
    private javax.swing.JButton btnEdit2;
    private javax.swing.JButton btnEdit3;
    private javax.swing.JButton btnInventory;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnOrders;
    private javax.swing.JButton btnProfiles;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnRemove1;
    private javax.swing.JButton btnRemove2;
    private javax.swing.JButton btnRemove3;
    private javax.swing.JButton btnStaff;
    private javax.swing.JTable invTable;
    private javax.swing.JScrollPane invTableContainer;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lbl$;
    private javax.swing.JLabel lbl$1;
    private javax.swing.JLabel lbl$2;
    private javax.swing.JLabel lblAccountName;
    private javax.swing.JLabel lblAmount;
    private javax.swing.JLabel lblAuth;
    private javax.swing.JLabel lblCost;
    private javax.swing.JLabel lblCurrentAccount;
    private javax.swing.JLabel lblCurrentMenu;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblItem;
    private javax.swing.JLabel lblLocation;
    private javax.swing.JLabel lblMenuName;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPosition;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblStaffName;
    private javax.swing.JLabel lblStock;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblWage;
    private javax.swing.JScrollPane orderInfo;
    private javax.swing.JTable orderTable;
    private javax.swing.JScrollPane orderTableContainer;
    private javax.swing.JPanel pnlInventory;
    private javax.swing.JPanel pnlMenuButtons;
    private javax.swing.JPanel pnlOrders;
    private javax.swing.JPanel pnlProfiles;
    private javax.swing.JPanel pnlStaff;
    private javax.swing.JTable profileTable;
    private javax.swing.JScrollPane profileTableContainer;
    private javax.swing.JSpinner spnAmount;
    private javax.swing.JSpinner spnAuth;
    private javax.swing.JSpinner spnCost;
    private javax.swing.JSpinner spnPrice;
    private javax.swing.JSpinner spnStock;
    private javax.swing.JSpinner spnWage;
    private javax.swing.JTable staffTable;
    private javax.swing.JScrollPane staffTableContainer;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtItem;
    private javax.swing.JTextField txtLocation;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtPosition;
    private javax.swing.JTextField txtStaffName;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
