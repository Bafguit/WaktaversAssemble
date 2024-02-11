package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.DamageAction;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.TargetType;
import com.fastcat.assemble.utils.DamageInfo.DamageType;

public class Pungsin extends AbstractMember {

    public Pungsin() {
        super("Pungsin");
        setAtk(3, 1);
        passive = true;
    }

    @Override
    public void onGainedBlock(int amount) {
        set(new DamageAction(new DamageInfo(tempClone, DamageType.NORMAL), TargetType.RANDOM_ENEMY));
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
