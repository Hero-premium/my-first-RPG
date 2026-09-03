package combat;

import entities.CombatEntity;
import util.Util;

public class CombatLogic {

    private static void applyDamage(CombatEntity target, int damage) {
        if (damage % 10 != 0)
            Util.logWarn("you may or may not have forgot to use the *10 multiplier");
        target.health.modifyHp(-damage);
        Util.log(target.name + " now has " + target.health.getHp());
    }

    public static void calculateDamage(CombatEntity target, CombatEntity user, int damage) {

        damage *= 10;
        int poisonDamage = 0;

        if (target.stats.getPoisonDuration() > 0) {
            poisonDamage = (Util.RANDOM.nextInt(2) + 1) * 10;
            damage += poisonDamage;
            Util.log(target.name + " took extra damage because of being on fire!");
            target.stats.modifyPoisonDuration(-1);
        }
        if (target.stats.isDodging) {
            Util.log(target.name + " has dodged the " + user.name + " attack!");
            target.stats.isDodging = false;
            target.stats.isFocused = false;
            applyDamage(target, poisonDamage);
            return;
        }
        if (target.stats.isDefending) {
            damage /= 2;
            Util.log("your damage was reduced by 50% because of " + target.name + " using a shield!");
            target.stats.isDefending = false;
        }
        if (user.stats.isFocused) {
            damage *= 2;
            Util.log(user.name + " focuses hard to deal 100% more damage!");
            user.stats.isFocused = false;
        }
        applyDamage(target, damage);
    }
}
