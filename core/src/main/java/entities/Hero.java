package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Assets;

import combat.CombatLogic;
import util.Util;

public class Hero extends CombatEntity implements CombatAnimation {

	boolean possessed = true;

	public Hero() {
		super(10, "Hero", false, 5000f, new Vector2(0, 0), new Rectangle(0f, 0f, 50f, 60f), 200, Assets.player);
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
	public void update(float deltaTime) {
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

	private void takeControl(float deltaTime) {

		if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
			speed *= 1.5;
		}
		if (Util.isKeyJustReleased(Input.Keys.SHIFT_LEFT)) {
			speed /= 1.5;
		}

		if (!movementLocked) {

			if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				facingLeft = true;
				velocity.x -= speed * deltaTime;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				facingLeft = false;
				velocity.x += speed * deltaTime;
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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}
}
