package com.haniel.game.Entities;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.haniel.game.GameScreen;

public class Player extends Entity{
	
	private Sprite standSprite = new Sprite(playerTexture);
	private Sprite jumpRightSprite = new Sprite(playerJumpRightTexture);
	private Sprite jumpLeftSprite = new Sprite(playerJumpLeftTexture);
	private boolean jumping = false;
	private CatmullRomSpline<Vector2> jumpAngle;
	protected float current = 0;
	private int speed = 100;
	private GameScreen gameScreen;
	private int xOffset;
	private float lastCurrent = 0;
	
	public Player(GameScreen gameScreen) {
		this.x = 145;
		this.y = 20;
		this.xOffset = 7;
		this.width = 19;
		this.height = 46;
		this.sprite = standSprite;
		this.rectangle = new Rectangle((float) x + xOffset, (float) y, width, 5);
		this.gameScreen = gameScreen;
	}
	
	//everything is ging by x,y in bottom right corner, need to change to middle!
	
	public void update() {
		int midX = (int) x + xOffset + (width / 2);
		if (!jumping) {
			if (Gdx.input.isTouched()) {
		        Vector2 touchPos = new Vector2();
		        touchPos.set(Gdx.input.getX(), 480 - Gdx.input.getY());
		        Vector2 landingGoal = new Vector2();
		        if( touchPos.y > y+30) {
			        if (touchPos.x >= midX){
			        	landingGoal.set( (float) (midX + ((touchPos.x - midX)*2)), -50);	
			        	this.sprite = jumpRightSprite;
			        } else {
			        	landingGoal.set( (float) ( midX - (midX - touchPos.x)*2), -50);
			        	this.sprite = jumpLeftSprite;
			        }
			        jumping = true;
			        if ((gameScreen.score / 10) < 1800) jumpSound.play();
			        jumpAngle = new CatmullRomSpline<Vector2>(new Vector2[] {
			        new Vector2((float) midX, (float)y), touchPos, landingGoal}, true);
			        current = 0;
		        }
		    }
		}
		if (jumping) {
			Vector2 out1 = new Vector2();
			jumpAngle.derivativeAt(out1, current);
			current += (Gdx.graphics.getDeltaTime() * speed) / out1.len();
			if(current >= 1) current -= 1;
			if (current - lastCurrent > .03) {
				current = lastCurrent + .02f;
			}
			lastCurrent = current;			
			Vector2 out = new Vector2();
			jumpAngle.valueAt(out, current);
			this.x = out.x - xOffset - (width / 2);
			this.y = out.y;
			if (current > .24) {
				setPosition();
				Iterator<Entity> entityIterator = gameScreen.entities.iterator();
		    	while (entityIterator.hasNext()) {
		    		Entity e = entityIterator.next();
		    		if (e.isSolid()){
			    		if (e.getRectangle().overlaps(rectangle)) {
							jumping = false;
							this.sprite = standSprite;
							this.y = e.getY() + e.getHeight();
			    			
			    		}
		    		}
		    	}
			}
			
			
		}
		if (x < 0 - width || x > 320 || y < 0 - height) {
			x = 145;
			y = 20;
			jumping = false;
			this.sprite = standSprite;
		}
		setPosition();
	}
	
	public boolean isJumping() {
		return jumping;
	}
	public void setPosition() {
		rectangle.setPosition((float)x + xOffset, (float)y);
	}
	public void movePlayerDown(float difficulty) {
		y -= difficulty * Gdx.graphics.getDeltaTime();
	}
	public double getTopY() {
		return y + height;
	}
	public void increaseSpeed(int increase) {
		speed += increase;
	}

}
