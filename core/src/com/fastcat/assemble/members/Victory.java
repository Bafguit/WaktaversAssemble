package com.fastcat.assemble.members;

import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.member.MemberVictoryAction;
import com.fastcat.assemble.interfaces.OnIncreaseMemberDamage;

public class Victory extends AbstractMember implements OnIncreaseMemberDamage {

    public Victory() {
        super("Victory");
        setValue(2, 1);
        instant = true;
    }

    @Override
    protected void onSummoned() {
        use();
    }

    @Override
    public int increaseMemberDamage() {
        return value;
    }

    @Override
    protected void useMember() {
        next(new MemberVictoryAction(this));
    }

    @Override
    public float multiplyMemberDamage() {
        return 1f;
    }
    
}
