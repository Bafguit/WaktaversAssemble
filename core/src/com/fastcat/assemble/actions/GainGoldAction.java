package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;

public class GainGoldAction extends AbstractAction {

    public GainGoldAction(int gold) {
        super(0.15f);
        amount = gold;
    }

    public GainGoldAction(int gold, float duration) {
        super(duration);
        amount = gold;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            //todo 이펙트
            WakTower.game.gainGold(amount);
        }
    }
}
