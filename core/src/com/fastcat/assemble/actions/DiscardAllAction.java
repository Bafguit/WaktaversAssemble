package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;

public class DiscardAllAction extends AbstractAction {

    public DiscardAllAction() {
        super(0.3f);
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            WakTower.game.battle.getStage().discardAll();
        }
    }
}
