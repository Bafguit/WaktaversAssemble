package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.member.MemberIneAction;

public class Ine extends AbstractMember {

    public Ine() {
        super("Ine");
        setDef(5, 3);
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) next(new MemberIneAction(this));
    }
}
