package main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setAlwaysOnTop(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setUndecorated(true);
		window.setResizable(false);
		window.setTitle("Game");
		
		gamePanel gamePanel = new gamePanel();
		window.add(gamePanel);
		
		window.pack();
		
		gamePanel.setupFullScreen(window);
		
		 GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	        if (gd.isFullScreenSupported()) {
	            gd.setFullScreenWindow(window);
	        } else {
	            window.setExtendedState(JFrame.MAXIMIZED_BOTH);
	        }
		window.setVisible(true);
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
		
		
		
	}

}

