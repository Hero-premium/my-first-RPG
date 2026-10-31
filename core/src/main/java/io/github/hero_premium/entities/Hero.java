package io.github.hero_premium.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Logger;
import io.github.hero_premium.Assets;
import io.github.hero_premium.entities.statuseffects.effects.Dodging;
import io.github.hero_premium.util.Util;

public class Hero extends CombatEntity {
    private static final Logger LOGGER = new Logger(Hero.class.getName(), Util.LOGGING_LEVEL);
    boolean possessed = true;

    public Hero() {
        super(10, "Hero", 32f, new Rectangle(0f, 0f, 50f, 60f), 200, Assets.player);
        isPlayable = true;
    }

    private int dodge(CombatEntity enemy) {
        LOGGER.info(getName() + " used dodge");
        if (Util.RANDOM.nextBoolean()) statusEffectsManager.allEffects.get(Dodging.class).setIsActive();
        return 1;
    }

    private void heroAi() {
        // TODO give the hero wandering and movement ai ASAP

    }

    private int kick(CombatEntity enemy) {
        LOGGER.info(getName() + " used kick");
        return Util.RANDOM.nextInt(2) + 3;
    }

    @Override
    public void update() {
        if (possessed) {
            takeControl();
        } else {
            heroAi();
        }

    }

    private int swordSlash(CombatEntity enemy) {
        LOGGER.info(getName() + " used swordSlash");
        int damage;
        if (80 <= Util.RANDOM.nextInt(100)) {
            damage = 8;
            LOGGER.info(getName() + " landed a critical hit with sword slash");

        } else {
            damage = Util.RANDOM.nextInt(3);
            if (damage == 0) {
                LOGGER.info(getName() + " did " + damage + " (aka missed)");
            }
        }
        return damage;
    }

    private int superSecretMove(CombatEntity entity) {
        entity.health.modifyHp(-1000);
        LOGGER.info("you used the super secret move, if this was a real build shame on you!");
        return 1000;
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
    public int takeTurn(CombatEntity entity) {
        //TODO give hero his own AI instead of the testing GateKeeper's AI
        if (isPlayable)
            throw new IllegalStateException("cannot call AI based combat while the hero is fighting in GUI");
        int choice = Util.RANDOM.nextInt(3);

        return switch (choice) {
            case 0 -> kick(entity);
            case 1 -> swordSlash(entity);
            case 2 -> dodge(entity);
            default -> throw new AssertionError("the switch in entities.Hero takeTurn ran into an unexpected case");
        };
    }

    @Override
    protected void registerMoves() {
        movesManager.addNewMove("kick", this::kick);
        movesManager.addNewMove("swordSlash", this::swordSlash);
        movesManager.addNewMove("dodge", this::dodge);
        movesManager.addNewMove("secret move", this::superSecretMove);
    }
}
