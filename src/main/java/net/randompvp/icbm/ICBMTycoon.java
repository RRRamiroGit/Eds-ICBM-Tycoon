/*
 * Ramiro Salazar
 * Period 1 APCS
*/

package net.randompvp.icbm;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ICBMTycoon implements ActionListener {
	
	public static ICBMTycoon inst; // Instance of the class
	
	// Window dimensions
	final int windowHeight = 576;
	final int windowWidth = 1024;
	
	JFrame frame;
	JPanel panel;
	Page screen;
	String name;
	final Capital[] capitals = { new Capital(217, 120, 480000, "Olympia"), new Capital(201, 157, 168000, "Salem"), new Capital(187, 274, 516000, "Sacramento"), new Capital(226, 270, 54000, "Carson City"), new Capital(303, 195, 240000, "Boise"), new Capital(368, 134, 32091, "Helena"), new Capital(433, 216, 57000, "Cheyenne"), new Capital(335, 252, 204000, "Salt Lake City"), new Capital(326, 385, 1597000, "Phoenix"), new Capital(448, 258, 722000, "Denver"), new Capital(405, 348, 84000, "Santa Fe"), new Capital(542, 456, 948000, "Austin"), new Capital(546, 336, 666000, "Oklahoma City"), new Capital(558, 272, 123000, "Topeka"), new Capital(546, 243, 287000, "Lincoln"), new Capital(514, 180, 14091, "Pierre"), new Capital(511, 129, 69000, "Bismarck"), new Capital(597, 171, 312000, "St. Paul"), new Capital(612, 225, 213000, "Des Moines"), new Capital(618, 282, 42000, "Jefferson City"), new Capital(620, 355, 206000, "Little Rock"), new Capital(629, 447, 223000, "Baton Rouge"), new Capital(649, 419, 159000, "Jackson"), new Capital(711, 333, 695000, "Nashville"), new Capital(730, 305, 28602, "Frankfort"), new Capital(667, 271, 107000, "Springfield"), new Capital(669, 189, 262000, "Madison"), new Capital(732, 202, 106000, "Lansing"), new Capital(706, 268, 893000, "Indianapolis"), new Capital(759, 254, 910000, "Columbus"), new Capital(774, 291, 41000, "Charleston"), new Capital(706, 415, 199000, "Montgomery"), new Capital(741, 448, 201000, "Tallahassee"), new Capital(748, 386, 503000, "Atlanta"), new Capital(789, 380, 142000, "Columbia"), new Capital(831, 336, 474000, "Raleigh"), new Capital(846, 314, 230000, "Richmond"), new Capital(846, 279, 46000, "Annapolis"), new Capital(840, 284, 692683, "Washington D.C."), new Capital(867, 279, 36000, "Dover"), new Capital(825, 245, 46000, "Harrisburg"), new Capital(866, 200, 103000, "Albany"), new Capital(876, 256, 96000, "Trenton"), new Capital(898, 226, 116000, "Hartford"), new Capital(914, 226, 203000, "Providence"), new Capital(921, 212, 617000, "Boston"), new Capital(894, 165, 8074, "Montpelier"), new Capital(907, 190, 48000, "Concord"), new Capital(936, 164, 188999, "Augusta"), new Capital(795, 551, 854, "Mummy Town") };
	Capital[] capitalsForGame = new Capital[20];
	int money = 5000; // starting
	
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
				
				// randomize 20 capitals and put them in a new list
				List<Capital> temp = new ArrayList<Capital>(Arrays.asList(capitals));
				Random random = new Random();
				for (int i = 0; i < 20; i++) {
					int randomNum = random.nextInt(temp.size()); // generate a random number using temporary variable
					capitalsForGame[i] = temp.get(randomNum);
					temp.remove(randomNum);
				}
				
				changeScreen(new GameMap());
			}
		} else if (com.equals("exitPopup")) {
			((GameMap) screen).removePopup();
		} else if (com.startsWith("capital:")) {
			Capital capital = capitalsForGame[Integer.parseInt(com.split(":")[1])];
			GameMap gm = (GameMap) screen;
			gm.clickCapital(capital);
		} else if (com.equals("clickMoney")) {
			((GameMap) screen).clickMoney();
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
	
	public void capitalPosition(Component component, int x, int y) {
		component.setBounds(x - 3, y - 3, 9, 9);
	}
	
	int calcStrike(double population, int basePercent) {
		return (int) (basePercent - ((population / 140000) - 4.4));
	}
	
	int percentOfPop(int percentage, double population) {
		return (int) ((population / 100) * percentage);
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
		final int x;
		final int y;
		final int population;
		final String name;
		
		public Capital(int x, int y, int population, String name) {
			this.x = x;
			this.y = y;
			this.population = population;
			this.name = name;
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
		
		public String getName() {
			return this.name;
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
		JLabel usMap = initializeImageLabel("map", true);
		JPanel popup;
		Component[] capitalElements = new Component[20];
		JLabel moneyText = new JLabel(NumberFormat.getInstance().format(money) + "K");
		JButton clickMoney = new JButton();
		
		public GameMap() {
			panel.add(usMap);
			usMap.setBounds(0, 0, windowWidth, windowHeight);
			
			usMap.add(moneyText);
			moneyText.setFont(new Font("Arial", Font.PLAIN, 36));
			moneyText.setBounds(610, 2, 160, 38);
			moneyText.setHorizontalAlignment(SwingConstants.RIGHT);
			moneyText.setForeground(Color.GREEN);
			
			addClickElements();
			
			clickMoney.setBounds(580, 1, 196, 36);
			clickMoney.setOpaque(false);
			clickMoney.setContentAreaFilled(false);
			clickMoney.setBorderPainted(false);
			clickMoney.addActionListener(inst);
			clickMoney.setActionCommand("clickMoney");
		}
		
		public void clickCapital(Capital c) {
			removeClickElements();
			addCapitalsAsLabels();
			
			popup = new JPanel();
			panel.add(popup);
			panel.setComponentZOrder(popup, 0);
			popup.setBounds(64, 36, 896, 504);
			popup.setLayout(null);
			popup.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
			
			JButton exitPopup = new JButton("X");
			popup.add(exitPopup);
			exitPopup.setFont(new Font("Arial", Font.PLAIN, 28));
			exitPopup.setBackground(Color.RED);
			exitPopup.setBounds(832, 30, 34, 34);
			exitPopup.setMargin(new Insets(0, 0, 0, 0));
			exitPopup.addActionListener(inst);
			exitPopup.setActionCommand("exitPopup");
			
			JLabel title = new JLabel("You will ICBM " + c.getName());
			popup.add(title);
			title.setFont(new Font("Arial", Font.PLAIN, 28));
			title.setBounds(30, 30, 500, 34);
			
			JLabel population = new JLabel("Population: " + NumberFormat.getInstance().format(c.getPopulation()));
			popup.add(population);
			population.setFont(new Font("Arial", Font.PLAIN, 18));
			population.setBounds(30, 64, 500, 20);
			
			JLabel icbmLayout = initializeImageLabel("icbmlayout", true);
			popup.add(icbmLayout);
			icbmLayout.setBounds(11, 123, 874, 370);
			
			JLabel IRBMStrike = new JLabel(calcStrike(c.getPopulation(), 70) + "%");
			icbmLayout.add(IRBMStrike);
			IRBMStrike.setBounds(15, 192, 50, 20);
			JLabel IRBMPop = new JLabel("25-30%");
			icbmLayout.add(IRBMPop);
			IRBMPop.setBounds(15, 228, 160, 20);
			JLabel IRBMPopNums = new JLabel("(" + NumberFormat.getInstance().format(percentOfPop(25, c.getPopulation())) + "-" + NumberFormat.getInstance().format(percentOfPop(30, c.getPopulation())) + ")");
			icbmLayout.add(IRBMPopNums);
			IRBMPopNums.setBounds(15, 242, 160, 20);
			
			JLabel SICBMStrike = new JLabel(calcStrike(c.getPopulation(), 65) + "%");
			icbmLayout.add(SICBMStrike);
			SICBMStrike.setBounds(192, 192, 50, 20);
			JLabel SICBMPop = new JLabel("40-50%");
			icbmLayout.add(SICBMPop);
			SICBMPop.setBounds(192, 228, 160, 20);
			JLabel SICBMPopNums = new JLabel("(" + NumberFormat.getInstance().format(percentOfPop(40, c.getPopulation())) + "-" + NumberFormat.getInstance().format(percentOfPop(50, c.getPopulation())) + ")");
			icbmLayout.add(SICBMPopNums);
			SICBMPopNums.setBounds(192, 242, 160, 20);
			
			JLabel TridentStrike = new JLabel(calcStrike(c.getPopulation(), 95) + "%");
			icbmLayout.add(TridentStrike);
			TridentStrike.setBounds(369, 192, 50, 20);
			JLabel TridentPop = new JLabel("35-40%");
			icbmLayout.add(TridentPop);
			TridentPop.setBounds(369, 228, 160, 20);
			JLabel TridentPopNums = new JLabel("(" + NumberFormat.getInstance().format(percentOfPop(35, c.getPopulation())) + "-" + NumberFormat.getInstance().format(percentOfPop(40, c.getPopulation())) + ")");
			icbmLayout.add(TridentPopNums);
			TridentPopNums.setBounds(369, 242, 160, 20);
			
			JLabel SarmatStrike = new JLabel(calcStrike(c.getPopulation(), 70) + "%");
			icbmLayout.add(SarmatStrike);
			SarmatStrike.setBounds(546, 192, 50, 20);
			JLabel SarmatPop = new JLabel("55-70%");
			icbmLayout.add(SarmatPop);
			SarmatPop.setBounds(546, 228, 160, 20);
			JLabel SarmatPopNums = new JLabel("(" + NumberFormat.getInstance().format(percentOfPop(55, c.getPopulation())) + "-" + NumberFormat.getInstance().format(percentOfPop(70, c.getPopulation())) + ")");
			icbmLayout.add(SarmatPopNums);
			SarmatPopNums.setBounds(546, 242, 160, 20);
			
			JLabel TSARStrike = new JLabel(calcStrike(c.getPopulation(), 55) + "%");
			icbmLayout.add(TSARStrike);
			TSARStrike.setBounds(723, 192, 50, 20);
			JLabel TSARPop = new JLabel("70-95%");
			icbmLayout.add(TSARPop);
			TSARPop.setBounds(723, 228, 160, 20);
			JLabel TSARPopNums = new JLabel("(" + NumberFormat.getInstance().format(percentOfPop(70, c.getPopulation())) + "-" + NumberFormat.getInstance().format(percentOfPop(95, c.getPopulation())) + ")");
			icbmLayout.add(TSARPopNums);
			TSARPopNums.setBounds(723, 242, 160, 20);
		}
		
		public void removePopup() {
			panel.remove(popup);
			removeClickElements();
			addClickElements();
			frame.repaint();
		}
		
		public void clickMoney() {
			removeClickElements();
			addCapitalsAsLabels();
			
			popup = new JPanel();
			panel.add(popup);
			panel.setComponentZOrder(popup, 0);
			popup.setBounds(297, 228, 430, 120);
			popup.setLayout(null);
			popup.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
			
			JButton exitPopup = new JButton("X");
			popup.add(exitPopup);
			exitPopup.setFont(new Font("Arial", Font.PLAIN, 28));
			exitPopup.setBackground(Color.RED);
			exitPopup.setBounds(366, 30, 34, 34);
			exitPopup.setMargin(new Insets(0, 0, 0, 0));
			exitPopup.addActionListener(inst);
			exitPopup.setActionCommand("exitPopup");
			
			JLabel title = new JLabel("Money owned:");
			popup.add(title);
			title.setFont(new Font("Arial", Font.PLAIN, 28));
			title.setBounds(30, 30, 500, 34);
			
			JLabel money = new JLabel("$" + moneyText.getText());
			popup.add(money);
			money.setFont(new Font("Arial", Font.PLAIN, 18));
			money.setBounds(30, 64, 500, 20);
		}
		
		void addClickElements() {
			for (int i = 0; i < capitalsForGame.length; i++) {
				Capital c = capitalsForGame[i];
				capitalElements[i] = initializeImageButton("dot", true);
				usMap.add(capitalElements[i]);
				capitalPosition(capitalElements[i], c.getX(), c.getY());
				((JButton) capitalElements[i]).addActionListener(inst);
				((JButton) capitalElements[i]).setActionCommand("capital:" + i);
			}
			usMap.add(clickMoney);
		}
		
		void addCapitalsAsLabels() {
			for (int i = 0; i < capitalsForGame.length; i++) {
				Capital c = capitalsForGame[i];
				capitalElements[i] = initializeImageLabel("dot", true);
				usMap.add(capitalElements[i]);
				capitalPosition(capitalElements[i], c.getX(), c.getY());
			}
		}
		
		void removeClickElements() {
			for (Component capital : capitalElements) {
				usMap.remove(capital);
			}
			usMap.remove(clickMoney);
		}
		
		@Override
		public void remove() {
			panel.remove(usMap);
		}
		
	}
	
}
