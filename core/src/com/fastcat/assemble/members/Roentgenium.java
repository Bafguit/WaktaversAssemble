package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.GainBlockAction;
import com.fastcat.assemble.actions.PlayAnimationAction;
import com.fastcat.assemble.actions.member.MemberRoentgeniumAction;
import com.fastcat.assemble.utils.TargetType;

public class Roentgenium extends AbstractMember {

    public Roentgenium() {
        super("Roentgenium");
        setDef(5, 1);
        setValue(2, 1);
    }

    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) {
            use();
        }
    }

    @Override
    protected void useMember() {
        next(new PlayAnimationAction(this));
        next(new GainBlockAction(TargetType.SELF, this, true));
        next(new MemberRoentgeniumAction(this));
    }
}
