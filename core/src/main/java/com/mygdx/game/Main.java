package com.mygdx.game;

import com.badlogic.gdx.Game;
import UI.MainGame;

public class Main extends Game {

    @Override
    public void create() {
        this.setScreen(new MainGame());
    }
}
    
    