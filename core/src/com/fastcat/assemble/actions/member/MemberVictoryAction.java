package com.fastcat.assemble.actions.member;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.members.Victory;

public class MemberVictoryAction extends AbstractAction {

    public Victory victory;

    public MemberVictoryAction(Victory victory) {
        super(0.5f);
        this.victory = victory;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            victory.animation.setAnimation("skill");
            victory.animation.addAnimation("idle");
            WakTower.game.battle.turnMemberDamage.add(victory);
        }
    }
}
