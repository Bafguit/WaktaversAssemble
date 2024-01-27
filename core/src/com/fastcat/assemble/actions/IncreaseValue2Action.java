package com.fastcat.assemble.actions;

import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;

public class IncreaseValue2Action extends AbstractAction {
    
    private final AbstractMember member;

    public IncreaseValue2Action(AbstractMember m, int amount) {
        super(0f);
        member = m;
        this.amount = amount;
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            member.value2 += amount;
        }
    }
}
