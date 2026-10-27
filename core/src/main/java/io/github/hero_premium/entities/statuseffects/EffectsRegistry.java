package io.github.hero_premium.entities.statuseffects;

import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import io.github.hero_premium.util.Util;

import java.lang.reflect.InvocationTargetException;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.stream.Stream;

public class EffectsRegistry {

    private static final ObjectSet<Class<? extends Effect>> ALL_EFFECTS = new ObjectSet<>();
    private static final Logger LOGGER = new Logger(EffectsRegistry.class.getName(), Util.LOGGING_LEVEL);

    static {
        try {
            Stream<ServiceLoader.Provider<Effect>> effects = ServiceLoader.load(Effect.class).stream();
            effects.forEach(provider -> {
                try {
                    Class<? extends Effect> effect = provider.type();
                    ALL_EFFECTS.add(effect);
                    LOGGER.info("Effect: " + effect.getSimpleName() + " has been added");
                } catch (ServiceConfigurationError e) {
                    LOGGER.error("Could not be loaded: " + e.getMessage());
                }
            });

        } catch (ServiceConfigurationError e) {
            LOGGER.error("no effects were loaded: " + e.getMessage());
        }
    }

    public static ObjectMap<Class<? extends Effect>, Effect> effectsFactory() {
        ObjectMap<Class<? extends Effect>, Effect> allEffects = new ObjectMap<>();
        for (Class<? extends Effect> key : ALL_EFFECTS) {
            try {
                allEffects.put(key, key.getDeclaredConstructor().newInstance());
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
        return allEffects;
    }
}
