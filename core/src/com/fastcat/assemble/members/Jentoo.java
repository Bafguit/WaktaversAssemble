package com.fastcat.assemble.members;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.GainBlockAction;
import com.fastcat.assemble.actions.IncreaseDefAction;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.TargetType;

public class Jentoo extends AbstractMember {

    public Jentoo() {
        super("Jentoo");
        setValue(2, 1);
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
        next(new MemberSkillAnimationAction(this, 0.3f));
    }
}
