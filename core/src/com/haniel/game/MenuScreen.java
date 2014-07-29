package com.haniel.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuScreen implements Screen{
	
	OrthographicCamera camera;
	final Sky game;
	private Stage stage;
	private Skin skin;
	private Table table;
	private Label title;
	private TextButton buttonPlay;
	private TextureAtlas atlas;
	private Music backgroundMusic;
	
	public MenuScreen(final Sky gam, OrthographicCamera camera) {
		this.camera = camera;
		this.game = gam;
        camera.setToOrtho(true, 320, 480);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, .9f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        Sky.batch.setProjectionMatrix(camera.combined);

        Sky.batch.begin();
        Sky.batch.end();
    	stage.act(Gdx.graphics.getDeltaTime());
    	stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		//backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sad_Exploring.mp3"));
		//backgroundMusic.play();
		//backgroundMusic.setLooping(true);
		atlas = new TextureAtlas("uiskin.atlas");
		skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);
		stage = new Stage();
		table = new Table(skin);
		stage.addActor(table);
		table.setBounds(0, 0, stage.getWidth(), stage.getHeight());
		Gdx.input.setInputProcessor(stage);
		
		title =new Label("The Sky's the Limit", skin);
		buttonPlay = new TextButton("Start Game", skin);
		buttonPlay.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new GameScreen(game, camera));
	            dispose();				
			}
		});
		
		table.add(title).padBottom(30);
		table.row();
		table.add(buttonPlay).padBottom(10).width(200).height(40);
		table.row();
		
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
		stage.dispose();
		//backgroundMusic.dispose();	
		
	}

}
