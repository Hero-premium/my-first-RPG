package entities;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Textures;

import combat.CombatLogic;

public class Player extends CombatEntity {

	public static String[] getDialog() {
		return new String[] { "player: yes! I know, but I want the gold the dragon is hiding beneath his throne.",
				"player: of course I didn't come unprepared, I do have some moves that will help me defeat this evil monster.",
				"player: dash! An awesome ability that would help me dodge the dragon's attacks. Sometimes I don't move fast enough though.",
				"player: swordSlash: I'm not very good at using my sword, but it helps me attack vital organs. Most of the time I can't.",
				"player: kick: One of my best moves that I mastered. It's still based on my human abilities, but it's very consistent." };
	}

	private boolean isAllowedToMove = true;

	public boolean isAllowedToMove() {
		return isAllowedToMove;
	}

	public void setAllowedToMove(boolean isAllowedToMove) {
		this.isAllowedToMove = isAllowedToMove;
	}

	public Player() {
		super(10, "Hero", false, false, true, 128f, new Vector2(0, 0), new Rectangle(0f, 0f, 50f, 60f), 200, 200, 0,
				false, false, false, getDialog(), Textures.player); // combat stats
	}
	// gold,name, texture position at start speed velocity hitbox

	public void kick(Random rand, CombatEntity enemy) {
		System.out.println(name + " used kick");
		int damage = rand.nextInt(2) + 3;
		CombatLogic.applyDamage(enemy, this, damage, rand);
	}

	public void swordSlash(Random rand, CombatEntity enemy) {
		System.out.println(name + " used swordSlash");
		int damage = 0;
		if (75 <= rand.nextInt(100)) {
			damage = 8;
			System.out.println("critical hit!");
			CombatLogic.applyDamage(enemy, this, damage, rand);

		} else {
			damage += rand.nextInt(2) + 3;
			CombatLogic.applyDamage(enemy, this, damage, rand);
		}

	}

	public void dodge(Random rand, CombatEntity enemy) {
		System.out.println(name + " used dodge");
		isDodging = rand.nextBoolean();
		int damage = 1;
		CombatLogic.applyDamage(enemy, this, damage, rand);
	}

	public void move(float deltaTime) {
		if (isAllowedToMove) {
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
