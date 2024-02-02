package com.fastcat.assemble.actions;

import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;

public class IncreaseAtkAction extends AbstractAction {
    
    private final AbstractMember member;

    public IncreaseAtkAction(AbstractMember m, int amount) {
        super(0f);
        member = m;
        this.amount = amount;
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            member.baseAtk += amount;
            if(member.tempClone != null) member.tempClone.baseAtk += amount;
        }
    }
}
