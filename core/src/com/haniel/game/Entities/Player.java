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
	private int speed = 160;
	private GameScreen gameScreen;
	private int xOffset;
	
	public Player(GameScreen gameScreen) {
		this.x = 145;
		this.y = 20;
		this.xOffset = 7;
		this.width = 19;
		this.height = 46;
		this.sprite = standSprite;
		this.rectangle = new Rectangle((float) x + xOffset, (float) y, width, 3);
		this.gameScreen = gameScreen;
	}
	
	public void update() {
		if (!jumping) {
			if (Gdx.input.isTouched()) {
		        Vector2 touchPos = new Vector2();
		        touchPos.set(Gdx.input.getX(), 480 - Gdx.input.getY());
		        Vector2 landingGoal = new Vector2();
		        if( touchPos.y > y+30) {
			        if (touchPos.x >= x){
			        	landingGoal.set( (float) (x + ((touchPos.x - x)*2)), -50);	
			        	this.sprite = jumpRightSprite;
			        } else {
			        	landingGoal.set( (float) ( x - (x - touchPos.x)*2), -50);
			        	this.sprite = jumpLeftSprite;
			        }
			        jumping = true;
			        jumpSound.play();
			        jumpAngle = new CatmullRomSpline<Vector2>(new Vector2[] {
			        new Vector2((float) x, (float)y), touchPos, landingGoal}, true);
			        current = 0;
		        }
		    }
		}
		if (jumping) {
			Vector2 out1 = new Vector2();
			jumpAngle.derivativeAt(out1, current);
			current += (Gdx.graphics.getDeltaTime() * speed) / out1.len();
			if(current >= 1) current -= 1;
			Vector2 out = new Vector2();
			jumpAngle.valueAt(out, current);
			this.x = out.x;
			this.y = out.y;
		    rectangle.setPosition((float)x + xOffset, (float)y);
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
		if (x < 0 - width || x > 320 || y < 0 - height) {
			x = 145;
			y = 20;
			jumping = false;
			this.sprite = standSprite;
		}
	    rectangle.setPosition((float)x + xOffset, (float)y);
	}

}
