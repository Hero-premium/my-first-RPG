package io.github.hero_premium.combat;

import io.github.hero_premium.entities.combat.CombatEntity;
import org.jetbrains.annotations.NotNull;

class AiBattleManager {
    void launchBattle(@NotNull CombatEntity entity1, @NotNull CombatEntity entity2) {
        while (entity1.health.getHp() > 0) {
            entity1.takeTurn(entity2);
            if (entity2.health.getHp() <= 0) break;
            entity2.takeTurn(entity1);
        }
    }
}
