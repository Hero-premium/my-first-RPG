package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Assets;

import com.mygdx.game.util.Util;
import com.mygdx.game.world.Flyable;

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
                movement.speed = movement.speed * 1.5f;
            }
            if (Util.isKeyJustReleased(Input.Keys.SHIFT_LEFT)) {
                movement.speed = movement.speed / 1.5f;
            }

            if (!movement.movementLocked) {

                if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    movement.facingLeft = true;
                    movement.velocity.x -= movement.speed;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    movement.facingLeft = false;
                    movement.velocity.x += movement.speed;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    movement.velocity.y += movement.speed;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    movement.velocity.y -= movement.speed;
                }

                if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
                    movement.velocity.x += movement.facingLeft ? -450 : 450;

                }
            }

        }
    }

}
