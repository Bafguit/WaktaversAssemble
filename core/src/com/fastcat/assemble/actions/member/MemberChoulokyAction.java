package com.fastcat.assemble.actions.member;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.members.Chouloky;

public class MemberChoulokyAction extends AbstractAction {

    public Chouloky chouloky;

    public MemberChoulokyAction(Chouloky chouloky) {
        super(0.5f);
        this.chouloky = chouloky;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            chouloky.animation.setAnimation("skill");
            chouloky.animation.addAnimation("idle");
            WakTower.game.battle.turnMemberDamage.remove(chouloky);
            WakTower.game.battle.turnMemberDamage.add(chouloky);
        }
    }
}
