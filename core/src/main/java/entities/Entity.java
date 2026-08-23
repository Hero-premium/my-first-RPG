package entities;

import java.util.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import util.Updatable;
import util.Util;

public abstract class Entity implements Updatable {

	private int gold;
	public String name;
	public boolean facingLeft = false;
	public boolean onGround;
	public boolean movementLocked = false;
	public final Vector2 velocity;
	public final Rectangle hitBox;
	public float speed;
	public final transient Texture texture;

	protected Entity(int gold, String name, boolean onGround, float speed, Vector2 velocity, Rectangle hitBox,
			Texture texture) {

		this.gold = Util.requireNonNegative(gold);
		this.name = name;
		this.onGround = onGround;
		this.velocity = Objects.requireNonNull(velocity);
		this.speed = speed;
		this.hitBox = Objects.requireNonNull(hitBox);
		this.texture = Objects.requireNonNull(texture);
	}

	public void draw(SpriteBatch batch) {
		batch.draw(texture, hitBox.x, hitBox.y, 64f, 64f, 0, 0, texture.getWidth(), texture.getHeight(), !facingLeft,
				false);
	}

	public int getGold() {
		return gold;
	}

	/**
	 * moves an amount of gold from an entity to another entity
	 *
	 * @param amount   - the amount of gold moved
	 * @param receiver - the entity receiving the gold
	 * @throws IllegalArgumentException - on negative input
	 * @throws NullPointerException     - if the receiver was null
	 * @return true - on successful operation, else it'll return false
	 */
	public boolean moveGold(int amount, Entity receiver) {
		Util.requireNonNegative(amount);
		Objects.requireNonNull(receiver, "the receiver cannot be null");
		if (gold < amount) {
			Util.logWarn("this entity doesn't have enough gold for this transaction");
			return false;
		}

		gold -= amount;
		receiver.gold += amount;
		Util.log("the " + receiver.name + " gained " + amount + " gold");
		return true;
	}

	/**
	 * to be overridden by other classes
	 */
	public void passiveAbility() {
	}

	/**
	 * Clamps all entities velocity to 2000
	 */
	public void velocityClamp() {
		velocity.x = Math.clamp(velocity.x, -2000, 2000);
		velocity.y = Math.clamp(velocity.y, -2000, 2000);
	}

}
