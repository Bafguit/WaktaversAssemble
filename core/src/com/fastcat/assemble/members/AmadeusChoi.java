package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;

public class AmadeusChoi extends AbstractMember {

    public AmadeusChoi() {
        super("AmadeusChoi");
        setDef(1, 1);
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) {
            use();
        }
    }

    @Override
    protected void useMember() {
        next(new MemberSkillAnimationAction(this));
        //TODO HealAction
    }
}
