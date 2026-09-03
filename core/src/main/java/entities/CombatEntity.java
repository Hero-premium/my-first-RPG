package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.function.Consumer;

public abstract class CombatEntity extends Entity {

    /**
     * The object responsible for
     */
    public final Stats stats;
    /**
     * The object responsible for registering and storing combat moves
     */
    public final CombatMovesManager movesManager;
    /**
     *
     */
    public boolean isGUIBased;
    /**
     * carries hp and all its related methods
     */
    public final Health health;

    protected CombatEntity(int gold, String name, float speed, Rectangle hitbox, int hp, Texture texture) {
        super(gold, name, speed, hitbox, texture);

        this.health = new Health(hp);
        this.movesManager = new CombatMovesManager();
        this.stats = new Stats();
        this.isGUIBased = false;
    }

    /**
     * Performs this entity's turn against the specified entity.
     *
     * @param entity the entity being attacked
     */
    public abstract void takeTurn(CombatEntity entity);


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

