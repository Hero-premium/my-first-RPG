package io.github.hero_premium.entities.statuseffects;


import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ObjectMap;
import io.github.hero_premium.entities.CombatEntity;
import io.github.hero_premium.util.ReadOnlyMap;
import io.github.hero_premium.util.Util;

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
    public final ReadOnlyMap<Effect> allEffects;


    public StatusEffectsManager(CombatEntity entity) {
        this.entity = Objects.requireNonNull(entity, "entity cannot be null");
        allEffectsPrivate = EffectsRegistry.getAllEffects();
        allEffects = new ReadOnlyMap<>(allEffectsPrivate);
    }

    public void resetBattleStates() {
        for (Effect effect : allEffectsPrivate.values()) {
            effect.reset();
        }
    }

    public void onTurn() {
        for (Effect effect : allEffectsPrivate.values()) {
            effect.onTurn(entity);
        }
    }

    public void onAfterTurn() {
        for (Effect effect : allEffectsPrivate.values()) {
            effect.onAfterTurn(entity);
        }
    }
}
