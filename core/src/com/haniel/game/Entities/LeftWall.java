package com.haniel.game.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class LeftWall extends Entity{
	
	public LeftWall(int x, int y, boolean solid) {
		this.x = x;
		this.y = y;
		this.width = 20;
		this.height = 20;
		if (solid) {
			this.sprite = new Sprite(leftLedgeTexture);
			this.rectangle = new Rectangle((float) x, (float) y + height - 10, width, 10);
			this.solid = true;
		}else {
			this.sprite = new Sprite(blankLeftWallTexture);			
		}
		
	}

}