package entities;

import java.util.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import util.Util;

public abstract class Entity {

	private int gold;
	public String name;
	public boolean facingLeft = false;
	public boolean onGround;
	public boolean isAlive;
	private boolean movementLocked = false;
	public Vector2 velocity;
	public Rectangle hitBox;
	public float speed;
	public transient Texture texture;

	protected Entity(int gold, String name, boolean onGround, boolean isAlive, float speed, Vector2 velocity,
			Rectangle hitBox, Texture texture) {

		this.gold = gold;
		this.name = name;
		this.onGround = onGround;
		this.isAlive = isAlive;
		this.velocity = velocity;
		this.speed = speed;
		this.hitBox = hitBox;
		this.texture = texture;
	}

	public void draw(SpriteBatch batch) {
		batch.draw(texture, hitBox.x, hitBox.y, 64f, 64f, 0, 0, texture.getWidth(), texture.getHeight(), !facingLeft,
				false);
	}

	public int gold() {
		return gold;
	}

	/**
	 * moves an amount of gold from an entity to another entity
	 *
	 * @param amount - the amount of gold moved
	 * @param receiver - the entity receiving the gold
	 * @throws IllegalArgumentException - on negative input
	 * @throws NullPointerException - if the receiver was null
	 */
	public void moveGold(int amount, Entity receiver) {
		if (amount < 0) {
			throw new IllegalArgumentException("this number can't be negative");
		}
		Objects.requireNonNull(receiver, "the receiver cannot be null");
		if (gold < amount) {
			Util.logWarn("this entity doesn't have enough gold for this transaction");
			return;
		}

		gold -= amount;
		receiver.gold += amount;
		Util.log("the " + receiver.name + " gained " + amount + " gold");
	}

	public boolean movementLocked() {
		return movementLocked;
	}

	// to be overridden by other classes
	public void passiveAbility() {
	}

	public void setMovementLocked(boolean movementLocked) {
		this.movementLocked = movementLocked;
	}

	/**
	 * Clamps all entities velocity to 2000
	 */
	public void velocityClamp() {
		velocity.x = Math.clamp(velocity.x, -2000, 2000);
		velocity.y = Math.clamp(velocity.y, -2000, 2000);
	}

}
