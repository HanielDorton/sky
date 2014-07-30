package com.haniel.game.Backgrounds;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Tower extends Background {
	
	public Tower( double x, double y, double moveY) {
		super(x, y, moveY);
		this.sprite = new Sprite(towerTexture);
		this.height = 480;
	}

}
