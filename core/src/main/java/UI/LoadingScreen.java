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
	public void resize(int width, int height) {
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

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		if  (!Assets.load()) {
			Util.log("loading!" + (Assets.getpres() * 100)+ "%");
			return;
		}
			Util.log("finished");
			game.setScreen(new MainGame());
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	
}
