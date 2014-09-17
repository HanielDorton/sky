package com.haniel.game.Backgrounds;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.haniel.game.Assets;

public abstract class Background {
	protected double x, y;
	protected Sprite sprite;
	protected boolean removed = false;
	protected static final Random rand = new Random();
	protected double moveY;
	protected int height;
	
	protected Texture towerTexture = Assets.manager.get("tower.png", Texture.class);
	protected Texture fieldTexture = Assets.manager.get("field.png", Texture.class);
	protected Texture starTexture = Assets.manager.get("star.png", Texture.class);
	protected Texture moonTexture = Assets.manager.get("Moon.png", Texture.class);
	
	public Background(double x, double y, double moveY) {
		this.x = x;
		this.y = y;
		this.moveY = moveY;		
	}
	
	public void move(double xa, double ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		ya *= Gdx.graphics.getDeltaTime();//  * speed);
		xa *= Gdx.graphics.getDeltaTime();//  * speed);
		
		while (xa != 0) {
			if(Math.abs(xa) > 1) {
				this.x += myAbs(xa);
				xa -= myAbs(xa);		
			}
			else {
				this.x += xa;
				xa = 0;
			}			
		}	
		
		while (ya != 0) {
			if(Math.abs(ya) > 1) {
				this.y += myAbs(ya) ;
				ya -= myAbs(ya);
			} else {
				this.y += ya;
				ya = 0;
			}
		}
		
	}
	
	protected int myAbs(double value) {
		if (value < 0) return -1;
		else return 1;
	}
	
	public void remove() {
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	public Sprite getSprite() {
		return sprite;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

	public void update() {
		move(0, -moveY);
		if (this.y + this.height < 0) remove();
		
	}
	public static void dispose() {
	}
}
