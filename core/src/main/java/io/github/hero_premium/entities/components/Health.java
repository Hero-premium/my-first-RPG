package io.github.hero_premium.entities.components;

import io.github.hero_premium.util.Util;

/**
 * This class is typically used as a component in other classes to grant them health and its methods.
 */
public class Health {
    /**
     * An entity's max hp, defined once in the constructor. hp can never exceed this
     * value.
     */
    public final int maxHp;
    private int hp;

    /**
     * 1 means you do normal damage.
     * <P>
     * 0 means all damage in negated.
     * <P>
     * anything above 1 deals extra damage.
     * <P>
     * anything below 0 means weaker damage.
     **/
    private int damageModifier;

    /**
     * Creates a Health object.
     *
     * @param hp the amount of hp this object will have.
     * @param maxHp the max amount of hp this health can have
     * @param damageModifier reduces the amount of damage this health takes
     * @throws IllegalArgumentException if hp > maxHp
     * @throws IllegalArgumentException if hp was negative
     */
    public Health(int hp, int maxHp, int damageModifier) {
        if (hp > maxHp)
            throw new IllegalArgumentException("hp cannot be bigger than maxHp" + "hp: " + hp + "maxHp: " + maxHp);
        this.hp = Util.requireNonNegative(hp);
        this.maxHp = maxHp;
        this.damageModifier = damageModifier;
    }

    /**
     * Creates a Health object with reductionPercentage = 1 as a default.
     *
     * @param hp the amount of hp this object will have.
     * @param maxHp the max amount of hp this health can have
     * @throws IllegalArgumentException if hp > maxHp
     * @throws IllegalArgumentException if hp was negative
     */
    public Health(int hp, int maxHp) {
        this(hp, maxHp, 1);
    }

    /**
     * Creates a Health object,
     * <p>
     * reductionPercentage = 1 and hp = maxHp as a default.
     *
     * @param maxHp the max amount of hp this health can have.
     * @throws IllegalArgumentException if a negative was passed.
     */
    public Health(int maxHp) {
        this(maxHp, maxHp);
    }

    /**
     * Hp cannot go under 0 or above {@code maxHp}.
     *
     * @return entity's current hp.
     */
    public int getHp() {
        return hp;
    }

    /**
     * Sets hp to given amount also clamps hp between 0 and maxHp.
     *
     * @param hp the new hp.
     */
    public final void setHp(int hp) {
        this.hp = Math.clamp(hp, 0, maxHp);
    }

    /**
     * 1 means you do normal damage.
     * <P>
     * 0 means all damage in negated.
     * <P>
     * anything above 1 deals extra damage.
     * <P>
     * anything below 0 means weaker damage.
     * <P>
     * make sure you use {@link Health#modifyHp(int)} for the modifiers to take effect.
     * @return the current damage modifier.
     */
    public int getDamageModifier() {
        return damageModifier;
    }

    /**
     * 1 means you do normal damage.
     * <P>
     * 0 means all damage in negated.
     * <P>
     * anything above 1 deals extra damage.
     * <P>
     * anything below 0 means weaker damage.
     * make sure you use {@link Health#modifyHp(int)} for the modifiers to take effect.
     * @param damageModifier the new damageModifier.
     */
    public void setDamageModifier(int damageModifier) {
        this.damageModifier = Math.clamp(damageModifier, 1, 100);
    }

    /**
     * Adds the given amount of hp to the existing amount of hp also clamps hp
     * between 0 and maxHp.
     * <p>
     *     uses {@link Health#damageModifier}
     * @param hp how much you want to add - pass a negative number to deal damage.
     */
    public final void modifyHp(int hp) {
        if (Math.abs(damageModifier) == 1) {
            // do nothing so it just falls through
        } else if (damageModifier == 0) {
            return;
        } else if (damageModifier > 1) {
            hp = hp * damageModifier;
        } else {
            hp = hp / Math.abs(damageModifier);
        }
        setHp(getHp() + hp);
    }

    /**
     * Resets hp to its max.
     */
    public final void resetHp() {
        setHp(maxHp);
    }
}
