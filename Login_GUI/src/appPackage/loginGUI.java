package appPackage;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;

public class loginGUI extends JFrame {

	private final JTextField userNameLabel;
	private final JTextField clicker;
	private final JPasswordField passwordLabel;
	private JButton blogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginGUI frame = new loginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection connection = null;
	/**
	 * Create the frame.
	 */
	public loginGUI() {
		setTitle("Login");
		setResizable(false);
		setSize(1440,810);
		setLocationRelativeTo(null); //center JFrame on screen
		setUndecorated(false); //removes frame around window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
        
		//background image
		setContentPane(new JLabel(new ImageIcon("src/Login-Screen-BG.jpg")));
	    getContentPane().setLayout(null);
	    
	 // Icon Image
	    ImageIcon icon = new ImageIcon("src/appIconImage.png");
	    setIconImage(icon.getImage());

	    clicker = new JTextField("");
	    add(clicker);
	    
		userNameLabel = new JTextField("User Name");
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
	    userNameLabel.setBounds(new Rectangle(508, 376, 442, 65));
	    
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
	    passwordLabel.setBounds(new Rectangle(508, 476, 442, 65));
	    
	    blogin = new JButton("Login");
	    blogin.setFont(new Font("Tahoma", Font.BOLD, 18));
	    blogin.setBounds(new Rectangle(508, 576, 442, 65));
	    
	    add(userNameLabel);
	    add(passwordLabel);
	    add(blogin);

	    actionlogin();
	    connection = sqliteConnection.dbConnector();
	}

//	public void actionlogin() {
//		blogin.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent ae) {
//				String puname = userNameLabel.getText();
//				@SuppressWarnings("deprecation")
//				String ppaswd = passwordLabel.getText();
//				if (puname.equals("test") && ppaswd.equals("12345")) {
//					newFrame regFace = new newFrame();
//					regFace.setVisible(true);
//					dispose();
//				} else {
//
//					JOptionPane.showMessageDialog(null, "Wrong Password / Username");
//					userNameLabel.setText("");
//					passwordLabel.setText("");
//					userNameLabel.requestFocus();
//				}
//
//			}
//		});
//	}
	
	public void actionlogin() {
		blogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try{
					String query="select * from UsernameInfo where username = ? and password = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, userNameLabel.getText() );
					pst.setString(2, passwordLabel.getText() );
					
					ResultSet rs = pst.executeQuery();
					int count = 0;
					while (rs.next()){
						
						count++;
						
					}
					if (count == 1){
						mainDashboard regFace = new mainDashboard();
						//regFace.setVisible(true);
						dispose();
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
