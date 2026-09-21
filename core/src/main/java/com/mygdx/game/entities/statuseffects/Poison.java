package com.mygdx.game.entities.statuseffects;

import com.mygdx.game.entities.CombatEntity;
import com.mygdx.game.util.Util;

public class Poison extends Effect {

    public Poison() {
    }

    @Override
    public void onTick(CombatEntity entity) {
        if (getPoisonDuration() <= 0) {
            value = 0;
            return;
        }
        entity.health.modifyHp(-value);
        modifyPoisonDuration(-1);
        Util.log(entity.name + "took " + value + " of poison damage");
    }

    /**
     * poisonDuration cannot go under 0 or above 9, to prevent game breaking bugs'
     *
     * @return entity current poisonDuration
     */
    public int getPoisonDuration() {
        return duration;
    }

    /**
     * sets this entity on poison for given duration and does {@code damage} on each tick
     *
     * @param poisonDuration the duration this entity will be on poison for
     * @param damage         the amount of damage this entity will receive each tick
     * @throws IllegalStateException if duration exceeds {@code 9} for balancing reasons
     */
    public void applyPoison(int poisonDuration, int damage) {
        setPoisonDuration(poisonDuration);
        setDamage(damage);
    }

    /**
     * sets the strength of the damage to given amount, if damage was lower than the already existing damage nothing happens
     * otherwise the damage will become the given amount
     *
     * @param damage the new damage
     */
    public void setDamage(int damage) {
        value = Math.max(value, damage);
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
        this.duration = Math.max(poisonDuration, 0);
    }

    /**
     * adds the given amount of poisonDuration to the existing amount
     *
     * @param poisonDuration how much you want to add - pass a negative number
     *                       remove from the duration
     */
    public void modifyPoisonDuration(int poisonDuration) {
        applyPoison(getPoisonDuration() + poisonDuration, value);
    }
}
