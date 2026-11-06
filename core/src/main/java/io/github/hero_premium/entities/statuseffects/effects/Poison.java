package io.github.hero_premium.entities.statuseffects.effects;

import com.badlogic.gdx.utils.Logger;
import io.github.hero_premium.entities.combat.CombatEntity;
import io.github.hero_premium.entities.statuseffects.base.Effect;
import io.github.hero_premium.util.Util;
import org.jetbrains.annotations.NotNull;

public class Poison implements Effect {

    private static final Logger LOGGER = new Logger(Poison.class.getName(), Util.LOGGING_LEVEL);

    private int duration;
    private int damage;

    @Override
    public void onTurn(@NotNull CombatEntity entity) {
        entity.health.modifyHp(-damage);
        LOGGER.info(entity.getName() + " took " + damage + " of poison damage");
    }

    @Override
    public void reset() {
        duration = 0;
        damage = 0;
    }

    @Override
    public void onAfterTurn(CombatEntity entity) {
        modifyPoisonDuration(-1);
        if (!isActive()) damage = 0;
    }


    @Override
    public boolean isActive() {
        return getPoisonDuration() > 0;
    }

    /**
     * poisonDuration cannot go under 0.
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
        this.damage = Math.max(this.damage, damage);
    }

    /**
     * sets poisonDuration to given amount
     *
     * @param poisonDuration the new poisonDuration
     */
    public void setPoisonDuration(int poisonDuration) {
        this.duration = Math.max(poisonDuration, 0);
    }

    /**
     * adds the given amount of poisonDuration to the existing amount
     *
     * @param poisonDuration how much you want to add - pass a negative number
     *                       remove from the duration
     */
    public void modifyPoisonDuration(int poisonDuration) {
        applyPoison(getPoisonDuration() + poisonDuration, damage);
    }
}
