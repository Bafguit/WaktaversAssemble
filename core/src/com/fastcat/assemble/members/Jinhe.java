package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.GainBlockAction;
import com.fastcat.assemble.actions.IncreaseDefAction;
import com.fastcat.assemble.actions.PlayAnimationAction;
import com.fastcat.assemble.utils.TargetType;

public class Jinhe extends AbstractMember {

    public Jinhe() {
        super("Jinhe");
        setDef(5, 0);
        setValue(2, 1);
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
        next(new IncreaseDefAction(this, tempClone.calculateValue()));
    }
}
