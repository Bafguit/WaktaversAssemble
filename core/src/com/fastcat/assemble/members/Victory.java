package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.member.MemberVictoryAction;
import com.fastcat.assemble.interfaces.OnIncreaseGlobalDamage;

public class Victory extends AbstractMember implements OnIncreaseGlobalDamage {

    public Victory() {
        super("Victory");
        setValue(2, 1);
    }

    @Override
    public void onSummon() {
        next(new MemberVictoryAction(this));
    }

    @Override
    public int increaseGlobalDamage() {
        return value;
    }

    @Override
    public float multiplyGlobalDamage() {
        return 1f;
    }
    
}
