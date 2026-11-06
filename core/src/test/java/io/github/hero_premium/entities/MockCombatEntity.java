package io.github.hero_premium.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import io.github.hero_premium.entities.combat.CombatEntity;
import io.github.hero_premium.entities.combat.moves.MoveAttack;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.mockito.Mockito;

public  class MockCombatEntity extends CombatEntity {
    public MockCombatEntity() {
        super(10, "police man", 12, new Rectangle(), 100, Mockito.mock(Texture.class));
    }

    @Contract("_ -> new")
    @Override
    public @NonNull MoveAttack takeTurn(CombatEntity entity) {
        return new MoveAttack(10, "super cat go");
    }

    @Contract(" -> new")
    public @NonNull MoveAttack sleep() {
        return new MoveAttack(0, "i am too lazy to fight you, please surrender");
    }

    @Override
    protected void registerMoves() {
        movesManager.addNewMove("sleep", (CombatEntity _) -> sleep());
    }

    @Override
    public void update() {

    }
}
