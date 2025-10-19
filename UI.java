package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {

	Graphics2D g2;

	public Rectangle quitButtonBounds;
	public Rectangle resumeButtonBounds;
	public Rectangle optionsButtonBounds;
	public Rectangle backButtonBounds;
	public Rectangle keybindButtonBounds;

	public Rectangle continueButtonBounds;
	public Rectangle quitAfterCreditsButtonBounds;

	gamePanel gp;
	Font arial_40Italic, arial_80Bold, arial_30, arial_20Italic;
	BufferedImage keyImage;

	public boolean messageOn = false;
	public String message = "";
	public int getXForCenteredText;
	public int commandNum = 0;

	int messageCounter = 0;
	int endCreditsCounter = 0;

	public boolean gameFinished = false;
	public boolean creditsShown = false;
	boolean creditsActive = false;
	boolean showPause = false;

	double playTime;
	DecimalFormat decimalFormat = new DecimalFormat("#0.00");

	boolean showCredits = false;
	int creditsScrollY = 0;

	public UI(gamePanel gp) {

		this.gp = gp;

		arial_40Italic = new Font("Arial", Font.ITALIC, 40);
		arial_80Bold = new Font("Arial", Font.BOLD, 80);
		arial_20Italic = new Font("Arial", Font.ITALIC, 20);

		OBJ_Key key = new OBJ_Key(gp);
		keyImage = key.image;
	}

	public void showMessage(String text) {

		message = text;
		messageOn = true;
	}

	public void draw(Graphics2D g2) {

		this.g2 = g2;

		if (showCredits) {
			drawCredits(g2);

		}

		if (gameFinished == true && !creditsActive) {

			g2.setFont(arial_40Italic);
			g2.setColor(Color.white);

			String text;
			int textLength;
			int x;
			int y;

			text = "You Beat The Game! Time: " + decimalFormat.format(playTime) + "!";
			textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

			x = gp.screenWidth / 2 - textLength / 2;
			y = gp.screenHeight / 2 - (gp.tileSize * 3);

			g2.drawString(text, x, y);

			g2.setFont(arial_80Bold);
			g2.setColor(Color.yellow);

			text = "Congratulations!";
			textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

			x = gp.screenWidth / 2 - textLength / 2;
			y = gp.screenHeight / 2 + (gp.tileSize * 2);

			g2.drawString(text, x, y);

			if (!showCredits) { // ONLY TRIGGER CREDITS ONCE
				messageCounter++;
				// After a short delay, trigger credits
				if (messageCounter > 300) { // ~5 seconds
					showCredits = true;

					creditsActive = true;
					messageCounter = 0;
				}
			}
		}

		if (gp.gameState == gp.TITLE_STATE) {

			drawTitleScreen(g2);
		}

		if (gp.gameState == gp.OPTIONS_STATE) {
			drawOptionsScreen(g2);
		}

		if (gp.gameState == gp.PAUSE_STATE) {
			drawPauseScreen(g2);

		}

		// TIME PLAYED

		if (gp.gameState == gp.PLAY_STATE && !gp.gamePaused && !gameFinished) {
			playTime += (double) 1 / 60;
		}

		// MESSAGE
		if (messageOn == true) {

			g2.setFont(g2.getFont().deriveFont(20F));
			g2.setColor(Color.WHITE);
			g2.drawString(message, gp.tileSize / 2 + 70, gp.tileSize * 5);

			messageCounter++;

			if (messageCounter > 180) {

				messageCounter = 0;
				messageOn = false;
			}
		}
	}

	public int getXforCenteredText(String text) {

		int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.getWidth() / 2 - textLength / 2;
		return x;
	}

	public void drawTitleScreen(Graphics2D g2) {

		g2.setColor(Color.BLUE);
		g2.fillRect(0, 0, gp.getWidth(), gp.getHeight());

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
		String text = "Crow Adventure";
		int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = getXforCenteredText(text);
		int y = gp.tileSize * 4;

		// SHADOW COLOR
		g2.setColor(Color.black);
		g2.drawString(text, x + 5, y + 5);

		// MAIN COLOR
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		// MENU BUTTONS
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40));

		text = "NEW GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize * 12;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">>", x - gp.tileSize - 15, y);
		}

		text = "LOAD GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize * 2;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">>", x - gp.tileSize - 15, y);
		}

		text = "OPTIONS";
		x = getXforCenteredText(text);
		y += gp.tileSize * 2;
		g2.drawString(text, x, y);
		if (commandNum == 2) {
			g2.drawString(">>", x - gp.tileSize - 15, y);
		}

		text = "QUIT";
		x = getXforCenteredText(text);
		y += gp.tileSize * 2;
		g2.drawString(text, x, y);
		if (commandNum == 3) {
			g2.drawString(">>", x - gp.tileSize - 15, y);
		}

	}

	public void drawOptionsScreen(Graphics2D g2) {
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		g2.setFont(arial_40Italic);
		g2.setColor(Color.white);
		String text = "Options Menu (Coming Soon)";
		int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		g2.drawString(text, gp.screenWidth / 2 - textWidth / 2, gp.screenHeight / 2 - 50);

		// Button setup
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 15F));
		int buttonWidth = 100;
		int buttonHeight = 25;
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 2 - 110;

		// DRAW KEYBIND BUTTON
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
		int keybindX = gp.screenWidth / 2 - buttonWidth / 2;
		int keybindY = gp.screenHeight / 2 + 50;
		keybindButtonBounds = new Rectangle(keybindX, keybindY + 40, buttonWidth, buttonHeight);

		g2.setColor(Color.white);
		g2.draw(keybindButtonBounds);
		g2.drawString("Keybind", keybindX + 20, keybindY + 60);

		// DRAW BACK BUTTON
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
		int backX = gp.screenWidth / 2 - buttonWidth / 2;
		int backY = gp.screenHeight / 2 + 50;
		backButtonBounds = new Rectangle(backX, backY + 80, buttonWidth, buttonHeight);

		g2.setColor(Color.white);
		g2.draw(backButtonBounds);
		g2.drawString("Back", backX + 25, backY + 100);

	}

	public void drawPauseScreen(Graphics2D g2) {

		// --- Box dimensions ---
		int boxWidth = gp.screenWidth / 2 + 100;
		int boxHeight = gp.screenHeight / 2;
		int BoxX = gp.screenWidth / 2 - boxWidth / 2;
		int BoxY = gp.screenHeight / 2 - boxHeight / 2;

		// --- Draw black box ---
		g2.setColor(new Color(0, 0, 0, 220)); // 220 alpha = slightly transparent
		g2.fillRoundRect(BoxX, BoxY, boxWidth, boxHeight, 30, 30);

		// --- Draw white outline ---
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(BoxX, BoxY, boxWidth, boxHeight, 30, 30);

		g2.setFont(arial_20Italic);
		g2.setColor(Color.white);

		int keyX = gp.screenWidth / 3 - gp.tileSize - 50;
		int keyY = gp.screenHeight / 3 - gp.tileSize / 2 + 20;
		g2.drawImage(keyImage, keyX, keyY, gp.tileSize / 2, gp.tileSize / 2, null);
		g2.drawString("x " + gp.player.hasKey, keyX + 25, keyY + 18);
		g2.drawString("ITEMS:", gp.screenWidth / 3 - gp.tileSize - 50, gp.screenHeight / 3 - gp.tileSize / 2 + 5);

		g2.drawString("Time: " + decimalFormat.format(playTime), gp.tileSize * 11, 60);

		g2.setFont(arial_30);
		g2.setColor(Color.white);

		String text = "PAUSED" + "  " + "Your Time Is: " + decimalFormat.format(playTime);
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 2 - 110;

		g2.drawString(text, x, y);

		// Button setup
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 15F));
		int buttonWidth = 100;
		int buttonHeight = 25;

		// Options Button
		int optionsX = gp.screenWidth / 2 - buttonWidth / 2;
		int optionsY = y + 120;
		optionsButtonBounds = new Rectangle(optionsX, optionsY - 35, buttonWidth, buttonHeight);
		g2.draw(optionsButtonBounds);
		g2.drawString("Options", optionsX + 25, optionsY - 20);

		// Resume Button
		int resumeX = gp.screenWidth / 2 - buttonWidth / 2;
		int resumeY = y + 160;
		resumeButtonBounds = new Rectangle(resumeX, resumeY - 35, buttonWidth, buttonHeight);
		g2.draw(resumeButtonBounds);
		g2.drawString("Resume", resumeX + 25, resumeY - 20);

		// Quit Button
		int quitX = gp.screenWidth / 2 - buttonWidth / 2;
		int quitY = y + 200;
		quitButtonBounds = new Rectangle(quitX, quitY - 35, buttonWidth, buttonHeight);
		g2.draw(quitButtonBounds);
		g2.drawString("Quit", quitX + 25, quitY - 20);

	}

	public void drawCredits(Graphics2D g2) {

		if (creditsShown == false) {

			// Fill background black
			g2.setColor(Color.black);
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

			g2.setColor(Color.white);
			g2.setFont(arial_30);

			String[] credits = {
					"CREDITS",
					"",
					"Programming: Jackson Chamberlin",
					" ",
					"Art & Design: ",
					" ",
					"Player Asset: ELV Games",
					"Boots Asset: Jackson Chamberlin",
					"Chest Asset: SkaldingDelight",
					"Key Asset: DantePixels",
					"Grass Asset: Jackson Chamberlin",
					"Mud Asset: Jackson Chamberlin",
					"Path Asset: Jackson Chamberlin",
					"Tree Asset: Jackson Chamberlin",
					"Wall Asset: Jackson Chamberlin",
					"Water Asset: Jackson Chamberlin",
					"NPC 1 - Chequered Ink",
					"Songs: ",
					" ",
					"Music: GameSong.wav by Clement Panchout",
					" ",
					"Sound Efects: ",
					" ",
					"TBD ",
					" ",
					"Special Thanks: You, the player!",
					" ",
					"Thank you for playing!"
			};

			int lineHeight = 25;
			int startY = gp.screenHeight + creditsScrollY;

			for (int i = 0; i < credits.length; i++) {
				String line = credits[i];
				int textWidth = (int) g2.getFontMetrics().getStringBounds(line, g2).getWidth();
				int x = gp.screenWidth / 2 - textWidth / 2;
				int y = startY + (i * lineHeight);
				g2.drawString(line, x, y);
			}

			creditsScrollY -= 1; // scroll speed

			// WHEN CREDITS ARE DONE SCROLLING

			if (startY + credits.length * lineHeight < gp.screenHeight / 2) {

				g2.setFont(arial_30);
				g2.setColor(Color.white);

				int buttonWidth = 160;
				int buttonHeight = 40;
				int spacing = 80;

				int baseX = gp.screenWidth / 2 - buttonWidth - spacing / 2;
				int baseY = gp.screenHeight - 150;

				// CONTINUE
				continueButtonBounds = new Rectangle(baseX, baseY, buttonWidth, buttonHeight);
				g2.draw(continueButtonBounds);
				g2.drawString("Continue", baseX + 30, baseY + 27);

				// QUIT
				int quitX = gp.screenWidth / 2 + spacing / 2;
				quitAfterCreditsButtonBounds = new Rectangle(quitX, baseY, buttonWidth, buttonHeight);
				g2.draw(quitAfterCreditsButtonBounds);
				g2.drawString("Quit", quitX + 55, baseY + 27);

			}
		}
	}

	public void handlePauseClick(int mouseX, int mouseY) {
		if (resumeButtonBounds.contains(mouseX, mouseY)) {
			gp.gameState = gp.PLAY_STATE; // Resume game
		} else if (optionsButtonBounds.contains(mouseX, mouseY)) {
			gp.gameState = gp.OPTIONS_STATE; // Go to options menu
		}
	}

	public void resetGameEndState() {
		gameFinished = false;
		showCredits = false;
		messageCounter = 0;
		creditsScrollY = 0;
	}
}
