package com.haniel.game.Backgrounds;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Moon extends Background{
	
	public Moon(double x, double y, double moveY) {
		super(x, y, moveY);
		this.sprite = new Sprite(moonTexture);
		this.height = 200;
	}
	
	public void update() {
		move(.5, -moveY);		
	}


}
