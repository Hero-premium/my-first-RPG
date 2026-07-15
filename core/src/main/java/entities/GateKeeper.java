package entities;

import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Textures;

import combat.CombatLogic;

public class GateKeeper extends CombatEntity {

	public static String[] getDialog() {
		return new String[] {
				/* 1 */ "gatekeeper: welcome to this dungeon; but coming here was a big mistake! as there's an angry dragon ready to hunt whoever enters.",
				/* 2 */ "gatekeeper: what do I call you, hunter?",
				/* 3 */ "gatekeeper: but , that is such a dangerous mission, how are you planning on doing it?",
				/* 4 */ "gatekeeper: and what are they...?",
				/* 5 */ "gatekeeper: oh you shouldn't have told me that, as I'll be your first foe, I'll defend my lord with my life!" };
	
	}

	public GateKeeper() {
		super(20, "GateKeeper", false, false, true, 12f, new Vector2(0, 0), new Rectangle(200f, 200f, 64f, 64f), 250,
				250, 0, false, false, false, getDialog(), Textures.player);
	}

	// TODO refactor the moves into methods
	// TODO replace the switches from : to ->
	// Priority: low

	@Override
	public void taketurn(CombatEntity Player, Random rand) {

		int choice = !isfocused ? rand.nextInt(3) : rand.nextInt(2) + 1;
		int damage = 0;

		switch (choice) {
		case 0:
			System.out.println(name + " used focus");
			isfocused = true;
			System.out.println(name + " is focusing on his attack... you may atack.");
			break;

		case 1:
			System.out.println(name + " used fireWand");
			damage += 2;
			CombatLogic.applyDamage(Player, this, damage, rand);
			Player.setPoisonDur(3);

			break;

		case 2:
			System.out.println(name + " used Sheild");
			damage += 2;
			CombatLogic.applyDamage(Player, this, damage, rand);
			isDefending = true;
			break;

		}

	}

}
