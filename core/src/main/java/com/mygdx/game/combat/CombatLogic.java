package com.mygdx.game.combat;

import com.mygdx.game.entities.CombatEntity;
import com.mygdx.game.util.Util;

public class CombatLogic {

    private static void applyDamage(CombatEntity target, int damage) {
        target.health.modifyHp(-damage);
        Util.log(target.name + " now has " + target.health.getHp());
    }

    public static void calculateDamage(CombatEntity target, CombatEntity user, int damage) {
        if (target == user) throw new IllegalArgumentException("the user " + user.name + " cannot also be the target");
        target.statusEffectsManager.onTick();
        user.statusEffectsManager.onTick();
        applyDamage(target, damage);
    }
}

