package entities.satuseffects;

import entities.CombatEntity;

import java.util.Objects;

public abstract class Effect {
    // Hii!! i am a new class, pleased to meet you!

    protected int value;
    protected CombatEntity entity;

    public Effect() {
    }

    abstract void onTick();

    void setEntity(CombatEntity entity) {
        if (this.entity != null) throw new IllegalArgumentException("cannot reassign entity");
        this.entity = Objects.requireNonNull(entity, "entity cannot be null");
    }
}
