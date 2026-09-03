package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Assets;


import combat.CombatLogic;
import util.Util;

public class Hero extends CombatEntity {

    boolean possessed = true;

    public Hero() {
        super(10, "Hero", 32f, new Rectangle(0f, 0f, 50f, 60f), 200, Assets.player);
        isGUIBased = false;
    }

    private void dodge(CombatEntity enemy) {
        Util.log(name + " used dodge");
        stats.isDodging = Util.RANDOM.nextBoolean();
        int damage = 1;
        CombatLogic.calculateDamage(enemy, this, damage);
        firstMove();
    }

    private void heroAi() {
        // TODO give the hero wandering and movement ai ASAP

    }

    private void kick(CombatEntity enemy) {
        Util.log(name + " used kick");
        int damage = Util.RANDOM.nextInt(2) + 3;
        CombatLogic.calculateDamage(enemy, this, damage);
        secondMove();
    }

    @Override
    public void update() {
        if (possessed) {
            takeControl();
        } else {
            heroAi();
        }

    }

    private void swordSlash(CombatEntity enemy) {
        Util.log(name + " used swordSlash");
        int damage = 0;
        if (80 <= Util.RANDOM.nextInt(100)) {
            damage = 8;
            Util.log(name + " landed a critical hit with sword slash");
            CombatLogic.calculateDamage(enemy, this, damage);

        } else {
            damage += Util.RANDOM.nextInt(3);
            if (damage == 0) {
                Util.log(name + " did " + damage + " (aka missed)");
            }
            CombatLogic.calculateDamage(enemy, this, damage);
        }
        thirdMove();

    }

    private void takeControl() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
            speed *= 1.5;
        }
        if (Util.isKeyJustReleased(Input.Keys.SHIFT_LEFT)) {
            speed /= 1.5;
        }

        if (!movementLocked) {

            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                facingLeft = true;
                velocity.x -= speed;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                facingLeft = false;
                velocity.x += speed;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && onGround) {
                velocity.y = 450;
                onGround = false;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
                velocity.x += facingLeft ? -450 : 450;

            }
        }

    }

    public void firstMove() {
        secondMove();

    }

    public void secondMove() {
        float movement = 1000;
        velocity.x += movement;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                velocity.x -= movement;
            }
        }, 2.5f);

    }

    public void thirdMove() {
        secondMove();

    }

    /**
     *
     * @param entity the entity being attacked
     * @throws IllegalStateException if called when this entity isn't in a fighting state
     */
    @Override
    public void takeTurn(CombatEntity entity) {
        //TODO give hero his own AI instead of the testing GateKeeper's AI
        if (isGUIBased) throw new IllegalStateException("cannot call AI based combat while the hero is fighting in GUI");
        int choice = !stats.isFocused ? Util.RANDOM.nextInt(3) : Util.RANDOM.nextInt(2) + 1;

        switch (choice) {
            case 0 -> kick(entity);
            case 1 -> swordSlash(entity);
            case 2 -> dodge(entity);
            default ->
                throw new AssertionError("the switch in entities.Hero takeTurn ran into an unexpected case");
        }
    }

    @Override
    protected void registerMoves() {
        movesManager.addNewMove("kick", (CombatEntity e) -> kick(e));
        movesManager.addNewMove("swordSlash", (CombatEntity e) -> swordSlash(e));
        movesManager.addNewMove("dodge", (CombatEntity e) -> dodge(e));
    }
}
