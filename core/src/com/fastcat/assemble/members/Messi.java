package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;

public class Messi extends AbstractMember {

    public Messi() {
        super("Messi");
        setDef(6, 3);
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) {
            //todo damage Action
        }
    }
}
