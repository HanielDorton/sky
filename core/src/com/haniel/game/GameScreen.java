package com.haniel.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.haniel.game.Entities.Entity;
import com.haniel.game.Entities.GrassTile;
import com.haniel.game.Entities.Player;


public class GameScreen implements Screen{
	
	OrthographicCamera camera;
	final Sky game;
	public List<Entity> entities = new ArrayList<Entity>();
    public Player player = new Player(this); 
    public int width = 320;
    public int height = 480;
	
	public GameScreen(final Sky gam) {
    	this.game = gam;  
    	camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        for (int i = 0; i < 17; i++) {
        	add(new GrassTile(i * 20,0));
        }
        add(new GrassTile(50, 100));
        add(new GrassTile(100, 200));
        add(new GrassTile(70, 300));
        add(new GrassTile(0, 350));
        add(new GrassTile(20, 350));
        add(new GrassTile(40, 350));
        add(new GrassTile(200, 40));
        
        add(new GrassTile(300, 100));
        add(new GrassTile(320, 100));
        add(new GrassTile(340, 100));
        add(new GrassTile(280, 370));
        add(new GrassTile(260, 370));
        add(new GrassTile(240, 350));
        add(new GrassTile(220, 330));
        
        
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 204, 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Sky.batch.begin();
		drawEntities();
		Sky.batch.draw(player.getSprite(), (float) player.getX(), (float) player.getY());
		Sky.batch.end();
		
		player.update();
		updateEntities();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	private void updateEntities() {
    	Iterator<Entity> entityIterator = entities.iterator();
    	while (entityIterator.hasNext()) {
    		Entity e = entityIterator.next();
        	e.update();
    	}
	}
	
	private void drawEntities() {
		Iterator<Entity> entityIterator = entities.iterator();
    	while (entityIterator.hasNext()) {
    		Entity e = entityIterator.next();
    		Sky.batch.draw(e.getSprite(), (float) e.getX(), (float) e.getY());
    	}
	}
	
    public void add(Entity e) {
    	entities.add(e);
    }  
	
	
}
