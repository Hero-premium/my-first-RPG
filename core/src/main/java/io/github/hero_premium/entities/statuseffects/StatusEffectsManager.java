package io.github.hero_premium.entities.statuseffects;


import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ObjectMap;
import io.github.hero_premium.entities.CombatEntity;
import io.github.hero_premium.util.Util;
import io.github.hero_premium.util.collections.ReadOnlyClassMap;

import java.util.Objects;

/**
 *
 */
public class StatusEffectsManager {

    private static final Logger LOGGER = new Logger(StatusEffectsManager.class.getName(), Util.LOGGING_LEVEL);


    private final CombatEntity entity;
    private final ObjectMap<Class<? extends Effect>, Effect> allEffectsPrivate;

    /**
     * the list containing all effects and their data per entity
     *
     * @see Effect
     */
    public final ReadOnlyClassMap<Effect> allEffects;


    public StatusEffectsManager(CombatEntity entity) {
        this.entity = Objects.requireNonNull(entity, "entity cannot be null");
        allEffectsPrivate = EffectsRegistry.effectsFactory();
        allEffects = new ReadOnlyClassMap<>(allEffectsPrivate);
    }

    public void resetBattleStates() {
        for (Effect effect : allEffectsPrivate.values()) {
            effect.reset();
        }
    }

    public void onTurn() {
        for (Effect effect : allEffectsPrivate.values()) {
            if (effect.isActive()) effect.onTurn(entity);
        }
    }

    public void onAfterTurn() {
        for (Effect effect : allEffectsPrivate.values()) {
            if (effect.isActive()) effect.onAfterTurn(entity);
        }
    }
}
