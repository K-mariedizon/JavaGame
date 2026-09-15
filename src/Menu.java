import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;

import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class Menu {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
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
	public Menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 970, 580);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 960, 545);
		lblNewLabel.setIcon(new ImageIcon(Menu.class.getResource("/bgImages/bg.png")));
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnStart = new JButton("");
		btnStart.setIcon(new ImageIcon(Menu.class.getResource("/btnImages/start.jpg")));
		btnStart.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 14));
		btnStart.setBackground(new Color(19, 26, 44));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Start s = new Start();
				s.start();
				frame.dispose();
			}
		});
		btnStart.setBounds(398, 172, 165, 40);
		frame.getContentPane().add(btnStart);
		
		JButton btnInstruction = new JButton("");
		btnInstruction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Instruction Ins = new Instruction();
				Ins.Instruct();
				
				frame.dispose();
			}
		});
		btnInstruction.setIcon(new ImageIcon(Menu.class.getResource("/btnImages/ins.jpg")));
		btnInstruction.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 14));
		btnInstruction.setBackground(new Color(19, 26, 44));
		btnInstruction.setBounds(379, 246, 204, 47);
		frame.getContentPane().add(btnInstruction);
		
		JButton btnHS = new JButton("");
		btnHS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HighScore hs = new HighScore();
				hs.HS();
				
				frame.dispose();
				
			}
		});
		btnHS.setIcon(new ImageIcon(Menu.class.getResource("/btnImages/hg.jpg")));
		btnHS.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 14));
		btnHS.setBackground(new Color(19, 26, 44));
		btnHS.setBounds(379, 325, 204, 47);
		frame.getContentPane().add(btnHS);
		
		JButton btnAbout = new JButton("");
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				About a = new About();
				a.about();
				
				frame.dispose();
			}
		});
		btnAbout.setIcon(new ImageIcon(Menu.class.getResource("/btnImages/about.jpg")));
		btnAbout.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 14));
		btnAbout.setBackground(new Color(19, 26, 44));
		btnAbout.setBounds(398, 399, 165, 47);
		frame.getContentPane().add(btnAbout);
		
		JButton btnExit = new JButton("");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 System.exit(0);
			}
		});
		btnExit.setIcon(new ImageIcon(Menu.class.getResource("/btnImages/exit.jpg")));
		btnExit.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 14));
		btnExit.setBackground(new Color(19, 26, 44));
		btnExit.setBounds(398, 474, 165, 47);
		frame.getContentPane().add(btnExit);
			
	}
	
	
}
