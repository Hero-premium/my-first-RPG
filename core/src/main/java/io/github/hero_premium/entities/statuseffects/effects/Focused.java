package io.github.hero_premium.entities.statuseffects.effects;

import io.github.hero_premium.entities.combat.CombatEntity;
import io.github.hero_premium.entities.statuseffects.base.TogglableEffect;
import io.github.hero_premium.util.vault.Key;
import org.jetbrains.annotations.NotNull;

public class Focused extends TogglableEffect {

    private final Key<Integer> key = new Key<>();


    @Override
    public void onTurn(@NotNull CombatEntity entity) {
        if (!key.contains(2)) key.addValue(2);
        entity.damageAttributes.add(key);
    }

    @Override
    public void reset() {
        if (key.contains(2)) key.removeValue(2);
        super.reset();
    }

    @Override
    public void onAfterTurn(CombatEntity entity) {
        if (key.contains(2)) key.removeValue(2);
        super.onAfterTurn(entity);
    }
}
