package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.function.Consumer;

public abstract class CombatEntity extends Entity {
    private int poisonDuration;
    public boolean isDodging;
    public boolean isDefending;
    public boolean isFocused;

    /**
     *
     */
    public final CombatMovesManager movesManager;
    public boolean isGUIBased;
    public final Health health;

    protected CombatEntity(int gold, String name, float speed, Rectangle hitbox, int hp, Texture texture) {
        super(gold, name, speed, hitbox, texture);

        this.health = new Health(hp);
        this.movesManager = new CombatMovesManager();

        this.poisonDuration = 0;
        this.isDodging = false;
        this.isDefending = false;
        this.isFocused = false;
        this.isGUIBased = false;
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

    /**
     * Call {@link CombatMovesManager#addNewMove(String, Consumer)} here to ensure they get added to the moves list.
     */
    protected abstract void registerMoves();

    public class CombatMovesManager {
        private boolean movesRegistered = false;

        public record Move(String name, Consumer<CombatEntity> move) {
        }

        private final Array<Move> moves;

        private CombatMovesManager() {
            moves = new Array<>();
        }

        /**
         *
         * @return a copy of the current moves list
         */
        public Array<Move> getMoves() {
            if (!movesRegistered) {
                registerMoves();
                movesRegistered = true;
            }
            return new Array<>(moves);
        }

        /**
         * Adds a new move to the moves list, make sure to call this inside {@link #registerMoves()} to avoid any issues
         *
         * @param name the name of the move
         * @param move the move itself
         */
        protected void addNewMove(String name, Consumer<CombatEntity> move) {
            moves.add((new Move(name, move)));
        }
    }
}

