package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entities.componants.Movement;
import com.mygdx.game.util.Util;

import java.util.Objects;

public abstract class Entity {

    public final transient Texture texture;
    public String name;
    private int gold;

    /**
     * resposable for holding the entities position and movement informations and methods
     */
    public final Movement movement;
    protected Entity(int gold, String name, float speed, Rectangle hitBox, Texture texture) {
        this.movement = new Movement(hitBox, speed);
        this.gold = Util.requireNonNegative(gold);
        this.name = Objects.requireNonNull(name);
        this.texture = Objects.requireNonNull(texture);
    }

    /**
     * This is meant to be called in the render loop after batch.begin to draw the
     * entity on the screen
     *
     * @param batch the SpriteBatch used in this screen
     * @throws IllegalStateException if this was called before SpriteBatch.begin
     */
    public void draw(SpriteBatch batch) {
        batch.draw(texture, movement.hitBox.x, movement.hitBox.y, movement.hitBox.width, movement.hitBox.height, 0, 0, texture.getWidth(),
            texture.getHeight(), !movement.facingLeft, false);
    }

    /**
     * @return the entity's current gold
     */
    public int getGold() {
        return gold;
    }

    /**
     * moves an amount of gold from an entity to another entity
     *
     * @param amount   - the amount of gold moved
     * @param receiver - the entity receiving the gold
     * @return true - on successful operation, else it'll return false
     * @throws IllegalArgumentException - on negative input
     * @throws NullPointerException     - if the receiver was null
     */
    public boolean moveGold(int amount, Entity receiver) {
        Util.requireNonNegative(amount);
        Objects.requireNonNull(receiver, "the receiver cannot be null");
        if (gold < amount) {
            Util.logWarn("this entity doesn't have enough gold for this transaction");
            return false;
        }

        gold -= amount;
        receiver.gold += amount;
        Util.log("the " + receiver.name + " gained " + amount + " gold");
        return true;
    }


    /**
     * Clamps all entities x and y velocity to 2000
     */
    public void velocityClamp() {
        movement.velocity.x = Math.clamp(movement.velocity.x, -2000, 2000);
        movement.velocity.y = Math.clamp(movement.velocity.y, -2000, 2000);
    }

    public abstract void update();

}
