package io.github.hero_premium.entities.statuseffects.effects;

import io.github.hero_premium.entities.CombatEntity;
import io.github.hero_premium.entities.statuseffects.base.TogglableEffect;
import io.github.hero_premium.util.vault.Key;
import org.jetbrains.annotations.NotNull;

public class Dodging extends TogglableEffect {
    private final Key<Integer> key = new Key<>();

    @Override
    public void onTurn(CombatEntity entity) {
        key.addValue(0);
        entity.health.addModifier(key);
    }


    @Override
    public void onAfterTurn(@NotNull CombatEntity entity) {
        key.removeValue(0);
        super.onAfterTurn(entity);
    }
}
