package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.member.MemberHikikingAction;

public class Hikiking extends AbstractMember {

    public Hikiking() {
        super("Hikiking");
        setAtk(2, 0);
        setValue(4, 1);
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) use();
    }

    @Override
    protected void useMember() {
        next(new MemberHikikingAction(this));
    }
}
