package io.github.hero_premium.entities.statuseffects.base;

import io.github.hero_premium.entities.CombatEntity;

public abstract class TogglableEffect implements Effect {

    private boolean isActive;

    /**
     * override this then call super.reset()
     */
    @Override
    public void reset() {
        setIsActive(false);
    }

    /**
     *  override this if needed then call super.onAfterTurn() and pass the same entity.
     * @param entity the entity being effected
     */
    @Override
    public void onAfterTurn(CombatEntity entity) {
        setIsActive(false);
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    public void setIsActive() {
        setIsActive(true);
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
