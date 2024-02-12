package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.member.MemberSoosemiAction;

public class Soosemi extends AbstractMember {

    public Soosemi() {
        super("Soosemi");
        setAtk(3, 0);
        setValue(2, 1);
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) use();
    }

    @Override
    protected void useMember() {
        next(new MemberSoosemiAction(this));
    }
}
