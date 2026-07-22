package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Assets;

import combat.CombatLogic;
import util.Util;

public class Player extends CombatEntity {

	public Player() {
		super(10, "", false, true, 128f, new Vector2(0, 0), new Rectangle(0f, 0f, 50f, 60f), 200, 200, Assets.player);
	}

	public void kick(CombatEntity enemy) {
		System.out.println(name + " used kick");
		int damage = Util.rand.nextInt(2) + 3;
		CombatLogic.applyDamage(enemy, this, damage);
	}

	public void swordSlash(CombatEntity enemy) {
		System.out.println(name + " used swordSlash");
		int damage = 0;
		if (75 <= Util.rand.nextInt(100)) {
			damage = 8;
			System.out.println("critical hit!");
			CombatLogic.applyDamage(enemy, this, damage);

		} else {
			damage += Util.rand.nextInt(2) + 3;
			CombatLogic.applyDamage(enemy, this, damage);
		}

	}

	public void dodge(CombatEntity enemy) {
		System.out.println(name + " used dodge");
		isDodging = Util.rand.nextBoolean();
		int damage = 1;
		CombatLogic.applyDamage(enemy, this, damage);
	}

	public void move(float deltaTime) {
		if (!getMovementLocked()) {
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
