package io.github.hero_premium.entities.combat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import io.github.hero_premium.components.Health;
import io.github.hero_premium.entities.Entity;
import io.github.hero_premium.entities.combat.moves.Move;
import io.github.hero_premium.entities.combat.moves.MoveAttack;
import io.github.hero_premium.entities.statuseffects.StatusEffectsManager;
import io.github.hero_premium.util.Attributes;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Extend this class when you want this entity to participate in combat
 */
public abstract class CombatEntity extends Entity {

    /**
     * The object responsible for registering and storing combat moves
     */
    protected final CombatMovesManager movesManager;
    /**
     * The object responsible for holding combat stats
     */
    public final StatusEffectsManager statusEffectsManager;
    /**
     * carries hp and all its related methods
     */
    public final Health health;
    /**
     *
     */
    public final Attributes damageAttributes;
    /**
     * Defines whether this entity currently fights via gui or AI based
     *
     * @see io.github.hero_premium.combat.BattleManager
     */
    public boolean isPlayable;

    /**
     *
     * @param gold    the gold this entity will have, cannot be negative
     * @param name    the in-game name this entity will be called by
     * @param speed   the speed this entity will move by in the world
     * @param hitbox  the physical being of this entity other objects will use to interact with
     * @param hp      the amount of health this entity has, cannot be negative
     * @param texture the texture being drawn on the screen, be sure to run {@link Entity#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch)} for it to display
     * @throws IllegalArgumentException if the hp passed was negative
     * @throws IllegalArgumentException if the gold passed was negative
     * @throws IllegalArgumentException if the speed passed was negative
     * @throws NullPointerException     if the hitBox passed was null
     * @throws NullPointerException     if the texture passed was null
     * @throws NullPointerException     if the name passed was null
     */
    protected CombatEntity(int gold, String name, float speed, Rectangle hitbox, int hp, Texture texture) {
        super(gold, name, speed, hitbox, texture);

        this.health = new Health(hp);
        this.statusEffectsManager = new StatusEffectsManager(this);
        this.movesManager = new CombatMovesManager();
        this.isPlayable = false;
        this.damageAttributes = new Attributes();
    }

    /**
     * Performs this entity's turn against the specified entity.
     *
     * @param entity the entity being attacked
     * @return the amount of damage done by this move
     */
    public abstract MoveAttack takeTurn(CombatEntity entity);

    /**
     * Call {@link CombatMovesManager#addNewMove(String, Function)} here to ensure they get added to the moves list.
     */
    protected abstract void registerMoves();

    /**
     *
     * @return a copy of the current moves list
     * @throws IllegalStateException if {@link registerMoves()} run but registers zero moves
     */
    public Array<Move> getMoves() {
        return movesManager.getMoves();
    }

    /**
     * responsible for loading moves via {@link CombatEntity#registerMoves()} and storing and exposing them.
     */
    public class CombatMovesManager {
        private boolean movesRegistered = false;
        private final Array<Move> moves;

        private CombatMovesManager() {
            moves = new Array<>();
        }

        /**
         *
         * @return a copy of the current moves list
         * @throws IllegalStateException if {@link registerMoves()} run but registers zero moves
         */

        @Contract(" -> new")
        private @NotNull Array<Move> getMoves() {
            if (!movesRegistered) {
                registerMoves();
                movesRegistered = true;
                if (moves.isEmpty()) {
                    throw new IllegalStateException("registerMoves() ran but registered zero moves");
                }
            }
            return new Array<>(moves);
        }

        /**
         * Adds a new move to the moves list, make sure to call this inside {@link #registerMoves()} to avoid any issues
         *
         * @param name the name of the move
         * @param move the move itself
         */
        public void addNewMove(String name, Function<CombatEntity, MoveAttack> move) {
            moves.add((new Move(name, move)));
        }
    }
}

