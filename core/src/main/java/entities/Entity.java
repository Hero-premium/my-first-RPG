package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

	public int gold;
	public String name;
	public boolean facingLeft;
	public boolean onGround;
	public boolean isAlive;
	private boolean movmentLocked;
	public Vector2 velocity;
	public Rectangle hitBox;
	public float speed;
	public Texture texture;

	protected Entity(int gold, String name, boolean facingLeft, boolean onGround, boolean isAlive,
			boolean movmentLocked, float speed, Vector2 velocity, Rectangle hitBox, Texture texture) {

		this.gold = gold;
		this.name = name;
		this.facingLeft = facingLeft;
		this.onGround = onGround;
		this.isAlive = isAlive;
		this.movmentLocked = movmentLocked;
		this.velocity = velocity;
		this.speed = speed;
		this.hitBox = hitBox;
		this.texture = texture;
	}

	/**
	 * clapms all entities velocity to 5000
	 */
	public void velocityClamp() {
		velocity.x = Math.clamp(velocity.x, -2000, 2000);
		velocity.y = Math.clamp(velocity.y, -2000, 2000);
	}

	// to be overridden by other classes
	public void passiveAbility() {
	}

	public boolean getMovmentLocked() {
		return movmentLocked;
	}

	public void setMovmentLocked(boolean movmentLocked) {
		this.movmentLocked = movmentLocked;
	}

	public void render(SpriteBatch batch) {
		batch.draw(texture, hitBox.x, hitBox.y, 64f, 64f, 0, 0, texture.getWidth(), texture.getHeight(), !facingLeft,
				false);
	}

}
