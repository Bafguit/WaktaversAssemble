package com.fastcat.assemble.actions;

import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;

public class MemberSkillAnimationAction extends AbstractAction {

    public AbstractMember member;
    public String key;

    public MemberSkillAnimationAction(AbstractMember m) {
        this(m, "skill", 0);
    }

    public MemberSkillAnimationAction(AbstractMember m, float delay) {
        this(m, "skill", delay);
    }

    public MemberSkillAnimationAction(AbstractMember m, String key) {
        this(m, key, 0);
    }

    public MemberSkillAnimationAction(AbstractMember m, String key, float delay) {
        super(delay);
        this.key = key;
        member = m;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            member.animation.setAnimation(key);
            member.animation.addAnimation("idle");
        }
    }
    
}
