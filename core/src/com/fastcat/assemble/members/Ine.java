package com.fastcat.assemble.members;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.GainBarrierAction;
import com.fastcat.assemble.actions.MemberSkillAnimationAction;

public class Ine extends AbstractMember {

    public Ine() {
        super("Ine");
        setDef(5, 2);
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
        next(new GainBarrierAction(WakTower.game.player, this.tempClone));
    }
}
