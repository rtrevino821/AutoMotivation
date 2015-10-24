package appPackage;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;

public class mainDashboard {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainDashboard window = new mainDashboard();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainDashboard() {
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
		
		ImageIcon homeIcon = new ImageIcon("src/house.png");
		ImageIcon checkIcon = new ImageIcon("src/check.png");
		ImageIcon inventoryIcon = new ImageIcon("src/inventory.png");
		ImageIcon managerIcon = new ImageIcon("src/manager.png");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(15, 186, 1388, 526);
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 16));
		frame.getContentPane().add(tabbedPane);
		
		JPanel mainDashPanel = new JPanel();
		tabbedPane.addTab("Main Dashboard", homeIcon, mainDashPanel, "Go to Home Screen");
		mainDashPanel.setLayout(null);
		
		JPanel checkoutPanel = new JPanel();
		tabbedPane.addTab("Checkout", checkIcon, checkoutPanel, null);
		checkoutPanel.setLayout(null);
		
		JPanel inventoryPanel = new JPanel();
		tabbedPane.addTab("Inventory", inventoryIcon, inventoryPanel, null);
		inventoryPanel.setLayout(null);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(15, 46, 1353, 430);
		inventoryPanel.add(tabbedPane_1);
		
		JPanel panel = new JPanel();
		tabbedPane_1.addTab("New tab", null, panel, null);
		
		JPanel panel_1 = new JPanel();
		tabbedPane_1.addTab("New tab", null, panel_1, null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane_1.addTab("New tab", null, panel_2, null);
		
		JPanel panel_3 = new JPanel();
		tabbedPane_1.addTab("New tab", null, panel_3, null);
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.addTab("New tab", null, tabbedPane_2, null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(15, 16, 115, 29);
		inventoryPanel.add(btnNewButton);
		
		JPanel managerPanel = new JPanel();
		tabbedPane.addTab("Manager", managerIcon, managerPanel, null);
		managerPanel.setLayout(null);
		
//		JPanel panel_4 = new JPanel();
//		tabbedPane.addTab("New tab", null, panel_4, null);
//		panel_4.setLayout(null);
	}
}
