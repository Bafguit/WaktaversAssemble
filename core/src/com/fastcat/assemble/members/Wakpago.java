package com.fastcat.assemble.members;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.member.MemberWakpagoAction;
import com.fastcat.assemble.interfaces.OnIncreaseMemberDef;

public class Wakpago extends AbstractMember implements OnIncreaseMemberDef {

    private boolean summoned = true;

    public Wakpago() {
        super("Wakpago");
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
        while(true) {
            if(!WakTower.game.battle.turnMemberDef.remove(this)) break;
        }
        use();
    }

    @Override
    public void onExit() {
        while(true) {
            if(!WakTower.game.battle.turnMemberDef.remove(this)) break;
        }
    }

    @Override
    public int increaseMemberDef() {
        return value;
    }

    @Override
    protected void useMember() {
        if(summoned) {
            next(new MemberWakpagoAction(this));
            summoned = false;
        } else {
            WakTower.game.battle.turnMemberDef.add(this);
        }
    }

    @Override
    public float multiplyMemberDef() {
        return 1f;
    }
    
}
