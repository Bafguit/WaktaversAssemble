package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.PlayAnimationAction;

public class AmadeusChoi extends AbstractMember {

    public AmadeusChoi() {
        super("AmadeusChoi");
        setValue(1, 1);
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) {
            use();
        }
    }

    @Override
    protected void useMember() {
        next(new PlayAnimationAction(this));
        //TODO HealAction
    }
}
