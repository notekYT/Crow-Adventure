package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class KeyHandler implements KeyListener {

	gamePanel gp;

	public KeyHandler(gamePanel gp) {
		this.gp = gp;
	}

	public boolean upPressed, downPressed, leftPressed, rightPressed, escPressed;

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();

		// PLAY STATE
		if (code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = true;

		}
		if (code == KeyEvent.VK_A) {
			leftPressed = true;

		}
		if (code == KeyEvent.VK_D) {
			rightPressed = true;

		}

		// PAUSE STATE

		// OPTIONS STATE

		// TITLE STATE
		if (gp.gameState == gp.TITLE_STATE) {
			if (code == KeyEvent.VK_UP) {
				gp.UI.commandNum--;
				if (gp.UI.commandNum < 0) {
					gp.UI.commandNum = 3;
				}
			}
			if (code == KeyEvent.VK_DOWN) {
				gp.UI.commandNum++;
				if (gp.UI.commandNum > 3) {
					gp.UI.commandNum = 0;
				}
			}
			if (code == KeyEvent.VK_ENTER) {
				if (gp.UI.commandNum == 0) {
					gp.gameState = gp.PLAY_STATE;

				}
				if (gp.UI.commandNum == 1) {
					// LOAD GAME
				}
				if (gp.UI.commandNum == 2) {
					gp.gameState = gp.OPTIONS_STATE;
				}
				if (gp.UI.commandNum == 3) {
					System.exit(0);
				}
			}
		}

		// ESCAPE HOTKEY
		if (code == KeyEvent.VK_ESCAPE) {

			gp.gamePaused = !gp.gamePaused;

			if (gp.gameState == gp.PLAY_STATE) {
				gp.gameState = gp.PAUSE_STATE;
			} else if (gp.gameState == gp.PAUSE_STATE) {
				gp.gameState = gp.PLAY_STATE;

			}

		}
		if (code == KeyEvent.VK_F11) {
			gp.toggleFullScreen((JFrame) SwingUtilities.getWindowAncestor(gp));
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W) {
			upPressed = false;

		}
		if (code == KeyEvent.VK_S) {
			downPressed = false;

		}
		if (code == KeyEvent.VK_A) {
			leftPressed = false;

		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;

		}

	}

}
