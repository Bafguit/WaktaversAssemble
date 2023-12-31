package com.fastcat.assemble.actions;

import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;

public class MemberSkillAnimationAction extends AbstractAction {

    public AbstractMember member;

    public MemberSkillAnimationAction(AbstractMember m) {
        super(0);
        member = m;
    }

    @Override
    protected void updateAction() {
        if(isDone) {
            member.animation.setAnimation("skill");
            member.animation.addAnimation("idle");
        }
    }
    
}
