package com.mygdx.game.entities.statuseffects;


import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.game.entities.CombatEntity;
import com.mygdx.game.util.ReadOnlyMap;
import com.mygdx.game.util.Util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 *
 */
public class StatusEffectsManager {
    private static final ObjectMap<Class<? extends Effect>, Effect> ALL_EFFECTS = new ObjectMap<>();

    static {
        for (Effect effect : ServiceLoader.load(Effect.class)) {
            ALL_EFFECTS.put(effect.getClass(), effect);
            Util.log(effect + " has been added");

        }
    }

    private final CombatEntity entity;

    private final ObjectMap<Class<?  extends Effect>, Effect> allEffects = new ObjectMap<>();

    /**
     * the list containing all effects and their data per entity
     *
     * @see Effect
     */
    public final ReadOnlyMap<Effect> allEffect = new ReadOnlyMap<>(allEffects);


    public StatusEffectsManager(CombatEntity entity) {
        this.entity = Objects.requireNonNull(entity, "entity cannot be null");

        for (Class<? extends Effect > key : ALL_EFFECTS.keys()) {
            try {
                Constructor<? extends Effect> ctor = key.getDeclaredConstructor();
                ctor.setAccessible(true);
                allEffects.put(key, ctor.newInstance());
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(key + " has no no-arg constructor", e);
            } catch (InstantiationException e) {
                throw new RuntimeException(key + " cannot be instantiated (is it abstract?)", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(key + " constructor is inaccessible (most likely because of module rules)", e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(key + " constructor threw an exception", e.getCause());
            }
        }
    }

    public void resetBattleStates(){
    }

    public void onTick(){
        for (Effect effect : allEffects.values()){
            effect.onTick(entity);
        }
    }

    public boolean isDodging = false;
    public boolean isDefending = false;
    public boolean isFocused = false;
}
