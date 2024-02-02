package com.fastcat.assemble.members;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.member.MemberVictoryAction;
import com.fastcat.assemble.interfaces.OnIncreaseMemberDamage;

public class Victory extends AbstractMember implements OnIncreaseMemberDamage {

    private boolean summoned = true;

    public Victory() {
        super("Victory");
        setValue(2, 1);
        passive = true;
    }

    @Override
    protected void onSummoned() {
        summoned = true;
        use();
    }

    @Override
    public void onSummon(AbstractMember m) {
        use();
    }

    @Override
    public void onAfterSummon(AbstractMember m) {
        use();
    }

    @Override
    public void onExit() {
        WakTower.game.battle.turnMemberDamage.remove(this);
    }

    @Override
    public int increaseMemberDamage() {
        return tempClone.calculateValue();
    }

    @Override
    protected void useMember() {
        if(summoned) {
            next(new MemberVictoryAction(this));
            summoned = false;
        } else {
            if(WakTower.game.battle.turnMemberDamage.remove(this)) WakTower.game.battle.turnMemberDamage.add(this);
        }
    }

    @Override
    public float multiplyMemberDamage() {
        return 1f;
    }
    
}
