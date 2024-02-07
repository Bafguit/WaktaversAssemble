package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.DamageAction;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.TargetType;
import com.fastcat.assemble.utils.DamageInfo.DamageType;

public class Wakgood extends AbstractMember {

    public Wakgood() {
        super("Wakgood");
        setAtk(6, 2);
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
        next(new DamageAction(new DamageInfo(this.tempClone, DamageType.NORMAL), TargetType.RANDOM));
    }
}