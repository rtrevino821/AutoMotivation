package appPackage;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.*;
import javax.swing.*;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class mainDashboard {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					mainDashboard window = new mainDashboard();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	Connection conn = null;
	private JTable allProductsTable;
	private JTable enginePartsTable;
	private JTable alternatorsTable;
	private JTable filtersTable;
	private JTable tiresTable;
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
		frame.setSize(1440, 865);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setContentPane(new JLabel(new ImageIcon("src/mainDash.jpg")));
		frame.setVisible(true);
		
		int gap = 10;
		
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
		
		JPanel checkoutTab = new JPanel();
		mainTabbedPane.addTab("Checkout", checkIcon, checkoutTab, null);
		checkoutTab.setLayout(null);
		
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
		
//		JPanel panel_4 = new JPanel();
//		tabbedPane.addTab("New tab", null, panel_4, null);
//		panel_4.setLayout(null);
	}
}
