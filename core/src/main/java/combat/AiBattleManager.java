package combat;

import entities.CombatEntity;

class AiBattleManager {
    void launchBattle(CombatEntity entity1, CombatEntity entity2) {
        while (entity1.health.getHp() > 0) {
            entity1.takeTurn(entity2);
            if (entity2.health.getHp() <= 0) break;
            entity2.takeTurn(entity1);
        }
    }
}
