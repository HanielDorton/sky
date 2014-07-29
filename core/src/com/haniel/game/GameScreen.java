package com.haniel.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
	public List<BackGround> backgrounds = new ArrayList<BackGround>();
    public Player player; 
    public int width = 320;
    public int height = 480;
    private Random rand = new Random();
    private float difficulty = 30;
    private float difficultyIncreaseBar = 1;
    private float countToIncreaseDifficulty = 0;
    private float meters = 0;
    public float score = 0;
    private float difficultyIncrease = 20;
    private RightWall lastBrick;
    private boolean addedSingle;
    private float green = 0.9f;
    private float blue = 1;
    protected Music backgroundMusic;
    private boolean addedStars = false;
    public boolean debug = false;
    
	
	public GameScreen(final Sky gam, OrthographicCamera camera) {
    	this.game = gam;
    	this.camera = camera;
    	this.player  = new Player(this, camera);    	
        camera.setToOrtho(true, 320, 480);
        createStage();
  
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, green, blue, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		Sky.batch.setProjectionMatrix(camera.combined);
		Sky.batch.begin();
		drawBackGround();
		
		drawEntities();
		Sky.batch.draw(player.getSprite(), (float) player.getX(), (float) player.getY());
		drawText();
		Sky.batch.end();
		
		player.update();
		if (difficulty > 30) updateEntities(difficulty);
		updateMeters();
		updateDifficulty();
		continueBuilding();
		if (blue > 0) updateColors();
		levelLogic();
		System.out.println(entities.size());
		
	}

	public void show() {
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("LittlePeopleAtWork.mp3"));
		backgroundMusic.setVolume(.5f);
		backgroundMusic.play();
		
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
	
	public void resize(int width, int height) {
	    camera.setToOrtho(false, width, height);
	}
	
	private void drawText() {
		game.font.draw(Sky.batch, "Meters: " + ( score / 100), 10, 470);
	}
	
	public void death() {
		//game.setScreen(new HighScoresScreen(game, camera));
	}
	
	private void levelLogic() {
		if (( score / 10) > 1500) {
			difficultyIncrease = 0;
			if (!addedStars) {
				addedStars = true;
			}
		}
		if ((score / 10) > 1900 && (score / 10) < 1950) {
			backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("WorldTravel_0.mp3"));
			backgroundMusic.setVolume(.1f);
			backgroundMusic.play();
		}
	}

	private void updateColors() {
		green -= (Gdx.graphics.getDeltaTime() * difficulty * .00005);
		blue -= (Gdx.graphics.getDeltaTime() * difficulty * .00005);
		if (green < 0) green = 0;
		if (blue < 0) blue = 0;
	}
	
	private void continueBuilding() {
		if (lastBrick.getY() <= 480) {
			double tempY = lastBrick.getY();
			addedSingle = false;
			for (int i = 0; i < 10; i++) {
				addBlankWall(tempY + 20 + (i*20), difficulty);
			}
			if (!addedSingle) addSingleLedge(tempY + 100);
		}	
	}
	
	private void updateMeters() {
		meters += difficulty * Gdx.graphics.getDeltaTime();
		if (player.getTopY() + meters > score) score = (int) (player.getTopY() + meters);
		//System.out.println("Meters: " + (score / 10) + " Difficulty: "+ difficulty);
	}
	
	private void updateDifficulty() {
		countToIncreaseDifficulty += Gdx.graphics.getDeltaTime();
		if (countToIncreaseDifficulty > difficultyIncreaseBar) {
			difficultyIncreaseBar++;
			countToIncreaseDifficulty = 0;
			difficulty+=difficultyIncrease;
			difficultyIncrease*=.90;
			player.increaseSpeed((int)difficultyIncrease); 
		}		
	}
	
	private void updateEntities(float difficulty) {
    	Iterator<Entity> entityIterator = entities.iterator();
    	while (entityIterator.hasNext()) {
    		Entity e = entityIterator.next();
        	e.update(difficulty);
        	if (e.isRemoved()) {
        		entityIterator.remove();
        	}
    	}
    	//if player is standing on a ledge, move him down same speed as ledge
    	if (!player.isJumping()) {
    		player.movePlayerDown(difficulty);
    		player.setPosition();
    	}
	}
	
	private void drawBackGround() {
		Iterator<BackGround> backgroundsIterator = backgrounds.iterator();
    	while (backgroundsIterator.hasNext()) {
    		BackGround e = backgroundsIterator.next();
    		Sky.batch.draw(e.getSprite(), (float) e.getX(), (float) e.getY());
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
        for (int i = 0; i < 26; i++) {
        	addBlankWall(20 + (i * 20), difficulty);
        }
    }
    
    private void addBlankWall(double d, float difficulty) {
    	add(new LeftWall(40, d, false));
    	for (int i = 0; i < 10; i++) {
    		add(new MidWall(60 + (20 * i), d, false));
    	}
    	lastBrick = new RightWall(260, d, false);
    	add(lastBrick);
    	if (rand.nextInt(5 + ((int) difficulty / 50)) == 4) { 
    		addSingleLedge(d);
    		addedSingle = true;
    	}
    	if (rand.nextInt(10 + ((int) difficulty / 50)) == 9) {
    		addDoubleLedge(d);
    		addedSingle = true;
    	}
    	if (rand.nextInt(30 + ((int) difficulty / 50)) == 29) {
    		addTripleLedge(d);
    		addedSingle = true;
    	}
    }
    
    private void addSingleLedge(double y) {
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
    
    private void addDoubleLedge(double y) {
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
    
    private void addTripleLedge(double y) {
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
