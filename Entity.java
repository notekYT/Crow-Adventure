package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.UtillityTool;
import main.gamePanel;

public class Entity {

	gamePanel gp;

	public int worldX, worldY;
	public int speed;

	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction;

	public int spriteCounter = 0;
	public int spriteNum = 1;

	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;

	public Entity(gamePanel gp) {
		this.gp = gp;
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
}
