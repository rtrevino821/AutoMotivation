package appPackage;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class mainDashboard {

	private JFrame frame;
	private JTextField userNameLabel;
	private JPasswordField passwordLabel;

	Connection conn = null;
	private JTable allProductsTable;
	private JTable enginePartsTable;
	private JTable alternatorsTable;
	private JTable filtersTable;
	private JTable tiresTable;
	private JTable table;
	private JTable productListTable;
	/**
	 * Create the application.
	 */
	public mainDashboard() {
		conn = sqliteConnection.dbConnector();
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
		
		JPanel mainDashTab = new JPanel();
		mainTabbedPane.addTab("Main Dashboard", homeIcon, mainDashTab, "Go to Home Screen");
		mainDashTab.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setBounds(0, 0, 1383, 492);
		mainDashTab.add(label);
		label.setIcon(new ImageIcon("src/mainDashTabBGFinal.jpg"));
		
		JPanel checkoutTab = new JPanel();
		mainTabbedPane.addTab("Checkout", checkIcon, checkoutTab, null);
		checkoutTab.setLayout(null);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(776, 74, 592, 377);
		checkoutTab.add(scrollPane_5);
		
		table = new JTable();
		scrollPane_5.setViewportView(table);
		
		JLabel lblOrderSummary = new JLabel("Order Summary");
		lblOrderSummary.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblOrderSummary.setBounds(776, 16, 189, 39);
		checkoutTab.add(lblOrderSummary);
		
		JLabel lblProductList = new JLabel("Product List");
		lblProductList.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblProductList.setBounds(39, 16, 189, 39);
		checkoutTab.add(lblProductList);
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTotal.setBounds(39, 442, 69, 20);
		checkoutTab.add(lblTotal);
		
		JLabel lblTax = new JLabel("Tax:");
		lblTax.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTax.setBounds(39, 406, 69, 20);
		checkoutTab.add(lblTax);
		
		JLabel lblSubTotal = new JLabel("Sub Total:");
		lblSubTotal.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSubTotal.setBounds(39, 371, 102, 20);
		checkoutTab.add(lblSubTotal);
		
		JButton btnNewButton = new JButton("Print Order");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton.setBounds(1219, 16, 149, 29);
		checkoutTab.add(btnNewButton);
		
		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(39, 74, 540, 280);
		checkoutTab.add(scrollPane_6);
		
		productListTable = new JTable();
		productListTable.setFont(new Font("Tahoma", Font.BOLD, 16));
		productListTable.setRowHeight(productListTable.getRowHeight()+ 10);
		scrollPane_6.setViewportView(productListTable);
		
		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAddItem.setBounds(608, 167, 130, 29);
		checkoutTab.add(btnAddItem);
		
		JButton btnRemoveItem = new JButton("Remove Item");
		btnRemoveItem.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRemoveItem.setBounds(608, 228, 130, 29);
		checkoutTab.add(btnRemoveItem);
		
		JButton btnLoadProducts = new JButton("Load Products");
		btnLoadProducts.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnLoadProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String queryAllProducts = "select Name, price, inStock from InventoryList";
					PreparedStatement pstAllProducts = conn.prepareStatement(queryAllProducts);					
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
		
		JPanel inventoryTab = new JPanel();
		mainTabbedPane.addTab("Inventory", inventoryIcon, inventoryTab, null);
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
					PreparedStatement pstAllProducts = conn.prepareStatement(queryAllProducts);					
					ResultSet rsAllProducts = pstAllProducts.executeQuery();
					
					allProductsTable.setModel(DbUtils.resultSetToTableModel(rsAllProducts));
										
					rsAllProducts.close();
					pstAllProducts.close();
					
					String queryEngineParts = "select * from InventoryList where Category = ?";
					PreparedStatement pstEngineParts = conn.prepareStatement(queryEngineParts);
					pstEngineParts.setString(1, "Engine Parts" );
					ResultSet rsEngineParts = pstEngineParts.executeQuery();
					
					enginePartsTable.setModel(DbUtils.resultSetToTableModel(rsEngineParts));
										
					rsEngineParts.close();
					pstEngineParts.close();
					
					String queryAlternators = "select * from InventoryList where Category = ?";
					PreparedStatement pstAlternators = conn.prepareStatement(queryAlternators);
					pstAlternators.setString(1, "Alternators" );
					ResultSet rsAlternators = pstAlternators.executeQuery();
					
					alternatorsTable.setModel(DbUtils.resultSetToTableModel(rsAlternators));
										
					rsAlternators.close();
					pstAlternators.close();
					
					String queryFilters = "select * from InventoryList where Category = ?";
					PreparedStatement pstFilters = conn.prepareStatement(queryFilters);
					pstFilters.setString(1, "Filters" );
					ResultSet rsFilters = pstFilters.executeQuery();
					
					filtersTable.setModel(DbUtils.resultSetToTableModel(rsFilters));
										
					rsFilters.close();
					pstFilters.close();
					
					String queryTires = "select * from InventoryList where Category = ?";
					PreparedStatement pstTires = conn.prepareStatement(queryTires);
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
		
		JPanel managerPanel = new JPanel();
		mainTabbedPane.addTab("Manager", managerIcon, managerPanel, null);
		managerPanel.setLayout(null);
		
		userNameLabel = new JTextField("Username");
		userNameLabel.setOpaque(false);
		userNameLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userNameLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		userNameLabel.setBounds(new Rectangle(475, 93, 442, 65));
		managerPanel.add(userNameLabel);
		
		passwordLabel = new JPasswordField("Password");
	    passwordLabel.setOpaque(false);
	    passwordLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	    passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
	    passwordLabel.setBounds(new Rectangle(475, 174, 442, 65));
	    managerPanel.add(passwordLabel);
		
		JLabel lblNewLabel = new JLabel("Manager Login");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(618, 62, 142, 20);
		managerPanel.add(lblNewLabel);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("src/managerTabBG.jpg"));
		label_1.setBounds(0, 0, 1383, 492);
		managerPanel.add(label_1);
		
		
//		JPanel panel_4 = new JPanel();
//		tabbedPane.addTab("New tab", null, panel_4, null);
//		panel_4.setLayout(null);
	}
}
