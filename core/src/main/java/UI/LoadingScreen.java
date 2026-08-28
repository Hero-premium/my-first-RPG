package UI;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Assets;
import com.mygdx.game.GameLauncher;

public class LoadingScreen extends ScreenAdapter {
	private final GameLauncher game;
	private SpriteBatch batch;
	private BitmapFont font;

	public LoadingScreen(GameLauncher game) {
		this.game = game;
		batch = new SpriteBatch();
		font = new BitmapFont();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		
		if (!Assets.load()) {
			font.draw(batch, "loading!" + (Assets.getProgress() * 100) + "%", 50, 400);
			batch.end();
			return;
		}
		
		batch.end();
		game.setScreen(new MainGame(game));
		dispose();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
