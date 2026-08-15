package entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Assets;

import combat.CombatLogic;
import util.Util;

public class GateKeeper extends CombatEntity {

	public GateKeeper() {
		super(20, "GateKeeper", false, true, 12f, new Vector2(0, 0), new Rectangle(200f, 200f, 64f, 64f), 250,
				Assets.PlaceHolder);
	}

	private void fireWand(CombatEntity player) {
		Util.log(name + " used fireWand");
		CombatLogic.calculateDamage(player, this, 2);
		player.poisonDuration = 3;
	}

	private void focus() {
		Util.log(name + " used focus");
		isFocused = true;
		Util.log(name + " is focusing on his attack... you may attack.");
	}

	private void shield(CombatEntity player) {
		Util.log(name + " used Shield");
		CombatLogic.calculateDamage(player, this, 2);
		isDefending = true;
	}

	// TODO add a smarter AI -# psttttt make it self aware
	@Override
	public void takeTurn(CombatEntity player) {

		int choice = !isFocused ? Util.rand.nextInt(3) : Util.rand.nextInt(2) + 1;

		switch (choice) {
		case 0 -> focus();
		case 1 -> fireWand(player);
		case 2 -> shield(player);
		}
	}

}
