package io.github.hero_premium.entities.statuseffects;

import io.github.hero_premium.entities.CombatEntity;

public class Dodging extends Effect {
    public Dodging() {
        super(true);
    }

    @Override
    void onTurn(CombatEntity entity) {
        entity.health.addModifier(0);
    }

    @Override
    void reset() {
    }

    @Override
    void onAfterTurn(CombatEntity entity) {
        entity.health.addModifier(1);
    }

    public void setActive() {
        duration = 1;
    }

    public boolean isActive() {
        return duration >= 1;
    }

}
