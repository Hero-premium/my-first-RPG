package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CombatEntity extends Entity {

	private int hp;
	public int maxHp;
	public int poisonDuration = 0;
	public boolean isDodging = false;
	public boolean isDefending = false;
	public boolean isfocused = false;

	public CombatEntity(int gold, String name, boolean onGround, boolean isAlive,
			 float speed, Vector2 velocity, Rectangle hitbox, int hp, int maxhp,
			Texture texture) {
		super(gold, name, onGround, isAlive, speed, velocity, hitbox, texture);

		this.hp = hp;
		this.maxHp = maxhp;
	}

	/**
	 * resets hp to its max
	 */
	public void resetHp() {
		this.hp = this.maxHp;
	}

	public int getHp() {
		return hp;
	}

	/**
	 * sets hp to given amount
	 * also clamps hp between 0 and maxHp
	 * @param hp the new hp
	 * @throws IllegalArgumentException on negative input
	 */
	public void setHp(int hp) {
		if (hp < 0) {
			throw new IllegalArgumentException("you can't set hp to negative");
		}
		this.hp = hp;
		this.hp = Math.clamp(this.hp, 0, maxHp);
	}

	/**
	 * adds the given amount of hp to the existing amount of hp
	 * also clamps hp between 0 and maxHp
	 * 
	 * @param hp how much you want to add
	 */
	public void modifyHp(int hp) {
		this.hp += hp;
		this.hp = Math.clamp(this.hp, 0, maxHp);
	}

	public void takeTurn(CombatEntity entity) {
	}
}