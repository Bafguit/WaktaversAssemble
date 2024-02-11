package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.member.MemberYungterAction;

public class Yungter extends AbstractMember {

    public Yungter() {
        super("Yungter");
        setValue(30, 5);
    }


    @Override
    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) use();
    }

    @Override
    protected void useMember() {
        next(new MemberYungterAction(this));
    }
}
