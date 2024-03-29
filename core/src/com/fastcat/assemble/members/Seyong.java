package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.member.MemberSeyongAction;

public class Seyong extends AbstractMember {

    public Seyong() {
        super("Seyong");
        setAtk(5, 1);
        setValue(1, 0);
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
