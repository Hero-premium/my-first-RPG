package io.github.hero_premium.entities.statuseffects;

import io.github.hero_premium.entities.CombatEntity;

public class Defending extends Effect {
    public Defending() {
        super(false);
    }

    @Override
    void onTurn(CombatEntity entity) {
        entity.health.setDamageModifier(-2);
    }

    @Override
    void reset() {

    }

    @Override
    void onAfterTurn(CombatEntity entity) {
        entity.health.setDamageModifier(1);
    }

    public void setActive() {
        duration = 1;
    }

    public boolean isActive() {
        return duration >= 1;
    }
}
