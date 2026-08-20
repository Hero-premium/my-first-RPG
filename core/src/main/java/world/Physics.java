package world;

import java.util.List;

import entities.Entity;

public class Physics {

	private static float gravity = 980f;
	private static float air = 980f;

	private static void handlePositiveMovement(Entity entity, float deltaTime) {
		if (entity.velocity.x < air * deltaTime) {
			entity.velocity.x = 0;
		} else {
			entity.velocity.x -= air * deltaTime;
		}
	}

	private static void handleNegativeMovement(Entity entity, float deltaTime) {
		float air = -Physics.air;
		if (entity.velocity.x > air * deltaTime) {
			entity.velocity.x = 0;
		} else {
			entity.velocity.x -= air * deltaTime;
		}
	}

	private static void airRes(Entity entity, float deltaTime) {
		if (entity.velocity.x > 0) {
			handlePositiveMovement(entity, deltaTime);
		} else if (entity.velocity.x < 0) {
			handleNegativeMovement(entity, deltaTime);
		}

		entity.hitBox.x += entity.velocity.x * deltaTime;

	}

	public static void applyPhysics(List<Entity> entities, float delta, float floorLevel) {
		for (Entity object : entities) {
			airRes(object, delta);

			if (!(object instanceof Flyable)) {
				gravity(object, delta, floorLevel);
				object.velocityClamp();
			}
		}
	}

	private static void gravity(Entity entity, float deltaTime, float floorLevel) {

		entity.velocity.y -= gravity * deltaTime;

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
