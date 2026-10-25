package io.github.hero_premium.entities.statuseffects;

import io.github.hero_premium.entities.CombatEntity;

public abstract class Effect {

    protected int duration;
    protected int value;

    boolean isActive;
    final boolean canByPassDefences;

    public Effect(boolean canByPassDefences) {
        this.canByPassDefences = canByPassDefences;
    }

    abstract void onTurn(CombatEntity entity);
    abstract void reset();

    @Override
    public String toString(){
        return getClass().getSimpleName() + " effect";
    }
}
