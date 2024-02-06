package com.fastcat.assemble.actions.member;

import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.actions.DamageAction;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.members.Hikiking;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.TargetType;
import com.fastcat.assemble.utils.DamageInfo.DamageType;

public class MemberHikikingAction extends AbstractAction {

    public Hikiking member;

    private int count = 0, index = 0;

    public MemberHikikingAction(Hikiking member) {
        super(0f);
        this.member = member;
        count = member.tempClone.calculateValue();
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            for(int i = 0; i < count; i++) {
                ActionHandler.set(new MemberSkillAnimationAction(member, "skill_" + index % 2));
                ActionHandler.set(new DamageAction(new DamageInfo(member.tempClone, DamageType.NORMAL), TargetType.RANDOM, true));
                index++;
            }
        }
    }
}
