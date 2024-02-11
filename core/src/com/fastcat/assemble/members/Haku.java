package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.ApplyStatusAction;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;
import com.fastcat.assemble.status.DecreaseDamageStatus;
import com.fastcat.assemble.utils.TargetType;

public class Haku extends AbstractMember {

    public Haku() {
        super("Haku");
        setValue(1, 1);
    }

    public void endOfTurn(boolean isPlayer) {
        if(isPlayer) {
            use();
        }
    }

    @Override
    protected void useMember() {
        next(new MemberSkillAnimationAction(this));
        next(new ApplyStatusAction(TargetType.ALL_ENEMY, new DecreaseDamageStatus(tempClone.calculateValue()), false));
    }
}
