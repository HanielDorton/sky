package com.haniel.game.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class RightWall extends Entity{
	
	public RightWall(int x, double y, boolean solid) {
		this.x = x;
		this.y = y;
		this.width = 20;
		this.height = 20;
		if (solid) {
			this.sprite = new Sprite(rightLedgeTexture);
			this.rectangle = new Rectangle((float) x, (float) y + height - 10, width, 10);
			this.solid = true;
		}else {
			this.sprite = new Sprite(blankRightWallTexture);			
		}
		
	}
}
