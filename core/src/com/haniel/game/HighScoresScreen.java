package com.haniel.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.haniel.game.Google.ActionResolver;
public class HighScoresScreen implements Screen{
	OrthographicCamera camera;
	final Sky game;
	private Stage stage;
	private Skin skin;
	private Table table;
	private Label title;
	private TextButton buttonPlay, buttonScores, buttonAchievements;
	private TextureAtlas atlas;
	private float score;
	private ActionResolver actionResolver;
	
	public HighScoresScreen(final Sky gam, OrthographicCamera camera, float score, ActionResolver actionResolve) {
		this.camera = camera;
		this.game = gam;
		this.score = score;
		this.actionResolver = actionResolve;
        camera.setToOrtho(true, 320, 480);
        
		
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
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		
		if (actionResolver.getSignedInGPGS()) {
			 actionResolver.submitScoreGPGS(score*100);
			 /*
			 if (score >= 20) game.actionResolver.unlockAchievementGPGS("CgkI4qvY37UcEAIQAQ");
			 if (score >= 40) game.actionResolver.unlockAchievementGPGS("CgkI4qvY37UcEAIQAg");
			 if (score >= 100) game.actionResolver.unlockAchievementGPGS("CgkI4qvY37UcEAIQAw");
			 if (score >= 150) game.actionResolver.unlockAchievementGPGS("CgkI4qvY37UcEAIQBA");
			 if (score >= 200) game.actionResolver.unlockAchievementGPGS("CgkI4qvY37UcEAIQBQ");
			 */
		}
		
		atlas = new TextureAtlas("uiskin.atlas");
		skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);
		stage = new Stage();
		table = new Table(skin);
		stage.addActor(table);
		table.setBounds(0, 0, stage.getWidth(), stage.getHeight());
		Gdx.input.setInputProcessor(stage);
		
		title =new Label("Score: " + score + " Meters!", skin);
		buttonPlay = new TextButton("Retry", skin);
		buttonPlay.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				stage.dispose();
				game.setScreen(new GameScreen(game, camera, actionResolver));
			}
		});
		
		buttonScores = new TextButton("High Scores", skin);
		buttonScores.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				game.actionResolver.getLeaderboardGPGS();
			}
		});
		
		buttonAchievements = new TextButton("Achievements", skin);
		buttonAchievements.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				game.actionResolver.getAchievementsGPGS();
			}
		});
		
		table.add(title).padBottom(30);
		table.row();
		table.add(buttonPlay).padBottom(10).width(200).height(40);
		table.row();
		if (actionResolver.getSignedInGPGS()) {
			table.add(buttonScores).padBottom(10).width(200).height(40);
			table.row();
			table.add(buttonAchievements).padBottom(10).width(200).height(40);
			table.row();
		}
		
		
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
}
