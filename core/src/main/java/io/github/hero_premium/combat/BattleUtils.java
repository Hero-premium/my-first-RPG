package io.github.hero_premium.combat;

import com.badlogic.gdx.utils.Logger;
import io.github.hero_premium.entities.CombatEntity;
import io.github.hero_premium.util.Util;

class BattleUtils {
    private static final Logger LOGGER = new Logger(BattleManager.class.getName(), Util.LOGGING_LEVEL);

    static CombatEntity other(CombatEntity entity, CombatEntity[] fighters) {
        return entity == fighters[0] ? fighters[1] : fighters[0];
    }

    static BattleState validateBattle(CombatEntity entity, CombatEntity[] fighters) {
        if (entity.health.getHp() <= 0) {
            LOGGER.info("the player lost");
            return BattleState.LOST;
        }
        if (other(entity, fighters).health.getHp() <= 0) {
            LOGGER.info("the player won");
            return BattleState.WON;
        }
        return BattleState.GOING;
    }

    enum BattleState {
        GOING, WON, LOST
    }
}
