package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.member.MemberSophiaAction;

public class Sophia extends AbstractMember {

    public Sophia() {
        super("Sophia");
        setValue(2, 1);
    }

    @Override
    public void onSummon() {
        next(new MemberSophiaAction(this));
    }
}
