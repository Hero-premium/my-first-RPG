package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Assets;

import combat.CombatLogic;
import util.Util;

public class Hero extends CombatEntity {

	boolean possessed = false;

	public Hero() {
		super(10, "", false, true, 128f, new Vector2(0, 0), new Rectangle(0f, 0f, 50f, 60f), 200, Assets.player);
	}

	public void dodge(CombatEntity enemy) {
		Util.log(name + " used dodge");
		isDodging = Util.rand.nextBoolean();
		int damage = 1;
		CombatLogic.calculateDamage(enemy, this, damage);
	}

	private void heroAi() {
		// TODO give the hero wandering and movement ai ASAP

	}

	public void kick(CombatEntity enemy) {
		Util.log(name + " used kick");
		int damage = Util.rand.nextInt(2) + 3;
		CombatLogic.calculateDamage(enemy, this, damage);
	}

	public void move(float deltaTime) {
		if (possessed) {
			takeControl(deltaTime);
		} else {
			heroAi();
		}

	}

	public void swordSlash(CombatEntity enemy) {
		Util.log(name + " used swordSlash");
		int damage = 0;
		if (80 <= Util.rand.nextInt(100)) {
			damage = 8;
			Util.log("the player landed a critical hit with sword slash");
			CombatLogic.calculateDamage(enemy, this, damage);

		} else {
			damage += Util.rand.nextInt(3);
			if (damage == 0) {
				Util.log("the player did " + damage + " (aka missed)");
			}
			CombatLogic.calculateDamage(enemy, this, damage);
		}

	}

	private void takeControl(float deltaTime) {

		if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
			speed *= 1.5;
		}
		if (Util.isKeyJustReleased(Input.Keys.SHIFT_LEFT)) {
			speed /= 1.5;
		}

		if (!movementLocked()) {

			if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				facingLeft = true;
				hitBox.x -= speed * deltaTime;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				facingLeft = false;
				hitBox.x += speed * deltaTime;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && onGround) {
				velocity.y = 450;
				onGround = false;
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
				velocity.x += facingLeft ? -450 : 450;

			}
		}

	}
}
