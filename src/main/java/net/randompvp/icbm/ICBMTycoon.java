/*
 * Ramiro Salazar
 * Period 1 APCS
*/

package net.randompvp.icbm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
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
	
	public static ICBMTycoon inst; // Instance of the class
	
	// Window dimensions
	final int windowHeight = 576;
	final int windowWidth = 1024;
	
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
		frame.setResizable(false); // Prevent the window from being resized
		frame.setMinimumSize(new Dimension(windowWidth, windowHeight)); // Set the dimensions of the window
		frame.setTitle("Ed's ICBM Tycoon"); // Set the title of the window
		frame.setVisible(true);
        frame.pack();
        changeScreen(new Welcome()); // Make the welcome screen appear
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("startGame")) {
			changeScreen(new Begin1());
		} else if (e.getActionCommand().equals("exitGame")) {
			System.exit(0); // Exit the program
		} else if (e.getActionCommand().equals("backWelcome")) {
			changeScreen(new Welcome());
		}
	}
	
	void changeScreen(Page newScreen) {
		if (screen != null) // If there is a screen then remove it first
			screen.remove();
		screen = newScreen; // Set screen variable
		frame.repaint();
	}
	
	int cenElement(int width) {
		return (windowWidth - width) / 2;
	}
	
	public static void main(String[] args) {
		new ICBMTycoon();
	}
	
	abstract class Page { // Class to be extended to for each class page of the game
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
			icbmImage.setBounds(0, 0, windowWidth, windowHeight);
			icbmImage.setOpaque(true);
			
			icbmImage.add(startButton);
			startButton.setFont(new Font("Arial", Font.PLAIN, 34));
			startButton.setBounds(332, 238, buttonWidth, buttonHeight);
			startButton.addActionListener(inst);
			startButton.setActionCommand("startGame");
			
			icbmImage.add(exitButton);
			exitButton.setFont(new Font("Arial", Font.PLAIN, 34));
			exitButton.setBounds(552, 238, buttonWidth, buttonHeight);
			exitButton.addActionListener(inst);
			exitButton.setActionCommand("exitGame");
			
			icbmImage.add(welcome);
			welcome.setFont(new Font("Arial", Font.PLAIN, 28));
			welcome.setBounds(cenElement(820), 50, 820, 32);
			welcome.setForeground(Color.WHITE);
			
			icbmImage.add(author);
			author.setBounds(cenElement(120), 85, 120, 14);
			author.setForeground(Color.WHITE);
			
			icbmImage.add(author2);
			author2.setBounds(cenElement(80), 102, 80, 14);
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
	
	class Begin1 extends Page {
		JLabel text1 = new JLabel("You have been ordered by a Russian government official named");
		JLabel text2 = new JLabel("Ed HHahdhcuud to send ICBM�s to the United States as while the");
		JLabel text3 = new JLabel("United States were transporting a nuclear warhead closer to");
		JLabel text4 = new JLabel("Russian borders, they dropped the warhead by accident, causing");
		JLabel text5 = new JLabel("hundreds of thousands of causalities.");
		JButton continueNext = new JButton("Continue");
		JButton backWelcome = new JButton("<");
		
		public Begin1() {
			panel.add(text1);
			text1.setFont(new Font("Arial", Font.PLAIN, 34));
			text1.setBounds(cenElement(940), 58, 940, 36);
			
			panel.add(text2);
			text2.setFont(new Font("Arial", Font.PLAIN, 34));
			text2.setBounds(cenElement(970), 90, 970, 36);
			
			panel.add(text3);
			text3.setFont(new Font("Arial", Font.PLAIN, 34));
			text3.setBounds(cenElement(880), 122, 880, 36);
			
			panel.add(text4);
			text4.setFont(new Font("Arial", Font.PLAIN, 34));
			text4.setBounds(cenElement(950), 154, 950, 36);
			
			panel.add(text5);
			text5.setFont(new Font("Arial", Font.PLAIN, 34));
			text5.setBounds(cenElement(550), 186, 550, 36);
			
			panel.add(continueNext);
			continueNext.setFont(new Font("Arial", Font.PLAIN, 38));
			continueNext.setBounds(cenElement(220), 320, 220, 46);
			continueNext.addActionListener(inst);
			continueNext.setActionCommand("beginContinue1");
			
			panel.add(backWelcome);
			backWelcome.setFont(new Font("Arial", Font.PLAIN, 38));
			backWelcome.setBounds(10, 10, 42, 42);
			backWelcome.addActionListener(inst);
			backWelcome.setActionCommand("backWelcome");
			backWelcome.setMargin(new Insets(0, 0, 0, 0));
		}
		
		@Override
		public void remove() {
			panel.remove(text1);
			panel.remove(text2);
			panel.remove(text3);
			panel.remove(text4);
			panel.remove(text5);
			panel.remove(continueNext);
			panel.remove(backWelcome);
		}
	}
	
}
