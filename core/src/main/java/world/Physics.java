package world;

import com.badlogic.gdx.utils.Array;

import entities.Entity;

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
        float air = friction * Math.signum(velocity);
        return velocity - air * deltaTime;
    }

    /**
     * applies airResistance and velocity clamping to all entities and applies
     * gravity to all non-flying entities
     *
     * @param entities   the list containing all entities
     * @param delta      deltaTime to prevent movement issues on higher FPS
     * @param floorLevel the floor level this screen uses, no entity can go below it
     *                   unless it can fly
     */
    public static void applyPhysics(Array<Entity> entities, float delta, float floorLevel) {
        for (Entity object : entities) {
            object.velocityClamp();
            object.velocity.x = dampFloats(object.velocity.x, AIR, delta);
            object.hitBox.x += object.velocity.x * delta;

            if (!(object instanceof Flyable)) {
                gravity(object, delta, floorLevel);
            } else {
                object.velocity.y = dampFloats(object.velocity.y, GRAVITY, delta);
                object.hitBox.y += object.velocity.y * delta;
            }
        }
    }

    private static void gravity(Entity entity, float deltaTime, float floorLevel) {

        entity.velocity.y -= GRAVITY * deltaTime;

        entity.hitBox.y += entity.velocity.y * deltaTime;

        if (entity.hitBox.y < floorLevel) {
            entity.hitBox.y = floorLevel;
            entity.velocity.y = 0f;
            entity.onGround = true;
        }
    }

}
