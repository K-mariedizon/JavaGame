import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class About {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void about() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					About window = new About();
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
	public About() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 970, 573);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 960, 540);
		lblNewLabel.setIcon(new ImageIcon(About.class.getResource("/bgImages/abtCont.png")));
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnBack = new JButton("");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu c = new Menu();
				c.main(null);
				
				frame.dispose();
			}
		});
		btnBack.setIcon(new ImageIcon(About.class.getResource("/btnImages/BACK.jpg")));
		btnBack.setBackground(new Color(19, 26, 44));
		btnBack.setBounds(57, 474, 169, 40);
		frame.getContentPane().add(btnBack);
	}
}
