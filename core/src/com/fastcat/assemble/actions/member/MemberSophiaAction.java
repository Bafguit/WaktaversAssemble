package com.fastcat.assemble.actions.member;

import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.actions.DrawAndDiscardAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.members.Sophia;

public class MemberSophiaAction extends AbstractAction {

    public Sophia sophia;

    public MemberSophiaAction(Sophia sophia) {
        super(0.5f);
        this.sophia = sophia;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            sophia.animation.setAnimation("skill");
            sophia.animation.addAnimation("idle");
            ActionHandler.next(new DrawAndDiscardAction(sophia.value));
        }
    }
}
