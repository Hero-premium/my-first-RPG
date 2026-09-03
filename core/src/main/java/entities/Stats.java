package entities;

// TODO present me believes this class needs a rework, future me gets to decide

public class Stats {
    private int poisonDuration;
    public boolean isDodging;
    public boolean isDefending;
    public boolean isFocused;

    Stats() {
        this.poisonDuration = 0;
        this.isDefending = false;
        this.isFocused = false;
        this.isDodging = false;
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
     * poisonDuration cannot go under 0 or above 10, to prevent game breaking bugs'
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
