package entities;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CombatEntity extends Entity {

	private int hp;
	public int maxhp;
	public int poisonDuration;
	public boolean isDodging;
	public boolean isDefending;
	public boolean isfocused;
	// TODO throw this somewhere else since it's unrelated
	public String[] dialog;

	public CombatEntity(int gold, String name, boolean facingLeft, boolean onGround, boolean isAlive, float speed,
			Vector2 velocity, Rectangle hitbox, int hp, int maxhp, int poisonDuration, boolean isDodging,
			boolean isDefending, boolean isfocused, String[] dialog, Texture texture) {
		super(gold, name, facingLeft, onGround, isAlive, speed, velocity, hitbox, texture);

		this.hp = hp;
		this.maxhp = maxhp;
		this.poisonDuration = poisonDuration;
		this.isDodging = isDodging;
		this.isDefending = isDefending;
		this.isfocused = isfocused;
		this.dialog = dialog;
	}

	/**
	 * resets hp to its max
	 */
	public void resetHp() {
		this.hp = this.maxhp;
	}

	public int getHp() {
		return hp;
	}

	/**
	 * sets hp to given amount
	 * 
	 * @param hp the new hp
	 * @throws IllegalArgumentException on negative input
	 *
	 */
	public void setHp(int hp) {
		if (hp < 0) {
			throw new IllegalArgumentException("you can't set hp to negative");
		}
		this.hp = hp;
		this.hp = Math.clamp(this.hp, 0, maxhp);
	}

	/**
	 * adds the given amount of hp to the existing amount of hp
	 * 
	 * @param hp how much you want to add
	 */
	public void modifyHp(int hp) {
		this.hp += hp;
		this.hp = Math.clamp(this.hp, 0, maxhp);
	}

	public void setPoisonDur(int duration) {
		poisonDuration = duration;
	}

	public void taketurn(CombatEntity player, Random rand) {
	}
}