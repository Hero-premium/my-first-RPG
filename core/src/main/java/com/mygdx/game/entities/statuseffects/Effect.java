package com.mygdx.game.entities.statuseffects;

import com.mygdx.game.entities.CombatEntity;

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
