package entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Assets;

import combat.CombatLogic;
import util.Util;

public class GateKeeper extends CombatEntity {


	public GateKeeper() {
		super(20, "GateKeeper", false, false, true, false, 12f, new Vector2(0, 0), new Rectangle(200f, 200f, 64f, 64f), 250,
				250, 0, false, false, false, Assets.PlaceHolder);
	}

	private int damage = 0;

	void focus() {
		System.out.println(name + " used focus");
		isfocused = true;
		System.out.println(name + " is focusing on his attack... you may atack.");
	}

	void fireWand(CombatEntity player) {
		System.out.println(name + " used fireWand");
		damage += 2;
		CombatLogic.applyDamage(player, this, damage);
		player.setPoisonDur(3);
	}

	void shield(CombatEntity player) {
		System.out.println(name + " used Shield");
		damage += 2;
		CombatLogic.applyDamage(player, this, damage);
		isDefending = true;
	}
    // TODO add a smarter AI -# psttttt make it self aware
	@Override
	public void taketurn(CombatEntity player) {

		int choice = !isfocused ? Util.rand.nextInt(3) : Util.rand.nextInt(2) + 1;

		switch (choice) {
		case 0 -> focus();
		case 1 -> fireWand(player);
		case 2 -> shield(player);
		}
        damage = 0;
	}

}
