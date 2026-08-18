package com.mygdx.game;

import com.badlogic.gdx.Game;

import UI.LoadingScreen;

public class GameLauncher extends Game {
	@Override
	public void create() {
		this.setScreen(new LoadingScreen(this));
	}
}
