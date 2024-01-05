package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.DamageAction;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.DamageInfo.DamageType;

public class Messi extends AbstractMember {

    public Messi() {
        super("Messi");
        setAtk(6, 3);
    }

    @Override
    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) {
            next(new MemberSkillAnimationAction(this));
            next(new DamageAction(new DamageInfo(this, DamageType.NORMAL)));
        }
    }
}
