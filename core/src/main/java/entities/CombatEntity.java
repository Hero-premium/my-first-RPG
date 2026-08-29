package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Util;

public abstract class CombatEntity extends Entity {

    /**
     * An entity's max hp, defined once in the constructor. hp can never exceed this
     * value, since {@link #setHp(int)} clamps against it.
     */
    public final int maxHp;
    public boolean isDodging;
    public boolean isDefending;
    public boolean isFocused;
    private int hp;
    private int poisonDuration;

    protected CombatEntity(int gold, String name, float speed, Rectangle hitbox, int hp, Texture texture) {
        super(gold, name, speed, hitbox, texture);

        this.hp = Util.requireNonNegative(hp);
        this.maxHp = hp;
        this.poisonDuration = 0;
        this.isDodging = false;
        this.isDefending = false;
        this.isFocused = false;
    }

    /**
     *
     * @return entity current hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * sets hp to given amount also clamps hp between 0 and maxHp
     *
     * @param hp the new hp
     */
    public final void setHp(int hp) {
        this.hp = Math.clamp(hp, 0, maxHp);
    }

    /**
     * adds the given amount of hp to the existing amount of hp also clamps hp
     * between 0 and maxHp
     *
     * @param hp how much you want to add - pass a negative number to deal damage
     */
    public final void modifyHp(int hp) {
        setHp(getHp() + hp);
    }

    /**
     * resets hp to its max
     */
    public final void resetHp() {
        setHp(maxHp);
    }

    /**
     * Sets isDodging, isDefending, isFocused to false and sets poisonDuration to 0.
     */
    public void resetBattleStates() {
        setPoisonDuration(0);
        isDodging = false;
        isDefending = false;
        isFocused = false;

    }

    /**
     * Performs this entity's turn against the specified entity.
     *
     * @param entity the entity being attacked
     */
    public abstract void takeTurn(CombatEntity entity);

    /**
     *
     * @return entity current poisonDuration
     */
    public int getPoisonDuration() {
        return poisonDuration;
    }

    /**
     * sets poisonDuration to given amount
     *
     * @param poisonDuration the new poisonDuration
     * @throws IllegalStateException if poisonDuration was >= 10 to prevent game
     *                               breaking bugs
     */
    public void setPoisonDuration(int poisonDuration) {
        if (poisonDuration >= 10)
            throw new IllegalStateException("balance breaking bug, poison been set for/more than 10 turns");
        this.poisonDuration = Math.max(poisonDuration, 0);
    }

    /**
     * adds the given amount of poisonDuration to the existing amount
     *
     * @param poisonDuration how much you want to add - pass a negative number
     *                       remove from the duration
     */
    public void modifyPoisonDuration(int poisonDuration) {
        setPoisonDuration(getPoisonDuration() + poisonDuration);
    }
}
