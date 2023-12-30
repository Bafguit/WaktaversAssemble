package com.fastcat.assemble.actions.member;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.members.Ine;

public class MemberIneAction extends AbstractAction {

    public Ine ine;

    public MemberIneAction(Ine ine) {
        super(1f);
        this.ine = ine;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            ine.animation.setAnimation("skill");
            ine.animation.addAnimation("idle");
            WakTower.game.player.gainBarrier(ine.calculatedDef());
            //todo barrier effect
        }
    }
}
