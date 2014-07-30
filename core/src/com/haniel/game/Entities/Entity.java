package com.haniel.game.Entities;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
	
	protected double x, y;
	protected int width, height;
	protected Sprite sprite;
	protected Rectangle rectangle;
	protected boolean removed = false;
	protected boolean solid = false;
	protected static final Random rand = new Random();
	
	//Player
	protected static final Texture playerTexture = new Texture(Gdx.files.internal("alienPink.png"));
	protected static final Texture playerJumpRightTexture = new Texture(Gdx.files.internal("alienPink_jumpRight.png"));
	protected static final Texture playerJumpLeftTexture = new Texture(Gdx.files.internal("alienPink_jumpLeft.png"));
	protected static final Sound jumpSound = Gdx.audio.newSound(Gdx.files.internal("phaseJump2.ogg"));
	
	//World
	protected static final Texture grassTexture = new Texture(Gdx.files.internal("grassMid.png"));
	
	//Building
	
	protected static final Texture blankWallTexture2 = new Texture(Gdx.files.internal("houseDarkAlt.png"));
	protected static final Texture blankWallTexture3 = new Texture(Gdx.files.internal("houseDarkAlt2.png"));
	protected static final Texture midLedgeTexture = new Texture(Gdx.files.internal("houseDarkLedge.png"));
	protected static final Texture leftLedgeTexture = new Texture(Gdx.files.internal("houseDarkLedgeLeft.png"));
	protected static final Texture rightLedgeTexture = new Texture(Gdx.files.internal("houseDarkLedgeRight.png"));
	protected static final Texture blankRightWallTexture = new Texture(Gdx.files.internal("houseDarkMidRight.png"));
	protected static final Texture blankLeftWallTexture = new Texture(Gdx.files.internal("houseDarkMidLeft.png"));
	
	
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

}
