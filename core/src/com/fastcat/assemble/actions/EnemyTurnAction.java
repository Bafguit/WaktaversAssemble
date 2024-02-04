package com.fastcat.assemble.actions;

import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.handlers.ActionHandler;

public class EnemyTurnAction extends AbstractAction {

    public EnemyTurnAction() {
        super(1f);
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            //적 턴

            ActionHandler.bot(new EndTurnAction(false));
        }
    }
}
