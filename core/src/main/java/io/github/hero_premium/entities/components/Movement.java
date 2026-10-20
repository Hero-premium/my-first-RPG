package io.github.hero_premium.entities.components;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Objects;

public class Movement {
    public final Vector2 velocity;
    public final Rectangle hitBox;
    private final float speed;
    private float speedModifier;
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
        this.speedModifier = 1;
    }

    public float getSpeed() {
        return speed * speedModifier;
    }

    public float getSpeedModifier() {
        return speedModifier;
    }

    public void setSpeedModifier(float speedModifier) {
        this.speedModifier = speedModifier;
    }
}
