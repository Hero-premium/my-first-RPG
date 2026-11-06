package io.github.hero_premium.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Logger;
import io.github.hero_premium.Assets;
import io.github.hero_premium.entities.combat.CombatEntity;
import io.github.hero_premium.entities.combat.moves.MoveAttack;
import io.github.hero_premium.entities.statuseffects.effects.Dodging;
import io.github.hero_premium.util.Util;
import org.jetbrains.annotations.NotNull;

public class Hero extends CombatEntity {
    private static final Logger LOGGER = new Logger(Hero.class.getName(), Util.LOGGING_LEVEL);
    boolean possessed = true;

    public Hero() {
        super(10, "Hero", 32f, new Rectangle(0f, 0f, 50f, 60f), 200, Assets.player);
        isPlayable = true;
    }

    private void heroAi() {
        // TODO give the hero wandering and movement ai ASAP

    }

    private @NotNull MoveAttack kick() {
        LOGGER.info(getName() + " used kick");
        return new MoveAttack(Util.RANDOM.nextInt(2) + 3, getName() + " used kick");
    }

    private @NotNull MoveAttack dodge() {
        LOGGER.info(getName() + " used dodge");
        if (Util.RANDOM.nextBoolean()) statusEffectsManager.allEffects.get(Dodging.class).setIsActive();
        return new MoveAttack(1, getName() + " used dodge");
    }

    private @NotNull MoveAttack swordSlash() {
        LOGGER.info(getName() + " used swordSlash");
        if (80 <= Util.RANDOM.nextInt(100)) {
            LOGGER.info(getName() + " landed a critical hit with sword slash");
            return new MoveAttack(8,
                getName() + " used swordSlash" + "\n " + getName() + " landed a critical hit with sword slash");
        }
        int damage = Util.RANDOM.nextInt(3);
        String isZero = "";
        if (damage == 0) {
            isZero = "\n" + getName() + " did 0 damage (aka missed)";
            LOGGER.info(isZero);
        }

        return new MoveAttack(damage, getName() + " used swordSlash" + isZero);
    }

    private @NotNull MoveAttack superSecretMove() {
        LOGGER.info("you used the super secret move, if this was a real build shame on you!");
        return new MoveAttack(1000, "you used the super secret move, if this was a real build shame on you!");
    }

    @Override
    public void update() {
        if (possessed) {
            takeControl();
        } else {
            heroAi();
        }

    }

    private void takeControl() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
            movement.setSpeedModifier(1.5F);
        }
        if (Util.isKeyJustReleased(Input.Keys.SHIFT_LEFT)) {
            movement.setSpeedModifier(1);
        }

        if (!movement.movementLocked) {

            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                movement.facingLeft = true;
                movement.velocity.x -= movement.getSpeed();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                movement.facingLeft = false;
                movement.velocity.x += movement.getSpeed();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && movement.onGround) {
                movement.velocity.y = 450;
                movement.onGround = false;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
                movement.velocity.x += movement.facingLeft ? -450 : 450;

            }
        }

    }

    /**
     *
     * @param entity the entity being attacked
     * @throws IllegalStateException if called when this entity isn't in a fighting state
     */
    @Override
    public @NotNull MoveAttack takeTurn(CombatEntity entity) {
        //TODO give hero his own AI instead of the testing GateKeeper's AI
        if (isPlayable)
            throw new IllegalStateException("cannot call AI based combat while the hero is fighting in GUI");
        int choice = Util.RANDOM.nextInt(3);

        return switch (choice) {
            case 0 -> kick();
            case 1 -> swordSlash();
            case 2 -> dodge();
            default ->
                throw new AssertionError("the switch in io.github.hero_premium.entities.Hero takeTurn ran into an unexpected case");
        };
    }

    @Override
    protected void registerMoves() {
        movesManager.addNewMove("kick", (CombatEntity _) -> kick());
        movesManager.addNewMove("swordSlash", (CombatEntity _) -> swordSlash());
        movesManager.addNewMove("dodge", (CombatEntity _) -> dodge());
        movesManager.addNewMove("secret move", (CombatEntity _) -> superSecretMove());
    }
}
