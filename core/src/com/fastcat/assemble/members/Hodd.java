package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.DamageAction;
import com.fastcat.assemble.actions.GainBlockAction;
import com.fastcat.assemble.actions.PlayAnimationAction;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.TargetType;
import com.fastcat.assemble.utils.DamageInfo.DamageType;

public class Hodd extends AbstractMember {

    public Hodd() {
        super("Hodd");
        setAtk(5, 1);
        setDef(5, 1);
    }

    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) {
            use();
        }
    }

    @Override
    protected void useMember() {
        next(new PlayAnimationAction(this, 0.15f));
        next(new DamageAction(new DamageInfo(this, DamageType.NORMAL), TargetType.RANDOM_ENEMY, true));
        next(new GainBlockAction(TargetType.SELF, this, true));
    }
}
