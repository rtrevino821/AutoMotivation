//package appPackage;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.EventQueue;
//import java.awt.Font;
//import java.awt.Point;
//import java.awt.Rectangle;
//import java.net.URISyntaxException;
//import java.sql.*;
//import java.text.MessageFormat;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//
//import net.proteanit.sql.DbUtils;
//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseMotionAdapter;
//import java.awt.print.PrinterException;
//
//public class managerFrame extends JPanel {
//
//	private JFrame frame;
//	private Point initialClick;
//	private JTable allProductsTable;
//	private JTable outOfStockTable;
//	private JTable scheduleTable;
//	private JTextField partNum_txt;
//	private JTextField name_txt;
//	private JTextField description_txt;
//	private JTextField price_txt;
//	private JTextField warranty_txt;
//	private JTextField inStock_txt;
//	private JTextField category_txt;
//	private JTextField category1_txt;
//	private JButton logout;
//
//	private JLabel empNameLb;
//
//	private JLabel partNumLb, nameLb, descriptionLb, priceLb, warrantyLb, inStockLb, categoryLb, categoryLb1;
//
//	PreparedStatement pst = null;
//	ResultSet rs = null;
//	Connection connection = null;
//
//	/*
//	 * private JTable allProductsTable; private JTable enginePartsTable; private
//	 * JTable alternatorsTable; private JTable filtersTable; private JTable
//	 * tiresTable;
//	 */
//
//	/**
//	 * Create the application.
//	 */
//
//	public managerFrame() throws URISyntaxException {
//		connection = sqliteConnection.dbConnector();
//		initialize();
//		Update_table();
//		Update_table_Schedule();
//	}
//
//	/**
//	 * Initialize the contents of the frame.
//	 */
//	private void initialize() {
//
//		frame = new JFrame();
//		frame.setSize(1440, 810);
//		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setUndecorated(true);
//		frame.getContentPane().setLayout(null);
//		frame.setContentPane(new JLabel(new ImageIcon("src/mainDashBG.jpg")));
//		frame.setVisible(true);
//		frame.setLocationRelativeTo(null);
//
//		frame.addMouseListener(new MouseAdapter() {
//			public void mousePressed(MouseEvent e) {
//				initialClick = e.getPoint();
//				getComponentAt(initialClick);
//			}
//		});
//
//		frame.addMouseMotionListener(new MouseMotionAdapter() {
//			public void mouseDragged(MouseEvent e) {
//
//				// get location of Window
//				int thisX = frame.getLocation().x;
//				int thisY = frame.getLocation().y;
//
//				// Determine how much the mouse moved since the initial click
//				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
//				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);
//
//				// Move window to this position
//				int X = thisX + xMoved;
//				int Y = thisY + yMoved;
//				frame.setLocation(X, Y);
//			}
//		});
//
//		ImageIcon img = new ImageIcon("src/appIconImage.png");
//		frame.setIconImage(img.getImage());
//
//		ImageIcon homeIcon = new ImageIcon("src/house.png");
//		ImageIcon checkIcon = new ImageIcon("src/check.png");
//		ImageIcon inventoryIcon = new ImageIcon("src/inventory.png");
//		ImageIcon managerIcon = new ImageIcon("src/manager.png");
//
//		logout = new JButton("Log Out");
//		// logout.setEnabled(true);
//		// logout.setBackground(Color.red);
//
//		logout.setForeground(Color.WHITE);
//		logout.setBackground(Color.BLACK);
//
//		// logout.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//		// logout.setOpaque(true);
//		// frame.add(logout, BorderLayout.NORTH);
//		// logout.setHorizontalAlignment(SwingConstants.LEFT);
//
//		logout.setFont(new Font("Tahoma", Font.BOLD, 26));
//		logout.setBounds(new Rectangle(1219, 187, 193, 55));
//
//		// logout.setText("Log Out");
//		/*
//		 * if(logout.isEnabled()); System.out.println("Hello world");
//		 */
//		logout.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				frame.dispose();
//			}
//		});
//
//		frame.add(logout);
//
//		JTabbedPane mainTabbedPane = new JTabbedPane(JTabbedPane.TOP);
//		mainTabbedPane.setBounds(25, 206, 1388, 526);
//
//		mainTabbedPane.setFont(new Font("Tahoma", Font.BOLD, 16));
//		frame.getContentPane().add(mainTabbedPane);
//
//		// JPanel mainDashTab = new JPanel();
//		// mainTabbedPane.addTab("View Sales", homeIcon, mainDashTab,
//		// "Go to Home Screen");
//		// mainDashTab.setLayout(null);
//
//		////////////////////////// Add Employee//////////////////////
//
//		JPanel addEmp = new JPanel();
//		mainTabbedPane.addTab("Add Employee", checkIcon, addEmp, null);
//		addEmp.setLayout(null);
//
//		JTabbedPane addEmpSubPanel = new JTabbedPane(JTabbedPane.TOP);
//		addEmpSubPanel.setBounds(230, 20, 1150, 430);
//
//		addEmpSubPanel.setFont(new Font("Tahoma", Font.BOLD, 16));
//		addEmp.add(addEmpSubPanel);
//
//		JPanel addEmpSubTab = new JPanel();
//		addEmpSubPanel.addTab("Staff", null, addEmpSubTab, null);
//		addEmpSubTab.setLayout(null);
//
//		JScrollPane scrollPanel_Emp = new JScrollPane();
//		scrollPanel_Emp.setBounds(0, 0, 1200, 396);
//		addEmpSubTab.add(scrollPanel_Emp);
//
//		JTable empTable = new JTable();
//		empTable.setFont(new Font("Tahoma", Font.BOLD, 16));
//		empTable.setRowHeight(empTable.getRowHeight() + 20);
//		scrollPanel_Emp.setViewportView(empTable);
//
//		JButton loadEmployees = new JButton("Load Employees");
//		loadEmployees.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				try {
//					String queryAllProducts = "select * from UsernameInfo";
//					PreparedStatement pstAllProducts = connection.prepareStatement(queryAllProducts);
//					ResultSet rsAllProducts = pstAllProducts.executeQuery();
//
//					empTable.setModel(DbUtils.resultSetToTableModel(rsAllProducts));
//
//					rsAllProducts.close();
//					pstAllProducts.close();
//
//				} catch (Exception e) {
//					JOptionPane.showMessageDialog(null, e);
//				}
//			}
//		});
//		loadEmployees.setFont(new Font("Tahoma", Font.BOLD, 16));
//		loadEmployees.setBounds(320, 1, 194, 38);
//		addEmp.add(loadEmployees);
//
//		JButton loadManagers = new JButton("Load Managers");
//		loadManagers.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				try {
//					String queryAllProducts = "select * from Managers";
//					PreparedStatement pstAllProducts = connection.prepareStatement(queryAllProducts);
//					ResultSet rsAllProducts = pstAllProducts.executeQuery();
//
//					empTable.setModel(DbUtils.resultSetToTableModel(rsAllProducts));
//
//					rsAllProducts.close();
//					pstAllProducts.close();
//
//				} catch (Exception e) {
//					JOptionPane.showMessageDialog(null, e);
//				}
//			}
//		});
//		loadManagers.setFont(new Font("Tahoma", Font.BOLD, 16));
//		loadManagers.setBounds(540, 1, 194, 38);
//		addEmp.add(loadManagers);
//
//		JLabel empNumber = new JLabel("Employee Number");
//		empNumber.setBounds(new Rectangle(10, 10, 200, 25));
//		empNumber.setFont(new Font("Tahoma", Font.BOLD, 16));
//		addEmp.add(empNumber);
//
//		JTextField empNumField = new JTextField();
//		empNumField.setFont(new Font("Tahoma", Font.BOLD, 16));
//		empNumField.setColumns(25);
//		empNumField.setBounds(10, 40, 200, 25);
//		addEmp.add(empNumField);
//
//		JLabel firstName = new JLabel("First Name");
//		firstName.setBounds(new Rectangle(10, 70, 200, 25));
//		firstName.setFont(new Font("Tahoma", Font.BOLD, 16));
//		addEmp.add(firstName);
//
//		JTextField firstNameField = new JTextField();
//		firstNameField.setFont(new Font("Tahoma", Font.BOLD, 16));
//		firstNameField.setColumns(25);
//		firstNameField.setBounds(10, 100, 200, 25);
//		addEmp.add(firstNameField);
//
//		JLabel lastName = new JLabel("Last Name");
//		lastName.setBounds(new Rectangle(10, 130, 200, 25));
//		lastName.setFont(new Font("Tahoma", Font.BOLD, 16));
//		addEmp.add(lastName);
//
//		JTextField lastNameField = new JTextField();
//		lastNameField.setFont(new Font("Tahoma", Font.BOLD, 16));
//		lastNameField.setColumns(25);
//		lastNameField.setBounds(10, 160, 200, 25);
//		addEmp.add(lastNameField);
//
//		JLabel userName = new JLabel("User Name");
//		userName.setBounds(new Rectangle(10, 185, 200, 25));
//		userName.setFont(new Font("Tahoma", Font.BOLD, 16));
//		addEmp.add(userName);
//
//		JTextField usernameField = new JTextField();
//		usernameField.setFont(new Font("Tahoma", Font.BOLD, 16));
//		usernameField.setColumns(25);
//		usernameField.setBounds(10, 210, 200, 25);
//		addEmp.add(usernameField);
//
//		JLabel password = new JLabel("Password");
//		password.setBounds(new Rectangle(10, 240, 200, 25));
//		password.setFont(new Font("Tahoma", Font.BOLD, 16));
//		addEmp.add(password);
//
//		JTextField passwordField = new JTextField();
//		passwordField.setFont(new Font("Tahoma", Font.BOLD, 16));
//		passwordField.setColumns(25);
//		passwordField.setBounds(10, 270, 200, 25);
//		addEmp.add(passwordField);
//
//		JLabel age = new JLabel("Age");
//		age.setBounds(new Rectangle(10, 300, 200, 25));
//		age.setFont(new Font("Tahoma", Font.BOLD, 16));
//		addEmp.add(age);
//
//		JTextField ageField = new JTextField();
//		ageField.setFont(new Font("Tahoma", Font.BOLD, 16));
//		ageField.setColumns(25);
//		ageField.setBounds(10, 330, 200, 25);
//		addEmp.add(ageField);
//
//		JLabel gender = new JLabel("Gender");
//		gender.setBounds(new Rectangle(10, 360, 200, 25));
//		gender.setFont(new Font("Tahoma", Font.BOLD, 16));
//		addEmp.add(gender);
//
//		JTextField genderField = new JTextField();
//		genderField.setFont(new Font("Tahoma", Font.BOLD, 16));
//		genderField.setColumns(25);
//		genderField.setBounds(10, 390, 200, 25);
//		addEmp.add(genderField);
//
//		JButton managerSave = new JButton("Save person as a manager");
//		managerSave.setBounds(new Rectangle(10, 420, 200, 25));
//		managerSave.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				try {
//					String query = "insert into Managers (rowid,FirstName,LastName,Username,Password,Age,Gender) values (?,?,?,?,?,?,?)";
//					PreparedStatement pst = connection.prepareStatement(query);
//					// pst.setString(1, textFieldEID.getText());
//					pst.setString(1, empNumField.getText());
//					pst.setString(2, firstNameField.getText());
//					pst.setString(3, lastNameField.getText());
//					pst.setString(4, usernameField.getText());
//					pst.setString(5, passwordField.getText());
//					pst.setString(6, ageField.getText());
//					pst.setString(7, genderField.getText());
//
//					pst.execute();
//					JOptionPane.showMessageDialog(null, "Data Saved");
//					pst.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			}
//		});
//		addEmp.add(managerSave);
//
//		JButton saveButton = new JButton("Save person as an employee");
//		saveButton.setBounds(new Rectangle(10, 450, 200, 25));
//
//		saveButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				try {
//					String query = "insert into UsernameInfo (rowid,FirstName,LastName,Username,Password,Age,Gender) values (?,?,?,?,?,?,?)";
//					PreparedStatement pst = connection.prepareStatement(query);
//					// pst.setString(1, textFieldEID.getText());
//					pst.setString(1, empNumField.getText());
//					pst.setString(2, firstNameField.getText());
//					pst.setString(3, lastNameField.getText());
//					pst.setString(4, usernameField.getText());
//					pst.setString(5, passwordField.getText());
//					pst.setString(6, ageField.getText());
//					pst.setString(7, genderField.getText());
//
//					pst.execute();
//					JOptionPane.showMessageDialog(null, "Data Saved");
//					pst.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			}
//
//		});
//		addEmp.add(saveButton);
//		////////////////////////// INVENTORY TAB
//		////////////////////////// //////////////////////////////////////
//
//		JPanel inventoryTab = new JPanel();
//		mainTabbedPane.addTab("Edit Inventory", inventoryIcon, inventoryTab, null);
//		inventoryTab.setLayout(null);
//
//		///// TextField and Labels/////
//		partNum_txt = new JTextField("");
//		partNumLb = new JLabel("Name (Integer):");
//		partNumLb.setFont(new Font("Tahoma", Font.BOLD, 16));
//		partNum_txt.setFont(new Font("Tahoma", Font.BOLD, 16));
//		partNum_txt.setBounds(new Rectangle(1180, 71, 190, 30));
//		partNumLb.setBounds(new Rectangle(1000, 71, 190, 27));
//		inventoryTab.add(partNum_txt);
//		inventoryTab.add(partNumLb);
//
//		name_txt = new JTextField("");
//		nameLb = new JLabel("Name (Text):");
//		nameLb.setFont(new Font("Tahoma", Font.BOLD, 16));
//		name_txt.setFont(new Font("Tahoma", Font.BOLD, 16));
//		name_txt.setBounds(new Rectangle(1180, 118, 190, 30));
//		nameLb.setBounds(new Rectangle(1000, 118, 190, 27));
//		inventoryTab.add(name_txt);
//		inventoryTab.add(nameLb);
//
//		description_txt = new JTextField("");
//		descriptionLb = new JLabel("Description (Text):");
//		descriptionLb.setFont(new Font("Tahoma", Font.BOLD, 16));
//		description_txt.setFont(new Font("Tahoma", Font.BOLD, 16));
//		description_txt.setBounds(new Rectangle(1180, 165, 190, 30));
//		descriptionLb.setBounds(new Rectangle(1000, 165, 190, 27));
//		inventoryTab.add(description_txt);
//		inventoryTab.add(descriptionLb);
//
//		price_txt = new JTextField("");
//		priceLb = new JLabel("Price (Double):");
//		priceLb.setFont(new Font("Tahoma", Font.BOLD, 16));
//		price_txt.setFont(new Font("Tahoma", Font.BOLD, 16));
//		price_txt.setBounds(new Rectangle(1180, 212, 190, 30));
//		priceLb.setBounds(new Rectangle(1000, 212, 190, 27));
//		inventoryTab.add(price_txt);
//		inventoryTab.add(priceLb);
//
//		warranty_txt = new JTextField("");
//		warrantyLb = new JLabel("Warranty (Text):");
//		warrantyLb.setFont(new Font("Tahoma", Font.BOLD, 16));
//		warranty_txt.setFont(new Font("Tahoma", Font.BOLD, 16));
//		warranty_txt.setBounds(new Rectangle(1180, 259, 190, 30));
//		warrantyLb.setBounds(new Rectangle(1000, 259, 190, 27));
//		inventoryTab.add(warranty_txt);
//		inventoryTab.add(warrantyLb);
//
//		inStock_txt = new JTextField("");
//		inStockLb = new JLabel("How many in stock:");
//		inStockLb.setFont(new Font("Tahoma", Font.BOLD, 16));
//		inStock_txt.setFont(new Font("Tahoma", Font.BOLD, 14));
//		inStock_txt.setBounds(new Rectangle(1180, 306, 190, 30));
//		inStockLb.setBounds(new Rectangle(1000, 306, 190, 27));
//		inventoryTab.add(inStock_txt);
//		inventoryTab.add(inStockLb);
//
//		category_txt = new JTextField("");
//		categoryLb = new JLabel("Category (Text):");
//		categoryLb.setFont(new Font("Tahoma", Font.BOLD, 16));
//		category_txt.setFont(new Font("Tahoma", Font.BOLD, 16));
//		category_txt.setBounds(new Rectangle(1180, 353, 190, 30));
//		categoryLb.setBounds(new Rectangle(1000, 353, 190, 27));
//		inventoryTab.add(category_txt);
//		inventoryTab.add(categoryLb);
//
//		JTabbedPane inventorySubPane = new JTabbedPane(JTabbedPane.TOP);
//		inventorySubPane.setBounds(15, 46, 960, 430);
//		inventorySubPane.setFont(new Font("Tahoma", Font.BOLD, 16));
//		inventoryTab.add(inventorySubPane);
//
//		JPanel allProductsTab = new JPanel();
//		inventorySubPane.addTab("All Products", null, allProductsTab, null);
//		allProductsTab.setLayout(null);
//
//		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.setBounds(0, 0, 960, 396);
//		allProductsTab.add(scrollPane);
//
//		allProductsTable = new JTable();
//		// allProductsTable.getTableHeader().setFont(new Font("Tahoma",
//		// Font.BOLD, 18));
//		allProductsTable.setFont(new Font("Tahoma", Font.BOLD, 16));
//		allProductsTable.setRowHeight(allProductsTable.getRowHeight() + 20);
//		// scrollPane.add(allProductsTable);
//
//		scrollPane.setViewportView(allProductsTable);
//
//		// Object[] columns = {"Product Name","Price"};
//		// DefaultTableModel model = new DefaultTableModel();
//		// model.setColumnIdentifiers(columns);
//
//		///////////////////////////// scrollPane.setViewportView(allProductsTable);
//
//		allProductsTable.addMouseListener(new MouseAdapter() {
//
//			// Program that when the mouse clicks a spot of the table autofills
//			// the textboxes
//			@Override
//			public void mouseClicked(java.awt.event.MouseEvent evt) {
//				try {
//					int row = allProductsTable.getSelectedRow();
//					partNum_txt.setText(allProductsTable.getModel().getValueAt(row, 0).toString());
//					name_txt.setText(allProductsTable.getModel().getValueAt(row, 1).toString());
//					description_txt.setText(allProductsTable.getModel().getValueAt(row, 2).toString());
//					price_txt.setText(allProductsTable.getModel().getValueAt(row, 3).toString());
//					warranty_txt.setText(allProductsTable.getModel().getValueAt(row, 4).toString());
//					inStock_txt.setText(allProductsTable.getModel().getValueAt(row, 5).toString());
//					category_txt.setText(allProductsTable.getModel().getValueAt(row, 6).toString());
//				} catch (Exception e) {
//					JOptionPane.showMessageDialog(null, e);
//				}
//			}
//		});
//		// scrollPane.setViewportView(allProductsTable);
//
//		// Button to Add Items in the form of a row
//		JButton btnAddToInventory = new JButton("Add");
//		btnAddToInventory.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				try {
//					String sql = "Insert into InventoryList (PartNo,Name,Description,Price,Warranty,InStock,Category) values (?,?,?,?,?,?,?)";
//					pst = connection.prepareStatement(sql);
//					pst.setString(1, partNum_txt.getText());
//					pst.setString(2, name_txt.getText());
//					pst.setString(3, description_txt.getText());
//					pst.setString(4, price_txt.getText());
//					pst.setString(5, warranty_txt.getText());
//					pst.setString(6, inStock_txt.getText());
//					pst.setString(7, category_txt.getText());
//					pst.execute();
//					JOptionPane.showMessageDialog(null, "Added");
//				} catch (Exception e) {
//					JOptionPane.showMessageDialog(null, e);
//				}
//				Update_table();
//
//			}
//		});
//		btnAddToInventory.setFont(new Font("Tahoma", Font.BOLD, 16));
//		btnAddToInventory.setBounds(230, 1, 194, 38);
//		inventoryTab.add(btnAddToInventory);
//
//		// Button to remove an entire item by removing the row
//		JButton btnRemoveInventory = new JButton("Remove");
//		btnRemoveInventory.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				try {
//					pst = connection.prepareStatement("DELETE FROM InventoryList WHERE PartNo = ?");
//					pst.setString(1, partNum_txt.getText());
//					pst.executeUpdate();
//					JOptionPane.showMessageDialog(null, "Removed");
//
//				} catch (Exception e) {
//					JOptionPane.showMessageDialog(null, e);
//				}
//				Update_table();
//			}
//		});
//		btnRemoveInventory.setFont(new Font("Tahoma", Font.BOLD, 16));
//		btnRemoveInventory.setBounds(445, 1, 194, 38);
//		inventoryTab.add(btnRemoveInventory);
//
//		// Button to update the Inventory and add it to the database
//		JButton btnUpdateInventory = new JButton("Update");
//		btnUpdateInventory.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				try {
//					String value1 = partNum_txt.getText();
//					String value2 = name_txt.getText();
//					String value3 = description_txt.getText();
//					String value4 = price_txt.getText();
//					String value5 = warranty_txt.getText();
//					String value6 = inStock_txt.getText();
//					String value7 = category_txt.getText();
//
//					String sql = "update InventoryList set PartNo ='" + value1 + "' , Name ='" + value2
//							+ "' , Description ='" + value3 + "' ,Price ='" + value4 + "' ,Warranty ='" + value5
//							+ "' ,InStock ='" + value6 + "' ,Category ='" + value7 + "' where PartNo ='" + value1 + "'";
//					pst = connection.prepareStatement(sql);
//					pst.execute();
//					JOptionPane.showMessageDialog(null, "Updated");
//				} catch (Exception e) {
//					JOptionPane.showMessageDialog(null, e);
//				}
//				Update_table();
//			}
//		});
//		btnUpdateInventory.setFont(new Font("Tahoma", Font.BOLD, 16));
//		btnUpdateInventory.setBounds(660, 1, 194, 38);
//		inventoryTab.add(btnUpdateInventory);
//		/////////////////////////// End Of Inventory
//		/////////////////////////// Tab////////////////////////////////////
//		// Button to update the Inventory and add it to the database
//
//		// Button to print out of stock items
//		JButton btnPrint = new JButton("Print");
//		btnPrint.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				// checks the currently selected tab to check which tab to print
//				int selIndex = inventorySubPane.getSelectedIndex();
//				try {
//					if (selIndex == 0) {
//						MessageFormat footer = new MessageFormat("All Products");
//						allProductsTable.print(JTable.PrintMode.FIT_WIDTH, null, footer);
//					}
//
//					else if (selIndex == 1) {
//						MessageFormat footer = new MessageFormat("Out Of Stock");
//						outOfStockTable.print(JTable.PrintMode.FIT_WIDTH, null, footer);
//					}
//				} catch (PrinterException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//		});
//
//		btnPrint.setFont(new Font("Tahoma", Font.BOLD, 16));
//		btnPrint.setBounds(875, 1, 194, 38);
//		inventoryTab.add(btnPrint);
//
//		////////////////////////////// Out of Stock
//		////////////////////////////// Tab/////////////////////////////////////////
//		JPanel outOfStockTab = new JPanel();
//		inventorySubPane.addTab("Out Of Stock", null, outOfStockTab, null);
//		outOfStockTab.setLayout(null);
//
//		JScrollPane scrollPane_1 = new JScrollPane();
//		scrollPane_1.setBounds(0, 0, 960, 396);
//		outOfStockTab.add(scrollPane_1);
//
//		outOfStockTable = new JTable();
//		outOfStockTable.setFont(new Font("Tahoma", Font.BOLD, 16));
//		outOfStockTable.setRowHeight(outOfStockTable.getRowHeight() + 20);
//		scrollPane_1.setViewportView(outOfStockTable);
//
//		/////////////////////////// End Of Inventory
//		/////////////////////////// Tab////////////////////////////////////
//
//		// JPanel managerPanel = new JPanel();
//		// mainTabbedPane.addTab("Schedule", inventoryIcon, managerPanel, "");
//		// managerPanel.setLayout(null);
//		//
//		// JTabbedPane scheduleSubP = new JTabbedPane(JTabbedPane.TOP);
//		// scheduleSubP.setBounds(15, 46, 1353, 430);
//		// scheduleSubP.setFont(new Font("Tahoma", Font.BOLD, 16));
//		// managerPanel.add(scheduleSubP);
//		//
//		// JPanel allProductsTab_1 = new JPanel();
//		// scheduleSubP.addTab("Weekly", null, allProductsTab, null);
//		// allProductsTab.setLayout(null);
//		//
//		// JScrollPane scrollPane_2 = new JScrollPane();
//		// scrollPane.setBounds(0, 0, 1348, 396);
//		// allProductsTab.add(scrollPane_2);
//		//
//		// scheduleTable = new JTable();
//		// scheduleTable.setFont(new Font("Tahoma", Font.BOLD, 16));
//		// scheduleTable.setRowHeight(scheduleTable.getRowHeight() + 10);
//		// scrollPane.setViewportView(scheduleTable);
//
//		// ***************** SCHEDULE *************************
//
//		// Font lblFont = new Font("Tahoma", Font.BOLD, 18);
//
//		JPanel scheduleTab = new JPanel();
//		mainTabbedPane.addTab("Schedule", inventoryIcon, scheduleTab, null);
//		scheduleTab.setLayout(null);
//
//		///// TextField and Labels/////
//		JTextField empName;
//		empName = new JTextField("");
//		empNameLb = new JLabel("Employee Name:");
//		empNameLb.setFont(new Font("Tahoma", Font.BOLD, 16));
//		empName.setFont(new Font("Tahoma", Font.BOLD, 16));
//		empName.setBounds(new Rectangle(1180, 71, 190, 30));
//		empNameLb.setBounds(new Rectangle(1000, 71, 190, 27));
//		scheduleTab.add(empName);
//		scheduleTab.add(empNameLb);
//
//		JTextField mon;
//		mon = new JTextField("");
//		JLabel monLb = new JLabel("Monday:");
//		monLb.setFont(new Font("Tahoma", Font.BOLD, 16));
//		mon.setFont(new Font("Tahoma", Font.BOLD, 16));
//		mon.setBounds(new Rectangle(1180, 118, 190, 30));
//		monLb.setBounds(new Rectangle(1000, 118, 190, 27));
//		scheduleTab.add(mon);
//		scheduleTab.add(monLb);
//
//		JTextField tue;
//		tue = new JTextField("");
//		JLabel tueLb = new JLabel("Tuesday:");
//		tueLb.setFont(new Font("Tahoma", Font.BOLD, 16));
//		tue.setFont(new Font("Tahoma", Font.BOLD, 16));
//		tue.setBounds(new Rectangle(1180, 165, 190, 30));
//		tueLb.setBounds(new Rectangle(1000, 165, 190, 27));
//		scheduleTab.add(tue);
//		scheduleTab.add(tueLb);
//
//		JTextField wed;
//		wed = new JTextField("");
//		JLabel wedLb = new JLabel("Wednesday:");
//		wedLb.setFont(new Font("Tahoma", Font.BOLD, 16));
//		wed.setFont(new Font("Tahoma", Font.BOLD, 16));
//		wed.setBounds(new Rectangle(1180, 212, 190, 30));
//		wedLb.setBounds(new Rectangle(1000, 212, 190, 27));
//		scheduleTab.add(wed);
//		scheduleTab.add(wedLb);
//
//		JTextField thu;
//		thu = new JTextField("");
//		JLabel thuLb = new JLabel("Thursday:");
//		thuLb.setFont(new Font("Tahoma", Font.BOLD, 16));
//		thu.setFont(new Font("Tahoma", Font.BOLD, 16));
//		thu.setBounds(new Rectangle(1180, 259, 190, 30));
//		thuLb.setBounds(new Rectangle(1000, 259, 190, 27));
//		scheduleTab.add(thu);
//		scheduleTab.add(thuLb);
//
//		JTextField fri;
//		fri = new JTextField("");
//		JLabel friLb = new JLabel("Friday:");
//		friLb.setFont(new Font("Tahoma", Font.BOLD, 16));
//		fri.setFont(new Font("Tahoma", Font.BOLD, 16));
//		fri.setBounds(new Rectangle(1180, 306, 190, 30));
//		friLb.setBounds(new Rectangle(1000, 306, 190, 27));
//		scheduleTab.add(fri);
//		scheduleTab.add(friLb);
//
//		JTextField sat;
//		sat = new JTextField("");
//		JLabel satLb = new JLabel("Saturday:");
//		satLb.setFont(new Font("Tahoma", Font.BOLD, 16));
//		sat.setFont(new Font("Tahoma", Font.BOLD, 16));
//		sat.setBounds(new Rectangle(1180, 353, 190, 30));
//		satLb.setBounds(new Rectangle(1000, 353, 190, 27));
//		scheduleTab.add(sat);
//		scheduleTab.add(satLb);
//
//		JTextField sun;
//		sun = new JTextField("");
//		JLabel sunLb = new JLabel("Sunday:");
//		sunLb.setFont(new Font("Tahoma", Font.BOLD, 16));
//		sun.setFont(new Font("Tahoma", Font.BOLD, 16));
//		sun.setBounds(new Rectangle(1180, 400, 190, 30));
//		sunLb.setBounds(new Rectangle(1000, 400, 190, 27));
//		scheduleTab.add(sun);
//		scheduleTab.add(sunLb);
//
//		JTabbedPane scheduleSubPane = new JTabbedPane(JTabbedPane.TOP);
//		scheduleSubPane.setBounds(15, 46, 940, 430);
//		scheduleSubPane.setFont(new Font("Tahoma", Font.BOLD, 16));
//		scheduleTab.add(scheduleSubPane);
//
//		JPanel weeklyTab = new JPanel();
//		scheduleSubPane.addTab("Weekly", null, weeklyTab, null);
//		weeklyTab.setLayout(null);
//
//		JScrollPane scrollPane_Schedule = new JScrollPane();
//		scrollPane_Schedule.setBounds(0, 0, 940, 396);
//		weeklyTab.add(scrollPane_Schedule);
//
//		// JTable ScheduleTable;
//		scheduleTable = new JTable();
//		scheduleTable.setFont(new Font("Tahoma", Font.BOLD, 16));
//		scheduleTable.setRowHeight(scheduleTable.getRowHeight() + 20);
//		scrollPane_Schedule.setViewportView(scheduleTable);
//
//		scheduleTable.addMouseListener(new MouseAdapter() {
//
//			// Program that when the mouse clicks a spot of the table autofills
//			// the textboxes
//			@Override
//			public void mouseClicked(java.awt.event.MouseEvent evt) {
//				try {
//					int row = scheduleTable.getSelectedRow();
//					empName.setText(scheduleTable.getModel().getValueAt(row, 0).toString());
//					mon.setText(scheduleTable.getModel().getValueAt(row, 1).toString());
//					tue.setText(scheduleTable.getModel().getValueAt(row, 2).toString());
//					wed.setText(scheduleTable.getModel().getValueAt(row, 3).toString());
//					thu.setText(scheduleTable.getModel().getValueAt(row, 4).toString());
//					fri.setText(scheduleTable.getModel().getValueAt(row, 5).toString());
//					sat.setText(scheduleTable.getModel().getValueAt(row, 6).toString());
//					sun.setText(scheduleTable.getModel().getValueAt(row, 7).toString());
//				} catch (Exception e) {
//					JOptionPane.showMessageDialog(null, e);
//				}
//			}
//		});
//
//		DefaultTableModel modelSchedule = new DefaultTableModel();
//		scheduleTable.setModel(modelSchedule);
//
//		// create an array of objects to set the row data
//		Object[] row = new Object[8];
//
//		// Button to Add Items in the form of a row
//		JButton btnAddSchedule = new JButton("Add");
//		btnAddSchedule.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//
//				row[0] = empName.getText();
//				row[1] = mon.getText();
//				row[2] = tue.getText();
//				row[3] = wed.getText();
//				row[4] = thu.getText();
//				row[5] = fri.getText();
//				row[6] = sat.getText();
//				row[7] = sun.getText();
//
//				// add row to the model
//				modelSchedule.addRow(row);
//
//				try {
//					String sql_1 = "Insert into Schedule (EmployeeName,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday) values (?,?,?,?,?,?,?,?)";
//
//					pst = connection.prepareStatement(sql_1);
//					pst.setString(1, empName.getText());
//					pst.setString(2, mon.getText());
//					pst.setString(3, tue.getText());
//					pst.setString(4, wed.getText());
//					pst.setString(5, thu.getText());
//					pst.setString(6, fri.getText());
//					pst.setString(7, sat.getText());
//					pst.setString(8, sun.getText());
//					pst.execute();
//					JOptionPane.showMessageDialog(null, "Added");
//				} catch (Exception e) {
//					JOptionPane.showMessageDialog(null, e);
//				}
//				Update_table_Schedule();
//
//			}
//		});
//		btnAddSchedule.setFont(new Font("Tahoma", Font.BOLD, 16));
//		btnAddSchedule.setBounds(230, 1, 194, 38);
//		scheduleTab.add(btnAddSchedule);
//
//		// Button to remove an entire item by removing the row
//		JButton btnRemoveSchedule = new JButton("Remove");
//		btnRemoveSchedule.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				try {
//					pst = connection.prepareStatement("DELETE FROM Schedule WHERE EmployeeName = ?");
//					pst.setString(1, empName.getText());
//					pst.executeUpdate();
//					JOptionPane.showMessageDialog(null, "Removed");
//
//				} catch (Exception e) {
//					JOptionPane.showMessageDialog(null, e);
//				}
//				Update_table_Schedule();
//			}
//		});
//		btnRemoveSchedule.setFont(new Font("Tahoma", Font.BOLD, 16));
//		btnRemoveSchedule.setBounds(445, 1, 194, 38);
//		scheduleTab.add(btnRemoveSchedule);
//
//		// Button to update the Schedule and add it to the database
//		JButton btnUpdateSchedule = new JButton("Update");
//		btnUpdateSchedule.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				try {
//					String value1 = empName.getText();
//					String value2 = mon.getText();
//					String value3 = tue.getText();
//					String value4 = wed.getText();
//					String value5 = thu.getText();
//					String value6 = fri.getText();
//					String value7 = sat.getText();
//					String value8 = sun.getText();
//
//					String sql = "Update Schedule set EmployeeName='" + value1 + "' , Monday ='" + value2
//							+ "' , Tuesday ='" + value3 + "' ,Wednesday ='" + value4 + "' ,Thursday ='" + value5
//							+ "' ,Friday ='" + value6 + "' ,Saturday ='" + value7 + "',Sunday ='" + value8
//							+ "' where EmployeeName='" + value1 + "'";
//					pst = connection.prepareStatement(sql);
//					pst.execute();
//					JOptionPane.showMessageDialog(null, "Updated");
//				} catch (Exception e) {
//					JOptionPane.showMessageDialog(null, e);
//				}
//				Update_table_Schedule();
//			}
//		});
//		btnUpdateSchedule.setFont(new Font("Tahoma", Font.BOLD, 16));
//		btnUpdateSchedule.setBounds(660, 1, 194, 38);
//		scheduleTab.add(btnUpdateSchedule);
//		/////////////////////////// End Of Schedule
//		/////////////////////////// Tab////////////////////////////////////
//
//		// Button to update the Inventory and add it to the database
//
//		// Button to print out of stock items
//		JButton btnPrint_12 = new JButton("Print");
//		btnPrint.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				// checks the currently selected tab to check which tab to print
//				int selIndex = scheduleSubPane.getSelectedIndex();
//				try {
//					if (selIndex == 0) {
//						MessageFormat footer = new MessageFormat("All Products");
//						allProductsTable.print(JTable.PrintMode.FIT_WIDTH, null, footer);
//					}
//
//					else if (selIndex == 1) {
//						MessageFormat footer = new MessageFormat("Out Of Stock");
//						outOfStockTable.print(JTable.PrintMode.FIT_WIDTH, null, footer);
//					}
//				} catch (PrinterException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//		});
//
//		btnPrint_12.setFont(new Font("Tahoma", Font.BOLD, 16));
//		btnPrint_12.setBounds(875, 1, 194, 38);
//		scheduleTab.add(btnPrint_12);
//	}
//
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					managerFrame frame = new managerFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//
//	}
//
//	private void Update_table_Schedule() {
//
//		try {
//			String querySchedule = "select * from Schedule";
//			PreparedStatement pstSchedule = connection.prepareStatement(querySchedule);
//			ResultSet rsSchedule = pstSchedule.executeQuery();
//
//			scheduleTable.setModel(DbUtils.resultSetToTableModel(rsSchedule));
//			rsSchedule.close();
//			pstSchedule.close();
//
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, e);
//		}
//
//	}
//
//	// Method that automatically updates the table with new items
//	private void Update_table() {
//
//		try {
//			String queryAllProducts = "select * from InventoryList";
//			PreparedStatement pstAllProducts = connection.prepareStatement(queryAllProducts);
//			ResultSet rsAllProducts = pstAllProducts.executeQuery();
//
//			allProductsTable.setModel(DbUtils.resultSetToTableModel(rsAllProducts));
//			rsAllProducts.close();
//			pstAllProducts.close();
//
//			// Updates the outOfStockTab;e
//			String queryInStock = "select * from InventoryList where InStock = ?";
//			PreparedStatement pstInStock = connection.prepareStatement(queryInStock);
//			pstInStock.setString(1, "0");
//			ResultSet rsInStock = pstInStock.executeQuery();
//
//			outOfStockTable.setModel(DbUtils.resultSetToTableModel(rsInStock));
//
//			rsInStock.close();
//			pstInStock.close();
//
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, e);
//		}
//
//	}
//}

package appPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.net.URISyntaxException;
import java.sql.*;
import java.text.MessageFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.print.PrinterException;

public class managerFrame extends JPanel {
  // Declaration of the JFrame, Point, JTables, JTextFields, JButtons, and
  // JLabels used in the ManagerFrame
  private JFrame frame;
  private Point initialClick;
  private JTable allProductsTable;
  private JTable outOfStockTable;
  private JTable scheduleTable;
  private JTextField partNum_txt;
  private JTextField name_txt;
  private JTextField description_txt;
  private JTextField price_txt;
  private JTextField warranty_txt;
  private JTextField inStock_txt;
  private JTextField category_txt;
  private JTextField category1_txt;
  private JButton logout;

  private JLabel empNameLb;

  private JLabel partNumLb, nameLb, descriptionLb, priceLb, warrantyLb,
      inStockLb, categoryLb, categoryLb1;
  // Connects to the SQLite Database
  PreparedStatement pst = null;
  ResultSet rs = null;
  Connection connection = null;

  /**
   * Create the application.
   */

  public managerFrame() throws URISyntaxException {
    connection = sqliteConnection.dbConnector();
    initialize();
    Update_table();
    Update_table_Schedule();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    // creates the new JFrame
    frame = new JFrame();
    // Sets the size of the JFrame
    frame.setSize(1440, 810);
    // Sets it to be undecorated
    frame.setUndecorated(true);
    // Sets the layout manager for this container to false
    frame.getContentPane().setLayout(null);
    // Sets the Icon image
    frame.setContentPane(new JLabel(new ImageIcon("src/mainDashBG.jpg")));
    // shows the JFrame
    frame.setVisible(true);
    // Centers in screen
    frame.setLocationRelativeTo(null);
    // Creates a mouse listener to get the initial click
    frame.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        initialClick = e.getPoint();
        getComponentAt(initialClick);
      }
    });
    // Allows for the window to be moved in the screen
    frame.addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {

        // get location of Window
        int thisX = frame.getLocation().x;
        int thisY = frame.getLocation().y;

        // Determine how much the mouse moved since the initial click
        int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
        int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

        // Move window to this position
        int X = thisX + xMoved;
        int Y = thisY + yMoved;
        frame.setLocation(X, Y);
      }
    });

    ImageIcon img = new ImageIcon("src/appIconImage.png");
    // Changes the Icon of the Frame
    frame.setIconImage(img.getImage());

    ImageIcon checkIcon = new ImageIcon("src/check.png");
    ImageIcon inventoryIcon = new ImageIcon("src/inventory.png");
    // Allows the user to log out without closeing the whoel program
    logout = new JButton("Log Out");
    // Sets the colors of the logout button
    logout.setForeground(Color.WHITE);
    logout.setBackground(Color.BLACK);
    // sets the font of the logout button
    logout.setFont(new Font("Tahoma", Font.BOLD, 26));
    // sets the position of the logout button
    logout.setBounds(new Rectangle(1219, 187, 193, 55));
    // adds an ActionListener to the logout button
    logout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
      }
    });
    // adds the button to the JFrame
    frame.add(logout);
    // Creates a new JTabbedPane
    JTabbedPane mainTabbedPane = new JTabbedPane(JTabbedPane.TOP);
    // sets the postion of the tabbed Pane
    mainTabbedPane.setBounds(25, 206, 1388, 526);
    // sets the font
    mainTabbedPane.setFont(new Font("Tahoma", Font.BOLD, 16));
    frame.getContentPane().add(mainTabbedPane);

    // ////////////////////////Add Employee//////////////////////
    // Creates a JPanel for Add employee
    JPanel addEmp = new JPanel();
    mainTabbedPane.addTab("Add Employee", checkIcon, addEmp, null);
    addEmp.setLayout(null);
    // creates a sub tabbed pane for the Add employee panel
    JTabbedPane addEmpSubPanel = new JTabbedPane(JTabbedPane.TOP);
    addEmpSubPanel.setBounds(230, 20, 1150, 430);
    // sets the font
    addEmpSubPanel.setFont(new Font("Tahoma", Font.BOLD, 16));
    // adds the sub panel to the panel
    addEmp.add(addEmpSubPanel);
    // Creates a Panel to show the Staff
    JPanel addEmpSubTab = new JPanel();
    addEmpSubPanel.addTab("Staff", null, addEmpSubTab, null);
    addEmpSubTab.setLayout(null);
    // Creates a ScrollPane to load the table into
    JScrollPane scrollPanel_Emp = new JScrollPane();
    scrollPanel_Emp.setBounds(0, 0, 1348, 396);
    addEmpSubTab.add(scrollPanel_Emp);
    // Creates the Table to store the Employees
    JTable empTable = new JTable();
    empTable.setFont(new Font("Tahoma", Font.BOLD, 16));
    empTable.setRowHeight(empTable.getRowHeight() + 20);
    scrollPanel_Emp.setViewportView(empTable);
    // Creates the Load Employee Button
    JButton loadEmployees = new JButton("Load Employees");
    // Adds an ActionListener to the Button
    loadEmployees.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        try {
          // When the button is pressed the employee table will be loaded into
          // the table created above
          String queryAllProducts = "select * from UsernameInfo";
          PreparedStatement pstAllProducts = connection
              .prepareStatement(queryAllProducts);
          ResultSet rsAllProducts = pstAllProducts.executeQuery();

          empTable.setModel(DbUtils.resultSetToTableModel(rsAllProducts));

          rsAllProducts.close();
          pstAllProducts.close();

        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);
        }
      }
    });
    // Sets the fonts and Position to the load employee button
    loadEmployees.setFont(new Font("Tahoma", Font.BOLD, 16));
    loadEmployees.setBounds(320, 1, 194, 38);
    addEmp.add(loadEmployees);
    // creates the load managers button
    JButton loadManagers = new JButton("Load Managers");
    // adds an actionlistener to the manager button
    loadManagers.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        try {
          // when the manager button is pressed the Managers table will be
          // loaded to the screen
          String queryAllProducts = "select * from Managers";
          PreparedStatement pstAllProducts = connection
              .prepareStatement(queryAllProducts);
          ResultSet rsAllProducts = pstAllProducts.executeQuery();

          empTable.setModel(DbUtils.resultSetToTableModel(rsAllProducts));

          rsAllProducts.close();
          pstAllProducts.close();

        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);
        }
      }
    });
    // sets the font and position for the load managers button
    loadManagers.setFont(new Font("Tahoma", Font.BOLD, 16));
    loadManagers.setBounds(540, 1, 194, 38);
    addEmp.add(loadManagers);
    // Creates a Label to tell what value to store in the following TextField
    JLabel empNumber = new JLabel("Employee Number");
    empNumber.setBounds(new Rectangle(10, 10, 200, 25));
    empNumber.setFont(new Font("Tahoma", Font.BOLD, 16));
    addEmp.add(empNumber);
    // Creates the TextField for the information that will be added
    JTextField empNumField = new JTextField();
    empNumField.setFont(new Font("Tahoma", Font.BOLD, 16));
    empNumField.setColumns(25);
    empNumField.setBounds(10, 40, 200, 25);
    addEmp.add(empNumField);
    // Creates a Label to tell what value to store in the following TextField
    JLabel firstName = new JLabel("First Name");
    firstName.setBounds(new Rectangle(10, 70, 200, 25));
    firstName.setFont(new Font("Tahoma", Font.BOLD, 16));
    addEmp.add(firstName);
    // Creates the TextField for the information that will be added
    JTextField firstNameField = new JTextField();
    firstNameField.setFont(new Font("Tahoma", Font.BOLD, 16));
    firstNameField.setColumns(25);
    firstNameField.setBounds(10, 100, 200, 25);
    addEmp.add(firstNameField);
    // Creates a Label to tell what value to store in the following TextField
    JLabel lastName = new JLabel("Last Name");
    lastName.setBounds(new Rectangle(10, 130, 200, 25));
    lastName.setFont(new Font("Tahoma", Font.BOLD, 16));
    addEmp.add(lastName);
    // Creates the TextField for the information that will be added
    JTextField lastNameField = new JTextField();
    lastNameField.setFont(new Font("Tahoma", Font.BOLD, 16));
    lastNameField.setColumns(25);
    lastNameField.setBounds(10, 160, 200, 25);
    addEmp.add(lastNameField);
    // Creates a Label to tell what value to store in the following TextField
    JLabel userName = new JLabel("User Name");
    userName.setBounds(new Rectangle(10, 185, 200, 25));
    userName.setFont(new Font("Tahoma", Font.BOLD, 16));
    addEmp.add(userName);
    // Creates the TextField for the information that will be added
    JTextField usernameField = new JTextField();
    usernameField.setFont(new Font("Tahoma", Font.BOLD, 16));
    usernameField.setColumns(25);
    usernameField.setBounds(10, 210, 200, 25);
    addEmp.add(usernameField);
    // Creates a Label to tell what value to store in the following TextField
    JLabel password = new JLabel("Password");
    password.setBounds(new Rectangle(10, 240, 200, 25));
    password.setFont(new Font("Tahoma", Font.BOLD, 16));
    addEmp.add(password);
    // Creates the TextField for the information that will be added
    JTextField passwordField = new JTextField();
    passwordField.setFont(new Font("Tahoma", Font.BOLD, 16));
    passwordField.setColumns(25);
    passwordField.setBounds(10, 270, 200, 25);
    addEmp.add(passwordField);
    // Creates a Label to tell what value to store in the following TextField
    JLabel age = new JLabel("Age");
    age.setBounds(new Rectangle(10, 300, 200, 25));
    age.setFont(new Font("Tahoma", Font.BOLD, 16));
    addEmp.add(age);
    // Creates the TextField for the information that will be added
    JTextField ageField = new JTextField();
    ageField.setFont(new Font("Tahoma", Font.BOLD, 16));
    ageField.setColumns(25);
    ageField.setBounds(10, 330, 200, 25);
    addEmp.add(ageField);
    // Creates a Label to tell what value to store in the following TextField
    JLabel gender = new JLabel("Gender");
    gender.setBounds(new Rectangle(10, 360, 200, 25));
    gender.setFont(new Font("Tahoma", Font.BOLD, 16));
    addEmp.add(gender);
    // Creates the TextField for the information that will be added
    JTextField genderField = new JTextField();
    genderField.setFont(new Font("Tahoma", Font.BOLD, 16));
    genderField.setColumns(25);
    genderField.setBounds(10, 390, 200, 25);
    addEmp.add(genderField);
    // This button is used to save the entered information into the Managers
    // Table
    JButton managerSave = new JButton("Save person as a manager");
    managerSave.setBounds(new Rectangle(10, 420, 200, 25));
    // Creates action listener for the button
    managerSave.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        try {
          // When the button is pressed the Information in the Textfields will
          // be stored to the Managers Table in the database
          String query = "insert into Managers (rowid,FirstName,LastName,Username,Password,Age,Gender) values (?,?,?,?,?,?,?)";
          PreparedStatement pst = connection.prepareStatement(query);
          pst.setString(1, empNumField.getText());
          pst.setString(2, firstNameField.getText());
          pst.setString(3, lastNameField.getText());
          pst.setString(4, usernameField.getText());
          pst.setString(5, passwordField.getText());
          pst.setString(6, ageField.getText());
          pst.setString(7, genderField.getText());

          pst.execute();
          JOptionPane.showMessageDialog(null, "Data Saved");
          pst.close();
        } catch (Exception e) {
          e.printStackTrace();
        }

      }
    });
    // Adds the manager save button to the add employee tab
    addEmp.add(managerSave);
    // This button is used to save the entered information into the UsernameInfo
    // Table
    JButton saveButton = new JButton("Save person as an employee");
    saveButton.setBounds(new Rectangle(10, 450, 200, 25));

    saveButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        try {
          // When the button is pressed the Information in the TextFields will
          // be stored to the UsernameInfo Table in the database
          String query = "insert into UsernameInfo (rowid,FirstName,LastName,Username,Password,Age,Gender) values (?,?,?,?,?,?,?)";
          PreparedStatement pst = connection.prepareStatement(query);

          pst.setString(1, empNumField.getText());
          pst.setString(2, firstNameField.getText());
          pst.setString(3, lastNameField.getText());
          pst.setString(4, usernameField.getText());
          pst.setString(5, passwordField.getText());
          pst.setString(6, ageField.getText());
          pst.setString(7, genderField.getText());

          pst.execute();
          JOptionPane.showMessageDialog(null, "Data Saved");
          pst.close();
        } catch (Exception e) {
          e.printStackTrace();
        }

      }

    });
    // adds the button to the add employee tab
    addEmp.add(saveButton);
    // //////////////////////// INVENTORY TAB
    // //////////////////////////////////////
    // Creates the panel the inventory tab will be under
    JPanel inventoryTab = new JPanel();
    mainTabbedPane.addTab("Edit Inventory", inventoryIcon, inventoryTab, null);
    inventoryTab.setLayout(null);

    // /// TextField and Labels/////
    partNum_txt = new JTextField("");
    partNumLb = new JLabel("Name (Integer):");
    partNumLb.setFont(new Font("Tahoma", Font.BOLD, 16));
    partNum_txt.setFont(new Font("Tahoma", Font.BOLD, 16));
    partNum_txt.setBounds(new Rectangle(1180, 71, 190, 30));
    partNumLb.setBounds(new Rectangle(1000, 71, 190, 27));
    inventoryTab.add(partNum_txt);
    inventoryTab.add(partNumLb);

    name_txt = new JTextField("");
    nameLb = new JLabel("Name (Text):");
    nameLb.setFont(new Font("Tahoma", Font.BOLD, 16));
    name_txt.setFont(new Font("Tahoma", Font.BOLD, 16));
    name_txt.setBounds(new Rectangle(1180, 118, 190, 30));
    nameLb.setBounds(new Rectangle(1000, 118, 190, 27));
    inventoryTab.add(name_txt);
    inventoryTab.add(nameLb);

    description_txt = new JTextField("");
    descriptionLb = new JLabel("Description (Text):");
    descriptionLb.setFont(new Font("Tahoma", Font.BOLD, 16));
    description_txt.setFont(new Font("Tahoma", Font.BOLD, 16));
    description_txt.setBounds(new Rectangle(1180, 165, 190, 30));
    descriptionLb.setBounds(new Rectangle(1000, 165, 190, 27));
    inventoryTab.add(description_txt);
    inventoryTab.add(descriptionLb);

    price_txt = new JTextField("");
    priceLb = new JLabel("Price (Double):");
    priceLb.setFont(new Font("Tahoma", Font.BOLD, 16));
    price_txt.setFont(new Font("Tahoma", Font.BOLD, 16));
    price_txt.setBounds(new Rectangle(1180, 212, 190, 30));
    priceLb.setBounds(new Rectangle(1000, 212, 190, 27));
    inventoryTab.add(price_txt);
    inventoryTab.add(priceLb);

    warranty_txt = new JTextField("");
    warrantyLb = new JLabel("Warranty (Text):");
    warrantyLb.setFont(new Font("Tahoma", Font.BOLD, 16));
    warranty_txt.setFont(new Font("Tahoma", Font.BOLD, 16));
    warranty_txt.setBounds(new Rectangle(1180, 259, 190, 30));
    warrantyLb.setBounds(new Rectangle(1000, 259, 190, 27));
    inventoryTab.add(warranty_txt);
    inventoryTab.add(warrantyLb);

    inStock_txt = new JTextField("");
    inStockLb = new JLabel("How many in stock:");
    inStockLb.setFont(new Font("Tahoma", Font.BOLD, 16));
    inStock_txt.setFont(new Font("Tahoma", Font.BOLD, 14));
    inStock_txt.setBounds(new Rectangle(1180, 306, 190, 30));
    inStockLb.setBounds(new Rectangle(1000, 306, 190, 27));
    inventoryTab.add(inStock_txt);
    inventoryTab.add(inStockLb);

    category_txt = new JTextField("");
    categoryLb = new JLabel("Category (Text):");
    categoryLb.setFont(new Font("Tahoma", Font.BOLD, 16));
    category_txt.setFont(new Font("Tahoma", Font.BOLD, 16));
    category_txt.setBounds(new Rectangle(1180, 353, 190, 30));
    categoryLb.setBounds(new Rectangle(1000, 353, 190, 27));
    inventoryTab.add(category_txt);
    inventoryTab.add(categoryLb);
    // This will create a sub pane that will be used to show the table of parts
    JTabbedPane inventorySubPane = new JTabbedPane(JTabbedPane.TOP);
    inventorySubPane.setBounds(15, 46, 960, 430);
    inventorySubPane.setFont(new Font("Tahoma", Font.BOLD, 16));
    inventoryTab.add(inventorySubPane);
    // This will be will all the products are shown
    JPanel allProductsTab = new JPanel();
    inventorySubPane.addTab("All Products", null, allProductsTab, null);
    allProductsTab.setLayout(null);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBounds(0, 0, 960, 396);
    allProductsTab.add(scrollPane);

    allProductsTable = new JTable();
    allProductsTable.setFont(new Font("Tahoma", Font.BOLD, 16));
    allProductsTable.setRowHeight(allProductsTable.getRowHeight() + 20);
    // The viewport will display the given data from the Table to the scrollPane
    scrollPane.setViewportView(allProductsTable);
    // Adds a MouseListener to the allproductstable
    allProductsTable.addMouseListener(new MouseAdapter() {

      // Program that when the mouse clicks a spot of the table autofills the
      // textboxes
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        try {
          int row = allProductsTable.getSelectedRow();
          partNum_txt.setText(allProductsTable.getModel().getValueAt(row, 0)
              .toString());
          name_txt.setText(allProductsTable.getModel().getValueAt(row, 1)
              .toString());
          description_txt.setText(allProductsTable.getModel()
              .getValueAt(row, 2).toString());
          price_txt.setText(allProductsTable.getModel().getValueAt(row, 3)
              .toString());
          warranty_txt.setText(allProductsTable.getModel().getValueAt(row, 4)
              .toString());
          inStock_txt.setText(allProductsTable.getModel().getValueAt(row, 5)
              .toString());
          category_txt.setText(allProductsTable.getModel().getValueAt(row, 6)
              .toString());
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);
        }
      }
    });
    // scrollPane.setViewportView(allProductsTable);

    // Button to Add Items in the form of a row
    JButton btnAddToInventory = new JButton("Add");
    btnAddToInventory.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        try {
          String sql = "Insert into InventoryList (PartNo,Name,Description,Price,Warranty,InStock,Category) values (?,?,?,?,?,?,?)";
          pst = connection.prepareStatement(sql);
          pst.setString(1, partNum_txt.getText());
          pst.setString(2, name_txt.getText());
          pst.setString(3, description_txt.getText());
          pst.setString(4, price_txt.getText());
          pst.setString(5, warranty_txt.getText());
          pst.setString(6, inStock_txt.getText());
          pst.setString(7, category_txt.getText());
          pst.execute();
          JOptionPane.showMessageDialog(null, "Added");
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);
        }
        Update_table();

      }
    });
    btnAddToInventory.setFont(new Font("Tahoma", Font.BOLD, 16));
    btnAddToInventory.setBounds(230, 1, 194, 38);
    inventoryTab.add(btnAddToInventory);

    // Button to remove an entire item by removing the row
    JButton btnRemoveInventory = new JButton("Remove");
    btnRemoveInventory.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        try {
          pst = connection
              .prepareStatement("DELETE FROM InventoryList WHERE PartNo = ?");
          pst.setString(1, partNum_txt.getText());
          pst.executeUpdate();
          JOptionPane.showMessageDialog(null, "Removed");

        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);
        }
        Update_table();
      }
    });
    btnRemoveInventory.setFont(new Font("Tahoma", Font.BOLD, 16));
    btnRemoveInventory.setBounds(445, 1, 194, 38);
    inventoryTab.add(btnRemoveInventory);

    // Button to update the Inventory and add it to the database
    JButton btnUpdateInventory = new JButton("Update");
    btnUpdateInventory.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        try {
          String value1 = partNum_txt.getText();
          String value2 = name_txt.getText();
          String value3 = description_txt.getText();
          String value4 = price_txt.getText();
          String value5 = warranty_txt.getText();
          String value6 = inStock_txt.getText();
          String value7 = category_txt.getText();

          String sql = "update InventoryList set PartNo ='" + value1
              + "' , Name ='" + value2 + "' , Description ='" + value3
              + "' ,Price ='" + value4 + "' ,Warranty ='" + value5
              + "' ,InStock ='" + value6 + "' ,Category ='" + value7
              + "' where PartNo ='" + value1 + "'";
          pst = connection.prepareStatement(sql);
          pst.execute();
          JOptionPane.showMessageDialog(null, "Updated");
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);
        }
        Update_table();
      }
    });
    btnUpdateInventory.setFont(new Font("Tahoma", Font.BOLD, 16));
    btnUpdateInventory.setBounds(660, 1, 194, 38);
    inventoryTab.add(btnUpdateInventory);
    // /////////////////////////End Of Inventory
    // Tab////////////////////////////////////
    // Button to update the Inventory and add it to the database

    // Button to print out of stock items
    JButton btnPrint = new JButton("Print");
    btnPrint.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // checks the currently selected tab to check which tab to print
        int selIndex = inventorySubPane.getSelectedIndex();
        try {
          if (selIndex == 0) {
            MessageFormat footer = new MessageFormat("All Products");
            allProductsTable.print(JTable.PrintMode.FIT_WIDTH, null, footer);
          }

          else if (selIndex == 1) {
            MessageFormat footer = new MessageFormat("Out Of Stock");
            outOfStockTable.print(JTable.PrintMode.FIT_WIDTH, null, footer);
          }
        } catch (PrinterException e1) {

          e1.printStackTrace();
        }
      }
    });

    btnPrint.setFont(new Font("Tahoma", Font.BOLD, 16));
    btnPrint.setBounds(875, 1, 194, 38);
    inventoryTab.add(btnPrint);

    // ////////////////////////////Out of Stock
    // Tab/////////////////////////////////////////
    JPanel outOfStockTab = new JPanel();
    inventorySubPane.addTab("Out Of Stock", null, outOfStockTab, null);
    outOfStockTab.setLayout(null);

    JScrollPane scrollPane_1 = new JScrollPane();
    scrollPane_1.setBounds(0, 0, 960, 396);
    outOfStockTab.add(scrollPane_1);

    outOfStockTable = new JTable();
    outOfStockTable.setFont(new Font("Tahoma", Font.BOLD, 16));
    outOfStockTable.setRowHeight(outOfStockTable.getRowHeight() + 20);
    scrollPane_1.setViewportView(outOfStockTable);

    // /////////////////////////End Of Inventory
    // Tab////////////////////////////////////

    // ***************** SCHEDULE *************************

    JPanel scheduleTab = new JPanel();
    mainTabbedPane.addTab("Schedule", inventoryIcon, scheduleTab, null);
    scheduleTab.setLayout(null);

    // /// TextField and Labels/////
    JTextField empName;
    empName = new JTextField("");
    empNameLb = new JLabel("Employee Name:");
    empNameLb.setFont(new Font("Tahoma", Font.BOLD, 16));
    empName.setFont(new Font("Tahoma", Font.BOLD, 16));
    empName.setBounds(new Rectangle(1180, 71, 190, 30));
    empNameLb.setBounds(new Rectangle(1000, 71, 190, 27));
    scheduleTab.add(empName);
    scheduleTab.add(empNameLb);

    JTextField mon;
    mon = new JTextField("");
    JLabel monLb = new JLabel("Monday:");
    monLb.setFont(new Font("Tahoma", Font.BOLD, 16));
    mon.setFont(new Font("Tahoma", Font.BOLD, 16));
    mon.setBounds(new Rectangle(1180, 118, 190, 30));
    monLb.setBounds(new Rectangle(1000, 118, 190, 27));
    scheduleTab.add(mon);
    scheduleTab.add(monLb);

    JTextField tue;
    tue = new JTextField("");
    JLabel tueLb = new JLabel("Tuesday:");
    tueLb.setFont(new Font("Tahoma", Font.BOLD, 16));
    tue.setFont(new Font("Tahoma", Font.BOLD, 16));
    tue.setBounds(new Rectangle(1180, 165, 190, 30));
    tueLb.setBounds(new Rectangle(1000, 165, 190, 27));
    scheduleTab.add(tue);
    scheduleTab.add(tueLb);

    JTextField wed;
    wed = new JTextField("");
    JLabel wedLb = new JLabel("Wednesday:");
    wedLb.setFont(new Font("Tahoma", Font.BOLD, 16));
    wed.setFont(new Font("Tahoma", Font.BOLD, 16));
    wed.setBounds(new Rectangle(1180, 212, 190, 30));
    wedLb.setBounds(new Rectangle(1000, 212, 190, 27));
    scheduleTab.add(wed);
    scheduleTab.add(wedLb);

    JTextField thu;
    thu = new JTextField("");
    JLabel thuLb = new JLabel("Thursday:");
    thuLb.setFont(new Font("Tahoma", Font.BOLD, 16));
    thu.setFont(new Font("Tahoma", Font.BOLD, 16));
    thu.setBounds(new Rectangle(1180, 259, 190, 30));
    thuLb.setBounds(new Rectangle(1000, 259, 190, 27));
    scheduleTab.add(thu);
    scheduleTab.add(thuLb);

    JTextField fri;
    fri = new JTextField("");
    JLabel friLb = new JLabel("Friday:");
    friLb.setFont(new Font("Tahoma", Font.BOLD, 16));
    fri.setFont(new Font("Tahoma", Font.BOLD, 16));
    fri.setBounds(new Rectangle(1180, 306, 190, 30));
    friLb.setBounds(new Rectangle(1000, 306, 190, 27));
    scheduleTab.add(fri);
    scheduleTab.add(friLb);

    JTextField sat;
    sat = new JTextField("");
    JLabel satLb = new JLabel("Saturday:");
    satLb.setFont(new Font("Tahoma", Font.BOLD, 16));
    sat.setFont(new Font("Tahoma", Font.BOLD, 16));
    sat.setBounds(new Rectangle(1180, 353, 190, 30));
    satLb.setBounds(new Rectangle(1000, 353, 190, 27));
    scheduleTab.add(sat);
    scheduleTab.add(satLb);

    JTextField sun;
    sun = new JTextField("");
    JLabel sunLb = new JLabel("Sunday:");
    sunLb.setFont(new Font("Tahoma", Font.BOLD, 16));
    sun.setFont(new Font("Tahoma", Font.BOLD, 16));
    sun.setBounds(new Rectangle(1180, 400, 190, 30));
    sunLb.setBounds(new Rectangle(1000, 400, 190, 27));
    scheduleTab.add(sun);
    scheduleTab.add(sunLb);

    JTabbedPane scheduleSubPane = new JTabbedPane(JTabbedPane.TOP);
    scheduleSubPane.setBounds(15, 46, 940, 430);
    scheduleSubPane.setFont(new Font("Tahoma", Font.BOLD, 16));
    scheduleTab.add(scheduleSubPane);

    JPanel weeklyTab = new JPanel();
    scheduleSubPane.addTab("Weekly", null, weeklyTab, null);
    weeklyTab.setLayout(null);

    JScrollPane scrollPane_Schedule = new JScrollPane();
    scrollPane_Schedule.setBounds(0, 0, 940, 396);
    weeklyTab.add(scrollPane_Schedule);

    // JTable ScheduleTable;
    scheduleTable = new JTable();
    scheduleTable.setFont(new Font("Tahoma", Font.BOLD, 16));
    scheduleTable.setRowHeight(scheduleTable.getRowHeight() + 20);
    scrollPane_Schedule.setViewportView(scheduleTable);

    scheduleTable.addMouseListener(new MouseAdapter() {

      // Program that when the mouse clicks a spot of the table autofills the
      // textboxes
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        try {
          int row = scheduleTable.getSelectedRow();
          empName.setText(scheduleTable.getModel().getValueAt(row, 0)
              .toString());
          mon.setText(scheduleTable.getModel().getValueAt(row, 1).toString());
          tue.setText(scheduleTable.getModel().getValueAt(row, 2).toString());
          wed.setText(scheduleTable.getModel().getValueAt(row, 3).toString());
          thu.setText(scheduleTable.getModel().getValueAt(row, 4).toString());
          fri.setText(scheduleTable.getModel().getValueAt(row, 5).toString());
          sat.setText(scheduleTable.getModel().getValueAt(row, 6).toString());
          sun.setText(scheduleTable.getModel().getValueAt(row, 7).toString());
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);
        }
      }
    });

    DefaultTableModel modelSchedule = new DefaultTableModel();
    scheduleTable.setModel(modelSchedule);

    // create an array of objects to set the row data
    Object[] row = new Object[8];

    // Button to Add Items in the form of a row
    JButton btnAddSchedule = new JButton("Add");
    btnAddSchedule.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {

        row[0] = empName.getText();
        row[1] = mon.getText();
        row[2] = tue.getText();
        row[3] = wed.getText();
        row[4] = thu.getText();
        row[5] = fri.getText();
        row[6] = sat.getText();
        row[7] = sun.getText();

        // add row to the model
        modelSchedule.addRow(row);

        try {
          String sql_1 = "Insert into Schedule (EmployeeName,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday) values (?,?,?,?,?,?,?,?)";

          pst = connection.prepareStatement(sql_1);
          pst.setString(1, empName.getText());
          pst.setString(2, mon.getText());
          pst.setString(3, tue.getText());
          pst.setString(4, wed.getText());
          pst.setString(5, thu.getText());
          pst.setString(6, fri.getText());
          pst.setString(7, sat.getText());
          pst.setString(8, sun.getText());
          pst.execute();
          JOptionPane.showMessageDialog(null, "Added");
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);
        }
        Update_table_Schedule();

      }
    });
    btnAddSchedule.setFont(new Font("Tahoma", Font.BOLD, 16));
    btnAddSchedule.setBounds(230, 1, 194, 38);
    scheduleTab.add(btnAddSchedule);

    // Button to remove an entire item by removing the row
    JButton btnRemoveSchedule = new JButton("Remove");
    btnRemoveSchedule.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        try {
          pst = connection
              .prepareStatement("DELETE FROM Schedule WHERE EmployeeName = ?");
          pst.setString(1, empName.getText());
          pst.executeUpdate();
          JOptionPane.showMessageDialog(null, "Removed");

        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);
        }
        Update_table_Schedule();
      }
    });
    btnRemoveSchedule.setFont(new Font("Tahoma", Font.BOLD, 16));
    btnRemoveSchedule.setBounds(445, 1, 194, 38);
    scheduleTab.add(btnRemoveSchedule);

    // Button to update the Schedule and add it to the database
    JButton btnUpdateSchedule = new JButton("Update");
    btnUpdateSchedule.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        try {
          String value1 = empName.getText();
          String value2 = mon.getText();
          String value3 = tue.getText();
          String value4 = wed.getText();
          String value5 = thu.getText();
          String value6 = fri.getText();
          String value7 = sat.getText();
          String value8 = sun.getText();

          String sql = "Update Schedule set EmployeeName='" + value1
              + "' , Monday ='" + value2 + "' , Tuesday ='" + value3
              + "' ,Wednesday ='" + value4 + "' ,Thursday ='" + value5
              + "' ,Friday ='" + value6 + "' ,Saturday ='" + value7
              + "',Sunday ='" + value8 + "' where EmployeeName='" + value1
              + "'";
          pst = connection.prepareStatement(sql);
          pst.execute();
          JOptionPane.showMessageDialog(null, "Updated");
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);
        }
        Update_table_Schedule();
      }
    });
    btnUpdateSchedule.setFont(new Font("Tahoma", Font.BOLD, 16));
    btnUpdateSchedule.setBounds(660, 1, 194, 38);
    scheduleTab.add(btnUpdateSchedule);
    // /////////////////////////End Of Schedule Tab////////////////////////////////////

    // Button to update the Inventory and add it to the database

    // Button to print out of stock items
    JButton btnPrint_12 = new JButton("Print");
    btnPrint.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // checks the currently selected tab to check which tab to print
        int selIndex = scheduleSubPane.getSelectedIndex();
        try {
          if (selIndex == 0) {
            MessageFormat footer = new MessageFormat("All Products");
            allProductsTable.print(JTable.PrintMode.FIT_WIDTH, null, footer);
          }

          else if (selIndex == 1) {
            MessageFormat footer = new MessageFormat("Out Of Stock");
            outOfStockTable.print(JTable.PrintMode.FIT_WIDTH, null, footer);
          }
        } catch (PrinterException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    });

    btnPrint_12.setFont(new Font("Tahoma", Font.BOLD, 16));
    btnPrint_12.setBounds(875, 1, 194, 38);
    scheduleTab.add(btnPrint_12);
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          managerFrame frame = new managerFrame();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

  }

  private void Update_table_Schedule() {
    // This method is pretty self explanetory it will update the table schedule
    try {
      String querySchedule = "select * from Schedule";
      PreparedStatement pstSchedule = connection
          .prepareStatement(querySchedule);
      ResultSet rsSchedule = pstSchedule.executeQuery();

      scheduleTable.setModel(DbUtils.resultSetToTableModel(rsSchedule));
      rsSchedule.close();
      pstSchedule.close();

    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }

  }

  // Method that automatically updates the table with new items
  private void Update_table() {

    try {
      String queryAllProducts = "select * from InventoryList";
      PreparedStatement pstAllProducts = connection
          .prepareStatement(queryAllProducts);
      ResultSet rsAllProducts = pstAllProducts.executeQuery();

      allProductsTable.setModel(DbUtils.resultSetToTableModel(rsAllProducts));
      rsAllProducts.close();
      pstAllProducts.close();

      // Updates the outOfStockTab;e
      String queryInStock = "select * from InventoryList where InStock = ?";
      PreparedStatement pstInStock = connection.prepareStatement(queryInStock);
      pstInStock.setString(1, "0");
      ResultSet rsInStock = pstInStock.executeQuery();

      outOfStockTable.setModel(DbUtils.resultSetToTableModel(rsInStock));

      rsInStock.close();
      pstInStock.close();

    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }

  }
}