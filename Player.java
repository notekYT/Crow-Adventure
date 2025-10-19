package entity;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.KeyHandler;
import main.UtillityTool;
import main.gamePanel;

public class Player extends Entity {

	KeyHandler KeyH;

	public final int screenX;
	public final int screenY;
	public int hasKey = 0;
	public boolean displayHitboxes = false;
	private boolean bootsActive = false;
	private long bootsStartTime = 0; // in milliseconds
	public final long BOOTS_DURATION = 12000; // 12 seconds in ms
	public String bootsMessage = "";

	public Player(gamePanel gp, KeyHandler KeyH) {

		super(gp);

		this.KeyH = KeyH;

		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 28;
		solidArea.height = 28;

		setDefaultValues();
		getPlayerImage();

	}

	public void setDefaultValues() {

		worldX = gp.tileSize * 50;
		worldY = gp.tileSize * 50;
		speed = 4;
		direction = "down";
	}

	public void getPlayerImage() {

		try {

			BufferedImage playerAssetSheet = ImageIO
					.read(getClass().getResourceAsStream("/player/PlayerAssetSheet.png"));
			int frameWidth = playerAssetSheet.getWidth() / 4;
			int frameHeight = playerAssetSheet.getHeight() / 4;

			down1 = playerAssetSheet.getSubimage(0, 0 * frameHeight, frameWidth, frameHeight);

			up1 = playerAssetSheet.getSubimage(0, 3 * frameHeight, frameWidth, frameHeight);

			left1 = playerAssetSheet.getSubimage(0, 1 * frameHeight, frameWidth, frameHeight);

			right1 = playerAssetSheet.getSubimage(0, 2 * frameHeight, frameWidth, frameHeight);

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public BufferedImage setup(String imageName) {

		UtillityTool uTool = new UtillityTool();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public void update() {

		if (bootsActive) {
			long elapsed = System.currentTimeMillis() - bootsStartTime;
			long remaining = BOOTS_DURATION - elapsed;
			if (remaining > 0) {

				bootsMessage = "Boots effect: " + (remaining / 1000 + 1) + "s remaining!";
				gp.UI.showMessage(bootsMessage);
			} else {

				speed -= 2; // reset speed
				bootsActive = false;
				gp.UI.showMessage("Boots effect has worn off!");
			}
		}

		if (KeyH.upPressed || KeyH.downPressed || KeyH.leftPressed || KeyH.rightPressed) {
			if (KeyH.upPressed == true) {
				direction = "up";

			} else if (KeyH.downPressed == true) {
				direction = "down";

			} else if (KeyH.leftPressed == true) {
				direction = "left";

			} else if (KeyH.rightPressed == true) {
				direction = "right";

			}

			// CHECK TILE COLLISION
			collisionOn = false;
			gp.collisionChecker.checkTile(this);

			// CHECK OBJECT COLLISION
			int objIndex = gp.collisionChecker.checkObject(this, true);
			pickUpObject(objIndex);

			// IF COLLISION IS FALSE PLAYER CAN MOVE
			if (collisionOn == false) {

				switch (direction) {
					case "up":
						worldY -= speed;
						break;
					case "down":
						worldY += speed;
						break;
					case "left":
						worldX -= speed;
						break;
					case "right":
						worldX += speed;
						break;
				}
			}
		}
	}

	public void pickUpObject(int i) {

		if (i != 999) {
			String objectName = gp.obj[i].name;

			switch (objectName) {

				case "Key":
					gp.playSE(1);
					hasKey++;
					gp.obj[i] = null;
					gp.UI.showMessage("Picked Up Key!");
					break;
				case "Door":
					if (hasKey > 0) {
						gp.playSE(4);
						gp.obj[i] = null;
						hasKey--;
						gp.UI.showMessage("You Opened The Door!");
					} else {
						gp.UI.showMessage("You Need A Key!");
					}
				case "DoubleDoor":
					if (hasKey > 0) {
						gp.playSE(4);
						gp.obj[i] = null;
						hasKey--;
						gp.UI.showMessage("You Opened The Double Door!");
					} else {
						gp.UI.showMessage("You Need A Key!");
					}
					break;
				case "Boots":
					gp.playSE(3);
					speed += 2;
					gp.obj[i] = null;
					gp.UI.showMessage("You Found Boots! Speed Up!");

					bootsActive = true;
					bootsStartTime = System.currentTimeMillis(); // start timer

					break;
				case "Chest":
					gp.UI.gameFinished = true;
					gp.stopMusic();
					gp.playSE(2);
					break;

			}
		}
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;

		switch (direction) {

			case "up":
				image = up1;
				break;

			case "down":
				image = down1;
				break;

			case "left":
				image = left1;
				break;

			case "right":
				image = right1;
				break;
		}
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

		if (displayHitboxes == true) {

			g2.setColor(Color.red);
			g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height); // Optional.
																											// Displays
																											// Hitboxes
		}

	}
}
