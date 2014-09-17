package com.haniel.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haniel.game.Google.ActionResolver;



public class Sky extends Game {
	public static SpriteBatch batch;
    public BitmapFont font;
	OrthographicCamera camera;
	public ActionResolver actionResolver;
	
	public Sky(ActionResolver actionResolve) {
        this.actionResolver = actionResolve;
    }
	
	@Override
	public void create () {

		camera = new OrthographicCamera();
		batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new LoadingScreen(this, camera, actionResolver));
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void resize(int width, int height) {	    
	}
	
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
