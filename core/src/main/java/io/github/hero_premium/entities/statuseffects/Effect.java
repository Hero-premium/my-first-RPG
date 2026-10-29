package io.github.hero_premium.entities.statuseffects;

import io.github.hero_premium.entities.CombatEntity;

public interface Effect {

    void onTurn(CombatEntity entity);

    void reset();

    void onAfterTurn(CombatEntity entity);

    boolean isActive();
}
