package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.ApplyStatusAction;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;
import com.fastcat.assemble.utils.TargetType;

public class Hodd extends AbstractMember {

    public Hodd() {
        super("Hodd");
        setValue(4, 2);
    }

    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) {
            use();
        }
    }

    @Override
    protected void useMember() {
        next(new MemberSkillAnimationAction(this));
        //next(new ApplyStatusAction(TargetType.ALL, null, false));
        //TODO Status 추가
    }
}
