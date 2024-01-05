package com.fastcat.assemble.actions;

import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;

public class MemberSkillAnimationAction extends AbstractAction {

    public AbstractMember member;

    public MemberSkillAnimationAction(AbstractMember m) {
        super(0);
        member = m;
    }

    public MemberSkillAnimationAction(AbstractMember m, float delay) {
        super(delay);
        member = m;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            member.animation.setAnimation("skill");
            member.animation.addAnimation("idle");
        }
    }
    
}
