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
import com.haniel.game.Backgrounds.Background;
import com.haniel.game.Backgrounds.Field;
import com.haniel.game.Backgrounds.Moon;
import com.haniel.game.Backgrounds.Star;
import com.haniel.game.Backgrounds.Tower;
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
	public List<Background> backgrounds = new ArrayList<Background>();
	public List<Background> tempBackgrounds = new ArrayList<Background>();
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
    //level logic booleans
    private boolean addedStars = false;
    public boolean debug = false;
    private boolean addedFirstMusic = false;
    private boolean playedAlien = false;
    private Music alienMusic = Gdx.audio.newMusic(Gdx.files.internal("ObservingTheStar.ogg"));
	
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
		updateBackgrounds();
		updateMeters();
		updateDifficulty();
		continueBuilding();
		if (blue > 0) updateColors();
		levelLogic();
		System.out.println(backgrounds.size());
	}

	public void show() {
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("LittlePeopleAtWork.mp3"));
		backgroundMusic.setVolume(.5f);
		backgroundMusic.play();
		
	}

	public void hide() {
		
		
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
		if (( score / 100) > 150) {
			difficultyIncrease = 0;
		}
		if (!addedStars) {
			if (( score / 100) > 120)  {
				addedStars = true;
				for (int i = 0; i < 70; i ++) {
					add(new Star(rand.nextInt(40), rand.nextInt(height) + 480, rand.nextInt(10) ));
					add(new Star(rand.nextInt(40) + 280, rand.nextInt(height) + 480, rand.nextInt(10)));
				}
			}
		}
		if (!addedFirstMusic) {
			if ((score / 100) > 170) {
				addedFirstMusic = true;
				backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("WorldTravel_0.mp3"));
				backgroundMusic.setVolume(.1f);
				backgroundMusic.play();
				add(new Moon(285, 480, 5));
			}
		}
		if (!playedAlien) {
			if ((score / 100) > 350) {
				alienMusic.setLooping(true);
				alienMusic.setVolume(.5f);
				alienMusic.play();
				playedAlien = true;				
			}
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
	
	private void updateBackgrounds() {
		Iterator<Background> backgroundIterator = backgrounds.iterator();
    	while (backgroundIterator.hasNext()) {
    		Background b = backgroundIterator.next();
        	b.update();
        	if (b.isRemoved()) {        		
        		if (b instanceof Star) {
        			if (backgrounds.size() < 200) {
	        			tempBackgrounds.add(new Star(rand.nextInt(40), rand.nextInt(height) + 480, rand.nextInt(10) ));
						tempBackgrounds.add(new Star(rand.nextInt(40) + 280, rand.nextInt(height) + 480, rand.nextInt(10)));
        			}
        		}
        		backgroundIterator.remove();
        	}
    	}
    	if (tempBackgrounds.size() > 0) {
    		for (Background b: tempBackgrounds) {
    			backgrounds.add(b);    			
    		}
    		tempBackgrounds.clear();
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
		Iterator<Background> backgroundsIterator = backgrounds.iterator();
    	while (backgroundsIterator.hasNext()) {
    		Background e = backgroundsIterator.next();
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
    
    public void add(Background b) {
    	backgrounds.add(b);
    }  
	
    private void createStage() {
    	add(new Tower(40, 0, 0));
    	add(new Field(0, -2, 1));
    	add(new Field(280, -2, 1));
        for (int i = 0; i < 17; i++) {
        	add(new GrassTile(i * 20,0));
        }
        for (int i = 0; i < 26; i++) {
        	addBlankWall(20 + (i * 20), difficulty);
        }
    }
    
    private void addBlankWall(double d, float difficulty) {
    	add(new LeftWall(40, d, false));
    	lastBrick = new RightWall(260, d, false);
    	add(lastBrick);
    	for (int i = 0; i < 10; i++) {
    		if (rand.nextInt(40) == 19) add(new MidWall(60 + (i * 20), d, false));
    	}    	
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
