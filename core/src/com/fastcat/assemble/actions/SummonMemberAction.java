package com.fastcat.assemble.actions;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.screens.battle.MemberDisplay;

public class SummonMemberAction extends AbstractAction {

    private final AbstractMember member;

    public SummonMemberAction(AbstractMember m) {
        super(1f);
        this.member = m;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            MemberDisplay m = WakTower.battleScreen.hand.remove(WakTower.battleScreen.indexOfHand(member));
            WakTower.battleScreen.updateHandPosition();
            m.isCard = false;
            WakTower.battleScreen.members.put(member, m);
            member.summon();
        }
    }
}
