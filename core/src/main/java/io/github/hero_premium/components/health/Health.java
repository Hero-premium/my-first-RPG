package io.github.hero_premium.components.health;

import com.badlogic.gdx.utils.IntArray;
import io.github.hero_premium.util.Util;

import java.util.NoSuchElementException;
import java.util.Objects;

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
    private final IntArray damageModifiers = new IntArray();


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
     * Creates a Health object with reductionPercentage = 1 as a default.
     *
     * @param hp the amount of hp this object will have.
     * @param maxHp the max amount of hp this health can have
     * @throws IllegalArgumentException if hp > maxHp
     * @throws IllegalArgumentException if hp was negative
     */
    public Health(int hp, int maxHp) {
        this(hp, maxHp, new int[]{});
    }

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
        this(hp, maxHp, new int[]{damageModifier});
    }

    /**
     * Creates a Health object.
     *
     * @param hp the amount of hp this object will have.
     * @param maxHp the max amount of hp this health can have
     * @param damageModifiers reduces the amount of damage this health takes
     * @throws IllegalArgumentException if hp > maxHp
     * @throws IllegalArgumentException if hp was negative
     */
    public Health(int hp, int maxHp, int[] damageModifiers) {
        if (hp > maxHp)
            throw new IllegalArgumentException("hp cannot be bigger than maxHp" + "hp: " + hp + "maxHp: " + maxHp);
        this.hp = Util.requireNonNegative(hp);
        this.maxHp = maxHp;
        this.damageModifiers.addAll(Objects.requireNonNull(damageModifiers, "damageModifiers must not be null"));
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
     * make sure you use {@link Health#modifyHp(int)} for the modifiers to take effect.
     * @param damageModifier the new damageModifier.
     */
    public void addModifier(int damageModifier) {
        damageModifiers.add(damageModifier);
    }

    /**
     * Removes the given modifier, keep in mind that it doesn't remove every modifier who shared this number
     * @param damageModifier the modifier to be removed
     * @throws NoSuchElementException if the modifier is not there.
     */
    public void removeModifier(int damageModifier) {
        if (!damageModifiers.removeValue(damageModifier)) throw new NoSuchElementException("damageModifiers not found");
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
    public int calculateDamageModifier() {
        if (damageModifiers.size == 0) return 1;
        if (damageModifiers.contains(0)) return 0;
        int highestDamageModifier = 0;
        int lowestDamageModifier = 0;
        for (int i = 0; i < damageModifiers.size; i++) {
            int damageModifier = damageModifiers.get(i);

            if (damageModifier > 1 && damageModifier > highestDamageModifier) {
                highestDamageModifier = damageModifier;
            }

            if (damageModifier < 0 && damageModifier < lowestDamageModifier) {
                lowestDamageModifier = damageModifier;
            }
        }

        int damageModifierCalculated = highestDamageModifier + lowestDamageModifier;
        return damageModifierCalculated == 0 ? 1 : damageModifierCalculated;
    }


    /**
     * Adds the given amount of hp to the existing amount of hp also clamps hp
     * between 0 and maxHp.
     * <p>
     *     uses the highest of {@link Health#damageModifiers}
     * @param hp how much you want to add - pass a negative number to deal damage.
     */
    public final void modifyHp(int hp) {
        int highestDamageModifier = calculateDamageModifier();
        if (highestDamageModifier == 0) {
            return;
        } else if (highestDamageModifier > 1) {
            hp *= highestDamageModifier;
        } else {
            hp /= Math.abs(highestDamageModifier);
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
