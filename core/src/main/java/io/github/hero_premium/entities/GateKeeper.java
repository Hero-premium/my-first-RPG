package io.github.hero_premium.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Logger;
import io.github.hero_premium.Assets;

import io.github.hero_premium.entities.statuseffects.effects.Defending;
import io.github.hero_premium.entities.statuseffects.effects.Focused;
import io.github.hero_premium.entities.statuseffects.effects.Poison;
import io.github.hero_premium.util.Util;

public class GateKeeper extends CombatEntity {
    private static final Logger LOGGER = new Logger(Entity.class.getName(), Util.LOGGING_LEVEL);

    public GateKeeper() {
        super(20, "GateKeeper", 12f, new Rectangle(200f, 200f, 64f, 64f), 250, Assets.gateKeeper);
        isPlayable = false;
    }

    private int fireWand(CombatEntity player) {
        LOGGER.info(getName() + " used fireWand");
        player.statusEffectsManager.allEffects.get(Poison.class).applyPoison(3, 10);
        return 2;
    }

    private int focus() {
        LOGGER.info(getName() + " used focus");
        statusEffectsManager.allEffects.get(Focused.class).setIsActive();
        LOGGER.info(getName() + " is focusing on his attack... you may attack.");
        return 0;
    }

    private int shield(CombatEntity player) {
        LOGGER.info(getName() + " used Shield");
        statusEffectsManager.allEffects.get(Defending.class).setIsActive();
        return 2;
    }

    // TODO add a smarter AI -# psttttt make it self aware
    @Override
    public int takeTurn(CombatEntity player) {
        if (isPlayable)
            throw new IllegalStateException("cannot call AI based combat while the GateKeeper is fighting in GUI");
        if (health.getHp() <= 0) {
            LOGGER.error("the gateKeeper tried attacking from the grave");
            return 0;
        }

        int choice = !statusEffectsManager.allEffects.get(Focused.class).isActive() ? Util.RANDOM.nextInt(3) : Util.RANDOM.nextInt(2) + 1;

        return switch (choice) {
            case 0 -> focus();
            case 1 -> fireWand(player);
            case 2 -> shield(player);
            default ->
                throw new AssertionError("the switch in entities.GateKeeper takeTurn ran into an unexpected case");
        };
    }

    @Override
    protected void registerMoves() {
        movesManager.addNewMove("focus", (CombatEntity _) -> focus());
        movesManager.addNewMove("fireWand", this::fireWand);
        movesManager.addNewMove("shield", this::shield);
    }

    @Override
    public void update() {
        // no-op, yet
    }

}
