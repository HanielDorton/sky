package com.haniel.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
	public static AssetManager manager = new AssetManager();
	
	
	public static void queueLoading(){

		manager.load("ObservingTheStar.ogg", Music.class);
		manager.load("LittlePeopleAtWork.mp3", Music.class);
		manager.load("WorldTravel_0.mp3", Music.class);
		
		manager.load("phaseJump2.ogg", Sound.class);
	
		manager.load("tower.png", Texture.class);
		manager.load("field.png", Texture.class);
		manager.load("star.png", Texture.class);
		manager.load("Moon.png", Texture.class);
		manager.load("alienPink.png", Texture.class);
		manager.load("alienPink_jumpRight.png", Texture.class);
		manager.load("alienPink_jumpLeft.png", Texture.class);
		manager.load("grassMid.png", Texture.class);
		manager.load("houseDarkAlt.png", Texture.class);
		manager.load("houseDarkAlt2.png", Texture.class);
		manager.load("houseDarkLedge.png", Texture.class);
		manager.load("houseDarkLedgeLeft.png", Texture.class);
		manager.load("houseDarkLedgeRight.png", Texture.class);
		manager.load("houseDarkMidRight.png", Texture.class);
		manager.load("houseDarkMidLeft.png", Texture.class);
		

		
	}
	
    public static boolean update() {
        return manager.update();
    }

}
