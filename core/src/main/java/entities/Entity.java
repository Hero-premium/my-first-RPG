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
	public boolean facingLeft;
	public boolean onGround;
	public boolean movementLocked;
	public final Vector2 velocity;
	public final Rectangle hitBox;
	public float speed;
	public final transient Texture texture;

	protected Entity(int gold, String name, float speed, Rectangle hitBox, Texture texture) {

		this.gold = Util.requireNonNegative(gold);
		this.name = Objects.requireNonNull(name);
		this.facingLeft = false;
		this.onGround = false;
		this.movementLocked = false;
		this.velocity = new Vector2();
		this.speed = Util.requireNonNegative(speed);
		this.hitBox = Objects.requireNonNull(hitBox);
		this.texture = Objects.requireNonNull(texture);
	}
	/**
	 * This is meant to be called in the render loop after batch.begin to draw the entity on the screen
	 * 
	 * @param batch the SpriteBatch used in this screen
	 * @throws IllegalStateException if this was called before SpriteBatch.begin
	 */
	public void draw(SpriteBatch batch) {
		batch.draw(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, 0, 0, texture.getWidth(), texture.getHeight(), !facingLeft,
				false);
	}
	/**
	 * @return the entity's current gold
	 */
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
	 * Clamps all entities x and y velocity to 2000
	 */
	public void velocityClamp() {
		velocity.x = Math.clamp(velocity.x, -2000, 2000);
		velocity.y = Math.clamp(velocity.y, -2000, 2000);
	}

}
