package com.haniel.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.haniel.game.Google.ActionResolver;

public class LoadingScreen implements Screen{
	
	OrthographicCamera camera;
	final Sky game;
	private Stage stage;
	private Skin skin;
	private Table table;
	private Label title;
	private TextureAtlas atlas;
	private ActionResolver actionResolver;
	
	public LoadingScreen(final Sky gam, OrthographicCamera camera, ActionResolver actionResolve){
		this.camera = camera;
		this.game = gam;
		this.actionResolver = actionResolve;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, .9f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        Sky.batch.setProjectionMatrix(camera.combined);

        Sky.batch.begin();
        Sky.batch.end();
    	stage.act(Gdx.graphics.getDeltaTime());
    	stage.draw();
        Assets.update();
        if(Assets.manager.getProgress()==1) {
        	stage.dispose();
			game.setScreen(new MenuScreen(game, camera, actionResolver));
        }
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		Assets.manager.clear(); 
		Assets.queueLoading();
		atlas = new TextureAtlas("uiskin.atlas");
		skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);
		stage = new Stage();
		table = new Table(skin);
		stage.addActor(table);
		table.setBounds(0, 0, stage.getWidth(), stage.getHeight());
		Gdx.input.setInputProcessor(stage);
		title =new Label("Loading Assets", skin);
		table.add(title).padBottom(30);
		
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
		stage.dispose();
		
	}

}
