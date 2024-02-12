package com.fastcat.assemble.actions.member;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.GainBlockAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.members.Jingburger;

public class MemberJingburgerAction extends AbstractAction {

    public Jingburger jingburger;

    public MemberJingburgerAction(Jingburger jingburger) {
        super(0.35f);
        this.jingburger = jingburger;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            jingburger.animation.setAnimation("skill");
            jingburger.animation.addAnimation("idle");
            int cnt = 0;
            for(AbstractMember m : WakTower.game.battle.members) {
                if(m == jingburger) break;
                else cnt++;
            }
            for(int i = 0; i < cnt; i++) {
                ActionHandler.set(new GainBlockAction(jingburger, jingburger.tempClone.calculateValue(), true));
            }
            //todo block effect
        }
    }
}
