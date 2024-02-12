package com.fastcat.assemble.actions.member;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.actions.DamageAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.members.Seyong;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.DamageInfo.DamageType;
import com.fastcat.assemble.utils.TargetType;

public class MemberSeyongAction extends AbstractAction {

    public Seyong seyong;

    public MemberSeyongAction(Seyong seyong) {
        super(0.2f);
        this.seyong = seyong;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            seyong.animation.setAnimation("skill");
            seyong.animation.addAnimation("idle");
            int cnt = WakTower.game.battle.hand.size();
            for(int i = 0; i < cnt; i++) {
                ActionHandler.set(new DamageAction(new DamageInfo(seyong.tempClone.calculatedAtk(), seyong, DamageType.NORMAL), TargetType.RANDOM, true));
            }
        }
    }
}
