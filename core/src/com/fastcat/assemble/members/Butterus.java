package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.GainGoldAction;
import com.fastcat.assemble.actions.PlayAnimationAction;

public class Butterus extends AbstractMember {

    public Butterus() {
        super("Butterus");
        setValue(6, 1);
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
        next(new GainGoldAction(tempClone.calculateValue(), 0.3f));
    }
}
