package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.PlayAnimationAction;
import com.fastcat.assemble.utils.DamageInfo;

public class Jentoo extends AbstractMember {

    public Jentoo() {
        super("Jentoo");
        setValue(2, 1);
        passive = true;
    }

    @Override
    public int damageTake(DamageInfo info) {
        return info.damage - tempClone.calculateValue();
    }

    @Override
    protected void onSummoned() {
        use();
    }

    @Override
    protected void useMember() {
        next(new PlayAnimationAction(this, 0.3f));
    }
}
