package main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_DoubleDoor;
import object.OBJ_Key;

public class AssetSetter {

	gamePanel gp;

	public AssetSetter(gamePanel gp) {

		this.gp = gp;
	}

	public void setObject() {

		gp.obj[0] = new OBJ_Key(gp);
		gp.obj[0].worldX = 20 * gp.tileSize;
		gp.obj[0].worldY = 41 * gp.tileSize;

		gp.obj[1] = new OBJ_Key(gp);
		gp.obj[1].worldX = 97 * gp.tileSize;
		gp.obj[1].worldY = 23 * gp.tileSize;

		gp.obj[2] = new OBJ_Key(gp);
		gp.obj[2].worldX = 4 * gp.tileSize;
		gp.obj[2].worldY = 58 * gp.tileSize;

		gp.obj[3] = new OBJ_Key(gp);
		gp.obj[3].worldX = 65 * gp.tileSize;
		gp.obj[3].worldY = 32 * gp.tileSize;

		gp.obj[4] = new OBJ_Key(gp);
		gp.obj[4].worldX = 7 * gp.tileSize;
		gp.obj[4].worldY = 98 * gp.tileSize;

		gp.obj[11] = new OBJ_Door(gp);
		gp.obj[11].worldX = 35 * gp.tileSize;
		gp.obj[11].worldY = 64 * gp.tileSize;

		gp.obj[21] = new OBJ_DoubleDoor(gp);
		gp.obj[21].worldX = 20 * gp.tileSize;
		gp.obj[21].worldY = 25 * gp.tileSize;

		gp.obj[22] = new OBJ_DoubleDoor(gp);
		gp.obj[22].worldX = 20 * gp.tileSize;
		gp.obj[22].worldY = 12 * gp.tileSize;

		gp.obj[23] = new OBJ_DoubleDoor(gp);
		gp.obj[23].worldX = 8 * gp.tileSize;
		gp.obj[23].worldY = 52 * gp.tileSize;

		gp.obj[24] = new OBJ_DoubleDoor(gp);
		gp.obj[24].worldX = 8 * gp.tileSize;
		gp.obj[24].worldY = 65 * gp.tileSize;

		gp.obj[31] = new OBJ_Chest(gp);
		gp.obj[31].worldX = 47 * gp.tileSize;
		gp.obj[31].worldY = 66 * gp.tileSize;

		gp.obj[32] = new OBJ_Chest(gp);
		gp.obj[32].worldX = 46 * gp.tileSize;
		gp.obj[32].worldY = 60 * gp.tileSize;

		gp.obj[33] = new OBJ_Chest(gp);
		gp.obj[33].worldX = 46 * gp.tileSize;
		gp.obj[33].worldY = 72 * gp.tileSize;

		gp.obj[34] = new OBJ_Chest(gp);
		gp.obj[34].worldX = 42 * gp.tileSize;
		gp.obj[34].worldY = 58 * gp.tileSize;

		gp.obj[35] = new OBJ_Chest(gp);
		gp.obj[35].worldX = 42 * gp.tileSize;
		gp.obj[35].worldY = 73 * gp.tileSize;

		gp.obj[36] = new OBJ_Boots(gp);
		gp.obj[36].worldX = 25 * gp.tileSize;
		gp.obj[36].worldY = 19 * gp.tileSize;

		gp.obj[37] = new OBJ_Boots(gp);
		gp.obj[37].worldX = 5 * gp.tileSize;
		gp.obj[37].worldY = 58 * gp.tileSize;

		gp.obj[38] = new OBJ_Boots(gp);
		gp.obj[38].worldX = 20 * gp.tileSize;
		gp.obj[38].worldY = 83 * gp.tileSize;

		gp.obj[39] = new OBJ_Boots(gp);
		gp.obj[39].worldX = 90 * gp.tileSize;
		gp.obj[39].worldY = 21 * gp.tileSize;

		gp.obj[40] = new OBJ_Boots(gp);
		gp.obj[40].worldX = 35 * gp.tileSize;
		gp.obj[40].worldY = 47 * gp.tileSize;

	}
}
