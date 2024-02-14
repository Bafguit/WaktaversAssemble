package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.HealAction;
import com.fastcat.assemble.actions.PlayAnimationAction;
import com.fastcat.assemble.utils.TargetType;

public class Kreaze extends AbstractMember {

    public Kreaze() {
        super("Kreaze");
        setValue(3, 1);
        instant = true;
    }

    @Override
    protected void onSummoned() {
        use();
    }

    @Override
    protected void useMember() {
        next(new PlayAnimationAction(this));
        next(new HealAction(TargetType.SELF, this));
    }
}
