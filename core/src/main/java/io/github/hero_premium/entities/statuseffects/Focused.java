package io.github.hero_premium.entities.statuseffects;

import io.github.hero_premium.entities.CombatEntity;
import io.github.hero_premium.util.vault.Key;

public class Focused implements Effect {
    private boolean isActive;
    private final Key<Integer> key = new Key<>();

    public Focused() {
    }

    @Override
    public void onTurn(CombatEntity entity) {
    }

    @Override
    public void reset() {
        isActive = false;
    }

    @Override
    public void onAfterTurn(CombatEntity entity) {
        isActive = false;
    }

    public void setActive() {
        isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }
}
