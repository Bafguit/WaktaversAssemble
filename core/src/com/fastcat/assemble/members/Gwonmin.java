package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;

public class Gwonmin extends AbstractMember {

    public Gwonmin() {
        super("Gwonmin");
        setValue(3, 1);
        passive = true;
    }

    @Override
    public int onGainBlock(AbstractEntity target, int amount) {
        if(!target.isPlayer) return amount - tempClone.calculateValue();
        return amount;
    }

    @Override
    protected void onSummoned() {
        use();
    }

    @Override
    protected void useMember() {
        next(new MemberSkillAnimationAction(this, 0.3f));
    }
}
