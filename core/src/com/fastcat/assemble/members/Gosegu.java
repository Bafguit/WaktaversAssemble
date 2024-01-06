package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.DrawAndDiscardAction;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;

public class Gosegu extends AbstractMember {

    public Gosegu() {
        super("Gosegu");
        setValue(2, 1);
    }

    public void startOfTurn(boolean isPlayer) {
        if(isPlayer) {
            use();
        }
    }

    @Override
    protected void useMember() {
        next(new MemberSkillAnimationAction(this));
        next(new DrawAndDiscardAction(value));
    }
}
