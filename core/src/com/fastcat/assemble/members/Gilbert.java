package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.ApplyStatusAction;
import com.fastcat.assemble.actions.DamageAction;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;
import com.fastcat.assemble.status.DecreaseBlockStatus;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.TargetType;
import com.fastcat.assemble.utils.DamageInfo.DamageType;

public class Gilbert extends AbstractMember {

    public Gilbert() {
        super("Gilbert");
        setValue(3, 1);
    }

    @Override
    public void startOfTurn(boolean isPlayer) {
        if(isPlayer) {
            use();
        }
    }

    @Override
    protected void useMember() {
        next(new MemberSkillAnimationAction(this));
        next(new DamageAction(new DamageInfo(tempClone.calculateValue(), DamageType.LOSE), TargetType.ALL_ENEMY, false));
    }
}
