package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.member.MemberJingburgerAction;

public class Jingburger extends AbstractMember {

    public Jingburger() {
        super("Jingburger");
        setDef(3, 1);
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) next(new MemberJingburgerAction(this));
    }
}
