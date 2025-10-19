package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.gamePanel;

public class OBJ_DoubleDoor extends SuperObject {

	public int widthInTiles = 2;
	public int hieghtInTiles = 1;

	gamePanel gp;

	public OBJ_DoubleDoor(gamePanel gp) {

		this.gp = gp;

		name = "DoubleDoor";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/DoubleDoor.png"));
			uTool.scaleImage(image, gp.tileSize, gp.tileSize);

		} catch (IOException e) {
			e.printStackTrace();
		}
		collision = true;

		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = 2 * 48; // double width
		solidArea.height = 1 * 48; // single tile height

		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

	}

	@Override
	public void draw(Graphics2D g2, gamePanel gp) {

		// Draw relative to player's view, same as tiles
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;

		g2.drawImage(
				image, screenX, screenY, gp.tileSize * widthInTiles, gp.tileSize * hieghtInTiles, null);
	}

}
