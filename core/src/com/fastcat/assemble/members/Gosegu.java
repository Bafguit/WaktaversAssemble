package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.DrawAndDiscardAction;
import com.fastcat.assemble.actions.PlayAnimationAction;

public class Gosegu extends AbstractMember {

    public Gosegu() {
        super("Gosegu");
        setValue(1, 1);
    }

    public void startOfTurn(boolean isPlayer) {
        if(isPlayer) {
            use();
        }
    }

    @Override
    protected void useMember() {
        next(new PlayAnimationAction(this));
        next(new DrawAndDiscardAction(this));
    }
}
