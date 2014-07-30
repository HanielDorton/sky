package com.haniel.game.Backgrounds;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Field extends Background{
	
	public Field(double x, double y, double moveY) {
		super(x, y, moveY);
		this.sprite = new Sprite(fieldTexture);
		this.height = 20;
	}

}
