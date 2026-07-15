package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TestScreen implements Screen {

	private final SpriteBatch batch = new SpriteBatch();
	private final OrthographicCamera camera = new OrthographicCamera();
	private final FitViewport viewport = new FitViewport(800, 600, camera);

	private int playerX = 100;
	private int playerY = 100;

	private final int speed = 4;

	@Override
	public void show() {
		camera.position.set(400, 300, 0);
		camera.update();
	}

	@Override
	public void render(float delta) {

		if (Gdx.input.isKeyPressed(Input.Keys.A))
			playerX -= speed;

		if (Gdx.input.isKeyPressed(Input.Keys.D))
			playerX += speed;

		if (Gdx.input.isKeyPressed(Input.Keys.W))
			playerY += speed;

		if (Gdx.input.isKeyPressed(Input.Keys.S))
			playerY -= speed;

		ScreenUtils.clear(0, 0, 0, 1);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(Textures.player, playerX, playerY, 64, 64);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
