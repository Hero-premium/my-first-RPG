package UI;

import com.badlogic.gdx.ScreenAdapter;
import com.mygdx.game.Assets;
import com.mygdx.game.GameLauncher;

import util.Util;

public class LoadingScreen extends ScreenAdapter {
	private final GameLauncher game;

	public LoadingScreen(GameLauncher game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		if (!Assets.load()) {
			Util.log("loading!" + (Assets.getProgress() * 100) + "%");
			return;
		}
		Util.log("finished");
		game.setScreen(new MainGame());
	}

}
