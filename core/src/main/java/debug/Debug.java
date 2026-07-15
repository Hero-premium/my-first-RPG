package debug;

import java.util.List;

import com.badlogic.gdx.Gdx;
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

	BitmapFont debugfont = new BitmapFont();
	ShapeRenderer shapeRenderer = new ShapeRenderer();
	public boolean isdebug = false;
	static Vector2 textPos = new Vector2();

	static Vector2 getCamera(OrthographicCamera camera, FitViewport viewport, int position) {
		float x = camera.position.x - viewport.getWorldWidth() / 2 + 20;
		float y = camera.position.y + viewport.getWorldHeight() / 2 - position;
		return textPos.set(x, y);
	}

	public void showDebug(SpriteBatch batch, Entity player, FitViewport viewport, List<Entity> objects,
			List<Touchable> touchables, OrthographicCamera camera) {
		textPos = getCamera(camera, viewport, 0);
		if (isdebug) {
			batch.begin();

			debugfont.draw(batch, "Player x " + player.hitBox.x + " / Player y " + player.hitBox.y, textPos.x,
					textPos.y = getCamera(camera, viewport, 20).y);

			debugfont.draw(batch, "FPS " + Gdx.graphics.getFramesPerSecond(), textPos.x,
					textPos.y = getCamera(camera, viewport, 35).y);
			debugfont.draw(batch, "Player velocityX " + player.velocity.x + " / Player velocityY " + player.velocity.y,
					textPos.x, textPos.y = getCamera(camera, viewport, 50).y);

			batch.end();
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

	public void dispose() {
		debugfont.dispose();
		shapeRenderer.dispose();
	}
}
