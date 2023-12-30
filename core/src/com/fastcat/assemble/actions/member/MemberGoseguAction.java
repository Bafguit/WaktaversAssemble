package com.fastcat.assemble.actions.member;

import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.actions.DrawAndDiscardAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.members.Gosegu;

public class MemberGoseguAction extends AbstractAction {

    public Gosegu gosegu;

    public MemberGoseguAction(Gosegu gosegu) {
        super(0.5f);
        this.gosegu = gosegu;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            gosegu.animation.setAnimation("skill");
            gosegu.animation.addAnimation("idle");
            ActionHandler.next(new DrawAndDiscardAction(gosegu.value));
        }
    }
}
