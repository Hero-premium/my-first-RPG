package io.github.hero_premium.world;

import com.badlogic.gdx.utils.Array;
import io.github.hero_premium.entities.components.Movement;

import java.util.Objects;

public final class Physics {

    private static final float GRAVITY = 980f;
    private static final float AIR = 980f;

    private Physics() {
        throw new AssertionError("No io.github.hero_premium.world.Physics instance for you!");
    }

    private static float dampFloats(float velocity, float friction, float deltaTime) {
        if (velocity == 0 || Math.abs(velocity) < friction * deltaTime)
            return 0;

        float rate = friction * Math.signum(velocity);
        return velocity - rate * deltaTime;
    }

    private static void gravity(Movement movement, float deltaTime, float floorLevel) {

        movement.velocity.y -= GRAVITY * deltaTime;

        movement.hitBox.y += movement.velocity.y * deltaTime;

        if (movement.hitBox.y < floorLevel) {
            movement.hitBox.y = floorLevel;
            movement.velocity.y = 0f;
            movement.onGround = true;
        }
    }

    /**
     * applies airResistance and velocity clamping to all movements and applies
     * gravity to all non-flying movements
     *
     * @param movements   the list containing all movements
     * @param delta      deltaTime to prevent movement issues on higher FPS
     * @param floorLevel the floor level this screen uses, no entity can go below it
     *                   unless it can fly
     */
    public static void applyPhysics(Array<Movement> movements, float delta, float floorLevel) {
        Objects.requireNonNull(movements, "movements must not be null");
        for (Movement m : movements) {
            applyPhysics(m, delta, floorLevel);
        }
    }

    /**
     * applies airResistance and velocity clamping to the given movement and applies
     * gravity to non-flying movement
     *
     * @param movement   the movement object physics is meant to be applied to
     * @param delta      deltaTime to prevent movement issues on higher FPS
     * @param floorLevel the floor level this screen uses, no entity can go below it
     *                   unless it can fly
     * @throws NullPointerException if movement was null
     */
    public static void applyPhysics(Movement movement, float delta, float floorLevel) {
        Objects.requireNonNull(movement, "movement must not be null");
        movement.velocityClamp();
        movement.velocity.x = dampFloats(movement.velocity.x, AIR, delta);
        movement.hitBox.x += movement.velocity.x * delta;

        if (movement.canFly) {
            movement.velocity.y = dampFloats(movement.velocity.y, GRAVITY, delta);
            movement.hitBox.y += movement.velocity.y * delta;
        } else {
            gravity(movement, delta, floorLevel);
        }
    }


}
