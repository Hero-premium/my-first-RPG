package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Assets;

import combat.CombatLogic;
import util.Util;

public class Hero extends CombatEntity implements CombatAnimation {

	boolean possessed = false;

	public Hero() {
		super(10, "Hero", 32f, new Rectangle(0f, 0f, 50f, 60f), 200, Assets.player);
	}

	public void dodge(CombatEntity enemy) {
		Util.log(name + " used dodge");
		isDodging = Util.rand.nextBoolean();
		int damage = 1;
		CombatLogic.calculateDamage(enemy, this, damage);
		firstMove();
	}

	private void heroAi() {
		// TODO give the hero wandering and movement ai ASAP

	}

	public void kick(CombatEntity enemy) {
		Util.log(name + " used kick");
		int damage = Util.rand.nextInt(2) + 3;
		CombatLogic.calculateDamage(enemy, this, damage);
		secondMove();
	}

	@Override
	public void update() {
		if (possessed) {
			takeControl();
		} else {
			heroAi();
		}

	}

	public void swordSlash(CombatEntity enemy) {
		Util.log(name + " used swordSlash");
		int damage = 0;
		if (80 <= Util.rand.nextInt(100)) {
			damage = 8;
			Util.log(name + " landed a critical hit with sword slash");
			CombatLogic.calculateDamage(enemy, this, damage);

		} else {
			damage += Util.rand.nextInt(3);
			if (damage == 0) {
				Util.log(name + " did " + damage + " (aka missed)");
			}
			CombatLogic.calculateDamage(enemy, this, damage);
		}
		thirdMove();

	}

	private void takeControl() {

		if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
			speed *= 1.5;
		}
		if (Util.isKeyJustReleased(Input.Keys.SHIFT_LEFT)) {
			speed /= 1.5;
		}

		if (!movementLocked) {

			if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				facingLeft = true;
				velocity.x -= speed;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				facingLeft = false;
				velocity.x += speed;
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

	@Override
	public void firstMove() {
		secondMove();

	}

	@Override
	public void secondMove() {
		float movement = 1000;
		velocity.x += movement;
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				velocity.x -= movement;
			}
		}, 2.5f);

	}

	@Override
	public void thirdMove() {
		secondMove();

	}

	/**
	 * Do not call this, the hero combat is GUI based
	 * 
	 * @throws UnsupportedOperationException always
	 */
	@Override
	public void takeTurn(CombatEntity entity) {
		throw new UnsupportedOperationException("the Hero can't takeTurn");
	}

}
