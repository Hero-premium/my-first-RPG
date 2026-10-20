package io.github.hero_premium.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import io.github.hero_premium.Assets;

import io.github.hero_premium.util.Util;

public class Ghost extends Entity {
    private final Hero hero;

    public Ghost(Hero hero) {
        super(0, "", 32, (new Rectangle(0f, 0f, 50f, 60f)), Assets.placeHolder);
        this.hero = hero;
        movement.canFly = true;
    }

    @Override
    public void update() {
        move();
    }

    private void move() {
        if (!hero.possessed) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
                movement.setSpeedModifier(1.5F);
            }
            if (Util.isKeyJustReleased(Input.Keys.SHIFT_LEFT)) {
                movement.setSpeedModifier(1);
            }

            if (!movement.movementLocked) {

                if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    movement.facingLeft = true;
                    movement.velocity.x -= movement.getSpeed();
                }
                if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    movement.facingLeft = false;
                    movement.velocity.x += movement.getSpeed();
                }
                if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    movement.velocity.y += movement.getSpeed();
                }
                if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    movement.velocity.y -= movement.getSpeed();
                }

                if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
                    movement.velocity.x += movement.facingLeft ? -450 : 450;

                }
            }

        }
    }

}
