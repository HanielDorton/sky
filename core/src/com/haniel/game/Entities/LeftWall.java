package com.haniel.game.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class LeftWall extends Entity{
	
	public LeftWall(int x, double d, boolean solid) {
		this.x = x;
		this.y = d;
		this.width = 20;
		this.height = 20;
		if (solid) {
			this.sprite = new Sprite(leftLedgeTexture);
			this.rectangle = new Rectangle((float) x, (float) d + height - 10, width, 10);
			this.solid = true;
		}else {
			this.sprite = new Sprite(blankLeftWallTexture);			
		}
		
	}

}
