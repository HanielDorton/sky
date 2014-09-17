package com.haniel.game.Entities;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.haniel.game.Assets;

public abstract class Entity {
	
	protected double x, y;
	protected int width, height;
	protected Sprite sprite;
	protected Rectangle rectangle;
	protected boolean removed = false;
	protected boolean solid = false;
	protected static final Random rand = new Random();
	
	//Player
	protected Texture playerTexture = Assets.manager.get("alienPink.png", Texture.class);
	protected Texture playerJumpRightTexture = Assets.manager.get("alienPink_jumpRight.png", Texture.class);
	protected Texture playerJumpLeftTexture = Assets.manager.get("alienPink_jumpLeft.png", Texture.class);
	public Sound jumpSound = Assets.manager.get("phaseJump2.ogg",Sound.class);
	
	//World
	protected Texture grassTexture = Assets.manager.get("grassMid.png", Texture.class);
	protected Texture moonTexture = Assets.manager.get("Moon.png", Texture.class);
	
	//Building
	
	protected Texture blankWallTexture2 = Assets.manager.get("houseDarkAlt.png", Texture.class);
	protected Texture blankWallTexture3 = Assets.manager.get("houseDarkAlt2.png", Texture.class);
	protected Texture midLedgeTexture = Assets.manager.get("houseDarkLedge.png", Texture.class);
	protected Texture leftLedgeTexture = Assets.manager.get("houseDarkLedgeLeft.png", Texture.class);
	protected Texture rightLedgeTexture = Assets.manager.get("houseDarkLedgeRight.png", Texture.class);
	protected Texture blankRightWallTexture = Assets.manager.get("houseDarkMidRight.png", Texture.class);
	protected Texture blankLeftWallTexture = Assets.manager.get("houseDarkMidLeft.png", Texture.class);
	
	
	public Entity() {
		
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
	
	public Rectangle getRectangle() {
		return rectangle;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	public void update(float difficulty) {
		this.y -= difficulty * Gdx.graphics.getDeltaTime();
		this.rectangle = new Rectangle((float) x, (float) y + height - 10, width, 10);
		if (this.y <= 0 - (this.width * 2)) remove();
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
	public int getHeight() {
		return height;
	}
	public boolean isSolid() {
		return solid;
	}

	public static void dispose() {
	}
}
