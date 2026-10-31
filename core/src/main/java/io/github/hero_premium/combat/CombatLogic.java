package io.github.hero_premium.combat;

import com.badlogic.gdx.utils.Logger;
import io.github.hero_premium.entities.CombatEntity;
import io.github.hero_premium.util.Util;
import org.jetbrains.annotations.NotNull;

class CombatLogic {
    private static final Logger LOGGER = new Logger(CombatLogic.class.getName(), Util.LOGGING_LEVEL);

    private static void applyDamage(@NotNull CombatEntity target, int damage) {
        damage *= 10;
        target.health.modifyHp(-damage);
        LOGGER.info(target.getName() + " now has " + target.health.getHp());
    }

     static void doTurn(@NotNull CombatEntity target, @NotNull CombatEntity user, int damage) {
        user.statusEffectsManager.onTurn();
        applyDamage(target, damage);
        user.statusEffectsManager.onAfterTurn();
    }

    private CombatLogic() {
        throw new AssertionError("no io.github.hero_premium.combat.CombatLogic instances for you!");
    }
}
