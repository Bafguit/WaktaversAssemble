package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.member.MemberDopamineAction;

public class Dopamine extends AbstractMember {

    public Dopamine() {
        super("Dopamine");
        setAtk(3, 1);
        setDef(3, 1);
    }

    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) {
            use();
        }
    }

    @Override
    protected void useMember() {
        next(new MemberDopamineAction(this));
    }
}
