package combat;

import entities.CombatEntity;
import util.Util;

public class CombatLogic {

	public static void applyDamage(CombatEntity target, CombatEntity user, int damage) {

		if (target.isDodging) {
			Util.log(target.name + " has dodged the " + user.name + " attack!");
			target.isDodging = false;
			target.isfocused = false;
			target.poisonDuration--;
			return;
		}
		if (target.isDefending) {
			damage /= 2;
			Util.log("your damage was reduced by 50% because of " + target.name + " using a sheild!");
			target.isDefending = false;
		}
		if (user.isfocused) {
			damage *= 2;
			Util.log(user.name + " focuses hard to deal 100% more damage!");
			user.isfocused = false;
		}
		if (target.poisonDuration > 0) {
			damage += Util.rand.nextInt(2) + 1;
			Util.log(target.name + " took extra damage because of being on fire!");
			target.poisonDuration--;
		}
		damage *= 10;
		target.modifyHp(-damage);
		Util.log(target.name + " now has " + target.getHp());
	}
}
