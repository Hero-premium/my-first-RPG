package io.github.hero_premium.entities.statuseffects;

import io.github.hero_premium.entities.CombatEntity;
import io.github.hero_premium.util.vault.Key;
import org.jetbrains.annotations.NotNull;

public class Defending implements Effect {
    private final Key<Integer> key = new Key<>();
    private boolean isActive;

    @Override
    public void onTurn(@NotNull CombatEntity entity) {
        key.addValue(-2);
        entity.health.addModifier(key);
    }

    @Override
    public void reset() {
        isActive = false;
    }

    @Override
    public void onAfterTurn(CombatEntity entity) {
        key.removeValue(-2);
        isActive = false;
    }

    public void setActive() {
        isActive = true;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }
}
