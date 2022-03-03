/*
 * Ramiro Salazar
 * Period 1 APCS
*/

package net.randompvp.icbm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ICBMTycoon implements ActionListener {
	
	public static ICBMTycoon inst; // Instance of the class
	
	// Window dimensions
	final int windowHeight = 576;
	final int windowWidth = 1024;
	
	JFrame frame;
	JPanel panel;
	Page screen;
	String name;
	
	public ICBMTycoon() {
		inst = this;
		frame = new JFrame();
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.GRAY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false); // Prevent the window from being resized
		panel.setPreferredSize(new Dimension(windowWidth, windowHeight)); // Set the dimensions of the window
		frame.getContentPane().add(panel);
		frame.setTitle("Ed's ICBM Tycoon"); // Set the title of the window
		frame.setVisible(true);
        frame.pack();
        changeScreen(new Welcome()); // Make the welcome screen appear
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String com = e.getActionCommand();
		if (com.equals("startGame")) {
			changeScreen(new Begin1());
		} else if (com.equals("exitGame")) {
			System.exit(0); // Exit the program
		} else if (com.equals("credit")) {
			changeScreen(new Credits());
		} else if (com.equals("backWelcome")) {
			changeScreen(new Welcome());
		} else if (com.equals("beginContinue1")) {
			changeScreen(new Begin2());
		} else if (com.equals("beginContinue2")) {
			changeScreen(new EnterName());
		} else if (com.equals("beginName")) {
			EnterName nameCl = (EnterName) screen;
			if (nameCl.getName().equals("")) {
				nameCl.emptyName();
			} else {
				name = nameCl.getName();
				changeScreen(new GameMap());
			}
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
	
	JLabel initializeImageLabel(String imageName, boolean isPNG) {
		try {
			return new JLabel(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/" + imageName + "." + (isPNG ? "png" : "jpg")))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	JButton initializeImageButton(String imageName, boolean isPNG) {
		try {
			return new JButton(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/" + imageName + "." + (isPNG ? "png" : "jpg")))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		new ICBMTycoon();
	}
	
	class Capital {
		int x;
		int y;
		int population;
		
		public Capital(int x, int y, int population) {
			this.x = x;
			this.y = y;
			this.population = population;
		}
		
		public int getX() {
			return this.x;
		}
		
		public int getY() {
			return this.y;
		}
		
		public int getPopulation() {
			return this.population;
		}
	}
	
	abstract class Page { // Class to be extended to for each class page of the game
		abstract public void remove();
	}
	
	class Welcome extends Page {
		JButton startButton = new JButton("Start!");
		JButton exitButton = new JButton("Exit");
		JButton creditButton = new JButton("Credit");
		JLabel welcome = new JLabel("Welcome to Ed's ICBM Tycoon! Press the Start button to continue");
		JLabel author = new JLabel("Developed by Ramiro");
		JLabel author2 = new JLabel("Concept by Ed");
		JLabel icbmImage = initializeImageLabel("icbm", false);
		
		public Welcome() {
			int buttonWidth = 140;
			int buttonHeight = 50;
			panel.add(icbmImage);
			icbmImage.setBounds(0, 0, windowWidth, windowHeight);
			
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
			
			icbmImage.add(creditButton);
			creditButton.setFont(new Font("Arial", Font.PLAIN, 34));
			creditButton.setBounds(cenElement(360), 305, 360, buttonHeight);
			creditButton.addActionListener(inst);
			creditButton.setActionCommand("credit");
			
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
		
		@Override
		public void remove() {
			panel.remove(icbmImage);
		}
	}
	
	class Credits extends Page {
		JButton backWelcome = new JButton("<");
		JLabel text1 = new JLabel("Ed's ICBM Tycoon");
		JLabel text2 = new JLabel("© 2022 Ramiro Salazar");
		JLabel text3 = new JLabel("Developed by Ramiro Salazar");
		JLabel text4 = new JLabel("Ideas by Ed Ruiz");
		JLabel text5 = new JLabel("Welcome page image from Rodong Sinmun");
		
		public Credits() {
			panel.add(backWelcome);
			backWelcome.setFont(new Font("Arial", Font.PLAIN, 38));
			backWelcome.setBounds(10, 10, 42, 42);
			backWelcome.addActionListener(inst);
			backWelcome.setActionCommand("backWelcome");
			backWelcome.setMargin(new Insets(0, 0, 0, 0));
			
			panel.add(text1);
			text1.setFont(new Font("Arial", Font.PLAIN, 18));
			text1.setBounds(10, 58, 600, 20);
			
			panel.add(text2);
			text2.setFont(new Font("Arial", Font.PLAIN, 18));
			text2.setBounds(10, 78, 600, 20);
			
			panel.add(text3);
			text3.setFont(new Font("Arial", Font.PLAIN, 18));
			text3.setBounds(10, 98, 600, 20);
			
			panel.add(text4);
			text4.setFont(new Font("Arial", Font.PLAIN, 18));
			text4.setBounds(10, 118, 600, 20);
			
			panel.add(text5);
			text5.setFont(new Font("Arial", Font.PLAIN, 18));
			text5.setBounds(10, 138, 600, 20);
		}
		
		@Override
		public void remove() {
			panel.remove(backWelcome);
			panel.remove(text1);
			panel.remove(text2);
			panel.remove(text3);
			panel.remove(text4);
			panel.remove(text5);
		}
	}
	
	class Begin1 extends Page {
		JLabel text1 = new JLabel("You have been ordered by a Russian government official named");
		JLabel text2 = new JLabel("Ed HHahdhcuud to send ICBM’s to the United States as while the");
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
	
	class Begin2 extends Page {
		JLabel text1 = new JLabel("After the tragic event the world prepared itself");
		JLabel text2 = new JLabel("for nuclear war...");
		JButton continueNext = new JButton("Continue");
		
		public Begin2() {
			panel.add(text1);
			text1.setFont(new Font("Arial", Font.PLAIN, 34));
			text1.setBounds(cenElement(670), 58, 670, 36);
			
			panel.add(text2);
			text2.setFont(new Font("Arial", Font.PLAIN, 34));
			text2.setBounds(cenElement(250), 90, 250, 36);
			
			panel.add(continueNext);
			continueNext.setFont(new Font("Arial", Font.PLAIN, 38));
			continueNext.setBounds(cenElement(220), 320, 220, 46);
			continueNext.addActionListener(inst);
			continueNext.setActionCommand("beginContinue2");
		}
		
		@Override
		public void remove() {
			panel.remove(text1);
			panel.remove(text2);
			panel.remove(continueNext);
		}
	}
	
	class EnterName extends Page {
		JLabel enterNameText = new JLabel("Please enter your name below!");
		JTextField nameBox = new JTextField();
		JLabel emptyName = new JLabel("You can't leave the box empty!");
		JButton continueNext = new JButton("Let's Begin!");
		
		public EnterName() {
			panel.add(enterNameText);
			enterNameText.setFont(new Font("Arial", Font.PLAIN, 34));
			enterNameText.setBounds(cenElement(460), 58, 460, 36);
			
			panel.add(nameBox);
			nameBox.setBounds(cenElement(300), 120, 300, 22);
			
			panel.add(emptyName);
			emptyName.setForeground(Color.RED);
			emptyName.setBounds(682, 120, 180, 22);
			emptyName.setVisible(false);
			
			panel.add(continueNext);
			continueNext.setFont(new Font("Arial", Font.PLAIN, 38));
			continueNext.setBounds(cenElement(240), 320, 240, 46);
			continueNext.addActionListener(inst);
			continueNext.setActionCommand("beginName");
		}
		
		public String getName() {
			return nameBox.getText();
		}
		
		public void emptyName() {
			emptyName.setVisible(true);
		}
		
		@Override
		public void remove() {
			panel.remove(enterNameText);
			panel.remove(nameBox);
			panel.remove(emptyName);
			panel.remove(continueNext);
		}
	}
	
	class GameMap extends Page {
		JLabel usMap = initializeImageLabel("map", false);
		JLabel side = initializeImageLabel("side", false);
		
		public GameMap() {
			panel.add(usMap);
			usMap.setBounds(0, 0, 868, windowHeight);
			
			panel.add(side);
			side.setBounds(868, 0, 191, 681);
		}
		
		@Override
		public void remove() {
			panel.remove(usMap);
			panel.remove(side);
		}
		
	}
	
}
