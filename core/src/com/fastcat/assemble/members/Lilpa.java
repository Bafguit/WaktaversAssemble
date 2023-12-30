package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.member.MemberLilpaAction;

public class Lilpa extends AbstractMember {

    public Lilpa() {
        super("Lilpa");
        setAtk(4, 1);
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) next(new MemberLilpaAction(this));
    }
}
