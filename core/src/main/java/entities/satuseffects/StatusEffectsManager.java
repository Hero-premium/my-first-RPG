package entities.satuseffects;

// TODO that future me is now present me!, now each effect will define itself it its own class, all registered automatically by the ServiceLoader

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import entities.CombatEntity;
import util.ReadOnlyMap;

import java.util.ServiceLoader;

/**
 *
 */
public class StatusEffectsManager {
    private final ObjectMap<Class<?>, Effect> allEffects = new ObjectMap<>();
    private final Array<Effect> activeEffects = new Array<>();
    /**
     *
     */
    public final ReadOnlyMap<Effect> allEffect = new ReadOnlyMap<>(allEffects);


    public StatusEffectsManager(CombatEntity entity) {
        for (Effect effect : ServiceLoader.load(Effect.class)) {
            allEffects.put(effect.getClass(), effect);
            effect.setEntity(entity);
        }
    }

    public void resetBattleStates(){

    }
    public boolean isDodging = false;
    public boolean isDefending = false;
    public boolean isFocused = false;
}
