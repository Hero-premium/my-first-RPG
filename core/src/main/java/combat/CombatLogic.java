package combat;

import entities.statuseffects.Poison;
import entities.CombatEntity;
import util.Util;

public class CombatLogic {

    private static void applyDamage(CombatEntity target, int damage) {
        target.health.modifyHp(-damage);
        Util.log(target.name + " now has " + target.health.getHp());
    }

    public static void calculateDamage(CombatEntity target, CombatEntity user, int damage) {
        if (target == user) throw new IllegalArgumentException("the user " + user.name + " cannot also be the target");
        damage *= 10;
        int poisonDamage = 0;

        if (target.statusEffectsManager.allEffect.get(Poison.class).getPoisonDuration() > 0) {
            poisonDamage = (Util.RANDOM.nextInt(2) + 1) * 10;
            damage += poisonDamage;
            Util.log(target.name + " took extra damage because of being on fire!");
        }
}}

