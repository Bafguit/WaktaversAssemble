package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractBattle.BattlePhase;
import com.fastcat.assemble.handlers.ActionHandler;

public class EndBattleAction extends AbstractAction {

    public EndBattleAction() {
        super(0f);
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration && WakTower.game.battle.phase != BattlePhase.BATTLE_END) {
            WakTower.game.battle.phase = BattlePhase.BATTLE_END;
            ActionHandler.bot(new ChangePhaseAction(BattlePhase.REWARD));
        }
    }
}
