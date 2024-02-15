package com.fastcat.assemble.actions.member;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.members.Wakpago;

public class MemberWakpagoAction extends AbstractAction {

    public Wakpago wakpago;

    public MemberWakpagoAction(Wakpago wakpago) {
        super(0.5f);
        this.wakpago = wakpago;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            wakpago.animation.setAnimation("skill");
            wakpago.animation.addAnimation("idle");
            WakTower.game.battle.turnMemberDef.add(wakpago);
        }
    }
}
