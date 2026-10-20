package io.github.hero_premium;

import io.github.hero_premium.UI.LoadingScreen;
import com.badlogic.gdx.Game;

public class GameLauncher extends Game {
    @Override
    public void create() {
        this.setScreen(new LoadingScreen(this));
    }
}
