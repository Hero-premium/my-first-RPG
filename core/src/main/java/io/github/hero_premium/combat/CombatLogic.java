package io.github.hero_premium.combat;

import com.badlogic.gdx.utils.Logger;
import io.github.hero_premium.entities.CombatEntity;
import io.github.hero_premium.util.Util;

public class CombatLogic {
    private static final Logger LOGGER = new Logger(CombatLogic.class.getName(), Util.LOGGING_LEVEL);

    private static void applyDamage(CombatEntity target, int damage) {
        target.health.modifyHp(-damage);
        LOGGER.info(target.name + " now has " + target.health.getHp());
    }

    public static void calculateDamage(CombatEntity target, CombatEntity user, int damage) {
        if (target == user) throw new IllegalArgumentException("the user " + user.name + " cannot also be the target");
        target.statusEffectsManager.onTick();
        user.statusEffectsManager.onTick();
        applyDamage(target, damage);
    }
}

