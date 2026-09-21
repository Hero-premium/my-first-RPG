package com.mygdx.game;

import com.mygdx.game.UI.LoadingScreen;
import com.badlogic.gdx.Game;

public class GameLauncher extends Game {
    @Override
    public void create() {
        this.setScreen(new LoadingScreen(this));
    }
}
