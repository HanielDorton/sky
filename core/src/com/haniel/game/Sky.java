package com.haniel.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haniel.game.Backgrounds.Background;
import com.haniel.game.Entities.Entity;


public class Sky extends Game {
	public static SpriteBatch batch;
    public BitmapFont font;
	OrthographicCamera camera;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
        font = new BitmapFont();
		this.setScreen(new MenuScreen(this, camera));
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
       Background.dispose();
	   Entity.dispose();
	   //MenuScreen.dispose();
    }
}
