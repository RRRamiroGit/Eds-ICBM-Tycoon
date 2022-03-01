package net.randompvp.icbm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ICBMTycoon {
	
	int windowHeight = 576;
	int windowWidth = 1024;
	JPanel panel;
	Page screen;
	
	public ICBMTycoon() {
		JFrame frame = new JFrame();
		panel = new JPanel();
		panel.setLayout(null);
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setMinimumSize(new Dimension(windowWidth, windowHeight));
		frame.setTitle("Ed's ICBM Tycoon");
        frame.pack();
        frame.setVisible(true);
        changeScreen(new Welcome());
	}
	
	void changeScreen(Page newScreen) {
		if (screen != null)
			screen.remove();
		screen = newScreen;
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
		JLabel author = new JLabel("Made by Ramiro");
		
		public Welcome() {
			int buttonWidth = 140;
			int buttonHeight = 50;
			panel.add(startButton);
			startButton.setFont(new Font("Arial", Font.PLAIN, 34));
			startButton.setBounds(332, 238, buttonWidth, buttonHeight);
			panel.add(exitButton);
			exitButton.setFont(new Font("Arial", Font.PLAIN, 34));
			exitButton.setBounds(552, 238, buttonWidth, buttonHeight);
			panel.add(welcome);
			welcome.setFont(new Font("Arial", Font.PLAIN, 28));
			welcome.setBounds(102, 50, 820, 32);
			panel.add(author);
			author.setBounds(462, 85, 100, 14);
		}

		@Override
		public void remove() {
			panel.remove(startButton);
			panel.remove(exitButton);
			panel.remove(welcome);
			panel.remove(author);
		}
	}

}
