package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Assets;

import util.Util;
import world.Flyable;

public class Ghost extends Entity implements Flyable {
    private final Hero hero;

    public Ghost(Hero hero) {
        super(0, "", 32, (new Rectangle(0f, 0f, 50f, 60f)), Assets.placeHolder);
        this.hero = hero;
    }

    @Override
    public void update() {
        move();
    }

    private void move() {
        if (!hero.possessed) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
                speed *= 1.5;
            }
            if (Util.isKeyJustReleased(Input.Keys.SHIFT_LEFT)) {
                speed /= 1.5;
            }

            if (!movementLocked) {

                if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    facingLeft = true;
                    velocity.x -= speed;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    facingLeft = false;
                    velocity.x += speed;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    velocity.y += speed;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    velocity.y -= speed;
                }

                if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
                    velocity.x += facingLeft ? -450 : 450;

                }
            }

        }
    }

}
