package appPackage;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.sql.*;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.awt.event.ActionEvent;

public class mainDashboard {

	private JFrame frame;
	private JTextField userNameLabel;
	private JPasswordField passwordLabel;
	private JButton blogin;

	Connection connection = null;
	
	private JTable allProductsTable;
	private JTable enginePartsTable;
	private JTable alternatorsTable;
	private JTable filtersTable;
	private JTable tiresTable;
	private JTable checkoutTable;
	private JTable productListTable;
	private JTextField textField_subTotal;
	private JTextField textField_tax;
	private JTextField textField_total;
	private DefaultTableModel model;
	private Object[] row;
	private double total;
	/**
	 * Create the application.
	 * 
	 */
	public mainDashboard() {
		connection = sqliteConnection.dbConnector();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("AutoMotivation POS System");
		frame.setSize(1440, 865);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		frame.setContentPane(new JLabel(new ImageIcon("src/mainDashBG.jpg")));
		frame.setVisible(true);
				
		int gap = 10;
		
		// Icon Image
	    ImageIcon icon = new ImageIcon("src/appIconImage.png");
	    frame.setIconImage(icon.getImage());
		
		ImageIcon homeIcon = new ImageIcon("src/house.png");
		ImageIcon checkIcon = new ImageIcon("src/check.png");
		ImageIcon inventoryIcon = new ImageIcon("src/inventory.png");
		ImageIcon managerIcon = new ImageIcon("src/manager.png");
		
		JTabbedPane mainTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		mainTabbedPane.setBounds(15, 186, 1388, 526);
		mainTabbedPane.setFont(new Font("Tahoma", Font.BOLD, 16));
		frame.getContentPane().add(mainTabbedPane);
		
		//////////////////////////MAIN DASH TAB ///////////////////////////////
		
		JPanel mainDashTab = new JPanel();
		mainTabbedPane.addTab("Main Dashboard", homeIcon, mainDashTab, "Go to Home Screen");
		mainDashTab.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setBounds(0, 0, 1383, 492);
		mainDashTab.add(label);
		label.setIcon(new ImageIcon("src/mainDashTabBGFinal.jpg"));
		
		////////////////////////// CHECKOUT TAB ///////////////////////////////
		
		JPanel checkoutTab = new JPanel();
		mainTabbedPane.addTab("Checkout", checkIcon, checkoutTab, "Checkout Customer");
		checkoutTab.setLayout(null);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(776, 74, 592, 377);
		checkoutTab.add(scrollPane_5);
		
		checkoutTable = new JTable();
		checkoutTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 18));
		checkoutTable.setFont(new Font("Tahoma", Font.BOLD, 16));
		checkoutTable.setRowHeight(checkoutTable.getRowHeight()+ 10);
		scrollPane_5.setViewportView(checkoutTable);
		
		Object[] columns = {"Product Name","Price"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        
        // set the model to the table
        checkoutTable.setModel(model);
        
        // create an array of objects to set the row data
        row = new Object[2];
		
        Font labelFont = new Font("Tahoma", Font.BOLD, 18);
        
		JLabel lblOrderSummary = new JLabel("Order Summary");
		lblOrderSummary.setFont(labelFont);
		lblOrderSummary.setBounds(776, 16, 189, 39);
		checkoutTab.add(lblOrderSummary);
		
		JLabel lblProductList = new JLabel("Product List");
		lblProductList.setFont(labelFont);
		lblProductList.setBounds(39, 16, 189, 39);
		checkoutTab.add(lblProductList);
		
		Font textFieldFont = new Font("Tahoma", Font.BOLD, 20);
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setFont(textFieldFont);
		lblTotal.setBounds(39, 442, 69, 20);
		checkoutTab.add(lblTotal);
		
		JLabel lblTax = new JLabel("Tax:");
		lblTax.setFont(textFieldFont);
		lblTax.setBounds(39, 406, 69, 20);
		checkoutTab.add(lblTax);
		
		
		JLabel lblSubTotal = new JLabel("Sub Total:");
		lblSubTotal.setFont(textFieldFont);
		lblSubTotal.setBounds(39, 371, 102, 20);
		checkoutTab.add(lblSubTotal);
		
		JButton btnNewButton = new JButton("Print Order");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MessageFormat footer = new MessageFormat("Total: $" + total);
				try {
					
					checkoutTable.print(JTable.PrintMode.FIT_WIDTH, null, footer);
				} catch (PrinterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton.setBounds(1219, 16, 149, 29);
		checkoutTab.add(btnNewButton);
		
		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(39, 74, 540, 280);
		checkoutTab.add(scrollPane_6);
		
		productListTable = new JTable();
		productListTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 18));
		productListTable.setFont(new Font("Tahoma", Font.BOLD, 16));
		productListTable.setRowHeight(productListTable.getRowHeight()+ 10);
		scrollPane_6.setViewportView(productListTable);
		
		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addItem();
                updateCheckout();

			}
		});
		
		btnAddItem.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAddItem.setBounds(608, 167, 130, 29);
		checkoutTab.add(btnAddItem);
		
		JButton btnRemoveItem = new JButton("Remove Item");
		btnRemoveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				removeItem();				
                updateCheckout();
                
			}
		});
		btnRemoveItem.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRemoveItem.setBounds(608, 228, 130, 29);
		checkoutTab.add(btnRemoveItem);
		
		JButton btnLoadProducts = new JButton("Load Products");
		btnLoadProducts.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnLoadProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String queryAllProducts = "select Name, price, inStock from InventoryList";
					PreparedStatement pstAllProducts = connection.prepareStatement(queryAllProducts);					
					ResultSet rsAllProducts = pstAllProducts.executeQuery();
					
					productListTable.setModel(DbUtils.resultSetToTableModel(rsAllProducts));
										
					rsAllProducts.close();
					pstAllProducts.close();
					
					
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnLoadProducts.setBounds(404, 16, 175, 42);
		checkoutTab.add(btnLoadProducts);
		
		textField_subTotal = new JTextField();
		textField_subTotal.setFont(new Font("Tahoma", Font.BOLD, 18));
		textField_subTotal.setEditable(false);
		textField_subTotal.setBounds(162, 370, 146, 26);
		checkoutTab.add(textField_subTotal);
		textField_subTotal.setColumns(10);
		
		textField_tax = new JTextField();
		textField_tax.setFont(new Font("Tahoma", Font.BOLD, 18));
		textField_tax.setEditable(false);
		textField_tax.setBounds(162, 405, 146, 26);
		checkoutTab.add(textField_tax);
		textField_tax.setColumns(10);
		
		textField_total = new JTextField();
		textField_total.setFont(new Font("Tahoma", Font.BOLD, 20));
		textField_total.setEditable(false);
		textField_total.setBounds(162, 441, 146, 26);
		checkoutTab.add(textField_total);
		textField_total.setColumns(10);

		
		////////////////////////// INVENTORY TAB ///////////////////////////////
		
		JPanel inventoryTab = new JPanel();
		mainTabbedPane.addTab("Inventory", inventoryIcon, inventoryTab, "Look up parts, price, & inventory");
		inventoryTab.setLayout(null);
		
		JTabbedPane inventorySubPane = new JTabbedPane(JTabbedPane.TOP);
		inventorySubPane.setBounds(15, 46, 1353, 430);
		inventorySubPane.setFont(new Font("Tahoma", Font.BOLD, 16));
		inventoryTab.add(inventorySubPane);
		
		JPanel allProductsTab = new JPanel();
		inventorySubPane.addTab("All Products", null, allProductsTab, null);
		allProductsTab.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 1348, 396);
		allProductsTab.add(scrollPane);
		
		allProductsTable = new JTable();
		allProductsTable.setFont(new Font("Tahoma", Font.BOLD, 16));
		allProductsTable.setRowHeight(allProductsTable.getRowHeight()+ 10);
		scrollPane.setViewportView(allProductsTable);
		
		JPanel engineTab = new JPanel();
		inventorySubPane.addTab("Engine Parts", null, engineTab, null);
		engineTab.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(15, 16, 1318, 364);
		engineTab.add(scrollPane_1);
		
		enginePartsTable = new JTable();
		enginePartsTable.setFont(new Font("Tahoma", Font.BOLD, 16));
		enginePartsTable.setRowHeight(enginePartsTable.getRowHeight()+ gap);
		scrollPane_1.setViewportView(enginePartsTable);
		
		JPanel alternatorsTab = new JPanel();
		inventorySubPane.addTab("Alternators", null, alternatorsTab, null);
		alternatorsTab.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(15, 16, 1318, 364);
		alternatorsTab.add(scrollPane_2);
		
		alternatorsTable = new JTable();
		alternatorsTable.setFont(new Font("Tahoma", Font.BOLD, 16));
		alternatorsTable.setRowHeight(alternatorsTable.getRowHeight()+ gap);
		scrollPane_2.setViewportView(alternatorsTable);
		
		JPanel filtersTab = new JPanel();
		inventorySubPane.addTab("Filters", null, filtersTab, null);
		filtersTab.setLayout(null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(15, 16, 1318, 364);
		filtersTab.add(scrollPane_3);
		
		filtersTable = new JTable();
		filtersTable.setFont(new Font("Tahoma", Font.BOLD, 16));
		filtersTable.setRowHeight(filtersTable.getRowHeight()+ gap);
		scrollPane_3.setViewportView(filtersTable);
		
		JPanel tiresTab = new JPanel();
		inventorySubPane.addTab("Tires", null, tiresTab, null);
		tiresTab.setLayout(null);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(15, 16, 1318, 364);
		tiresTab.add(scrollPane_4);
		
		tiresTable = new JTable();
		tiresTable.setFont(new Font("Tahoma", Font.BOLD, 16));
		tiresTable.setRowHeight(tiresTable.getRowHeight()+ gap);
		scrollPane_4.setViewportView(tiresTable);
		
		JButton btnLoadInventory = new JButton("Load Inventory");
		btnLoadInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String queryAllProducts = "select * from InventoryList";
					PreparedStatement pstAllProducts = connection.prepareStatement(queryAllProducts);					
					ResultSet rsAllProducts = pstAllProducts.executeQuery();
					
					allProductsTable.setModel(DbUtils.resultSetToTableModel(rsAllProducts));
										
					rsAllProducts.close();
					pstAllProducts.close();
					
					String queryEngineParts = "select * from InventoryList where Category = ?";
					PreparedStatement pstEngineParts = connection.prepareStatement(queryEngineParts);
					pstEngineParts.setString(1, "Engine Parts" );
					ResultSet rsEngineParts = pstEngineParts.executeQuery();
					
					enginePartsTable.setModel(DbUtils.resultSetToTableModel(rsEngineParts));
										
					rsEngineParts.close();
					pstEngineParts.close();
					
					String queryAlternators = "select * from InventoryList where Category = ?";
					PreparedStatement pstAlternators = connection.prepareStatement(queryAlternators);
					pstAlternators.setString(1, "Alternators" );
					ResultSet rsAlternators = pstAlternators.executeQuery();
					
					alternatorsTable.setModel(DbUtils.resultSetToTableModel(rsAlternators));
										
					rsAlternators.close();
					pstAlternators.close();
					
					String queryFilters = "select * from InventoryList where Category = ?";
					PreparedStatement pstFilters = connection.prepareStatement(queryFilters);
					pstFilters.setString(1, "Filters" );
					ResultSet rsFilters = pstFilters.executeQuery();
					
					filtersTable.setModel(DbUtils.resultSetToTableModel(rsFilters));
										
					rsFilters.close();
					pstFilters.close();
					
					String queryTires = "select * from InventoryList where Category = ?";
					PreparedStatement pstTires = connection.prepareStatement(queryTires);
					pstTires.setString(1, "Tires" );
					ResultSet rsTires = pstTires.executeQuery();
					
					tiresTable.setModel(DbUtils.resultSetToTableModel(rsTires));
										
					rsTires.close();
					pstTires.close();
					
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnLoadInventory.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnLoadInventory.setBounds(15, 1, 194, 38);
		inventoryTab.add(btnLoadInventory);
		
		///////////////////////MANAGER TAB ///////////////////////////////
		
		JPanel managerPanel = new JPanel();
		mainTabbedPane.addTab("Manager Login", managerIcon, managerPanel, "Manager Login");
		managerPanel.setLayout(null);
		
		userNameLabel = new JTextField("Username");
		userNameLabel.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				userNameLabel.setText("");
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (userNameLabel.getText().trim().isEmpty()) {
					userNameLabel.setText("User Name");
				}
			}
		});		
		userNameLabel.setOpaque(false);
		userNameLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userNameLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		userNameLabel.setBounds(new Rectangle(468, 93, 442, 65));
		managerPanel.add(userNameLabel);
		
		passwordLabel = new JPasswordField("Password");
		passwordLabel.addFocusListener(new FocusListener() {
	        @Override
	        public void focusGained(FocusEvent e) {
	          passwordLabel.setText("");
	        }

	        @Override
	        public void focusLost(FocusEvent arg0) {
	          if (passwordLabel.getPassword().length == 0) {
	            passwordLabel.setText("password");
	          }
	        }
	      });
	    passwordLabel.setOpaque(false);
	    passwordLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	    passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
	    passwordLabel.setBounds(new Rectangle(468, 174, 442, 65));
	    managerPanel.add(passwordLabel);
	    
	    blogin = new JButton("Login");
	    blogin.setFont(new Font("Tahoma", Font.BOLD, 18));
	    blogin.setBounds(new Rectangle(589, 255, 200, 45));
	    managerPanel.add(blogin);
		
		JLabel lblNewLabel = new JLabel("Manager Login");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(618, 62, 142, 20);
		managerPanel.add(lblNewLabel);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("src/managerTabBG.jpg"));
		label_1.setBounds(0, 0, 1383, 492);
		managerPanel.add(label_1);
		
	    actionlogin();
	    connection = sqliteConnection.dbConnector();
		
		
//		JPanel panel_4 = new JPanel();
//		tabbedPane.addTab("New tab", null, panel_4, null);
//		panel_4.setLayout(null);
	}
	
	public void incrementStock(String productName){
		for(int i = 0; i < productListTable.getRowCount(); i++){//For each row
	            if(productListTable.getValueAt(i, 0).equals(productName)){
	        		int tempStock = (int)productListTable.getValueAt(i, 2);
	        		tempStock++;
	        		productListTable.setValueAt(tempStock, i, 2);
	            }
		}
	}
	
	public void decrementStock(){
		int i = productListTable.getSelectedRow();
		int tempStock = (int)productListTable.getValueAt(i, 2);
		tempStock--;
		productListTable.setValueAt(tempStock, i, 2);
	}
	
	public void addItem(){
		int i = productListTable.getSelectedRow();
		
		if((int)productListTable.getValueAt(i, 2) > 0){
			row[0] = productListTable.getValueAt(i, 0);
	        row[1] = productListTable.getValueAt(i, 1);
	        
	        //add row to the model
	        model.addRow(row);
	        decrementStock();
		}else{
			JOptionPane.showMessageDialog(null, "No more in stock!");;
		}
	}
	
	public void removeItem(){
		// i = the index of the selected row
        int i = checkoutTable.getSelectedRow();
        String productName = model.getValueAt(i, 0).toString();
        incrementStock(productName);
        if(i >= 0){
            // remove a row from JTable
            model.removeRow(i);
        }
        else{
            System.out.println("Delete Error");
        }
	}
	
	public void updateCheckout(){
		double subTotal = 0;        
        
        for (int rowIndex = 0; rowIndex < model.getRowCount(); rowIndex++){
        	subTotal += ((Double)model.getValueAt(rowIndex, 1));
        }
        
        double tax = subTotal * 0.06;
        
        total = subTotal + tax;
        
        textField_subTotal.setText("$" + Double.toString(subTotal));
        textField_tax.setText("$" + Double.toString(tax));
        textField_total.setText("$" + Double.toString(total));
	}
	
	public void actionlogin() {
		blogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try{
					String query="select * from Managers where username = ? and password = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, userNameLabel.getText() );
					pst.setString(2, passwordLabel.getText() );
					
					ResultSet rs = pst.executeQuery();
					int count = 0;
					while (rs.next()){
						
						count++;
						
					}
					if (count == 1){
						managerFrame manFrame = new managerFrame();
						//regFace.setVisible(true);
						//dispose();
					}else if (count == 2){
						JOptionPane.showMessageDialog(null,"Duplicate Username and Password");
					} else{
						JOptionPane.showMessageDialog(null,"Username and/or Password is not correct!");
					}
					
					rs.close();
					pst.close();
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
	}
}
