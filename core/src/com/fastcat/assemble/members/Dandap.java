package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.DiscardAllAction;
import com.fastcat.assemble.actions.DrawCardAction;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;

public class Dandap extends AbstractMember {

    public Dandap() {
        super("Dandap");
        setValue(3, 1);
    }

    @Override
    protected void onSummoned() {
        use();
    }

    @Override
    protected void useMember() {
        next(new MemberSkillAnimationAction(this));
        next(new DiscardAllAction());
        next(new DrawCardAction(this));
    }
}
