package com.haniel.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.haniel.game.Entities.Entity;
import com.haniel.game.Entities.GrassTile;
import com.haniel.game.Entities.LeftWall;
import com.haniel.game.Entities.MidWall;
import com.haniel.game.Entities.Player;
import com.haniel.game.Entities.RightWall;


public class GameScreen implements Screen{
	
	OrthographicCamera camera;
	final Sky game;
	public List<Entity> entities = new ArrayList<Entity>();
    public Player player = new Player(this); 
    public int width = 320;
    public int height = 480;
    private Random rand = new Random();
    private float difficulty = 30;
    private float meters = 0;
    private int score = 0;
	
	public GameScreen(final Sky gam) {
    	this.game = gam;  
    	camera = new OrthographicCamera();
        camera.setToOrtho(false, 320, 480);
        createStage();
  
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 204, 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Sky.batch.begin();
		drawEntities();
		Sky.batch.draw(player.getSprite(), (float) player.getX(), (float) player.getY());
		Sky.batch.end();
		
		player.update();
		updateEntities(difficulty);
		updateMeters();
		
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
	
	private void updateMeters() {
		meters += difficulty * Gdx.graphics.getDeltaTime();
		if (player.getTopY() + meters > score) score = (int) (player.getTopY() + meters);
		System.out.println("Meters: " + (score / 20));
	}
	
	private void updateEntities(float difficulty) {
    	Iterator<Entity> entityIterator = entities.iterator();
    	while (entityIterator.hasNext()) {
    		Entity e = entityIterator.next();
        	e.update(difficulty);        	
    	}
    	//if player is standing on a ledge, move him down same speed as ledge
    	if (!player.isJumping()) {
    		player.movePlayerDown(difficulty);
    		player.setPosition();
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
	
    private void createStage() {
        for (int i = 0; i < 17; i++) {
        	add(new GrassTile(i * 20,0));
        }
        for (int i = 0; i < 36; i++) {
        	addBlankWall(20 + (i * 20));
        }
    }
    //stage is 16 squares of 20 across
    //lets do borders of 2
    //so 12 rows starting at 40

    
    private void addBlankWall(int y) {
    	add(new LeftWall(40, y, false));
    	for (int i = 0; i < 10; i++) {
    		add(new MidWall(60 + (20 * i), y, false));
    	}
    	add(new RightWall(260, y, false));
    	if (rand.nextInt(5) == 4) addSingleLedge(y);
    	if (rand.nextInt(10) == 9) addDoubleLedge(y);
    	if (rand.nextInt(30) == 29) addTripleLedge(y);
    }
    
    private void addSingleLedge(int y) {
    	int ledge = rand.nextInt(12);
    	switch (ledge) {
    	case 0: {
    		add(new LeftWall(40, y, true));
    		break;
    	}
    	case 11: {
    		add(new RightWall(260, y, true));
    		break;
    	}
    	default: {
    		add(new MidWall(40 + (ledge * 20), y, true));    		
    	}
    			
    	}

    }
    
    private void addDoubleLedge(int y) {
    	int ledge = rand.nextInt(11);
    	switch (ledge) {
	    	case 0: {
	    		add(new LeftWall(40, y, true));
	    		add(new MidWall(60, y, true));
	    		break;
	    	}
	    	case 10: {
	    		add(new MidWall(240, y, true));
	    		add(new RightWall(260, y, true));
	    		break;
	    	}
	    	default: {
	    		add(new MidWall(40 + (ledge * 20), y, true));    
	    		add(new MidWall(60 + (ledge * 20), y, true));
	    	}    			
    	}
    }
    
    private void addTripleLedge(int y) {
    	int ledge = rand.nextInt(10);
    	switch (ledge) {
	    	case 0: {
	    		add(new LeftWall(40, y, true));
	    		add(new MidWall(60, y, true));
	    		add(new MidWall(80, y, true));
	    		break;
	    	}
	    	case 9: {
	    		add(new MidWall(220, y, true));
	    		add(new MidWall(240, y, true));
	    		add(new RightWall(260, y, true));	    		
	    		break;
	    	}
	    	default: {
	    		add(new MidWall(40 + (ledge * 20), y, true));    
	    		add(new MidWall(60 + (ledge * 20), y, true));
	    		add(new MidWall(80+ (ledge * 20), y, true));
	    	}    			
    	}
    }
	
}
