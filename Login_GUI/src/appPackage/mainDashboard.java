package appPackage;

import java.awt.Font;
import java.awt.Rectangle;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;

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
	private DecimalFormat df;

	/**
	 * Create the Main Application.
	 * 
	 */
	public mainDashboard() throws URISyntaxException {
		connection = sqliteConnection.dbConnector();
		initialize();
		UpdateTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws URISyntaxException {
		frame = new JFrame();
		frame.setTitle("AutoMotivation POS System");
		frame.setSize(1440, 865);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		frame.setContentPane(new JLabel(new ImageIcon("src/mainDashBG.jpg")));
		frame.setVisible(true);

		//JTable Cell Spacing Height
		int gap = 20;

		// Application Icon Image
		ImageIcon icon = new ImageIcon("src/appIconImage.png");
		frame.setIconImage(icon.getImage());

		// Tab Icons
		ImageIcon homeIcon = new ImageIcon("src/house.png");
		ImageIcon checkIcon = new ImageIcon("src/check.png");
		ImageIcon inventoryIcon = new ImageIcon("src/inventory.png");
		ImageIcon managerIcon = new ImageIcon("src/manager.png");

		// Container for Tabs
		JTabbedPane mainTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		mainTabbedPane.setBounds(15, 186, 1388, 526);
		mainTabbedPane.setFont(new Font("Tahoma", Font.BOLD, 16));
		frame.getContentPane().add(mainTabbedPane);
		

		////////////////////////// MAIN DASH TAB ///////////////////////////////

		JPanel mainDashTab = new JPanel();
		mainTabbedPane.addTab("Main Dashboard", homeIcon, mainDashTab, "Go to Home Screen");
		mainDashTab.setLayout(null);

		// Website Button to links to Company Website
		ImageIcon websiteButtonBG = new ImageIcon("src/WebsiteButton.jpg");
		JButton websiteButton = new JButton(websiteButtonBG);
		websiteButton.setOpaque(true);
		websiteButton.setBorderPainted(false);
		websiteButton.setBounds(870, 20, 500, 176);
		websiteButton.setToolTipText("www.automotivation.razorgraphixx.com");
		websiteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String command1 = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe http://automotivation.razorgraphixx.com/";
					Process link1 = Runtime.getRuntime().exec(command1);
				} catch (Exception ex) {
					System.out.println("cannot execute command. " + ex);
				}
			}
		});
		mainDashTab.add(websiteButton);

		//Main Dashboard Tab Background Image
		JLabel label = new JLabel("");
		label.setBounds(0, 0, 1383, 492);
		mainDashTab.add(label);
		label.setIcon(new ImageIcon("src/mainDashTabBGFinal.jpg"));

		// Display Current Logged In User
		JPanel statusPanel = new JPanel();
		frame.add(statusPanel, BorderLayout.NORTH);
		statusPanel.setBounds(615, 6, 230, 29); // 900, 1, 230, 89
		statusPanel.setBackground(Color.WHITE);
		statusPanel.setOpaque(false);
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));

		JLabel statusLabel = new JLabel("Current User: " + loginGUI.returnUsername());
		statusLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		statusLabel.setForeground(Color.WHITE);
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);

		
		
		////////////////////////// CHECKOUT TAB ///////////////////////////////

		JPanel checkoutTab = new JPanel();
		checkoutTab.setBorder(null);
		mainTabbedPane.addTab("Checkout", checkIcon, checkoutTab, "Checkout Customer");
		checkoutTab.setLayout(null);

		JScrollPane scrollPaneCheckout = new JScrollPane();
		scrollPaneCheckout.setBounds(776, 74, 592, 377);
		checkoutTab.add(scrollPaneCheckout);

		//Checkout Table
		checkoutTable = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		checkoutTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 18));
		checkoutTable.setFont(new Font("Tahoma", Font.BOLD, 16));
		checkoutTable.setRowHeight(checkoutTable.getRowHeight() + 10);
		scrollPaneCheckout.setViewportView(checkoutTable);

		//Table Headers
		Object[] columns = { "Product Name", "Price" };
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);

		// set the model to the table
		checkoutTable.setModel(model);

		// create an array of objects to set the row data
		row = new Object[2];

		//Font styling for Labels
		Font labelFont = new Font("Tahoma", Font.BOLD, 18);

		JLabel lblOrderSummary = new JLabel("Order Summary");
		lblOrderSummary.setFont(labelFont);
		lblOrderSummary.setBounds(776, 16, 189, 39);
		checkoutTab.add(lblOrderSummary);

		JLabel lblProductList = new JLabel("Product List");
		lblProductList.setFont(labelFont);
		lblProductList.setBounds(39, 16, 189, 39);
		checkoutTab.add(lblProductList);
		
		//Font styling for textfields
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

		//Print order button
		JButton btnNewButton = new JButton("Print Order");
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MessageFormat header = new MessageFormat("AutoMotivation POS System");
				MessageFormat footer = new MessageFormat("Total: " + textField_total.getText());
				try {
					checkoutTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
				} catch (PrinterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}); // end btnNewButton.addActionListener
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton.setBounds(1219, 16, 149, 29);
		checkoutTab.add(btnNewButton);

		// Product List Table
		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(39, 74, 540, 280);
		checkoutTab.add(scrollPane_6);

		productListTable = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		productListTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 18));
		productListTable.setFont(new Font("Tahoma", Font.BOLD, 16));
		productListTable.setRowHeight(productListTable.getRowHeight() + 10);
		scrollPane_6.setViewportView(productListTable);
		//end product list table

		//Add Item Button
		JButton btnAddItem2 = new JButton("Add Item");
		btnAddItem2.setForeground(Color.WHITE);
		btnAddItem2.setBackground(Color.BLACK);
		btnAddItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addItem();
				updateCheckout();

			}
		});

		btnAddItem2.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAddItem2.setBounds(608, 167, 130, 29);
		checkoutTab.add(btnAddItem2);

		//Remove Item Button
		JButton btnRemoveItem = new JButton("Remove Item");
		btnRemoveItem.setForeground(Color.WHITE);
		btnRemoveItem.setBackground(Color.BLACK);
		btnRemoveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				removeItem();
				updateCheckout();

			}
		});
		btnRemoveItem.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRemoveItem.setBounds(608, 228, 130, 29);
		checkoutTab.add(btnRemoveItem);

		//Refresh Product List
		JButton btnRefreshProducts = new JButton("Refresh");
		btnRefreshProducts.setForeground(Color.WHITE);
		btnRefreshProducts.setBackground(Color.BLACK);
		btnRefreshProducts.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnRefreshProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String queryAllProducts = "select Name, price, inStock from InventoryList";
					PreparedStatement pstAllProducts = connection.prepareStatement(queryAllProducts);
					ResultSet rsAllProducts = pstAllProducts.executeQuery();

					productListTable.setModel(DbUtils.resultSetToTableModel(rsAllProducts));

					rsAllProducts.close();
					pstAllProducts.close();

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnRefreshProducts.setBounds(404, 16, 175, 42);
		checkoutTab.add(btnRefreshProducts);

		//SubTotal Field
		textField_subTotal = new JTextField();
		textField_subTotal.setFont(new Font("Tahoma", Font.BOLD, 18));
		textField_subTotal.setEditable(false);
		textField_subTotal.setBounds(162, 370, 146, 26);
		checkoutTab.add(textField_subTotal);
		textField_subTotal.setColumns(10);

		//Tax Field
		textField_tax = new JTextField();
		textField_tax.setFont(new Font("Tahoma", Font.BOLD, 18));
		textField_tax.setEditable(false);
		textField_tax.setBounds(162, 405, 146, 26);
		checkoutTab.add(textField_tax);
		textField_tax.setColumns(10);

		//Total Field
		textField_total = new JTextField();
		textField_total.setFont(new Font("Tahoma", Font.BOLD, 20));
		textField_total.setEditable(false);
		textField_total.setBounds(162, 441, 146, 26);
		checkoutTab.add(textField_total);
		textField_total.setColumns(10);
		
		////////////////////////// END OF CHECKOUT TAB /////////////////////////

		////////////////////////// INVENTORY TAB ///////////////////////////////

		JPanel inventoryTab = new JPanel();
		mainTabbedPane.addTab("Inventory", inventoryIcon, inventoryTab, "Look up parts, price, & inventory");
		inventoryTab.setLayout(null);

		JTabbedPane inventorySubPane = new JTabbedPane(JTabbedPane.TOP);
		inventorySubPane.setBounds(15, 46, 1353, 430);
		inventorySubPane.setFont(new Font("Tahoma", Font.BOLD, 16));
		inventoryTab.add(inventorySubPane);

		//All Products Sub Tabs
		JPanel allProductsTab = new JPanel();
		inventorySubPane.addTab("All Products", null, allProductsTab, null);
		allProductsTab.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 1348, 396);
		allProductsTab.add(scrollPane);

		//All Products Table
		allProductsTable = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		allProductsTable.setFont(new Font("Tahoma", Font.BOLD, 16));
		allProductsTable.setRowHeight(allProductsTable.getRowHeight() + 20);
		scrollPane.setViewportView(allProductsTable);

		//Engine Parts sub category tab
		JPanel engineTab = new JPanel();
		inventorySubPane.addTab("Engine Parts", null, engineTab, null);
		engineTab.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(15, 16, 1318, 364);
		engineTab.add(scrollPane_1);

		// Engine Parts Table
		enginePartsTable = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		enginePartsTable.setFont(new Font("Tahoma", Font.BOLD, 16));
		enginePartsTable.setRowHeight(enginePartsTable.getRowHeight() + gap);
		scrollPane_1.setViewportView(enginePartsTable);

		//Alternator Parts sub category tab
		JPanel alternatorsTab = new JPanel();
		inventorySubPane.addTab("Alternators", null, alternatorsTab, null);
		alternatorsTab.setLayout(null);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(15, 16, 1318, 364);
		alternatorsTab.add(scrollPane_2);

		//Alternator Tables
		alternatorsTable = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		alternatorsTable.setFont(new Font("Tahoma", Font.BOLD, 16));
		alternatorsTable.setRowHeight(alternatorsTable.getRowHeight() + gap);
		scrollPane_2.setViewportView(alternatorsTable);

		//Filter Parts sub category tab
		JPanel filtersTab = new JPanel();
		inventorySubPane.addTab("Filters", null, filtersTab, null);
		filtersTab.setLayout(null);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(15, 16, 1318, 364);
		filtersTab.add(scrollPane_3);

		//Filter Table
		filtersTable = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells are not editable
				return false;
			}
		};
		filtersTable.setFont(new Font("Tahoma", Font.BOLD, 16));
		filtersTable.setRowHeight(filtersTable.getRowHeight() + gap);
		scrollPane_3.setViewportView(filtersTable);

		//Tire Parts sub category tab
		JPanel tiresTab = new JPanel();
		inventorySubPane.addTab("Tires", null, tiresTab, null);
		tiresTab.setLayout(null);

		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(15, 16, 1318, 364);
		tiresTab.add(scrollPane_4);

		//Tires Table
		tiresTable = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tiresTable.setFont(new Font("Tahoma", Font.BOLD, 16));
		tiresTable.setRowHeight(tiresTable.getRowHeight() + gap);
		scrollPane_4.setViewportView(tiresTable);

		JButton btnRefreshInventory = new JButton("Refresh Inventory");
		btnRefreshInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String queryAllProducts = "select * from InventoryList";
					PreparedStatement pstAllProducts = connection.prepareStatement(queryAllProducts);
					ResultSet rsAllProducts = pstAllProducts.executeQuery();

					allProductsTable.setModel(DbUtils.resultSetToTableModel(rsAllProducts));

					rsAllProducts.close();
					pstAllProducts.close();

					String queryEngineParts = "select * from InventoryList where Category = ?";
					PreparedStatement pstEngineParts = connection.prepareStatement(queryEngineParts);
					pstEngineParts.setString(1, "Engine Parts");
					ResultSet rsEngineParts = pstEngineParts.executeQuery();

					enginePartsTable.setModel(DbUtils.resultSetToTableModel(rsEngineParts));

					rsEngineParts.close();
					pstEngineParts.close();

					String queryAlternators = "select * from InventoryList where Category = ?";
					PreparedStatement pstAlternators = connection.prepareStatement(queryAlternators);
					pstAlternators.setString(1, "Alternators");
					ResultSet rsAlternators = pstAlternators.executeQuery();

					alternatorsTable.setModel(DbUtils.resultSetToTableModel(rsAlternators));

					rsAlternators.close();
					pstAlternators.close();

					String queryFilters = "select * from InventoryList where Category = ?";
					PreparedStatement pstFilters = connection.prepareStatement(queryFilters);
					pstFilters.setString(1, "Filters");
					ResultSet rsFilters = pstFilters.executeQuery();

					filtersTable.setModel(DbUtils.resultSetToTableModel(rsFilters));

					rsFilters.close();
					pstFilters.close();

					String queryTires = "select * from InventoryList where Category = ?";
					PreparedStatement pstTires = connection.prepareStatement(queryTires);
					pstTires.setString(1, "Tires");
					ResultSet rsTires = pstTires.executeQuery();

					tiresTable.setModel(DbUtils.resultSetToTableModel(rsTires));

					rsTires.close();
					pstTires.close();

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnRefreshInventory.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnRefreshInventory.setBounds(15, 1, 194, 38);
		inventoryTab.add(btnRefreshInventory);
		
		//////////////////////////// END OF INVENTORY TAB //////////////////////

		//////////////////////////// MANAGER TAB ///////////////////////////////

		JPanel managerPanel = new JPanel();
		mainTabbedPane.addTab("Manager Login", managerIcon, managerPanel, "Manager Login");
		managerPanel.setLayout(null);

		//Manager label above login fields
		JLabel lblNewLabel = new JLabel("Manager Login");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(618, 62, 142, 20);
		managerPanel.add(lblNewLabel);
		
		//Username Field
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

		//Password Field
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

		//Login Button
		blogin = new JButton("Login");
		blogin.setFont(new Font("Tahoma", Font.BOLD, 18));
		blogin.setBounds(new Rectangle(589, 255, 200, 45));
		managerPanel.add(blogin);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("src/managerTabBG.jpg"));
		label_1.setBounds(0, 0, 1383, 492);
		managerPanel.add(label_1);

		//Login method Call
		actionlogin();
		
	}

	//Increments stock quantity when an Item is removed from order summary table
	public void incrementStock(String productName) {
		for (int i = 0; i < productListTable.getRowCount(); i++) {
			if (productListTable.getValueAt(i, 0).equals(productName)) {
				int tempStock = (int) productListTable.getValueAt(i, 2);
				tempStock++;
				productListTable.setValueAt(tempStock, i, 2);
			}
		}
	}

	//Decrements stock quantity when an Item is added to order summary table
	public void decrementStock() {
		int i = productListTable.getSelectedRow();
		int tempStock = (int) productListTable.getValueAt(i, 2);
		tempStock--;
		productListTable.setValueAt(tempStock, i, 2);
	}

	//While sufficient in stock, copy item from inventory list to order summary
	public void addItem() {
		int i = productListTable.getSelectedRow();
		if (i >= 0) {
			if ((int) productListTable.getValueAt(i, 2) > 0) {
				row[0] = productListTable.getValueAt(i, 0);
				row[1] = productListTable.getValueAt(i, 1);

				// add row to the model
				model.addRow(row);
				decrementStock();
			} else {
				JOptionPane.showMessageDialog(null, "No more in stock!");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Select an item to add from inventory!");
		}
	}

	//While Item is selected and cart not empty, remove from order summary
	public void removeItem() {
		// i = the index of the selected row
		int i = checkoutTable.getSelectedRow();

		if (i >= 0) {
			String productName = model.getValueAt(i, 0).toString();
			incrementStock(productName);
			// remove a row from JTable
			model.removeRow(i);
		} else {
			JOptionPane.showMessageDialog(null, "Cart is empty, or No item selected to remove!");
		}
	}

	//Update Subtotal, Tax, and Totals
	public void updateCheckout() {
		double subTotal = 0;

		for (int rowIndex = 0; rowIndex < model.getRowCount(); rowIndex++) {
			subTotal += ((Double) model.getValueAt(rowIndex, 1));
		}

		double tax = subTotal * 0.06;

		total = round(subTotal + tax);

		textField_subTotal.setText("$" + Double.toString(round(subTotal)));
		textField_tax.setText("$" + Double.toString(round(tax)));
		textField_total.setText("$" + Double.toString(round(total)));
	}

	//Format to two Decimal places // Example: 458.9930495 ===> 458.99
	public double round(double value) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	
	public void UpdateTable() {
		///////////////Load Products ///////////////
		try {
			String queryAllProducts = "select Name, price, inStock from InventoryList";
			PreparedStatement pstAllProducts = connection.prepareStatement(queryAllProducts);
			ResultSet rsAllProducts = pstAllProducts.executeQuery();

			productListTable.setModel(DbUtils.resultSetToTableModel(rsAllProducts));

			rsAllProducts.close();
			pstAllProducts.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

		////////// Load Inventory///////////////////
		try {
			String queryAllProducts = "select * from InventoryList";
			PreparedStatement pstAllProducts = connection.prepareStatement(queryAllProducts);
			ResultSet rsAllProducts = pstAllProducts.executeQuery();

			allProductsTable.setModel(DbUtils.resultSetToTableModel(rsAllProducts));

			rsAllProducts.close();
			pstAllProducts.close();

			String queryEngineParts = "select * from InventoryList where Category = ?";
			PreparedStatement pstEngineParts = connection.prepareStatement(queryEngineParts);
			pstEngineParts.setString(1, "Engine Parts");
			ResultSet rsEngineParts = pstEngineParts.executeQuery();

			enginePartsTable.setModel(DbUtils.resultSetToTableModel(rsEngineParts));

			rsEngineParts.close();
			pstEngineParts.close();

			String queryAlternators = "select * from InventoryList where Category = ?";
			PreparedStatement pstAlternators = connection.prepareStatement(queryAlternators);
			pstAlternators.setString(1, "Alternators");
			ResultSet rsAlternators = pstAlternators.executeQuery();

			alternatorsTable.setModel(DbUtils.resultSetToTableModel(rsAlternators));

			rsAlternators.close();
			pstAlternators.close();

			String queryFilters = "select * from InventoryList where Category = ?";
			PreparedStatement pstFilters = connection.prepareStatement(queryFilters);
			pstFilters.setString(1, "Filters");
			ResultSet rsFilters = pstFilters.executeQuery();

			filtersTable.setModel(DbUtils.resultSetToTableModel(rsFilters));

			rsFilters.close();
			pstFilters.close();

			String queryTires = "select * from InventoryList where Category = ?";
			PreparedStatement pstTires = connection.prepareStatement(queryTires);
			pstTires.setString(1, "Tires");
			ResultSet rsTires = pstTires.executeQuery();

			tiresTable.setModel(DbUtils.resultSetToTableModel(rsTires));

			rsTires.close();
			pstTires.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	//Open URL Connection
	public void open(URI uri) {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(uri);
			} catch (IOException e) {
				System.out.println("Seems there was an error connecting to the internet!"); }
		} else {}
	}

	//Execute Login and check credentials
	public void actionlogin() {
		blogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					String query = "select * from Managers where username = ? and password = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, userNameLabel.getText());
					pst.setString(2, passwordLabel.getText());

					ResultSet rs = pst.executeQuery();
					int count = 0;
					while (rs.next()) {

						count++;

					}
					if (count == 1) {
						managerFrame manFrame = new managerFrame();
					} else if (count == 2) {
						JOptionPane.showMessageDialog(null, "Duplicate Username and Password");
					} else {
						JOptionPane.showMessageDialog(null, "Username and/or Password is not correct!");
					}

					rs.close();
					pst.close();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
	}
}
