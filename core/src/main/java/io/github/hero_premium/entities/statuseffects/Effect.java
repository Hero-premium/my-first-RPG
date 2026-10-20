package io.github.hero_premium.entities.statuseffects;

import io.github.hero_premium.entities.CombatEntity;

public abstract class Effect {

    protected int duration;
    protected int value;

    boolean isActive;
    boolean canByPassDefences;

    public Effect() {
    }

    abstract void onTick(CombatEntity entity);

    @Override
    public String toString(){
        return getClass().getSimpleName() + " effect";
    }
}
