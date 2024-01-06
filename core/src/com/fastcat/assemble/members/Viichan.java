package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.DamageAction;
import com.fastcat.assemble.actions.IncreseAttackAction;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.DamageInfo.DamageType;

public class Viichan extends AbstractMember {

    public Viichan() {
        super("Viichan");
        setAtk(8, 0);
        setValue(2, 1);
    }


    @Override
    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) {
            use();
        }
    }

    @Override
    protected void useMember() {
        next(new MemberSkillAnimationAction(this));
        next(new DamageAction(new DamageInfo(this, DamageType.NORMAL)));
        next(new IncreseAttackAction(this, value));
    }
}
