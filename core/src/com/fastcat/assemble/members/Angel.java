package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.GainBlockAction;
import com.fastcat.assemble.actions.PlayAnimationAction;
import com.fastcat.assemble.utils.TargetType;

public class Angel extends AbstractMember {

    public Angel() {
        super("Angel");
        setDef(5, 2);
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
        next(new GainBlockAction(TargetType.SELF, this));
    }
}
