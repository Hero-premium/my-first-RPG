package entities;

import util.Util;

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
     * Creates a Health object, hp = maxHp at creation.
     *
     * @param hp the amount of hp this object will have.
     */
    public Health(int hp) {
        this.hp = Util.requireNonNegative(hp);
        this.maxHp = hp;
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
     * Adds the given amount of hp to the existing amount of hp also clamps hp
     * between 0 and maxHp.
     *
     * @param hp how much you want to add - pass a negative number to deal damage.
     */
    public final void modifyHp(int hp) {
        setHp(getHp() + hp);
    }

    /**
     * Resets hp to its max.
     */
    public final void resetHp() {
        setHp(maxHp);
    }
}
