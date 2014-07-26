package com.haniel.game.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class GrassTile extends Entity{
	
	public GrassTile(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 20;
		this.height = 20;
		this.sprite = new Sprite(grassTexture);
		this.rectangle = new Rectangle((float) x, (float) y + height - 10, width, 10);
		this.solid = true;
	}

}
