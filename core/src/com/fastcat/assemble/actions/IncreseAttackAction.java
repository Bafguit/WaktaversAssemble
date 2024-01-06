package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.abstracts.AbstractRelic;
import com.fastcat.assemble.abstracts.AbstractStatus;
import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.handlers.ActionHandler;

public class IncreseAttackAction extends AbstractAction {
    
    private final AbstractMember member;

    public IncreseAttackAction(AbstractMember m, int amount) {
        super(0f);
        member = m;
        this.amount = amount;
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            member.baseAtk += amount;
        }
    }
}
