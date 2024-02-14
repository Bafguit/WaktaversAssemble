package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.DamageAction;
import com.fastcat.assemble.actions.PlayAnimationAction;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.TargetType;
import com.fastcat.assemble.utils.DamageInfo.DamageType;

public class Dokohyeji extends AbstractMember {

    public Dokohyeji() {
        super("Dokohyeji");
        setValue(5, 2);
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
        next(new DamageAction(new DamageInfo(tempClone.calculateValue(), DamageType.LOSE), TargetType.RANDOM_ENEMY, false));
    }
}
