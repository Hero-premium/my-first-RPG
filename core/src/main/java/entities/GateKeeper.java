package entities;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Assets;

import combat.CombatLogic;
import util.Util;

public class GateKeeper extends CombatEntity implements CombatAnimation {

    public GateKeeper() {
        super(20, "GateKeeper", 12f, new Rectangle(200f, 200f, 64f, 64f), 250, Assets.gateKeeper);
    }

    private void fireWand(CombatEntity player) {
        Util.log(name + " used fireWand");
        CombatLogic.calculateDamage(player, this, 2);
        player.setPoisonDuration(3);
    }

    private void focus() {
        Util.log(name + " used focus");
        isFocused = true;
        Util.log(name + " is focusing on his attack... you may attack.");
    }

    private void shield(CombatEntity player) {
        Util.log(name + " used Shield");
        CombatLogic.calculateDamage(player, this, 2);
        isDefending = true;
    }

    // TODO add a smarter AI -# psttttt make it self aware
    @Override
    public void takeTurn(CombatEntity player) {
        if (getHp() <= 0) {
            Util.log("the gateKeeper tried attacking from the grave");
            return;
        }

        int choice = !isFocused ? Util.RANDOM.nextInt(3) : Util.RANDOM.nextInt(2) + 1;

        switch (choice) {
            case 0 -> focus();
            case 1 -> fireWand(player);
            case 2 -> shield(player);
            default ->
                throw new AssertionError("the switch in entities.GateKeeper takeTurn ran into an unexpected case");
        }
    }

    @Override
    public void update() {
        // no-op, yet
    }

    @Override
    public void firstMove() {
        // TODO Auto-generated method stub

    }

    @Override
    public void secondMove() {
        // TODO Auto-generated method stub

    }

    @Override
    public void thirdMove() {
        // TODO Auto-generated method stub

    }

}
