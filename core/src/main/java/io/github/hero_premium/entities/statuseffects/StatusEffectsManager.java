package io.github.hero_premium.entities.statuseffects;


import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ObjectMap;
import io.github.hero_premium.entities.CombatEntity;
import io.github.hero_premium.util.ReadOnlyMap;
import io.github.hero_premium.util.Util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.stream.Stream;

/**
 *
 */
public class StatusEffectsManager {

    private static final Logger LOGGER = new Logger(StatusEffectsManager.class.getName(), Util.LOGGING_LEVEL);
    private static final ObjectMap<Class<? extends Effect>, Effect> ALL_EFFECTS = new ObjectMap<>();

    static {
        try {
            Stream<ServiceLoader.Provider<Effect>> effects = ServiceLoader.load(Effect.class).stream();
            effects.forEach(provider -> {
                try {
                    Effect effect = provider.get();
                    ALL_EFFECTS.put(effect.getClass(), effect);
                    LOGGER.info(effect + " has been added");
                } catch (ServiceConfigurationError e) {
                    LOGGER.error("Could not be loaded: " + e.getMessage());
                }
            });

        } catch (ServiceConfigurationError e) {
            LOGGER.error("no effects were loaded: " + e.getMessage());
        }
    }

    private final CombatEntity entity;
    private final ObjectMap<Class<? extends Effect>, Effect> allEffects;

    /**
     * the list containing all effects and their data per entity
     *
     * @see Effect
     */
    public final ReadOnlyMap<Effect> allEffect;


    public StatusEffectsManager(CombatEntity entity) {
        this.entity = Objects.requireNonNull(entity, "entity cannot be null");
        this.allEffects = new ObjectMap<>();
        for (Class<? extends Effect> key : ALL_EFFECTS.keys()) {
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
        allEffect = new ReadOnlyMap<>(allEffects);
    }

    public void resetBattleStates() {
    }

    public void onTick() {
        for (Effect effect : allEffects.values()) {
            effect.onTick(entity);
        }
    }

    public boolean isDodging = false;
    public boolean isDefending = false;
    public boolean isFocused = false;
}
