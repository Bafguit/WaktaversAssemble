package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractBattle.BattlePhase;

public class ChangePhaseAction extends AbstractAction {

    private final BattlePhase phase;

    public ChangePhaseAction(BattlePhase phase) {
        super(0f);
        this.phase = phase;
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            WakTower.game.battle.phase = phase;
        }
    }
}
