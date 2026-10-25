package io.github.hero_premium.world;

import com.badlogic.gdx.utils.Array;
import io.github.hero_premium.entities.components.Movement;

public final class Physics {

    private static final float GRAVITY = 980f;
    private static final float AIR = 980f;

    private Physics() {
        throw new AssertionError("No world.Physics instance for you!");
    }

    private static float dampFloats(float velocity, float friction, float deltaTime) {
        if (velocity == 0)
            return 0;

        if (Math.abs(velocity) < friction * deltaTime) {
            return 0;
        }
        float rate = friction * Math.signum(velocity);
        return velocity - rate * deltaTime;
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
        for (Movement m : movements) {
            applyPhysics(m, delta, floorLevel);
        }
    }

    public static void applyPhysics(Movement m, float delta, float floorLevel) {
        m.velocityClamp();
        m.velocity.x = dampFloats(m.velocity.x, AIR, delta);
        m.hitBox.x += m.velocity.x * delta;

        if (m.canFly) {
            m.velocity.y = dampFloats(m.velocity.y, GRAVITY, delta);
            m.hitBox.y += m.velocity.y * delta;
        } else {
            gravity(m, delta, floorLevel);
        }
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

}
