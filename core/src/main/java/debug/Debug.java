package debug;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

import entities.Entity;
import touchables.Touchable;

public class Debug {

	private Vector2 textPos = new Vector2();
	private BitmapFont debugfont = new BitmapFont();
	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	public boolean isdebug = false;

	public void dispose() {
		debugfont.dispose();
		shapeRenderer.dispose();
	}

	private Vector2 getCamera(OrthographicCamera camera, FitViewport viewport, int position) {
		return textPos.set(//
				camera.position.x - viewport.getWorldWidth() / 2 + 20,
				camera.position.y + viewport.getWorldHeight() / 2 - position//
				);
	}

	public void showHitboxes(OrthographicCamera camera, List<Entity> objects, List<Touchable> touchables) {
		if (isdebug) {
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

			shapeRenderer.setColor(Color.RED);

			for (Entity object : objects) {
				shapeRenderer.rect(object.hitBox.x, object.hitBox.y, object.hitBox.width, object.hitBox.height);

			}
			for (Touchable object : touchables) {
				shapeRenderer.rect(object.hitBox.x, object.hitBox.y, object.hitBox.width, object.hitBox.height);
			}
			shapeRenderer.end();
		}
	}

	public void showInformations(SpriteBatch batch, Entity player, FitViewport viewport, OrthographicCamera camera) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
			isdebug = !isdebug;
		}

		getCamera(camera, viewport, 0);
		if (isdebug) {

			debugfont.draw(batch, "Player x " + player.hitBox.x + " / Player y " + player.hitBox.y, textPos.x,
					getCamera(camera, viewport, 20).y);

			debugfont.draw(batch, "FPS " + Gdx.graphics.getFramesPerSecond(), textPos.x,
					textPos.y = getCamera(camera, viewport, 35).y);
			debugfont.draw(batch, "Player velocityX " + player.velocity.x + " / Player velocityY " + player.velocity.y,
					textPos.x, getCamera(camera, viewport, 50).y);
		}
	}
}
