package com.mygdx.game.entities.componants;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Objects;

public class Movement {
    public float speed;
    public final Vector2 velocity;
    public final Rectangle hitBox;
    public boolean onGround;
    public boolean movementLocked;
    public boolean facingLeft;

    public Movement(Rectangle hitBox, float speed) {
        this.velocity = new Vector2();
        this.hitBox = Objects.requireNonNull(hitBox);
        this.facingLeft = false;
        this.onGround = false;
        this.movementLocked = false;
        this.speed = speed;
    }
}
