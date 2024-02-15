package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.GainBarrierAction;
import com.fastcat.assemble.actions.PlayAnimationAction;
import com.fastcat.assemble.utils.TargetType;

public class Kimchimandu extends AbstractMember {

    public Kimchimandu() {
        super("Kimchimandu");
        setDef(4, 1);
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) use();
    }

    @Override
    protected void useMember() {
        next(new PlayAnimationAction(this));
        next(new GainBarrierAction(TargetType.SELF, this));
    }
}
