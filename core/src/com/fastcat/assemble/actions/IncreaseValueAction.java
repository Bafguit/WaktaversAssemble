package com.fastcat.assemble.actions;

import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;

public class IncreaseValueAction extends AbstractAction {
    
    private final AbstractMember member;

    public IncreaseValueAction(AbstractMember m, int amount) {
        super(0f);
        member = m;
        this.amount = amount;
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            member.baseValue += amount;
            if(member.tempClone != null) member.tempClone.baseValue += amount;
        }
    }
}
