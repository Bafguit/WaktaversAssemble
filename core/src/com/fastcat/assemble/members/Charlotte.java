package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.member.MemberCharlotteAction;

public class Charlotte extends AbstractMember {

    public Charlotte() {
        super("Charlotte");
        setDef(3, 2);
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) {
            use();
        }
    }

    @Override
    protected void useMember() {
        next(new MemberCharlotteAction(this));
    }
}
