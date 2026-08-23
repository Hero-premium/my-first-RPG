package world;

import java.util.List;

import entities.Entity;

public final class Physics {

	private static final float GRAVITY = 980f;
	private static final float AIR = 980f;

	private static void airRes(Entity entity, float deltaTime) {
		if (entity.velocity.x == 0)
			return;

		if (Math.abs(entity.velocity.x) < AIR * deltaTime) {
			entity.velocity.x = 0;
		} else {
			float air = Physics.AIR * Math.signum(entity.velocity.x);
			entity.velocity.x -= air * deltaTime;
		}

		entity.hitBox.x += entity.velocity.x * deltaTime;
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
	public static void applyPhysics(List<Entity> entities, float delta, float floorLevel) {
		for (Entity object : entities) {
			object.velocityClamp();
			airRes(object, delta);

			if (!(object instanceof Flyable)) {
				gravity(object, delta, floorLevel);
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

	private Physics() {
		throw new AssertionError("No world.Physics instance for you!");
	}

}
