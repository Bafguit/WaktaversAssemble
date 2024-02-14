package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.DamageAction;
import com.fastcat.assemble.actions.PlayAnimationAction;
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
        use();
    }

    @Override
    protected void useMember() {
        set(new PlayAnimationAction(this));
        set(new DamageAction(new DamageInfo(this, DamageType.NORMAL), TargetType.RANDOM_ENEMY, true));
    }
}
