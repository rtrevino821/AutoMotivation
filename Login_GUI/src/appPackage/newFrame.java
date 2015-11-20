package appPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class newFrame extends JFrame {

	private JButton logOut;

	JLabel welcome = new JLabel("Welcome to a New Frame");

	newFrame() {
		super("Welcome");
		setResizable(false);
		setSize(1440, 810);
		setLocationRelativeTo(null);
		setContentPane(new JLabel(new ImageIcon("src/mainDash.jpg")));

		welcome.setBounds(520, 405, 400, 60);
		welcome.setHorizontalAlignment(SwingConstants.CENTER);
		welcome.setFont(new Font("Tahoma", Font.BOLD, 28));

		logOut = new JButton("Log Out");
		logOut.setBounds(new Rectangle(508, 576, 442, 65));

		add(welcome);
		add(logOut);
		// getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

}
