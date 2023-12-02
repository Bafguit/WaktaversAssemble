package com.fastcat.assemble.actions;

import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractChar;

public class StartBattleAction extends AbstractAction {

    public StartBattleAction() {
        this(0f);
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            ActionHandler.next(new BattleStartEffectAction());
            
            for(AbstractRelic relic : WakTower.game.relics) {
                relic.startOfBattle();
            }
            
            ActionHandler.bot(new StartTurnAction(true, true));
        }
    }
}
