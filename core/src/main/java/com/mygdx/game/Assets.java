package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

	public final static Texture player = new Texture(Gdx.files.internal("textures/hollowKnight.png"));
	public final static Texture backGround = new Texture(Gdx.files.internal("textures/main_game_bg.jpeg"));
	public final static Texture openedSpringTrap = new Texture(Gdx.files.internal("textures/spring_trap_opened.png"));
	public final static Texture closedSpringTrap = new Texture(Gdx.files.internal("textures/spring_trap_closed.png"));
	public final static Texture PlaceHolder = new Texture(Gdx.files.internal("textures/place_holder.png"));
	public final static  Skin skin = new Skin(Gdx.files.internal("textures/uiskin/uiskin.json"));

	public final static Music mainMenu =  Gdx.audio.newMusic(Gdx.files.internal("music/Walen - Conspiracy Detective (freetouse.com).mp3"));
	
	
	public static void dispose() {
		player.dispose();
		backGround.dispose();
		closedSpringTrap.dispose();
		openedSpringTrap.dispose();
		PlaceHolder.dispose();
		mainMenu.dispose();
		skin.dispose();
	}
}
