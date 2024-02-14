package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.DamageAction;
import com.fastcat.assemble.actions.GainGoldAction;
import com.fastcat.assemble.actions.PlayAnimationAction;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.TargetType;
import com.fastcat.assemble.utils.DamageInfo.DamageType;

public class Sullivan extends AbstractMember {

    public Sullivan() {
        super("Sullivan");
        setAtk(6, 1);
        setValue(3, 1);
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
        next(new DamageAction(new DamageInfo(this, DamageType.NORMAL), TargetType.RANDOM, true));
        next(new GainGoldAction(tempClone.calculateValue()));
    }
}
