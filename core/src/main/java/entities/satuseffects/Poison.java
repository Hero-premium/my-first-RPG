package entities.satuseffects;

public class Poison extends Effect {

    public Poison() {
    }

    @Override
    public void onTick(){
        entity.health.modifyHp(-10);
        value--;
    }
    /**
     * poisonDuration cannot go under 0 or above 10, to prevent game breaking bugs'
     *
     * @return entity current poisonDuration
     */
    public int getPoisonDuration() {
        return value;
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
        this.value = Math.max(poisonDuration, 0);
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
