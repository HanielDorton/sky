package com.haniel.game.Backgrounds;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Star extends Background{
	
	
	public Star(double x, double y, double moveY) {
		super(x, y, moveY);
		this.height = 1;
		this.sprite = new Sprite(starTexture);
	}


}
