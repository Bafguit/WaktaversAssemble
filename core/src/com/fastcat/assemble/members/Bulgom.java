package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.GainBlockAction;
import com.fastcat.assemble.actions.PlayAnimationAction;
import com.fastcat.assemble.utils.TargetType;

public class Bulgom extends AbstractMember {

    public Bulgom() {
        super("Bulgom");
        setDef(3, 1);
        setValue(2, 0);
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) {
            use();
        }
    }

    @Override
    protected void useMember() {
        next(new PlayAnimationAction(this, 0.2f));
        for(int i = 0; i < calculateValue(); i++) {
            next(new GainBlockAction(TargetType.SELF, this, true));
        }
    }
}
