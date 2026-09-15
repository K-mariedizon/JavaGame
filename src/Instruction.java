import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Instruction {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void Instruct() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Instruction window = new Instruction();
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
	public Instruction() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 974, 582);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 960, 545);
		lblNewLabel.setIcon(new ImageIcon(Instruction.class.getResource("/bgImages/InsCont.png")));
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnBack = new JButton("");
		btnBack.setBackground(new Color(19, 26, 44));
		btnBack.setIcon(new ImageIcon(Instruction.class.getResource("/btnImages/BACK.jpg")));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu c = new Menu();
				c.main(null);
				
				frame.dispose();
			}
		});
		btnBack.setBounds(57, 477, 175, 43);
		frame.getContentPane().add(btnBack);
		
	
	



}
	}
