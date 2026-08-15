package world;

import java.util.List;

import entities.Entity;
import entities.Ghost;

public class Physics {

	private static float gravity = -980f;

	private static float air = -980;
	private static void airRes(Entity entity, float deltaTime) {
		if (!entity.facingLeft) {
			if (entity.velocity.x <= 0) {
				entity.velocity.x = 0;
				return;
			}
			entity.velocity.x += air * deltaTime;

		} else {

			if (entity.velocity.x >= 0) {
				entity.velocity.x = 0;
				return;
			}

			entity.velocity.x -= air * deltaTime;
		}
		entity.hitBox.x += entity.velocity.x * deltaTime;

	}

	public static void applyPhysics(List<Entity> entities, float delta, float FLOOR_LEVEL) {
		for (Entity object : entities) {
			airRes(object, delta);

			if (!(object instanceof Ghost)) {
				gravity(object, delta, FLOOR_LEVEL);
				object.velocityClamp();
			}
		}
	}

	private static void gravity(Entity entity, float deltaTime, float floorLevel) {

		entity.velocity.y += gravity * deltaTime;

		entity.hitBox.y += entity.velocity.y * deltaTime;

		if (entity.hitBox.y < floorLevel) {
			entity.hitBox.y = floorLevel;
			entity.velocity.y = 0f;
			entity.onGround = true;
		}
	}

	private Physics() {}

}
