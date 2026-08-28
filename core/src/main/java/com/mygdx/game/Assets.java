package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public final class Assets {

	private static final AssetManager manager = new AssetManager();

	public static float getProgress() {
		return manager.getProgress();
	}

	private static boolean loading = false;

	public static Texture player;
	public static Texture backGround;
	public static Texture openedSpringTrap;
	public static Texture closedSpringTrap;
	public static Texture placeHolder;
	public static Texture wall;
	public static Skin skin;
	public static Music mainMenu;
	public static Texture gateKeeper;

	public static boolean load() {
		if (!loading) {
			loading = true;

			manager.load("textures/hollowKnight.png", Texture.class);
			manager.load("textures/main_game_bg.jpeg", Texture.class);
			manager.load("textures/spring_trap_opened.png", Texture.class);
			manager.load("textures/spring_trap_closed.png", Texture.class);
			manager.load("textures/place_holder.png", Texture.class);
			manager.load("textures/wall.jpg", Texture.class);
			manager.load("textures/uiskin/uiskin.json", Skin.class);
			manager.load("music/Walen - Conspiracy Detective (freetouse.com).mp3", Music.class);
		}
		if (!manager.update())
			return false;

		player = manager.get("textures/hollowKnight.png", Texture.class);
		backGround = manager.get("textures/main_game_bg.jpeg", Texture.class);
		openedSpringTrap = manager.get("textures/spring_trap_opened.png", Texture.class);
		closedSpringTrap = manager.get("textures/spring_trap_closed.png", Texture.class);
		placeHolder = manager.get("textures/place_holder.png", Texture.class);
		wall = manager.get("textures/wall.jpg", Texture.class);
		skin = manager.get("textures/uiskin/uiskin.json", Skin.class);
		mainMenu = manager.get("music/Walen - Conspiracy Detective (freetouse.com).mp3", Music.class);
		gateKeeper = placeHolder;
		return true;
	}

	public static void dispose() {
		loading = false;
		manager.dispose();
	}
}