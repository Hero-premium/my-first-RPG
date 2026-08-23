package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import util.Util;

public abstract class CombatEntity extends Entity {

	private int hp;
	/**
	 * an entities max hp, defined once in the contractor, an entity can never go
	 * past it thanks to the getters and setters
	 */
	public final int maxHp;
	private int poisonDuration = 0;
	public boolean isDodging = false;
	public boolean isDefending = false;
	public boolean isFocused = false;

	public CombatEntity(int gold, String name, boolean onGround, float speed, Vector2 velocity, Rectangle hitbox,
			int hp, Texture texture) {
		super(gold, name, onGround, speed, velocity, hitbox, texture);

		this.hp = Util.requireNonNegative(hp);
		this.maxHp = hp;
	}

	/**
	 * 
	 * @return entity current hp
	 */
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
		setHp(getHp() + hp);
	}

	/**
	 * resets hp to its max
	 */
	public final void resetHp() {
		setHp(maxHp);
	}

	/**
	 * sets hp to given amount also clamps hp between 0 and maxHp
	 *
	 * @param hp - the new hp
	 */
	public final void setHp(int hp) {
		this.hp = Math.clamp(hp, 0, maxHp);
	}

	public void resetBattleStates() {
		setPoisonDuration(0);
		isDodging = false;
		isDefending = false;
		isFocused = false;

	}

	/**
	 * a method that throws by default, but is meant to be overridden by other
	 * entities that take combat actions
	 * 
	 * @param entity - the entity being attacked
	 * @throws UnsupportedOperationException if the caller entity didn't override
	 *                                       this method.
	 */
	public void takeTurn(CombatEntity entity) {
		throw new UnsupportedOperationException(
				"this entity " + this.getClass().getName() + " doesn't have a takeTurn method");
	}

	/**
	 * 
	 * @return entity current poisonDuration
	 */
	public int getPoisonDuration() {
		return poisonDuration;
	}

	/**
	 * sets poisonDuration to given amount
	 * 
	 * @param poisonDuration the new poisonDuration
	 * @throws IllegalStateException if poisonDuration was >= 10 to prevent game
	 *                               breaking bugs
	 */
	public void setPoisonDuration(int poisonDuration) {
		if (poisonDuration >= 10)
			throw new IllegalStateException("balance breaking bug, poison been set for/more than 10 turns");
		this.poisonDuration = Math.max(poisonDuration, 0);
	}

	/**
	 * adds the given amount of poisonDuration to the existing amount
	 *
	 * @param poisonDuration - how much you want to add - pass a negative number
	 *                       remove from the duration
	 */
	public void modifyPoisonDuration(int poisonDuration) {
		setPoisonDuration(getPoisonDuration() + poisonDuration);
	}
}