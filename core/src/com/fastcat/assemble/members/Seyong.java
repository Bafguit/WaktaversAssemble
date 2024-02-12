package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.member.MemberSeyongAction;

public class Seyong extends AbstractMember {

    public Seyong() {
        super("Seyong");
        setAtk(3, 1);
    }

    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) {
            use();
        }
    }

    @Override
    protected void useMember() {
        next(new MemberSeyongAction(this));
    }
}
