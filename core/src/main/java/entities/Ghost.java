package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Assets;

import util.Util;

public class Ghost extends Entity {

	public Ghost() {
		super(0, "", false, true, 256, (new Vector2(0, 0)), (new Rectangle(0f, 0f, 50f, 60f)), Assets.placeHolder);
	}

	public void move(float deltaTime, Hero hero) {
		if (!hero.possessed) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
				speed *= 1.5;
			}
			if (Util.isKeyJustReleased(Input.Keys.SHIFT_LEFT)) {
				speed /= 1.5;
			}

			if (!movementLocked()) {

				if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
					facingLeft = true;
					hitBox.x -= speed * deltaTime;
				}
				if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
					facingLeft = false;
					hitBox.x += speed * deltaTime;
				}
				if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
					hitBox.y += speed * deltaTime;
				}
				if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
					hitBox.y -= speed * deltaTime;
				}

				if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
					velocity.x += facingLeft ? -450 : 450;

				}
			}

		}
	}

}
