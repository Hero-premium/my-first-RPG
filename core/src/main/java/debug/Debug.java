package debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import entities.Entity;
import touchables.Touchable;

public class Debug {


    public boolean isDebug = false;
    private final Vector2 textPos = new Vector2();
    private final BitmapFont debugFont = new BitmapFont();
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    public void dispose() {
        debugFont.dispose();
        shapeRenderer.dispose();
    }

    private Vector2 getCamera(OrthographicCamera camera, FitViewport viewport, int position) {
        return textPos.set(//
            camera.position.x - viewport.getWorldWidth() / 2 + 20,
            camera.position.y + viewport.getWorldHeight() / 2 - position//
        );
    }

    public void showHitboxes(OrthographicCamera camera, Array<Entity> objects, Array<Touchable> touchables) {
        if (isDebug) {
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
            isDebug = !isDebug;
        }

        getCamera(camera, viewport, 0);
        if (isDebug) {

            debugFont.draw(batch, "Player x " + player.hitBox.x + " / Player y " + player.hitBox.y, textPos.x,
                getCamera(camera, viewport, 20).y);

            debugFont.draw(batch, "FPS " + Gdx.graphics.getFramesPerSecond(), textPos.x,
                textPos.y = getCamera(camera, viewport, 35).y);
            debugFont.draw(batch, "Player velocityX " + player.velocity.x + " / Player velocityY " + player.velocity.y,
                textPos.x, getCamera(camera, viewport, 50).y);
        }
    }
}
