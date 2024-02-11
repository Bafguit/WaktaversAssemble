package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.member.MemberCallyCarlyAction;

public class CallyCarly extends AbstractMember {

    public CallyCarly() {
        super("CallyCarly");
        setAtk(6, 2);
    }

    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) {
            use();
        }
    }

    @Override
    protected void useMember() {
        next(new MemberCallyCarlyAction(this));
    }
}
