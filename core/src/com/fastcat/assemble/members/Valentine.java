package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.DamageAction;
import com.fastcat.assemble.actions.GainBarrierAction;
import com.fastcat.assemble.actions.PlayAnimationAction;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.TargetType;
import com.fastcat.assemble.utils.DamageInfo.DamageType;

public class Valentine extends AbstractMember {

    public Valentine() {
        super("Valentine");
        setAtk(5, 1);
        setDef(2, 1);
    }

    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) {
            use();
        }
    }

    @Override
    protected void useMember() {
        next(new PlayAnimationAction(this, 0.2f));
        next(new DamageAction(new DamageInfo(this, DamageType.NORMAL), TargetType.RANDOM_ENEMY, true));
        next(new GainBarrierAction(TargetType.SELF, this, true));
    }
}
