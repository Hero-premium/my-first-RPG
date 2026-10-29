package io.github.hero_premium.entities.statuseffects;

import io.github.hero_premium.entities.CombatEntity;
import io.github.hero_premium.util.vault.Key;
import org.jetbrains.annotations.NotNull;

public class Dodging implements Effect {
    private boolean isActive;
    private final Key<Integer> key = new Key<>();

    public Dodging() {

    }

    @Override
    public void onTurn(CombatEntity entity) {
        key.addValue(0);
        entity.health.addModifier(key);
    }

    @Override
    public void reset() {
        isActive = false;
    }

    @Override
    public void onAfterTurn(@NotNull CombatEntity entity) {
        key.removeValue(0);
        isActive = false;
    }

    public void setActive() {
        isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

}
