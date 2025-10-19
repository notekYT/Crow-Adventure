package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class gamePanel extends JPanel implements Runnable {

	// SCREEEN SETTINGS

	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;

	public final int tileSize = originalTileSize * scale; // 48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

	// FULLSCREEN SCALLING
	double Fscale = 1.0;
	int offsetX = 0;
	int offsetY = 0;
	public boolean fullScreenOn = true;
	private GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

	// WORLD SETINGS
	public final int maxWorldCol = 100;
	public final int maxWorldRow = 100;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;

	// GAME STATES
	public int gameState;
	public final int TITLE_STATE = 0;
	public final int PLAY_STATE = 1;
	public final int PAUSE_STATE = 2;
	public final int OPTIONS_STATE = 3;
	public final int KEYBIND_STATE = 4;
	public boolean gamePaused = false;

	// FPS
	int FPS = 60;

	// INITIATE CLASSES
	TileManager tileManager;
	KeyHandler KeyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound SE = new Sound();
	public CollisionChecker collisionChecker = new CollisionChecker(this);
	public AssetSetter assetSetter = new AssetSetter(this);
	public UI UI = new UI(this);

	Thread gameThread;

	// ENTITY AND OBJECT
	public Player player = new Player(this, KeyH);
	public SuperObject obj[] = new SuperObject[50];

	public gamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(KeyH);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (gameState == PAUSE_STATE) {
					int mx = (int) ((e.getX() - offsetX) / scale);
					int my = (int) ((e.getY() - offsetY) / scale);

					if (UI.resumeButtonBounds != null && UI.resumeButtonBounds.contains(mx, my)) {
						gamePaused = false;
						gameState = PLAY_STATE;
					} else if (UI.optionsButtonBounds != null && UI.optionsButtonBounds.contains(mx, my)) {
						gameState = OPTIONS_STATE; // You can handle this new state later
					} else if (UI.quitButtonBounds != null && UI.quitButtonBounds.contains(mx, my)) {
						System.exit(0);
					}
				} else if (gameState == OPTIONS_STATE) {

					int mx = (int) ((e.getX() - offsetX) / scale);
					int my = (int) ((e.getY() - offsetY) / scale);

					if (UI.backButtonBounds != null && UI.backButtonBounds.contains(mx, my)) {
						gameState = PAUSE_STATE;
					}
					if (UI.keybindButtonBounds != null && UI.keybindButtonBounds.contains(mx, my)) {
						gameState = KEYBIND_STATE;
					}

				}

				if (UI.showCredits) {

					int mx = (int) ((e.getX() - offsetX) / scale);
					int my = (int) ((e.getY() - offsetY) / scale);

					if (UI.continueButtonBounds != null && UI.continueButtonBounds.contains(mx, my)) {
						UI.showCredits = false;
						UI.resetGameEndState();
						gameState = PLAY_STATE; // go back to gameplay
						UI.messageOn = false;
						UI.creditsShown = true;
						UI.gameFinished = false;

					}

					if (UI.quitAfterCreditsButtonBounds != null && UI.quitAfterCreditsButtonBounds.contains(mx, my)) {
						System.exit(0);
					}
				}
			}
		});

		this.setFocusable(true);

		tileManager = new TileManager(this);

		setupGame();

	}

	public void setupGame() {

		assetSetter.setObject();

		playMusic(0);

		gameState = TITLE_STATE;
	}

	public void setupFullScreen(JFrame window) {

		// Get screen size from the system
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int windowWidth = screenSize.width;
		int windowHeight = screenSize.height;

		// Compute how much we can scale up your designed resolution
		double FscaleX = (double) windowWidth / screenWidth;
		double FscaleY = (double) windowHeight / screenHeight;
		Fscale = Math.min(FscaleX, FscaleY);

		// Compute offsets (to center the game)
		offsetX = (int) ((windowWidth - screenWidth * scale) / 2);
		offsetY = (int) ((windowHeight - screenHeight * scale) / 2);

		// Resize the window so it matches fullscreen size
		window.setPreferredSize(screenSize);
		window.pack();
	}

	public void toggleFullScreen(JFrame window) {
		fullScreenOn = !fullScreenOn;

		if (fullScreenOn) {
			// Go fullscreen
			window.dispose();
			window.setUndecorated(true);
			gd.setFullScreenWindow(window);

			// Let the window resize before calculating
			SwingUtilities.invokeLater(() -> {
				int newWidth = window.getWidth();
				int newHeight = window.getHeight();

				double screenWidthRatio = (double) newWidth / (tileSize * maxScreenCol);
				double screenHeightRatio = (double) newHeight / (tileSize * maxScreenRow);
				Fscale = Math.min(screenWidthRatio, screenHeightRatio);

				int scaledWidth = (int) ((tileSize * maxScreenCol) * scale);
				int scaledHeight = (int) ((tileSize * maxScreenRow) * scale);

				offsetX = (newWidth - scaledWidth) / 2;
				offsetY = (newHeight - scaledHeight) / 2;

				window.revalidate();
				window.repaint();
			});

			window.setVisible(true);
		} else {
			// Exit fullscreen
			gd.setFullScreenWindow(null);
			window.dispose();
			window.setUndecorated(false);
			window.setVisible(true);

			// Reset scale and offsets
			Fscale = 1.0;
			offsetX = 0;
			offsetY = 0;

			window.revalidate();
			window.repaint();
		}
	}

	public void startGameThread() {

		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {

		double drawInterval = 1000000000 / FPS; // 0.01666 seconds
		double nextDrawTime = System.nanoTime() + drawInterval;

		while (gameThread != null) {

			long currentTime = System.nanoTime();

			// 1. Update Information such as character positions
			update();

			// 2. Draw the screen with the updated information
			repaint();

			try {

				double remainingTime = nextDrawTime - System.nanoTime();
				// convert to milli seconds
				remainingTime = remainingTime / 1000000;

				if (remainingTime < 0) {
					remainingTime = 0;

				}

				Thread.sleep((long) remainingTime);

				nextDrawTime += drawInterval;

			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}
	}

	public void update() {

		if (gameState == PLAY_STATE) {
			player.update();
			hideCursor();
		}
		if (gameState == PAUSE_STATE) {
			showCursor();
		}
		if (gameState == OPTIONS_STATE) {
			showCursor();
		}

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		// TITLE SCREEN
		if (gameState == TITLE_STATE) {

			UI.draw(g2);
		}

		else {

			// Fill background black
			g2.setColor(Color.black);
			g2.fillRect(0, 0, getWidth(), getHeight());

			// Apply fullscreen scaling + centering
			g2.translate(offsetX, offsetY);
			g2.scale(scale, scale);

			// DRAW TILE
			tileManager.draw(g2);

			// DRAW OBJECT
			for (int i = 0; i < obj.length; i++) {

				if (obj[i] != null) {
					obj[i].draw(g2, this);
				}
			}

			// DRAW PLAYER
			player.draw(g2);

			// DRAW UI
			UI.draw(g2);
		}

		g2.dispose();
	}

	public void playMusic(int i) {

		music.setFile(i);
		music.play();
		music.loop();

	}

	public void stopMusic() {

		music.stop();
	}

	public void playSE(int i) {

		SE.setFile(i);
		SE.play();
	}

	public void hideCursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.createImage("");
		Cursor invisibleCursor = toolkit.createCustomCursor(
				image, new Point(0, 0), "invisibleCursor");
		setCursor(invisibleCursor);
	}

	public void showCursor() {
		setCursor(Cursor.getDefaultCursor());
	}
}
