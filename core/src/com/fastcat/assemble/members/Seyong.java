package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.ApplyStatusAction;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;
import com.fastcat.assemble.utils.TargetType;

public class Seyong extends AbstractMember {

    public Seyong() {
        super("Seyong");
        setValue(2, 1);
    }

    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) {
            use();
        }
    }

    @Override
    protected void useMember() {
        next(new MemberSkillAnimationAction(this.tempClone));
        //next(new ApplyStatusAction(TargetType.RANDOM, null, false));
        //TODO Status 추가
    }
}
