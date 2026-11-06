package io.github.hero_premium.entities.statuseffects.effects;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import io.github.hero_premium.entities.MockCombatEntity;
import io.github.hero_premium.entities.combat.CombatEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PoisonTest {

    @BeforeAll
    static void setUp() {
        Gdx.app = Mockito.mock(Application.class);
    }

    @Test
    void basicUsage() {
        CombatEntity combatEntity = new MockCombatEntity();
        combatEntity.statusEffectsManager.allEffects.get(Poison.class).applyPoison(5, 10);
        for (int i = 0; i < 5; i++) {
            combatEntity.statusEffectsManager.onTurn();
            combatEntity.statusEffectsManager.onAfterTurn();
        }
        assertEquals(50, combatEntity.health.getHp());
    }

    @Test
    void reset() {
        CombatEntity combatEntity = new MockCombatEntity();
        combatEntity.statusEffectsManager.allEffects.get(Poison.class).applyPoison(5, 10);
        combatEntity.statusEffectsManager.resetBattleStates();
        assertFalse(combatEntity.statusEffectsManager.allEffects.get(Poison.class).isActive());
        assertEquals(0, combatEntity.statusEffectsManager.allEffects.get(Poison.class).getPoisonDuration());
    }

    @Test
    void applyWeakAfterStrongDamage() {
        CombatEntity combatEntity = new MockCombatEntity();
        Poison poison = combatEntity.statusEffectsManager.allEffects.get(Poison.class);
        poison.applyPoison(5, 5);
        poison.applyPoison(5, 2);
        poison.onTurn(combatEntity);
        assertEquals(95, combatEntity.health.getHp());
    }

    @Test
    void applyStrongAfterWeakDamage() {
        CombatEntity combatEntity = new MockCombatEntity();
        Poison poison = combatEntity.statusEffectsManager.allEffects.get(Poison.class);
        poison.applyPoison(5, 2);
        poison.applyPoison(5, 5);
        poison.onTurn(combatEntity);
        assertEquals(95, combatEntity.health.getHp());
    }

    @Test
    void isActive() {
    }

    @Test
    void getPoisonDuration() {
    }

    @Test
    void applyPoison() {
    }

    @Test
    void setDamage() {
    }

    @Test
    void setPoisonDuration() {
    }

    @Test
    void modifyPoisonDuration() {
    }


}
