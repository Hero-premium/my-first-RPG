package combat;

import entities.CombatEntity;
import util.Util;

class BattleUtils {
    static CombatEntity other(CombatEntity entity, CombatEntity[] fighters) {
        return entity == fighters[0] ? fighters[1] : fighters[0] ;
    }
    static BattleState validateBattle(CombatEntity entity,CombatEntity[] fighters) {
        if (entity.health.getHp() <= 0) {
            Util.log("the player lost");
            return BattleState.LOST;
        }
        if (other(entity, fighters).health.getHp() <= 0) {
            Util.log("the player won");
            return BattleState.WON;
        }
        return BattleState.GOING;
    }

    enum BattleState {
        GOING, WON, LOST
    }
}
