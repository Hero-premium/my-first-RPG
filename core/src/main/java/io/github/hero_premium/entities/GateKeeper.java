package io.github.hero_premium.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Logger;
import io.github.hero_premium.Assets;

import io.github.hero_premium.combat.CombatLogic;
import io.github.hero_premium.entities.statuseffects.Poison;
import io.github.hero_premium.util.Util;

public class GateKeeper extends CombatEntity {
    private static final Logger LOGGER = new Logger(Entity.class.getName(), Util.LOGGING_LEVEL);

    public GateKeeper() {
        super(20, "GateKeeper", 12f, new Rectangle(200f, 200f, 64f, 64f), 250, Assets.gateKeeper);
        isPlayable = false;
    }

    private void fireWand(CombatEntity player) {
        LOGGER.info(name + " used fireWand");
        CombatLogic.calculateDamage(player, this, 2);
        player.statusEffectsManager.allEffects.get(Poison.class).applyPoison(3, 10);
    }

    private void focus() {
        LOGGER.info(name + " used focus");
        statusEffectsManager.isFocused = true;
        LOGGER.info(name + " is focusing on his attack... you may attack.");
    }

    private void shield(CombatEntity player) {
        LOGGER.info(name + " used Shield");
        CombatLogic.calculateDamage(player, this, 2);
        statusEffectsManager.isDefending = true;
    }

    // TODO add a smarter AI -# psttttt make it self aware
    @Override
    public void takeTurn(CombatEntity player) {
        if (isPlayable)
            throw new IllegalStateException("cannot call AI based combat while the GateKeeper is fighting in GUI");
        if (health.getHp() <= 0) {
            LOGGER.error("the gateKeeper tried attacking from the grave");
            return;
        }

        int choice = !statusEffectsManager.isFocused ? Util.RANDOM.nextInt(3) : Util.RANDOM.nextInt(2) + 1;

        switch (choice) {
            case 0 -> focus();
            case 1 -> fireWand(player);
            case 2 -> shield(player);
            default ->
                throw new AssertionError("the switch in entities.GateKeeper takeTurn ran into an unexpected case");
        }
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
