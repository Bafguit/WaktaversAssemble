package com.fastcat.assemble.actions.member;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.members.Wakpago;

public class MemberWakpagoAction extends AbstractAction {

    public Wakpago victory;

    public MemberWakpagoAction(Wakpago victory) {
        super(2f);
        this.victory = victory;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            victory.animation.setAnimation("skill");
            victory.animation.addAnimation("idle");
            WakTower.game.battle.turnMemberDef.add(victory);
        }
    }
}
