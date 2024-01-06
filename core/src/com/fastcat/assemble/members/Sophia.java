package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.DrawAndDiscardAction;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;

public class Sophia extends AbstractMember {

    public Sophia() {
        super("Sophia");
        setValue(2, 1);
    }

    @Override
    protected void onSummoned() {
        use();
    }

    @Override
    protected void useMember() {
        next(new MemberSkillAnimationAction(this));
        next(new DrawAndDiscardAction(value));
    }
}
