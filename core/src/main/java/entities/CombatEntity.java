package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import util.Util;

public abstract class CombatEntity extends Entity {

	private int hp;
	public final int maxHp;
	private int poisonDuration = 0;
	public boolean isDodging = false;
	public boolean isDefending = false;
	public boolean isFocused = false;

	public CombatEntity(int gold, String name, boolean onGround, boolean isAlive, float speed, Vector2 velocity,
			Rectangle hitbox, int hp, Texture texture) {
		super(gold, name, onGround, isAlive, speed, velocity, hitbox, texture);

		this.hp = Util.requireNonNegative(hp);
		this.maxHp = hp;
	}

	public int getHp() {
		return hp;
	}

	/**
	 * adds the given amount of hp to the existing amount of hp also clamps hp
	 * between 0 and maxHp
	 *
	 * @param hp how much you want to add - pass a negative number to deal damage
	 */
	public final void modifyHp(int hp) {
		this.hp += hp;
		this.hp = Math.clamp(this.hp, 0, maxHp);
	}

	/**
	 * resets hp to its max
	 */
	public final void resetHp() {
		this.hp = this.maxHp;
	}

	/**
	 * sets hp to given amount also clamps hp between 0 and maxHp
	 *
	 * @param hp the new hp
	 * @throws IllegalArgumentException on negative input
	 */
	public final void setHp(int hp) {
		this.hp = Util.requireNonNegative(hp);
		this.hp = Math.clamp(this.hp, 0, maxHp);
	}

	public void resetBattleStates() {
		setPoisonDuration(0);
		isDodging = false;
		isDefending = false;
		isFocused = false;

	}

	public void takeTurn(CombatEntity entity) {
		throw new UnsupportedOperationException(
				"this entity " + this.getClass().getName() + " doesn't have a takeTurn method");
	}

	public int getPoisonDuration() {
		return poisonDuration;
	}

	public void setPoisonDuration(int poisonDuration) {
		this.poisonDuration = Util.requireNonNegative(poisonDuration);
	}
}