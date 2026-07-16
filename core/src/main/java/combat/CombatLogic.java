package combat;

import java.util.Random;

import entities.CombatEntity;

public class CombatLogic {

	public static void applyDamage(CombatEntity target, CombatEntity user, int damage, Random rand) {

		if (target.isDodging) {
			System.out.println(target.name + " has dodged the " + user.name + " attack!");
			target.isDodging = false;
			target.isfocused = false;
			target.poisonDuration--;
			return;
		}
		if (target.isDefending) {
			damage /= 2;
			System.out.println("your damage was reduced by 50% because of " + target.name + " using a sheild!");
			target.isDefending = false;
		}
		if (user.isfocused) {
			damage *= 2;
			System.out.println(user.name + " focuses hard to deal 100% more damage!");
			user.isfocused = false;
		}
		if (target.poisonDuration > 0) {
			damage += rand.nextInt(2) + 1;
			System.out.println(target.name + " took extra damage because of being on fire!");
			target.poisonDuration--;
		}
		damage *= 10;
		target.modifyHp(damage);
	}
}
