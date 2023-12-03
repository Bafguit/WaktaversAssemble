package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;

public class DrawCardAction extends AbstractAction {

    public DrawCardAction(int amount) {
        super(0.5f);
        this.amount = amount;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            WakTower.game.battle.draw(amount);
        }
    }
}
