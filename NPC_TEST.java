package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.gamePanel;

public class NPC_TEST extends Entity {

    public NPC_TEST(gamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
    }

    public void getPlayerImage() {

        up1 = setup("");
        up2 = setup("");
        down1 = setup("");
        down2 = setup("");
        left1 = setup("");
        left2 = setup("");
        right1 = setup("");
        right2 = setup("");
    }
}
