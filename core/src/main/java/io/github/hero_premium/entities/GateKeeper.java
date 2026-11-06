package io.github.hero_premium.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Logger;
import io.github.hero_premium.Assets;

import io.github.hero_premium.entities.combat.CombatEntity;
import io.github.hero_premium.entities.combat.moves.MoveAttack;
import io.github.hero_premium.entities.statuseffects.effects.Defending;
import io.github.hero_premium.entities.statuseffects.effects.Focused;
import io.github.hero_premium.entities.statuseffects.effects.Poison;
import io.github.hero_premium.util.Util;
import org.jetbrains.annotations.NotNull;

public class GateKeeper extends CombatEntity {
    private static final Logger LOGGER = new Logger(Entity.class.getName(), Util.LOGGING_LEVEL);

    public GateKeeper() {
        super(20, "GateKeeper", 12f, new Rectangle(200f, 200f, 64f, 64f), 250, Assets.gateKeeper);
        isPlayable = false;
    }


    private @NotNull MoveAttack fireWand(@NotNull CombatEntity player) {
        LOGGER.info(getName() + " used fireWand");
        player.statusEffectsManager.allEffects.get(Poison.class).applyPoison(3, 10);
        return new MoveAttack(2, getName() + " used fireWand");
    }

    private @NotNull MoveAttack focus() {
        LOGGER.info(getName() + " used focus");
        statusEffectsManager.allEffects.get(Focused.class).setIsActive();
        LOGGER.info(getName() + " is focusing on his attack... you may attack.");
        return new MoveAttack(0, getName() + " used focus");
    }

    private @NotNull MoveAttack shield() {
        LOGGER.info(getName() + " used shield");
        statusEffectsManager.allEffects.get(Defending.class).setIsActive();
        return new MoveAttack(2, getName() + " used shield");
    }

    // TODO add a smarter AI -# psttttt make it self aware
    @Override
    public MoveAttack takeTurn(CombatEntity player) {
        if (isPlayable)
            throw new IllegalStateException("cannot call AI based combat while the GateKeeper is fighting in GUI");
        if (health.getHp() <= 0) {
            LOGGER.error("the gateKeeper tried attacking from the grave");
            return new MoveAttack(0, "");
        }

        int choice = !statusEffectsManager.allEffects.get(Focused.class).isActive() ? Util.RANDOM.nextInt(3) : Util.RANDOM.nextInt(2) + 1;

        return switch (choice) {
            case 0 -> focus();
            case 1 -> fireWand(player);
            case 2 -> shield();
            default ->
                throw new AssertionError("the switch in io.github.hero_premium.entities.GateKeeper takeTurn ran into an unexpected case");
        };
    }

    @Override
    protected void registerMoves() {
        movesManager.addNewMove("focus", (CombatEntity _) -> focus());
        movesManager.addNewMove("fireWand", this::fireWand);
        movesManager.addNewMove("shield", (CombatEntity _) -> shield());
    }

    @Override
    public void update() {
        // no-op, yet
    }

}
