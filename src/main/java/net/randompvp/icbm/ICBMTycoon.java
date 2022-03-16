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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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

	Random random = new Random();

	JFrame frame;
	JPanel panel;
	Page screen;
	String name;
	final Capital[] capitals = { new Capital(217, 120, 480000, "Olympia"), new Capital(201, 157, 168000, "Salem"), new Capital(187, 274, 516000, "Sacramento"), new Capital(226, 270, 54000, "Carson City"), new Capital(303, 195, 240000, "Boise"), new Capital(368, 134, 32091, "Helena"), new Capital(433, 216, 57000, "Cheyenne"), new Capital(335, 252, 204000, "Salt Lake City"), new Capital(326, 385, 1597000, "Phoenix"), new Capital(448, 258, 722000, "Denver"), new Capital(405, 348, 84000, "Santa Fe"), new Capital(542, 456, 948000, "Austin"), new Capital(546, 336, 666000, "Oklahoma City"), new Capital(558, 272, 123000, "Topeka"), new Capital(546, 243, 287000, "Lincoln"), new Capital(514, 180, 14091, "Pierre"), new Capital(511, 129, 69000, "Bismarck"), new Capital(597, 171, 312000, "St. Paul"), new Capital(612, 225, 213000, "Des Moines"), new Capital(618, 282, 42000, "Jefferson City"), new Capital(620, 355, 206000, "Little Rock"), new Capital(629, 447, 223000, "Baton Rouge"), new Capital(649, 419, 159000, "Jackson"), new Capital(711, 333, 695000, "Nashville"), new Capital(730, 305, 28602, "Frankfort"), new Capital(667, 271, 107000, "Springfield"), new Capital(669, 189, 262000, "Madison"), new Capital(732, 202, 106000, "Lansing"), new Capital(706, 268, 893000, "Indianapolis"), new Capital(759, 254, 910000, "Columbus"), new Capital(774, 291, 41000, "Charleston"), new Capital(706, 415, 199000, "Montgomery"), new Capital(741, 448, 201000, "Tallahassee"), new Capital(748, 386, 503000, "Atlanta"), new Capital(789, 380, 142000, "Columbia"), new Capital(831, 336, 474000, "Raleigh"), new Capital(846, 314, 230000, "Richmond"), new Capital(846, 279, 46000, "Annapolis"), new Capital(840, 284, 692683, "Washington D.C."), new Capital(867, 279, 36000, "Dover"), new Capital(825, 245, 46000, "Harrisburg"), new Capital(866, 200, 103000, "Albany"), new Capital(876, 256, 96000, "Trenton"), new Capital(898, 226, 116000, "Hartford"), new Capital(914, 226, 203000, "Providence"), new Capital(921, 212, 617000, "Boston"), new Capital(894, 165, 8074, "Montpelier"), new Capital(907, 190, 48000, "Concord"), new Capital(936, 164, 188999, "Augusta"), new Capital(795, 551, 854, "Mummy Town") };
	Capital[] capitalsForGame = new Capital[20];
	HashSet<Capital> capitalsICBM = new HashSet<Capital>();
	final ICBM[] icbms = { new ICBM(500, 70, 25, 30, "IRBM"), new ICBM(750, 65, 40, 50, "Standard ICBM"), new ICBM(900, 95, 35, 40, "Trident II"), new ICBM(1000, 70, 55, 70, "Sarmat"), new ICBM(1250, 55, 70, 95, "TSAR ICBM") };
	HashMap<Integer, Integer> ownedICBM = new HashMap<Integer, Integer>();
	int killed = 0;
	int alive = 0;
	int money = 5000; // starting
	int misses = 0;
	boolean activityStarted = false;
	Timer interceptActivity;

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
				for (int i = 0; i < 20; i++) {
					int randomNum = random.nextInt(temp.size()); // generate a random number using temporary variable
					capitalsForGame[i] = temp.get(randomNum);
					temp.remove(randomNum);
				}

				for (int i = 0; i < 5; i++) {
					ownedICBM.put(i, 0);
				}

				for (Capital capitals : capitalsForGame) {
					alive = alive + capitals.getPopulation();
				}

				changeScreen(new GameMap());
			}
		} else if (com.equals("exitPopup")) {
			((GameMap) screen).removePopup();
		} else if (com.startsWith("capital:")) {
			Capital capital = capitalsForGame[Integer.parseInt(com.split(":")[1])];
			GameMap gm = (GameMap) screen;
			gm.clickCapital(capital);
		} else if (com.equals("clickICBMs")) {
			((GameMap) screen).clickICBMs();
		} else if (com.equals("clickDead")) {
			((GameMap) screen).clickDead();
		} else if (com.equals("clickAlive")) {
			((GameMap) screen).clickAlive();
		} else if (com.equals("clickMoney")) {
			((GameMap) screen).clickMoney();
		} else if (com.startsWith("strike:")) {
			((GameMap) screen).strike(Integer.parseInt(com.split(":")[1]));
		} else if (com.equals("clickICBM")) {
			((GameMap) screen).clickICBM();
		} else if (com.equals("interceptICBM")) {
			((GameMap) screen).clickIntercept();
		} else if (com.equals("clickShop")) {
			changeScreen(new Shop());
		} else if (com.equals("returnToMenu")) {
			capitalsForGame = new Capital[20];
			capitalsICBM = new HashSet<Capital>();
			ownedICBM = new HashMap<Integer, Integer>();
			killed = 0;
			alive = 0;
			money = 5000;
			misses = 0;
			activityStarted = false;
			changeScreen(new Welcome());
		} else if (com.equals("backMap")) {
			changeScreen(new GameMap());
		} else if (com.startsWith("buyicbm:")) {
			int icbm = Integer.parseInt(com.split(":")[1]);
			if (money >= icbms[icbm].getPrice()) { // can buy
				ownedICBM.put(icbm, ownedICBM.get(icbm) + 1);
				money = money - icbms[icbm].getPrice();
				((Shop) screen).updateText();
			}
		}
	}

	void runUSInbound() {
		interceptActivity = new Timer();
		interceptActivity.schedule(new TimerTask() {
			public void run() {
				if (screen instanceof GameMap) {
					((GameMap) screen).interceptActivity();
				}
				runUSInbound();
			}
		}, random.nextInt(5000) + 15000);
	}

	void endGame(boolean won) {
		interceptActivity.cancel();
		changeScreen(new End(won));
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

	class ICBM {
		final int price;
		final int percentageStrike;
		final int populationLow;
		final int populationHigh;
		final String name;

		public ICBM(int price, int percentageStrike, int populationLow, int populationHigh, String name) {
			this.price = price;
			this.percentageStrike = percentageStrike;
			this.populationLow = populationLow;
			this.populationHigh = populationHigh;
			this.name = name;
		}

		public int getPrice() {
			return this.price;
		}

		public int getpercentageStrike() {
			return this.percentageStrike;
		}

		public int getPopulationLow() {
			return this.populationLow;
		}

		public int getPopulationHigh() {
			return this.populationHigh;
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
		JLabel text6 = new JLabel("Capital population numbers from Wikipedia®");

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

			panel.add(text6);
			text6.setFont(new Font("Arial", Font.PLAIN, 18));
			text6.setBounds(10, 158, 600, 20);
		}

		@Override
		public void remove() {
			panel.remove(backWelcome);
			panel.remove(text1);
			panel.remove(text2);
			panel.remove(text3);
			panel.remove(text4);
			panel.remove(text5);
			panel.remove(text6);
		}
	}

	class Begin1 extends Page {
		JLabel text1 = new JLabel("You have been ordered by a Russian government official named");
		JLabel text2 = new JLabel("Ed HHahdhcuud to send ICBM's to the United States as while the");
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
		JButton[] capitalElementsButton = new JButton[20];
		JLabel[] capitalElementsLabel = new JLabel[20];
		JLabel icbmsText = new JLabel(), deadText = new JLabel(), aliveText = new JLabel(), moneyText = new JLabel();
		JButton clickicbms = new JButton(), clickDead = new JButton(), clickAlive = new JButton(), clickMoney = new JButton();
		JButton shopButton = new JButton();

		// popup
		JPanel popup;
		JButton exitPopup = new JButton("X");
		Capital capitalSelected;
		JLabel icbmLayout;
		JLabel population;
		boolean noExit = false;

		int icbmClicks = 0;

		Timer interceptTimer;
		JButton interceptButton = new JButton("INTERCEPT");
		JLabel interceptText = new JLabel("US ICBM inbound, click the red button on screen to intercept");
		boolean run = false;

		public GameMap() {
			panel.add(usMap);
			usMap.setBounds(0, 0, windowWidth, windowHeight);

			usMap.add(icbmsText);
			icbmsText.setFont(new Font("Arial", Font.PLAIN, 36));
			icbmsText.setBounds(40, 1, 80, 38);
			icbmsText.setHorizontalAlignment(SwingConstants.RIGHT);

			usMap.add(deadText);
			deadText.setFont(new Font("Arial", Font.PLAIN, 30));
			deadText.setBounds(170, 4, 135, 32);
			deadText.setHorizontalAlignment(SwingConstants.RIGHT);

			usMap.add(aliveText);
			aliveText.setFont(new Font("Arial", Font.PLAIN, 36));
			aliveText.setBounds(355, 1, 215, 38);
			aliveText.setHorizontalAlignment(SwingConstants.RIGHT);

			usMap.add(moneyText);
			moneyText.setFont(new Font("Arial", Font.PLAIN, 36));
			moneyText.setBounds(610, 1, 160, 38);
			moneyText.setHorizontalAlignment(SwingConstants.RIGHT);
			moneyText.setForeground(Color.GREEN);

			updateText();

			for (int i = 0; i < capitalsForGame.length; i++) {
				Capital c = capitalsForGame[i];
				capitalElementsButton[i] = initializeImageButton("dot", true);
				capitalPosition(capitalElementsButton[i], c.getX(), c.getY());
				((JButton) capitalElementsButton[i]).addActionListener(inst);
				((JButton) capitalElementsButton[i]).setActionCommand("capital:" + i);
			}

			for (int i = 0; i < capitalsForGame.length; i++) {
				Capital c = capitalsForGame[i];
				capitalElementsLabel[i] = initializeImageLabel("dot", true);
				capitalPosition(capitalElementsLabel[i], c.getX(), c.getY());
			}

			addClickElements();

			clickicbms.setBounds(2, 1, 122, 36);
			clickicbms.setOpaque(false);
			clickicbms.setContentAreaFilled(false);
			clickicbms.setBorderPainted(false);
			clickicbms.addActionListener(inst);
			clickicbms.setActionCommand("clickICBMs");

			clickDead.setBounds(138, 1, 167, 36);
			clickDead.setOpaque(false);
			clickDead.setContentAreaFilled(false);
			clickDead.setBorderPainted(false);
			clickDead.addActionListener(inst);
			clickDead.setActionCommand("clickDead");

			clickAlive.setBounds(323, 1, 247, 36);
			clickAlive.setOpaque(false);
			clickAlive.setContentAreaFilled(false);
			clickAlive.setBorderPainted(false);
			clickAlive.addActionListener(inst);
			clickAlive.setActionCommand("clickAlive");

			clickMoney.setBounds(580, 1, 196, 36);
			clickMoney.setOpaque(false);
			clickMoney.setContentAreaFilled(false);
			clickMoney.setBorderPainted(false);
			clickMoney.addActionListener(inst);
			clickMoney.setActionCommand("clickMoney");

			exitPopup.setFont(new Font("Arial", Font.PLAIN, 28));
			exitPopup.setBackground(Color.RED);
			exitPopup.setMargin(new Insets(0, 0, 0, 0));
			exitPopup.addActionListener(inst);
			exitPopup.setActionCommand("exitPopup");

			interceptButton.setFont(new Font("Arial", Font.PLAIN, 28));
			interceptButton.setMargin(new Insets(0, 0, 0, 0));
			interceptButton.setBackground(new Color(156, 0, 3));
			interceptButton.setBounds(20 + random.nextInt(814), 526, 170, 30);
			interceptButton.addActionListener(inst);
			interceptButton.setActionCommand("interceptICBM");

			interceptText.setFont(new Font("Arial", Font.BOLD, 22));
			interceptText.setBounds(230, 50, 710, 26);

			shopButton.setBounds(980, 38, 44, 538);
			shopButton.setOpaque(false);
			shopButton.setContentAreaFilled(false);
			shopButton.setBorderPainted(false);
			shopButton.addActionListener(inst);
			shopButton.setActionCommand("clickShop");
		}

		void updateText() {
			int amount = 0;
			for (Entry<Integer, Integer> icbms : ownedICBM.entrySet()) {
				amount = amount + icbms.getValue();
			}
			icbmsText.setText(NumberFormat.getInstance().format(amount));

			deadText.setText(NumberFormat.getInstance().format(killed));

			aliveText.setText(NumberFormat.getInstance().format(alive - killed));

			moneyText.setText(NumberFormat.getInstance().format(money) + "K");
		}

		public void clickCapital(Capital c) {
			addCapitalsAsLabels();

			capitalSelected = c;

			popup = new JPanel();
			panel.add(popup);
			panel.setComponentZOrder(popup, 0);
			popup.setBounds(64, 36, 896, 504);
			popup.setLayout(null);
			popup.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));

			popup.add(exitPopup);
			exitPopup.setBounds(832, 30, 34, 34);

			JLabel title = new JLabel("You will ICBM " + c.getName());
			popup.add(title);
			title.setFont(new Font("Arial", Font.PLAIN, 28));
			title.setBounds(30, 30, 500, 34);

			population = new JLabel("Population: " + NumberFormat.getInstance().format(c.getPopulation()));
			popup.add(population);
			population.setFont(new Font("Arial", Font.PLAIN, 18));
			population.setBounds(30, 64, 500, 20);

			icbmLayout = initializeImageLabel("icbmlayout", true);
			popup.add(icbmLayout);
			icbmLayout.setBounds(11, 123, 874, 370);

			for (int i = 0; i < 5; i++) {
				ICBM icbm = icbms[i];
				int x = 15 + (i * 177);

				JLabel strike = new JLabel(calcStrike(c.getPopulation(), icbm.getpercentageStrike()) + "%");
				icbmLayout.add(strike);
				strike.setBounds(x, 192, 50, 20);
				JLabel population = new JLabel(icbm.getPopulationLow() + "-" + icbm.getPopulationHigh() + "%");
				icbmLayout.add(population);
				population.setBounds(x, 228, 160, 20);
				JLabel populationNums = new JLabel("(" + NumberFormat.getInstance().format(percentOfPop(icbm.getPopulationLow(), c.getPopulation())) + "-" + NumberFormat.getInstance().format(percentOfPop(icbm.getPopulationHigh(), c.getPopulation())) + ")");
				icbmLayout.add(populationNums);
				populationNums.setBounds(x, 242, 160, 20);
				JLabel owned = new JLabel(String.valueOf(ownedICBM.get(i)));
				icbmLayout.add(owned);
				owned.setBounds(x, 280, 50, 20);
				if (ownedICBM.get(i) > 0) {
					JButton launchButton = new JButton("Strike!");
					icbmLayout.add(launchButton);
					launchButton.setFont(new Font("Arial", Font.PLAIN, 28));
					launchButton.setBounds(x, 325, 136, 30);
					launchButton.addActionListener(inst);
					launchButton.setActionCommand("strike:" + i);
				}
			}
		}

		public void removePopup() {
			panel.remove(popup);
			popup = null;
			removeClickElements();
			addClickElements();
			frame.repaint();
		}

		public void clickICBMs() {
			addCapitalsAsLabels();

			popup = new JPanel();
			panel.add(popup);
			panel.setComponentZOrder(popup, 0);
			popup.setBounds(297, 228, 430, 120);
			popup.setLayout(null);
			popup.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));

			popup.add(exitPopup);
			exitPopup.setBounds(366, 30, 34, 34);

			JLabel title = new JLabel("ICBMs owned:");
			popup.add(title);
			title.setFont(new Font("Arial", Font.PLAIN, 28));
			title.setBounds(30, 30, 500, 34);

			JLabel icbms = new JLabel(icbmsText.getText());
			popup.add(icbms);
			icbms.setFont(new Font("Arial", Font.PLAIN, 18));
			icbms.setBounds(30, 64, 500, 20);
		}

		public void clickDead() {
			removeClickElements();
			addCapitalsAsLabels();

			popup = new JPanel();
			panel.add(popup);
			panel.setComponentZOrder(popup, 0);
			popup.setBounds(297, 228, 430, 120);
			popup.setLayout(null);
			popup.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));

			popup.add(exitPopup);
			exitPopup.setBounds(366, 30, 34, 34);

			JLabel title = new JLabel("People killed:");
			popup.add(title);
			title.setFont(new Font("Arial", Font.PLAIN, 28));
			title.setBounds(30, 30, 500, 34);

			JLabel dead = new JLabel(deadText.getText());
			popup.add(dead);
			dead.setFont(new Font("Arial", Font.PLAIN, 18));
			dead.setBounds(30, 64, 500, 20);
		}

		public void clickAlive() {
			addCapitalsAsLabels();

			popup = new JPanel();
			panel.add(popup);
			panel.setComponentZOrder(popup, 0);
			popup.setBounds(297, 228, 430, 120);
			popup.setLayout(null);
			popup.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));

			popup.add(exitPopup);
			exitPopup.setBounds(366, 30, 34, 34);

			JLabel title = new JLabel("People alive:");
			popup.add(title);
			title.setFont(new Font("Arial", Font.PLAIN, 28));
			title.setBounds(30, 30, 500, 34);

			JLabel alive = new JLabel(aliveText.getText());
			popup.add(alive);
			alive.setFont(new Font("Arial", Font.PLAIN, 18));
			alive.setBounds(30, 64, 500, 20);
		}

		public void clickMoney() {
			addCapitalsAsLabels();

			popup = new JPanel();
			panel.add(popup);
			panel.setComponentZOrder(popup, 0);
			popup.setBounds(297, 228, 430, 120);
			popup.setLayout(null);
			popup.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));

			popup.add(exitPopup);
			exitPopup.setBounds(366, 30, 34, 34);

			JLabel title = new JLabel("Money owned:");
			popup.add(title);
			title.setFont(new Font("Arial", Font.PLAIN, 28));
			title.setBounds(30, 30, 500, 34);

			JLabel money = new JLabel("$" + moneyText.getText());
			popup.add(money);
			money.setFont(new Font("Arial", Font.PLAIN, 18));
			money.setBounds(30, 64, 500, 20);
		}

		public void interceptActivity() {
			if (noExit)
				return;
			if (popup != null)
				removePopup();
			addCapitalsAsLabels();
			usMap.add(interceptButton);
			usMap.add(interceptText);
			interceptTimer = new Timer();
			interceptTimer.schedule(new TimerTask() {
				public void run() {
					if (run)
						return;
					run = true;
					usMap.remove(interceptButton);
					usMap.remove(interceptText);
					removeClickElements();
					addClickElements();
					misses++;
					if (misses == 5) {
						endGame(false);
						return;
					}
					JLabel missText = new JLabel("You didn't intercept the ICBM! (" + misses + " miss" + (misses > 1 ? "es" : "") + ")");
					usMap.add(missText);
					missText.setFont(new Font("Arial", Font.BOLD, 22));
					missText.setForeground(Color.RED);
					missText.setBounds(230, 50, 710, 26);
					new Timer().schedule(new TimerTask() {
						public void run() {
							usMap.remove(missText);
							run = false;
							usMap.repaint();
						}
					}, 2500);
				}
			}, 2600);
		}

		public void clickIntercept() {
			if (run)
				return;
			run = true;
			interceptTimer.cancel();
			usMap.remove(interceptButton);
			usMap.remove(interceptText);
			removeClickElements();
			addClickElements();
			int moneyGained = (random.nextInt(21) * 10) + 500;
			money = money + moneyGained;
			updateText();
			JLabel hitText = new JLabel("You intercepted the ICBM! (+$" + moneyGained + "K)");
			usMap.add(hitText);
			hitText.setFont(new Font("Arial", Font.BOLD, 22));
			hitText.setForeground(Color.GREEN);
			hitText.setBounds(230, 50, 710, 26);
			new Timer().schedule(new TimerTask() {
				public void run() {
					usMap.remove(hitText);
					run = false;
					usMap.repaint();
				}
			}, 2500);
		}

		void addClickElements() {
			for (int i = 0; i < capitalsForGame.length; i++) {
				Capital c = capitalsForGame[i];
				if (capitalsICBM.contains(c))
					continue;
				usMap.add(capitalElementsButton[i]);
			}
			usMap.add(clickicbms);
			usMap.add(clickDead);
			usMap.add(clickAlive);
			usMap.add(clickMoney);
			usMap.add(shopButton);
		}

		void addCapitalsAsLabels() {
			removeClickElements();
			for (int i = 0; i < capitalsForGame.length; i++) {
				Capital c = capitalsForGame[i];
				if (capitalsICBM.contains(c))
					continue;
				usMap.add(capitalElementsLabel[i]);
			}
		}

		void removeClickElements() {
			for (JButton capital : capitalElementsButton) {
				usMap.remove(capital);
			}
			for (JLabel capital : capitalElementsLabel) {
				usMap.remove(capital);
			}
			usMap.remove(clickicbms);
			usMap.remove(clickDead);
			usMap.remove(clickAlive);
			usMap.remove(clickMoney);
			usMap.remove(shopButton);
			usMap.repaint();
		}

		public void strike(int icbmClicked) {
			noExit = true;
			ownedICBM.put(icbmClicked, ownedICBM.get(icbmClicked) - 1);
			updateText();
			ICBM icbm = icbms[icbmClicked];

			popup.remove(exitPopup);
			popup.remove(icbmLayout);
			popup.remove(population);
			popup.repaint();
			JButton icbmClick = new JButton("CLICK!!");
			popup.add(icbmClick);
			icbmClick.setFont(new Font("Arial", Font.BOLD, 104));
			icbmClick.setBounds(224, 126, 448, 252);
			icbmClick.setBackground(new Color(207, 91, 91));
			icbmClick.setForeground(new Color(125, 12, 12));
			icbmClick.addActionListener(inst);
			icbmClick.setActionCommand("clickICBM");
			new Timer().schedule(new TimerTask() {
				public void run() {
					popup.remove(icbmClick);
					popup.repaint();
					if (icbmClicks > 50) {
						icbmClicks = 50;
					}
					JLabel icbmLaunching = new JLabel(icbm.getName() + " launching to " + capitalSelected.getName());
					popup.add(icbmLaunching);
					icbmLaunching.setBounds(0, 230, 896, 42);
					icbmLaunching.setHorizontalAlignment(SwingConstants.CENTER);
					icbmLaunching.setForeground(new Color(29, 161, 46));
					icbmLaunching.setFont(new Font("Arial", Font.PLAIN, 38));
					for (int i = 0; i < 11; i++) {
						int j = i; // get around timer limitation
						new Timer().schedule(new TimerTask() {
							public void run() {
								if (j % 2 == 0) {
									icbmLaunching.setVisible(false);
								} else {
									icbmLaunching.setVisible(true);
								}
							}
						}, i * 400);
					}
					new Timer().schedule(new TimerTask() {
						public void run() {
							popup.remove(icbmLaunching);
							if (random.nextInt(100) < calcStrike(capitalSelected.getPopulation(), icbm.percentageStrike) + (icbmClicks / 16)) { // ICBM hit
								int people = (int) Math.round(capitalSelected.getPopulation() / 100 * (icbm.getPopulationHigh() - (random.nextDouble() * (icbm.getPopulationHigh() - icbm.getPopulationLow() - (((double) icbm.getPopulationHigh() - (double) icbm.getPopulationLow()) / 100 * icbmClicks)))));
								killed = killed + people;
								JLabel hit = new JLabel("Congrats! Your " + icbm.getName() + " hit " + capitalSelected.getName() + " and killed " + NumberFormat.getInstance().format(people) + " people!");
								popup.add(hit);
								hit.setBounds(0, 230, 896, 28);
								hit.setHorizontalAlignment(SwingConstants.CENTER);
								hit.setForeground(Color.GREEN);
								hit.setFont(new Font("Arial", Font.PLAIN, 24));
							} else { // ICBM got intercepted
								JLabel intercepted = new JLabel("Unfortunately, your " + icbm.getName() + " was intercepted");
								popup.add(intercepted);
								intercepted.setBounds(0, 230, 896, 28);
								intercepted.setHorizontalAlignment(SwingConstants.CENTER);
								intercepted.setForeground(Color.RED);
								intercepted.setFont(new Font("Arial", Font.PLAIN, 24));
							}
							icbmClicks = 0;
							updateText();
							capitalsICBM.add(capitalSelected);
							new Timer().schedule(new TimerTask() {
								public void run() {
									noExit = false;
									removePopup();
									if (!activityStarted) {
										activityStarted = true;
										runUSInbound();
									}
									if (capitalsICBM.size() == 20) {
										endGame(true);
									}
								}
							}, 4500);
						}
					}, 5000);
				}
			}, 5000);
		}

		public void clickICBM() {
			icbmClicks++;
		}

		@Override
		public void remove() {
			panel.remove(usMap);
		}

	}

	class Shop extends Page {
		JLabel icbmShopLayout = initializeImageLabel("icbmshop", true);
		JLabel moneyText = new JLabel();
		JLabel[] icbmOwned = new JLabel[5];
		JButton[] buyicbm = new JButton[5];
		JButton backMap = new JButton("<");

		public Shop() {
			panel.add(icbmShopLayout);
			icbmShopLayout.setBounds(0, 0, windowWidth, windowHeight);

			icbmShopLayout.add(moneyText);
			moneyText.setForeground(Color.GREEN);
			moneyText.setHorizontalAlignment(SwingConstants.RIGHT);
			moneyText.setFont(new Font("Arial", Font.PLAIN, 36));
			moneyText.setBounds(870, 5, 145, 40);

			icbmShopLayout.add(backMap);
			backMap.setFont(new Font("Arial", Font.PLAIN, 38));
			backMap.setBounds(10, 10, 42, 42);
			backMap.addActionListener(inst);
			backMap.setActionCommand("backMap");
			backMap.setMargin(new Insets(0, 0, 0, 0));

			for (int i = 0; i < 5; i++) {
				icbmOwned[i] = new JLabel();
				icbmShopLayout.add(icbmOwned[i]);
				icbmOwned[i].setBounds(85 + (202 * i), 482, 30, 12);
			}

			for (int i = 0; i < 5; i++) {
				buyicbm[i] = new JButton();
				icbmShopLayout.add(buyicbm[i]);
				buyicbm[i].setFont(new Font("Arial", Font.BOLD, 14));
				buyicbm[i].setMargin(new Insets(0, 0, 0, 0));
				buyicbm[i].setBounds(32 + (202 * i), 502, 152, 42);
				buyicbm[i].addActionListener(inst);
				buyicbm[i].setActionCommand("buyicbm:" + i);
			}

			updateText();
		}

		public void updateText() {
			moneyText.setText(NumberFormat.getInstance().format(money) + "K");
			for (int i = 0; i < 5; i++) {
				icbmOwned[i].setText(String.valueOf(ownedICBM.get(i)));
			}
			for (int i = 0; i < 5; i++) {
				if (money >= icbms[i].getPrice()) {
					buyicbm[i].setText("Buy");
					buyicbm[i].setBackground(Color.GREEN);
				} else {
					buyicbm[i].setText("Not enough money");
					buyicbm[i].setBackground(Color.RED);
				}
			}
		}

		@Override
		public void remove() {
			panel.remove(icbmShopLayout);
		}

	}

	class End extends Page {
		JLabel title = new JLabel();
		JButton returnToMenu = new JButton("Click to return to main menu");
		JButton addLeaderboard = new JButton("Add your score to leaderboard!");

		public End(boolean won) {
			panel.add(title);
			title.setBounds(0, 30, windowWidth, 32);
			title.setFont(new Font("Arial", Font.PLAIN, 28));
			title.setHorizontalAlignment(SwingConstants.CENTER);
			if (won) {
				title.setText("Congratulations! You successfully ICBM'd all the US Capitals!");
			} else {
				title.setText("Unfortunately, 5 ICBMs hit Russia and you died.");
			}

			panel.add(returnToMenu);
			returnToMenu.setBounds(cenElement(282), 70, 282, 30);
			returnToMenu.setFont(new Font("Arial", Font.PLAIN, 18));
			returnToMenu.addActionListener(inst);
			returnToMenu.setActionCommand("returnToMenu");

			if (won) {
				panel.add(addLeaderboard);
				addLeaderboard.setBounds(cenElement(282), 120, 282, 30);
				addLeaderboard.setFont(new Font("Arial", Font.PLAIN, 18));
				addLeaderboard.addActionListener(inst);
				addLeaderboard.setActionCommand("addToLeaderboard");
			}
		}

		@Override
		public void remove() {
			panel.remove(title);
			panel.remove(returnToMenu);
			panel.remove(addLeaderboard);
		}

	}

}
