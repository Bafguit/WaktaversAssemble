package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;

public class ChargeEnergyAction extends AbstractAction {

    public ChargeEnergyAction(boolean isNew) {
        super(0f);
        amount = isNew ? WakTower.game.energyStart : WakTower.game.energyCharge;
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            WakTower.game.battle.energy += amount;
        }
    }
}
