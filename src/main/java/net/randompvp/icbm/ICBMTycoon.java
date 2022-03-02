package net.randompvp.icbm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ICBMTycoon implements ActionListener {
	
	public static ICBMTycoon inst;
	int windowHeight = 576;
	int windowWidth = 1024;
	JFrame frame;
	JPanel panel;
	Page screen;
	
	public ICBMTycoon() {
		inst = this;
		frame = new JFrame();
		panel = new JPanel();
		panel.setLayout(null);
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setMinimumSize(new Dimension(windowWidth, windowHeight));
		frame.setTitle("Ed's ICBM Tycoon");
        frame.pack();
        changeScreen(new Welcome());
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("startGame")) {
			
		} else if (e.getActionCommand().equals("exitGame")) {
			System.exit(0);
		}
	}
	
	void changeScreen(Page newScreen) {
		if (screen != null)
			screen.remove();
		screen = newScreen;
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new ICBMTycoon();
	}
	
	abstract class Page {
		abstract public void remove();
	}
	
	class Welcome extends Page {
		JButton startButton = new JButton("Start!");
		JButton exitButton = new JButton("Exit");
		JLabel welcome = new JLabel("Welcome to Ed's ICBM Tycoon! Press the Start button to continue");
		JLabel author = new JLabel("Developed by Ramiro");
		JLabel author2 = new JLabel("Concept by Ed");
		JLabel icbmImage = initializeImage();
		
		public Welcome() {
			int buttonWidth = 140;
			int buttonHeight = 50;
			panel.add(icbmImage);
			icbmImage.setBounds(0, 0, 1024, 576);
			icbmImage.setOpaque(true);
			icbmImage.add(startButton);
			startButton.setFont(new Font("Arial", Font.PLAIN, 34));
			startButton.setBounds(332, 238, buttonWidth, buttonHeight);
			startButton.addActionListener(ICBMTycoon.inst);
			startButton.setActionCommand("startGame");
			icbmImage.add(exitButton);
			exitButton.setFont(new Font("Arial", Font.PLAIN, 34));
			exitButton.setBounds(552, 238, buttonWidth, buttonHeight);
			exitButton.addActionListener(ICBMTycoon.inst);
			exitButton.setActionCommand("exitGame");
			icbmImage.add(welcome);
			welcome.setFont(new Font("Arial", Font.PLAIN, 28));
			welcome.setBounds(102, 50, 820, 32);
			welcome.setForeground(Color.WHITE);
			icbmImage.add(author);
			author.setBounds(452, 85, 120, 14);
			author.setForeground(Color.WHITE);
			icbmImage.add(author2);
			author2.setBounds(472, 102, 80, 14);
			author2.setForeground(Color.WHITE);
			
		}
		
		JLabel initializeImage() {
			try {
				return new JLabel(new ImageIcon(ImageIO.read(new File(getClass().getResource("/icbm.jpg").toURI()))));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public void remove() {
			panel.remove(icbmImage);
		}
	}

}
