package io.github.hero_premium.combat;

import com.badlogic.gdx.utils.Logger;
import io.github.hero_premium.entities.CombatEntity;
import io.github.hero_premium.util.Util;
import org.jetbrains.annotations.NotNull;

public class CombatLogic {
    private static final Logger LOGGER = new Logger(CombatLogic.class.getName(), Util.LOGGING_LEVEL);

    private static void applyDamage(@NotNull CombatEntity target, int damage) {
        damage *= 10;
        target.health.modifyHp(-damage);
        LOGGER.info(target.name + " now has " + target.health.getHp());
    }

    public static void doTurn(@NotNull CombatEntity target, @NotNull CombatEntity user, int damage) {
        target.statusEffectsManager.onTurn();
        user.statusEffectsManager.onTurn();
        applyDamage(target, damage);
        target.statusEffectsManager.onAfterTurn();
        user.statusEffectsManager.onAfterTurn();
    }
}
