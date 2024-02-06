package com.fastcat.assemble.actions.member;

import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractAction;
import com.fastcat.assemble.abstracts.AbstractMember;
import com.fastcat.assemble.actions.DamageAction;
import com.fastcat.assemble.handlers.ActionHandler;
import com.fastcat.assemble.members.Lilpa;
import com.fastcat.assemble.utils.DamageInfo;
import com.fastcat.assemble.utils.TargetType;
import com.fastcat.assemble.utils.DamageInfo.DamageType;

public class MemberLilpaAction extends AbstractAction {

    public Lilpa lilpa;

    public MemberLilpaAction(Lilpa lilpa) {
        super(1f);
        this.lilpa = lilpa;
    }

    @Override
    protected void updateAction() {
        if(duration == baseDuration) {
            lilpa.animation.setAnimation("skill");
            lilpa.animation.addAnimation("idle");
            int cnt = 0;
            for(AbstractMember m : WakTower.game.battle.members) {
                if(m == lilpa) break;
                else cnt++;
            }
            for(int i = 0; i < cnt; i++) {
                ActionHandler.set(new DamageAction(new DamageInfo(lilpa.tempClone, DamageType.NORMAL), TargetType.RANDOM, true));
            }
        }
    }
}
