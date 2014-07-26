package com.haniel.game.Entities;

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
	
	//Player
	protected static final Texture playerTexture = new Texture(Gdx.files.internal("alienPink.png"));
	protected static final Texture playerJumpRightTexture = new Texture(Gdx.files.internal("alienPink_jumpRight.png"));
	protected static final Texture playerJumpLeftTexture = new Texture(Gdx.files.internal("alienPink_jumpLeft.png"));
	protected static final Sound jumpSound = Gdx.audio.newSound(Gdx.files.internal("phaseJump2.ogg"));
	
	//World
	protected static final Texture grassTexture = new Texture(Gdx.files.internal("grassMid.png"));
	
	
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
	public void update() {
		
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
