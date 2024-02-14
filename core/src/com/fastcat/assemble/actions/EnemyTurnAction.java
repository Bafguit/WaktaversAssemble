package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractEnemy;
import com.fastcat.assemble.handlers.ActionHandler;

public class EnemyTurnAction extends AbstractAction {

    public EnemyTurnAction() {
        super(1f);
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            //적 턴

            for(AbstractEnemy e : WakTower.game.battle.enemies) {
                if(e.isAlive()) e.action();
            }

            ActionHandler.bot(new EndTurnAction(false));
        }
    }
}
