package com.haniel.game.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class MidWall extends Entity{
	
	
	
	public MidWall(int x, double y, boolean solid) {
		this.x = x;
		this.y = y;
		this.width = 20;
		this.height = 20;
		if (solid) {
			this.sprite = new Sprite(midLedgeTexture);
			this.rectangle = new Rectangle((float) x, (float) y + height - 10, width, 10);
			this.solid = true;
		}else {
			if (rand.nextInt(2) == 1) this.sprite = new Sprite(blankWallTexture2);
			else this.sprite = new Sprite(blankWallTexture3);			
		}
		
	}
}
